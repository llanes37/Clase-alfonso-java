# 📁 Instrucciones para el Driver JDBC de SQL Server

## ¿Qué es el driver JDBC?

El driver JDBC (Java Database Connectivity) es una librería que permite a Java conectarse con SQL Server.

## 📥 Descarga del Driver

1. Ve a la página oficial de Microsoft:
   https://docs.microsoft.com/es-es/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server

2. Descarga la versión más reciente (por ejemplo: `mssql-jdbc-12.4.2.jre11.jar`)

3. También puedes descargarlo desde Maven Repository:
   https://mvnrepository.com/artifact/com.microsoft.sqlserver/mssql-jdbc

## 📂 Instalación

1. Crea la carpeta `lib` en el directorio del proyecto si no existe
2. Copia el archivo `.jar` descargado a la carpeta `lib`
3. El archivo debería llamarse algo como:
   - `mssql-jdbc-12.4.2.jre11.jar`
   - `mssql-jdbc-11.2.0.jre11.jar`

## ✅ Verificación

Después de colocar el archivo, la estructura debería ser:

```
Proyecto final/
├── lib/
│   └── mssql-jdbc-12.4.2.jre11.jar  ← El driver aquí
├── src/
├── compilar.bat
├── ejecutar.bat
└── ...
```

## ⚠️ Importante

- Asegúrate de descargar la versión compatible con tu versión de Java
- `jre8` para Java 8
- `jre11` para Java 11+
- `jre17` para Java 17+

Puedes verificar tu versión de Java con:
```
java -version
```
