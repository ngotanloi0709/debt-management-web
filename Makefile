# Makefile for Debt Management Web Application
.PHONY: help backend stop backend-logs db-logs clean build clean-ports

# Default target - show help
.DEFAULT_GOAL := help

help: ## Show available commands
	@echo "=== MAIN COMMANDS ==="
	@echo "  dev          - Start all services in development mode"
	@echo "  frontend     - Start frontend development server"
	@echo "  backend      - Start backend and database services"
	@echo "  prod         - Start all services in production mode"
	@echo ""
	@echo "=== LOG COMMANDS ==="
	@echo "  frontend-logs - View frontend logs"
	@echo "  backend-logs  - View backend Java logs"
	@echo "  db-logs       - View database logs"
	@echo ""
	@echo "=== FORMAT COMMANDS ==="
	@echo "  format-frontend - Format frontend code (Vue.js/TypeScript)"
	@echo "  format-backend  - Format backend code (Java)"
	@echo "  format-all      - Format both frontend and backend code"
	@echo ""
	@echo "=== UTILITY COMMANDS ==="
	@echo "  stop          - Stop all services"
	@echo "  clean-ports   - Clean occupied ports (3000, 5173, 8080, 3306)"
	@echo "  clean         - Clean everything and restart fresh"
	@echo "  build         - Force rebuild (ignores cache)"
	@echo "  help          - Show this help message"

# === MAIN COMMANDS ===
dev: ## Start all services in development mode
	$(MAKE) clean-ports
	- timeout /t 2 >nul 2>&1
	docker-compose build
	docker-compose up frontend-dev backend-dev database

prod: ## Start all services in production mode
	$(MAKE) clean-ports
	- timeout /t 2 >nul 2>&1
	docker-compose build
	docker-compose up frontend-prod backend-prod database

frontend: ## Start frontend development server
	$(MAKE) clean-ports-frontend
	- timeout /t 2 >nul 2>&1
	docker-compose build frontend-dev
	docker-compose up frontend-dev

backend: ## Start backend and database services
	$(MAKE) clean-ports-backend
	- timeout /t 2 >nul 2>&1
	docker-compose build backend-dev
	docker-compose up backend-dev database

build: ## Force rebuild (when code changes)
	docker-compose build --no-cache

# === FORMAT COMMANDS ===
format-frontend: ## Format frontend code (Vue.js/TypeScript)
	cd client && npm run format
	cd client && npm run lint

format-backend: ## Format backend code (Java)
	cd server && mvnw.cmd spotless:apply

format-all: ## Format both frontend and backend code
	$(MAKE) format-frontend
	$(MAKE) format-backend

# === LOG COMMANDS ===
frontend-logs: ## View frontend logs
	docker-compose logs -f frontend-dev

backend-logs: ## View backend Java logs
	docker-compose logs -f backend-dev

db-logs: ## View database logs
	docker-compose logs -f database

# === UTILITY COMMANDS ===
stop: ## Stop all services
	docker-compose down

# Internal commands (not shown in help)
clean-ports-frontend:
	- docker-compose stop frontend-dev frontend-prod 2>nul
	- docker-compose rm -f frontend-dev frontend-prod 2>nul
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :3000 2^>nul') do taskkill /f /pid %%a 2>nul
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :5173 2^>nul') do taskkill /f /pid %%a 2>nul

clean-ports-backend:
	- docker-compose stop backend-dev backend-prod 2>nul
	- docker-compose rm -f backend-dev backend-prod 2>nul
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080 2^>nul') do taskkill /f /pid %%a 2>nul
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :3306 2^>nul') do taskkill /f /pid %%a 2>nul

clean-ports: ## Clean occupied ports (3000, 5173, 8080, 3306)
	- docker-compose down --remove-orphans 2>nul
	- docker stop $$(docker ps -aq) 2>nul
	- docker rm $$(docker ps -aq) 2>nul
	- timeout /t 3 >nul 2>&1
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :3000 2^>nul') do taskkill /f /pid %%a 2>nul
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :5173 2^>nul') do taskkill /f /pid %%a 2>nul
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8080 2^>nul') do taskkill /f /pid %%a 2>nul
	- for /f "tokens=5" %%a in ('netstat -aon ^| findstr :3306 2^>nul') do taskkill /f /pid %%a 2>nul

clean: ## Clean everything and restart fresh
	docker-compose down -v --remove-orphans
	- docker rmi debt-management-web-frontend-dev debt-management-web-frontend-prod debt-management-web-backend-dev debt-management-web-backend-prod 2>nul
	- docker volume rm debt-management-web_mysql_data debt-management-web_maven_cache 2>nul