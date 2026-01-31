-- Script base de datos Hotel
-- Trabajo de recuperacion - Programacion 2 DAM

-- Crear base de datos
IF EXISTS (SELECT name FROM sys.databases WHERE name = 'HotelDB')
BEGIN
    ALTER DATABASE HotelDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE HotelDB;
END
GO

CREATE DATABASE HotelDB;
GO

USE HotelDB;
GO

-- Tabla Habitaciones
CREATE TABLE Habitaciones (
    id INT PRIMARY KEY IDENTITY(1,1),
    tipo VARCHAR(20) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    disponible BIT DEFAULT 1
);
GO

-- Tabla Clientes
CREATE TABLE Clientes (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100)
);
GO

-- Tabla Reservas con claves foraneas
CREATE TABLE Reservas (
    id INT PRIMARY KEY IDENTITY(1,1),
    idCliente INT NOT NULL,
    idHabitacion INT NOT NULL,
    fechaEntrada DATE NOT NULL,
    fechaSalida DATE NOT NULL,
    total DECIMAL(10,2),
    CONSTRAINT FK_Reserva_Cliente FOREIGN KEY (idCliente) REFERENCES Clientes(id),
    CONSTRAINT FK_Reserva_Habitacion FOREIGN KEY (idHabitacion) REFERENCES Habitaciones(id)
);
GO

-- Datos de prueba habitaciones
SET IDENTITY_INSERT Habitaciones ON;

INSERT INTO Habitaciones (id, tipo, precio, disponible) VALUES
    (1, 'individual', 45.00, 1),
    (2, 'doble', 70.00, 1),
    (3, 'suite', 120.00, 1),
    (4, 'doble', 65.00, 0),
    (5, 'suite', 150.00, 1);

SET IDENTITY_INSERT Habitaciones OFF;
GO

-- Verificar
SELECT * FROM Habitaciones;
GO
