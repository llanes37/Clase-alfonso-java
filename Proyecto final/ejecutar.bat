@echo off
REM ============================================================
REM  SCRIPT DE EJECUCIÓN - Sistema de Gestión de Hotel
REM ============================================================
REM  Este script ejecuta la aplicación compilada.
REM  
REM  REQUISITOS:
REM    - Haber ejecutado compilar.bat primero
REM    - Driver JDBC de SQL Server en la carpeta lib/
REM    - Base de datos HotelDB creada en SQL Server
REM
REM  USO:
REM    Doble clic en ejecutar.bat o ejecutar desde consola
REM ============================================================

echo.
echo ============================================================
echo   EJECUTANDO - Sistema de Gestion de Hotel
echo ============================================================
echo.

REM Verificamos que existe la carpeta bin
if not exist "bin" (
    echo ERROR: No se encontro la carpeta bin/
    echo Ejecute primero compilar.bat
    pause
    exit /b 1
)

REM Ejecutamos la aplicación
REM -cp "bin;lib/*" : Classpath con las clases compiladas y los JARs
REM hotel.Main      : Clase principal (paquete.Clase)

java -cp "bin;lib/*" hotel.Main

echo.
pause
