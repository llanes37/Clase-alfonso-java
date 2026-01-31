# 🏨 GUÍA DIDÁCTICA: Sistema de Gestión de Reservas de Hotel

## 📋 Índice
1. [Introducción al Proyecto](#1-introducción-al-proyecto)
2. [Estructura del Proyecto](#2-estructura-del-proyecto)
3. [Conceptos Clave Aplicados](#3-conceptos-clave-aplicados)
4. [Paso a Paso: Construcción del Sistema](#4-paso-a-paso-construcción-del-sistema)
5. [Configuración de la Base de Datos](#5-configuración-de-la-base-de-datos)
6. [Compilación y Ejecución](#6-compilación-y-ejecución)

---

## 1. Introducción al Proyecto

### 🎯 ¿Qué vamos a construir?
Un sistema completo de gestión de reservas de hotel que permite:
- Gestionar habitaciones (listar disponibles)
- Registrar clientes
- Crear y cancelar reservas
- Consultar todas las reservas

### 🧩 Tecnologías utilizadas
- **Java**: Lenguaje de programación principal
- **JDBC**: Para conectar Java con la base de datos
- **SQL Server**: Base de datos relacional

---

## 2. Estructura del Proyecto

```
Proyecto final/
│
├── 📘_GUIA_PROYECTO_HOTEL.md    ← Esta guía
├── script_base_datos.sql         ← Script para crear la BD
├── compilar.bat                  ← Script para compilar
├── ejecutar.bat                  ← Script para ejecutar
│
└── src/
    └── hotel/
        │
        ├── modelo/               ← 🟦 CAPA MODELO (Entidades)
        │   ├── Persona.java           → Clase abstracta base
        │   ├── Cliente.java           → Hereda de Persona
        │   ├── Reservable.java        → Interfaz
        │   ├── Habitacion.java        → Implementa Reservable
        │   └── Reserva.java           → Representa una reserva
        │
        ├── excepciones/          ← 🟥 EXCEPCIONES PERSONALIZADAS
        │   └── ReservaInvalidaException.java
        │
        ├── dao/                  ← 🟨 CAPA DE ACCESO A DATOS
        │   ├── ConexionBD.java        → Conexión JDBC
        │   ├── ClienteDAO.java        → CRUD de clientes
        │   ├── HabitacionDAO.java     → CRUD de habitaciones
        │   └── ReservaDAO.java        → CRUD de reservas
        │
        ├── servicio/             ← 🟩 CAPA DE SERVICIO (Lógica)
        │   └── ServicioHotel.java     → Menú y lógica principal
        │
        └── Main.java             ← 🚀 PUNTO DE ENTRADA
```

---

## 3. Conceptos Clave Aplicados

### 🔵 Programación Orientada a Objetos (POO)

| Concepto | Dónde se aplica | Explicación |
|----------|-----------------|-------------|
| **Clase Abstracta** | `Persona` | No se puede instanciar directamente, define estructura base |
| **Herencia** | `Cliente extends Persona` | Cliente hereda atributos y métodos de Persona |
| **Interfaz** | `Reservable` | Define un contrato que deben cumplir las clases |
| **Implementación** | `Habitacion implements Reservable` | Habitacion cumple el contrato de Reservable |
| **Encapsulamiento** | Todos los atributos `private` | Acceso controlado mediante getters/setters |
| **Polimorfismo** | Método `mostrarInfo()` | Mismo método, diferente comportamiento en cada clase |

### 🟡 Patrón DAO (Data Access Object)

```
┌─────────────────┐      ┌─────────────┐      ┌──────────────┐
│  ServicioHotel  │ ───▶ │    DAO      │ ───▶ │  Base Datos  │
│   (Lógica)      │      │  (Acceso)   │      │  (SQL Server)│
└─────────────────┘      └─────────────┘      └──────────────┘
```

**¿Por qué usar DAO?**
- Separa la lógica de negocio del acceso a datos
- Facilita el mantenimiento y las pruebas
- Si cambiamos de base de datos, solo modificamos el DAO

### 🔴 Excepciones Personalizadas

```java
// En lugar de usar Exception genérica:
throw new Exception("Error");

// Usamos nuestra propia excepción:
throw new ReservaInvalidaException("La habitación no está disponible");
```

**Ventajas:**
- Mensajes de error más claros y específicos
- Podemos capturar solo los errores que nos interesan
- Código más legible y profesional

---

## 4. Paso a Paso: Construcción del Sistema

### 📦 PASO 1: Crear la Capa Modelo

#### 1.1 Clase Abstracta `Persona`
```
¿Qué es una clase abstracta?
- Es una clase que NO se puede instanciar directamente
- Sirve como "plantilla" para otras clases
- Puede tener métodos abstractos (sin implementación)
```

#### 1.2 Clase `Cliente`
```
¿Qué es la herencia?
- Cliente "hereda" todo de Persona (nombre, telefono)
- Añade sus propios atributos (email)
- Debe implementar los métodos abstractos
```

#### 1.3 Interfaz `Reservable`
```
¿Qué es una interfaz?
- Define un "contrato" que las clases deben cumplir
- Solo declara métodos, no los implementa
- Una clase puede implementar múltiples interfaces
```

#### 1.4 Clase `Habitacion`
```
¿Qué significa "implements"?
- La clase se compromete a implementar todos los métodos de la interfaz
- Si no los implementa, dará error de compilación
```

### 📦 PASO 2: Crear las Excepciones

#### 2.1 `ReservaInvalidaException`
```
¿Cuándo lanzamos esta excepción?
- Cuando intentamos reservar una habitación ocupada
- Cuando las fechas de la reserva son inválidas
- Cuando hay cualquier error relacionado con reservas
```

### 📦 PASO 3: Crear la Capa DAO

#### 3.1 `ConexionBD`
```
¿Qué es JDBC?
- Java Database Connectivity
- API de Java para conectar con bases de datos
- Nos permite ejecutar consultas SQL desde Java
```

#### 3.2 Los DAOs (ClienteDAO, HabitacionDAO, ReservaDAO)
```
Operaciones CRUD:
- C = Create (INSERT)
- R = Read (SELECT)
- U = Update (UPDATE)
- D = Delete (DELETE)
```

### 📦 PASO 4: Crear la Capa de Servicio

#### 4.1 `ServicioHotel`
```
¿Qué hace esta clase?
- Contiene toda la lógica de negocio
- Muestra el menú al usuario
- Coordina las operaciones entre el usuario y los DAOs
```

---

## 5. Configuración de la Base de Datos

### 📝 Paso 1: Crear la base de datos en SQL Server

Ejecuta el script `script_base_datos.sql` en SQL Server Management Studio.

### 📝 Paso 2: Configurar la conexión

Edita el archivo `ConexionBD.java` con tus datos:

```java
// Cambia estos valores según tu configuración:
private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HotelDB";
private static final String USUARIO = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";
```

### 📝 Paso 3: Descargar el driver JDBC

1. Descarga el driver de Microsoft JDBC desde:
   https://docs.microsoft.com/es-es/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server

2. Coloca el archivo `.jar` en la carpeta `lib/`

---

## 6. Compilación y Ejecución

### 🔧 Opción 1: Usando los scripts .bat

```batch
:: Compilar el proyecto
compilar.bat

:: Ejecutar el proyecto
ejecutar.bat
```

### 🔧 Opción 2: Comandos manuales

```batch
:: Compilar
javac -d bin -cp "lib/*" src/hotel/*.java src/hotel/**/*.java

:: Ejecutar
java -cp "bin;lib/*" hotel.Main
```

---

## 📊 Diagrama de Clases (UML Simplificado)

```
                    ┌─────────────────┐
                    │   <<abstract>>  │
                    │     Persona     │
                    ├─────────────────┤
                    │ - nombre        │
                    │ - telefono      │
                    ├─────────────────┤
                    │ + mostrarInfo() │
                    └────────┬────────┘
                             │ extends
                             ▼
                    ┌─────────────────┐
                    │     Cliente     │
                    ├─────────────────┤
                    │ - email         │
                    ├─────────────────┤
                    │ + mostrarInfo() │
                    └─────────────────┘

    ┌─────────────────┐              ┌─────────────────┐
    │  <<interface>>  │              │    Habitacion   │
    │   Reservable    │◄─implements──┤                 │
    ├─────────────────┤              ├─────────────────┤
    │ + reservar()    │              │ - id            │
    │ + cancelar()    │              │ - tipo          │
    └─────────────────┘              │ - precio        │
                                     │ - disponible    │
                                     └─────────────────┘

                    ┌─────────────────┐
                    │     Reserva     │
                    ├─────────────────┤
                    │ - id            │
                    │ - cliente       │──────▶ Cliente
                    │ - habitacion    │──────▶ Habitacion
                    │ - fechaEntrada  │
                    │ - fechaSalida   │
                    │ - importeTotal  │
                    └─────────────────┘
```

---

## ✅ Lista de Verificación

Antes de entregar, asegúrate de que:

- [ ] El proyecto compila sin errores
- [ ] La base de datos está creada con las tablas
- [ ] Puedes listar habitaciones disponibles
- [ ] Puedes registrar un nuevo cliente
- [ ] Puedes crear una reserva
- [ ] Puedes cancelar una reserva
- [ ] Puedes ver todas las reservas

---

## 🎓 Consejos para el Examen

1. **Entiende la herencia**: `Cliente extends Persona` significa que Cliente ES UNA Persona
2. **Entiende las interfaces**: `Habitacion implements Reservable` significa que Habitacion PUEDE SER reservada
3. **El patrón DAO**: Separa el acceso a datos de la lógica de negocio
4. **Excepciones personalizadas**: Nos permiten manejar errores específicos de nuestro dominio

---

> 📌 **Nota**: Este proyecto aplica los conceptos fundamentales de POO y acceso a datos con JDBC. Asegúrate de entender cada parte antes de la entrega.
