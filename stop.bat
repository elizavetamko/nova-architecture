@echo off
title Nova Architecture - Stop Servers

echo ============================================
echo    NOVA ARCHITECTURE - STOPPING SERVERS
echo ============================================
echo.

echo Stopping Java processes (Backend)...
taskkill /F /IM java.exe /T > nul 2>&1
if errorlevel 1 (
    echo No Java processes found.
) else (
    echo Java processes stopped.
)

echo.
echo Stopping Python processes (Frontend)...
taskkill /F /FI "WINDOWTITLE eq Nova Frontend*" > nul 2>&1
if errorlevel 1 (
    echo No frontend server found.
) else (
    echo Frontend server stopped.
)

echo.
echo ============================================
echo    ALL SERVERS STOPPED
echo ============================================
echo.
pause
