# Makefile for Debt Management Web Application
.PHONY: help backend stop backend-logs db-logs clean build clean-ports

# Default target - show help
.DEFAULT_GOAL := help

help: ## Show available commands
	@echo "=== MAIN COMMAND ==="
	@echo "  backend      - Start java and database services"
	@echo ""
	@echo "=== LOG COMMANDS ==="
	@echo "  backend-logs - View backend Java logs"
	@echo "  db-logs      - View database logs"
	@echo ""
	@echo "=== UTILITY COMMANDS ==="
	@echo "  stop         - Stop all services"
	@echo "  clean-ports  - Clean occupied ports (8080, 3306)"
	@echo "  clean        - Clean everything and restart fresh"
	@echo "  build        - Force rebuild (ignores cache)"
	@echo "  help         - Show this help message"

# === MAIN COMMAND ===
backend: ## Start all services and return to terminal
	$(MAKE) clean-ports
	- timeout /t 2 >nul 2>&1
	docker-compose build
	docker-compose up backend-dev

build: ## Force rebuild (when code changes)
	docker-compose build --no-cache

# === LOG COMMANDS ===
backend-logs: ## View backend Java logs
	docker-compose logs -f backend-dev

db-logs: ## View database logs
	docker-compose logs -f database

# === UTILITY COMMANDS ===
stop: ## Stop all services
	docker-compose down

clean-ports: ## Clean occupied ports (8080, 3306)
	- docker-compose down --remove-orphans 2>nul
	- docker stop $$(docker ps -aq) 2>nul
	- docker rm $$(docker ps -aq) 2>nul
	- timeout /t 3 >nul 2>&1
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080 2^>nul') do taskkill /f /pid %%a 2>nul
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :3306 2^>nul') do taskkill /f /pid %%a 2>nul

clean: ## Clean everything and restart fresh
	docker-compose down -v --remove-orphans
	docker volume prune -f
	docker system prune -f