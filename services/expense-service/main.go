package main

import (
	"context"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"strconv"
	"syscall"
	"time"

	"financier/expense-service/handler"
	"financier/expense-service/model"
	"financier/expense-service/repository"
	"financier/expense-service/util"

	"github.com/gin-gonic/gin"
	"github.com/hudl/fargo"
	"github.com/spf13/viper"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func main() {
	// 1. Load Configuration
	viper.SetConfigFile(".env")
	viper.AutomaticEnv()
	if err := viper.ReadInConfig(); err != nil {
		log.Println("Warning: .env file not found")
	}

	port := viper.GetString("PORT")
	if port == "" {
		port = "8081"
	}
	postgresDSN := viper.GetString("POSTGRES_DSN")
	eurekaURL := viper.GetString("EUREKA_URL")

	// 2. Connect to PostgreSQL
	db, err := gorm.Open(postgres.Open(postgresDSN), &gorm.Config{})
	if err != nil {
		log.Fatalf("Failed to connect to PostgreSQL: %v", err)
	}
	log.Println("Successfully connected to PostgreSQL.")

	// Auto-migrate schema
	if err := db.AutoMigrate(&model.Expense{}); err != nil {
		log.Fatalf("Failed to migrate database schema: %v", err)
	}

	// 3. Register with Eureka
	if eurekaURL != "" {
		go registerWithEureka(port, eurekaURL)
	} else {
		log.Println("EUREKA_URL not set, skipping service registration.")
	}

	// 4. Set up Repository and Handler
	expenseRepo := repository.NewPostgresExpenseRepository(db)
	expenseHandler := handler.NewExpenseHandler(expenseRepo)

	// 5. Set up Gin Router
	router := gin.Default()
	
	// Middleware to extract User ID from header
	router.Use(util.AuthMiddleware())

	api := router.Group("/api/expenses")
	{
		api.POST("", expenseHandler.CreateExpense)
		api.GET("", expenseHandler.GetUserExpenses)
		api.GET("/summary", expenseHandler.GetSummary)
		api.GET("/recent", expenseHandler.GetRecent)
		api.GET("/:id", expenseHandler.GetExpenseByID)
		api.PUT("/:id", expenseHandler.UpdateExpense)
		api.DELETE("/:id", expenseHandler.DeleteExpense)
	}

	// 6. Start Server Gracefully
	srv := &http.Server{
		Addr:    ":" + port,
		Handler: router,
	}

	go func() {
		if err := srv.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatalf("listen: %s\n", err)
		}
	}()

	// Wait for interrupt signal to gracefully shut down the server
	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit
	log.Println("Shutting down server...")

	ctxShutdown, cancelShutdown := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancelShutdown()

	if err := srv.Shutdown(ctxShutdown); err != nil {
		log.Fatal("Server forced to shutdown:", err)
	}

	log.Println("Server exiting")
}

func registerWithEureka(port, eurekaURL string) {
	instanceName := "expense-service"
	hostname, _ := os.Hostname()

	iPort, _ := strconv.Atoi(port)
	eurekaConn := fargo.NewConn(eurekaURL)
	instance := &fargo.Instance{
		HostName:         hostname,
		Port:             iPort, // The actual port number from environment
		App:              instanceName,
		IPAddr:           util.GetOutboundIP().String(),
		VipAddress:       instanceName,
		SecureVipAddress: instanceName,
		DataCenterInfo:   fargo.DataCenterInfo{Name: fargo.MyOwn},
		Status:           fargo.UP,
		HealthCheckUrl:   fmt.Sprintf("http://%s:%s/actuator/health", util.GetOutboundIP().String(), port),
	}

	// Register and heartbeat
	ticker := time.NewTicker(30 * time.Second)
	
	// Initial registration
	err := eurekaConn.RegisterInstance(instance)
	if err != nil {
		log.Printf("Failed to register with Eureka: %s. Retrying...", err.Error())
	} else {
		log.Println("Successfully registered with Eureka.")
	}

	for range ticker.C {
		err := eurekaConn.HeartBeatInstance(instance)
		if err != nil {
			log.Printf("Failed to send heartbeat to Eureka: %s. Re-registering...", err.Error())
			// Attempt to re-register on heartbeat failure
			err_reg := eurekaConn.RegisterInstance(instance)
			if err_reg != nil {
				log.Printf("Failed to re-register: %s", err_reg.Error())
			}
		}
	}
}
