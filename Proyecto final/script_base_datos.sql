-- ============================================================
-- 🏨 SCRIPT DE CREACIÓN DE BASE DE DATOS - HOTEL
-- ============================================================
-- Este script crea la base de datos y las tablas necesarias
-- para el sistema de gestión de reservas de hotel.
--
-- 📌 INSTRUCCIONES:
-- 1. Abre SQL Server Management Studio
-- 2. Conecta a tu servidor local
-- 3. Ejecuta este script completo (F5 o Ctrl+Shift+E)
-- ============================================================

-- ------------------------------------------------------------
-- PASO 1: Crear la base de datos
-- ------------------------------------------------------------
-- Primero verificamos si existe y la eliminamos para empezar limpio
-- (¡CUIDADO! Esto borra todos los datos existentes)

IF EXISTS (SELECT name FROM sys.databases WHERE name = 'HotelDB')
BEGIN
    -- Cerrar conexiones activas
    ALTER DATABASE HotelDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE HotelDB;
END
GO

-- Crear la base de datos
CREATE DATABASE HotelDB;
GO

-- Usar la base de datos recién creada
USE HotelDB;
GO

-- ------------------------------------------------------------
-- PASO 2: Crear la tabla HABITACIONES
-- ------------------------------------------------------------
-- Esta tabla almacena todas las habitaciones del hotel
--
-- 📋 Campos:
--   • id: Identificador único (se genera automáticamente)
--   • tipo: Tipo de habitación (individual, doble, suite)
--   • precio: Precio por noche en euros
--   • disponible: 1 = disponible, 0 = ocupada

CREATE TABLE Habitaciones (
    id INT PRIMARY KEY IDENTITY(1,1),  -- IDENTITY = autoincremental
    tipo VARCHAR(20) NOT NULL,          -- NOT NULL = obligatorio
    precio DECIMAL(10,2) NOT NULL,      -- DECIMAL(10,2) = hasta 10 dígitos, 2 decimales
    disponible BIT DEFAULT 1            -- BIT = booleano (0 o 1), por defecto disponible
);
GO

-- ------------------------------------------------------------
-- PASO 3: Crear la tabla CLIENTES
-- ------------------------------------------------------------
-- Esta tabla almacena los datos de los clientes del hotel
--
-- 📋 Campos:
--   • id: Identificador único del cliente
--   • nombre: Nombre completo del cliente
--   • telefono: Número de teléfono
--   • email: Correo electrónico

CREATE TABLE Clientes (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100)
);
GO

-- ------------------------------------------------------------
-- PASO 4: Crear la tabla RESERVAS
-- ------------------------------------------------------------
-- Esta tabla almacena las reservas realizadas
--
-- 📋 Campos:
--   • id: Identificador único de la reserva
--   • idCliente: Referencia al cliente (FOREIGN KEY)
--   • idHabitacion: Referencia a la habitación (FOREIGN KEY)
--   • fechaEntrada: Fecha de entrada (check-in)
--   • fechaSalida: Fecha de salida (check-out)
--   • total: Importe total de la reserva
--
-- 🔗 FOREIGN KEY: Crea una relación entre tablas
--    Garantiza que el cliente y la habitación existan

CREATE TABLE Reservas (
    id INT PRIMARY KEY IDENTITY(1,1),
    idCliente INT NOT NULL,
    idHabitacion INT NOT NULL,
    fechaEntrada DATE NOT NULL,
    fechaSalida DATE NOT NULL,
    total DECIMAL(10,2),
    
    -- Claves foráneas (relaciones)
    CONSTRAINT FK_Reserva_Cliente 
        FOREIGN KEY (idCliente) REFERENCES Clientes(id),
    CONSTRAINT FK_Reserva_Habitacion 
        FOREIGN KEY (idHabitacion) REFERENCES Habitaciones(id)
);
GO

-- ------------------------------------------------------------
-- PASO 5: Insertar datos de prueba - HABITACIONES
-- ------------------------------------------------------------
-- Insertamos algunas habitaciones para poder probar el sistema

-- Nota: Usamos SET IDENTITY_INSERT para poder especificar el ID manualmente
SET IDENTITY_INSERT Habitaciones ON;

INSERT INTO Habitaciones (id, tipo, precio, disponible) VALUES
    (1, 'individual', 45.00, 1),   -- Habitación 1: Individual, 45€/noche, DISPONIBLE
    (2, 'doble', 70.00, 1),        -- Habitación 2: Doble, 70€/noche, DISPONIBLE
    (3, 'suite', 120.00, 1),       -- Habitación 3: Suite, 120€/noche, DISPONIBLE
    (4, 'doble', 65.00, 0),        -- Habitación 4: Doble, 65€/noche, OCUPADA
    (5, 'suite', 150.00, 1);       -- Habitación 5: Suite, 150€/noche, DISPONIBLE

SET IDENTITY_INSERT Habitaciones OFF;
GO

-- ------------------------------------------------------------
-- PASO 6: Verificar la creación (OPCIONAL)
-- ------------------------------------------------------------
-- Estas consultas te permiten verificar que todo se creó correctamente

-- Ver todas las habitaciones
SELECT * FROM Habitaciones;

-- Ver estructura de las tablas
-- EXEC sp_help 'Habitaciones';
-- EXEC sp_help 'Clientes';
-- EXEC sp_help 'Reservas';

-- ------------------------------------------------------------
-- ✅ ¡LISTO! La base de datos está creada y lista para usar
-- ------------------------------------------------------------

PRINT '============================================';
PRINT '✅ Base de datos HotelDB creada correctamente';
PRINT '✅ Tabla Habitaciones: 5 registros insertados';
PRINT '✅ Tabla Clientes: lista (vacía)';
PRINT '✅ Tabla Reservas: lista (vacía)';
PRINT '============================================';
GO
