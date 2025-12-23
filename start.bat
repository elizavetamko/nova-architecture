@echo off
chcp 65001 > nul 2>&1
title Nova Architecture - Startup

echo ============================================
echo    NOVA ARCHITECTURE - PROJECT STARTUP
echo ============================================
echo.

REM Check Java
echo [1/4] Checking Java...
java -version > nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java not found!
    echo Please install JDK 17 or higher from https://adoptium.net/
    echo.
    pause
    exit /b 1
)
echo [OK] Java found

REM Check Maven
echo [2/4] Checking Maven...
mvn -version > nul 2>&1
if errorlevel 1 (
    echo [ERROR] Maven not found!
    echo Please install Maven from https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)
echo [OK] Maven found

REM Check Python
echo [3/4] Checking Python...
python --version > nul 2>&1
if errorlevel 1 (
    echo [WARNING] Python not found. Frontend server will not start automatically.
    echo You can install Python from https://www.python.org/downloads/
    echo Or use any other HTTP server for frontend folder.
    set PYTHON_FOUND=0
) else (
    echo [OK] Python found
    set PYTHON_FOUND=1
)

echo.
echo ============================================
echo    STARTING SERVERS
echo ============================================
echo.

REM Get current directory
set PROJECT_DIR=%~dp0
set BACKEND_DIR=%PROJECT_DIR%backend
set FRONTEND_DIR=%PROJECT_DIR%frontend

REM Start Backend
echo [4/4] Starting Backend server...
echo Backend directory: %BACKEND_DIR%
echo.

cd /d "%BACKEND_DIR%"

REM Start backend in new window
start "Nova Backend - Spring Boot" cmd /k "title Nova Backend - Spring Boot && echo Starting Spring Boot... && echo. && mvn spring-boot:run"

echo Backend starting in new window...
echo Waiting 5 seconds before starting frontend...
timeout /t 5 /nobreak > nul

REM Start Frontend
if "%PYTHON_FOUND%"=="1" (
    echo.
    echo Starting Frontend server...
    echo Frontend directory: %FRONTEND_DIR%
    echo.
    cd /d "%FRONTEND_DIR%"
    start "Nova Frontend - HTTP Server" cmd /k "title Nova Frontend - HTTP Server && echo Starting HTTP server on port 8000... && echo. && python -m http.server 8000"
    echo Frontend starting in new window...
)

echo.
echo ============================================
echo    STARTUP COMPLETE
echo ============================================
echo.
echo Backend:  http://localhost:8080
echo Frontend: http://localhost:8000
echo.
echo Admin Panel: http://localhost:8000/admin.html
echo Login Page:  http://localhost:8000/login.html
echo.
echo Default credentials:
echo   Username: admin
echo   Password: admin123
echo.
echo H2 Database Console: http://localhost:8080/h2-console
echo   JDBC URL: jdbc:h2:file:./data/novadb
echo   Username: admin
echo   Password: admin
echo.
echo ============================================
echo.
echo Press any key to open website in browser...
pause > nul

REM Open browser
start "" "http://localhost:8000"

echo.
echo To stop servers, close the backend and frontend windows.
echo.
pause
