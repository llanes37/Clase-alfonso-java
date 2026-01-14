# Proyecto Final (Basico) — Agenda de Citas en Consola

Proyecto final basico pensado como escalon previo al proyecto completo **Gestor de Cursos**. Es una aplicacion de consola que gestiona **clientes** y sus **citas** para un pequeño negocio (peluqueria, estetica, consulta, etc.).

- Lenguaje: Java 17+
- Arquitectura: MVC sencillo (modelo, repositorio, controlador, vista de consola)
- Persistencia: ficheros CSV en `resources/data`
- Objetivo didactico: practicar POO, colecciones, enums, fechas, validaciones y menus en consola con un dominio muy simple.

---

## Objetivos didacticos

- Modelar un dominio pequeño de negocio: clientes y citas.
- Separar responsabilidades en capas:
  - **model**: clases `Cliente`, `Cita`, `EstadoCita`.
  - **repository**: acceso a datos en CSV (`ClienteRepository`, `CitaRepository`).
  - **controller**: reglas de negocio y validaciones (`ClienteController`, `CitaController`).
  - **view**: entrada/salida por consola (`ConsoleView`).
  - **util / persistence / exception**: utilidades comunes.
- Practicar comentarios tipo **Better Comments** (`// !`, `// *`, `// ?`, `// TODO`) dentro del codigo.

---

## Arquitectura (alto nivel)

- `com.curso.proyectobasico.Application`
  - Punto de entrada `main`.
  - Crea repositorios, controladores y la vista de consola.
  - Gestiona el bucle de menus:
    - **Clientes**: listar, crear, borrar.
    - **Citas**: listar, crear, marcar como realizadas, borrar.

- `model`
  - `Cliente`: id, nombre, email, telefono.
  - `Cita`: id, clienteId, fecha (LocalDate), estado (enum), descripcion breve.
  - `EstadoCita`: `PENDIENTE`, `REALIZADA`, `CANCELADA`.

- `repository`
  - `Repository<T>`: interfaz CRUD minima (findAll, findById, save, update, delete).
  - `ClienteRepository`: CSV `resources/data/clientes.csv`.
  - `CitaRepository`: CSV `resources/data/citas.csv`.

- `persistence`
  - `FileStorage`: leer/escribir lineas UTF-8.
  - `CsvUtils`: join/split de campos con `;`.

- `controller`
  - `ClienteController`: valida nombre y email, evita duplicados por email.
  - `CitaController`: valida cliente existente, parsea fecha, gestiona cambio de estado.

- `view`
  - `ConsoleView`: utilidades para titulo, lineas, prompt de texto y pausa.

- `util`
  - `Validator`: comprobar no vacio, email, numeros positivos.
  - `DateUtils`: parseo/formato de fechas `yyyy-MM-dd`.

- `exception`
  - `ValidationException`: errores de negocio (no tecnicos).

---

## Estructura de carpetas

- `src/com/curso/proyectobasico` — codigo fuente Java
- `resources/data/` — ficheros CSV (se crean tras la primera ejecucion)
- `bin/` — clases compiladas
- `build.bat` — compilar
- `run.bat` — ejecutar la aplicacion
- `package.bat` — crear JAR ejecutable
- `build.ps1` — alternativa PowerShell
- `EJERCICIOS.md` — cuaderno de practicas progresivas

---

## Como compilar y ejecutar (Windows)

Desde una terminal en la carpeta `proyecto-final-basico`:

```powershell
.\build.bat      # compilar todas las clases a bin/
.\run.bat        # ejecutar la aplicacion
.\package.bat    # crear proyecto-final-basico.jar
```

Requisitos:
- JDK 17 o superior instalado.
- `java` y `javac` accesibles en el `PATH`.

---

## Formato de datos (CSV)

Los ficheros se guardan en `resources/data` con separador `;`:

- `clientes.csv`  
  `id;nombre;email;telefono`

- `citas.csv`  
  `id;clienteId;fecha;estado;descripcion`

Reglas:
- `fecha` se escribe como `yyyy-MM-dd`.
- Si algun campo de texto es nulo se guarda como cadena vacia.

---

## Flujo principal de uso

1. Al arrancar, los repositorios cargan los CSV existentes (si los hay).
2. Se muestra el menu principal:
   - Gestion de clientes.
   - Gestion de citas.
3. Cada accion se pide por consola con `ConsoleView`.
4. Los controladores aplican validaciones y usan los repos para persistir cambios.
5. Al crear/modificar/borrar se reescriben los CSV completos.

---

## Ideas de ampliacion

- Editar datos de cliente (cambiar telefono o email).
- Buscar clientes por nombre o email parcial.
- Listar solo citas pendientes o solo las de hoy.
- Mostrar nombre del cliente junto a la cita en los listados.
- Añadir un pequeño resumen al salir (total de clientes, total de citas pendientes).

Consulta `EJERCICIOS.md` para una guia paso a paso de mejoras propuestas.
