package util

import (
	"log"
	"net"
	"net/http"

	"github.com/gin-gonic/gin"
)

// AuthMiddleware extracts the user ID from a request header.
// The API Gateway is responsible for validating the JWT and adding this header.
func AuthMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		userID := c.GetHeader("X-User-Id")
		if userID == "" {
			c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "X-User-Id header is missing"})
			return
		}
		// Set the userID in the context for downstream handlers to use.
		c.Set("userID", userID)
		c.Next()
	}
}

// GetOutboundIP gets the preferred outbound ip of this machine
func GetOutboundIP() net.IP {
	conn, err := net.Dial("udp", "8.8.8.8:80")
	if err != nil {
		log.Fatal(err)
	}
	defer conn.Close()

	localAddr := conn.LocalAddr().(*net.UDPAddr)

	return localAddr.IP
}
