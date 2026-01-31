@echo off
echo Compilando proyecto...
javac -cp "lib/*" -d bin src/hotel/excepciones/*.java src/hotel/modelo/*.java src/hotel/dao/*.java src/hotel/servicio/*.java src/hotel/Main.java
echo Compilacion completada
pause
