# Stage 1: Build the Go binary
FROM golang:1.23-alpine AS builder

WORKDIR /app

# Copy go.mod and go.sum files and download dependencies
COPY go.mod go.sum ./
RUN go mod download

# Copy the source code
COPY . .

# Build the application, disabling CGO for a static binary
RUN CGO_ENABLED=0 GOOS=linux go build -a -installsuffix cgo -o expense-service .

# Stage 2: Create a minimal, final image
FROM alpine:latest

RUN apk --no-cache add ca-certificates

WORKDIR /root/

# Copy the built binary from the builder stage
COPY --from=builder /app/expense-service .

# Expose port 8081
EXPOSE 8081

# Command to run the executable
CMD ["./expense-service"]
