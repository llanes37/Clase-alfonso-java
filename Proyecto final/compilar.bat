@echo off
REM ============================================================
REM  SCRIPT DE COMPILACIÓN - Sistema de Gestión de Hotel
REM ============================================================
REM  Este script compila todos los archivos Java del proyecto.
REM  
REM  REQUISITOS:
REM    - Java JDK instalado (javac disponible en PATH)
REM    - Driver JDBC de SQL Server en la carpeta lib/
REM
REM  USO:
REM    Doble clic en compilar.bat o ejecutar desde consola
REM ============================================================

echo.
echo ============================================================
echo   COMPILANDO PROYECTO - Sistema de Gestion de Hotel
echo ============================================================
echo.

REM Creamos la carpeta bin si no existe
if not exist "bin" mkdir bin

REM Compilamos todos los archivos Java
REM -d bin         : Directorio de salida para los .class
REM -cp "lib/*"    : Classpath con los JARs del driver
REM -encoding UTF-8: Para soportar caracteres especiales

echo Compilando archivos Java...
echo.

javac -d bin -cp "lib/*" -encoding UTF-8 ^
    src\hotel\modelo\*.java ^
    src\hotel\excepciones\*.java ^
    src\hotel\dao\*.java ^
    src\hotel\servicio\*.java ^
    src\hotel\Main.java

REM Verificamos si la compilación fue exitosa
if %ERRORLEVEL% == 0 (
    echo.
    echo ============================================================
    echo   COMPILACION EXITOSA
    echo ============================================================
    echo   Los archivos .class se encuentran en la carpeta bin/
    echo   Para ejecutar, use: ejecutar.bat
    echo ============================================================
) else (
    echo.
    echo ============================================================
    echo   ERROR DE COMPILACION
    echo ============================================================
    echo   Revise los errores mostrados arriba.
    echo   Posibles causas:
    echo     - Error de sintaxis en el codigo
    echo     - Falta el driver JDBC en lib/
    echo     - Java JDK no esta instalado
    echo ============================================================
)

echo.
pause
