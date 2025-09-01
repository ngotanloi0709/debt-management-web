# Makefile for running the backend (Spring Boot)
.PHONY: help install run format format-check

# Default target - show help
.DEFAULT_GOAL := help

help: ## Show this help message
	@echo "Available commands:"
	@echo "  install      - Download dependencies and build the project"
	@echo "  run          - Start the Spring Boot application"
	@echo "  format       - Format all Java files using Google Java Style"
	@echo "  format-check - Check if all Java files are properly formatted"
	@echo "  help         - Show this help message"

install: ## Download dependencies and build the project
	cd system && mvnw.cmd clean install && cd ..

run: ## Start the Spring Boot application
	cd system && mvnw.cmd spring-boot:run && cd ..

format: ## Format all Java files using Google Java Style
	cd system && mvnw.cmd spotless:apply && cd ..

format-check: ## Check if all Java files are properly formatted
	cd system && mvnw.cmd spotless:check && cd ..
