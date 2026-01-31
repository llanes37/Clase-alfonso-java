# 🏨 Sistema de Gestión de Reservas de Hotel

## 📋 Descripción del Proyecto

Este proyecto implementa un sistema completo de gestión de reservas para un hotel utilizando Java y SQL Server. El sistema permite:

- ✅ Listar habitaciones disponibles
- ✅ Registrar nuevos clientes
- ✅ Crear reservas
- ✅ Cancelar reservas
- ✅ Ver todas las reservas

## 🎓 Conceptos Aplicados

| Concepto | Implementación |
|----------|----------------|
| **Clase Abstracta** | `Persona` - clase base no instanciable |
| **Herencia** | `Cliente extends Persona` |
| **Interfaz** | `Reservable` - contrato de métodos |
| **Implementación** | `Habitacion implements Reservable` |
| **Excepciones personalizadas** | `ReservaInvalidaException` |
| **Patrón DAO** | `ClienteDAO`, `HabitacionDAO`, `ReservaDAO` |
| **JDBC** | Conexión a SQL Server |

## 📁 Estructura del Proyecto

```
Proyecto final/
│
├── 📘_GUIA_PROYECTO_HOTEL.md    ← Guía didáctica completa
├── script_base_datos.sql         ← Script SQL para crear la BD
├── compilar.bat                  ← Script para compilar
├── ejecutar.bat                  ← Script para ejecutar
├── README.md                     ← Este archivo
│
├── lib/                          ← Driver JDBC (mssql-jdbc-XX.jar)
│   └── LEER_INSTRUCCIONES.md
│
├── bin/                          ← Archivos compilados (.class)
│
└── src/
    └── hotel/
        ├── Main.java                    ← Punto de entrada
        │
        ├── modelo/                      ← Clases del modelo
        │   ├── Persona.java             (abstracta)
        │   ├── Cliente.java             (hereda de Persona)
        │   ├── Reservable.java          (interfaz)
        │   ├── Habitacion.java          (implementa Reservable)
        │   └── Reserva.java
        │
        ├── excepciones/
        │   └── ReservaInvalidaException.java
        │
        ├── dao/                         ← Acceso a datos
        │   ├── ConexionBD.java
        │   ├── ClienteDAO.java
        │   ├── HabitacionDAO.java
        │   └── ReservaDAO.java
        │
        └── servicio/                    ← Lógica de negocio
            └── ServicioHotel.java
```

## 🚀 Instrucciones de Instalación

### Paso 1: Configurar la Base de Datos

1. Abre **SQL Server Management Studio**
2. Ejecuta el archivo `script_base_datos.sql`
3. Verifica que se creó la base de datos `HotelDB` con las tablas:
   - `Habitaciones`
   - `Clientes`
   - `Reservas`

### Paso 2: Descargar el Driver JDBC

1. Descarga el driver desde: https://docs.microsoft.com/es-es/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server
2. Copia el archivo `.jar` a la carpeta `lib/`

### Paso 3: Configurar la Conexión

1. Abre el archivo `src/hotel/dao/ConexionBD.java`
2. Modifica las constantes con tus datos:
   ```java
   private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HotelDB;...";
   private static final String USUARIO = "tu_usuario";
   private static final String PASSWORD = "tu_contraseña";
   ```

### Paso 4: Compilar y Ejecutar

```batch
# Compilar
compilar.bat

# Ejecutar
ejecutar.bat
```

O manualmente:
```batch
# Compilar
javac -d bin -cp "lib/*" -encoding UTF-8 src/hotel/modelo/*.java src/hotel/excepciones/*.java src/hotel/dao/*.java src/hotel/servicio/*.java src/hotel/Main.java

# Ejecutar
java -cp "bin;lib/*" hotel.Main
```

## 🖥️ Uso del Sistema

Al ejecutar, verás el siguiente menú:

```
╔═══════════════════════════════════════╗
║        --- GESTIÓN DE HOTEL ---       ║
╠═══════════════════════════════════════╣
║  1. 🛏️  Listar habitaciones disponibles║
║  2. 👤 Registrar cliente               ║
║  3. 📅 Crear reserva                   ║
║  4. ❌ Cancelar reserva                ║
║  5. 📋 Mostrar todas las reservas      ║
║  0. 🚪 Salir                           ║
╚═══════════════════════════════════════╝
```

## 📚 Documentación

Consulta el archivo `📘_GUIA_PROYECTO_HOTEL.md` para:
- Explicación detallada de cada clase
- Conceptos de POO aplicados
- Diagrama UML del proyecto
- Guía paso a paso

## 🛠️ Requisitos

- Java JDK 11 o superior
- SQL Server 2017 o superior
- Driver JDBC de SQL Server

## 📝 Autor

Proyecto Final - Módulo de Programación
