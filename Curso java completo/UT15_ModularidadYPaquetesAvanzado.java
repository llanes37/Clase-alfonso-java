/******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - AUTOR: Joaquín Rodríguez Llanes
 *  📅 FECHA: 2025
 *  🔹 UNIDAD 15 AVANZADO: MODULARIDAD Y PAQUETES EN JAVA
 *  🔐 REPOSITORIO PRIVADO EN GITHUB (USO EDUCATIVO EXCLUSIVO)
 ******************************************************************************************/

/*
 * ******************************************************************************************
 *                📘 TEORÍA AVANZADA: MODULARIDAD Y PAQUETES EN PROYECTOS REALES
 * ──────────────────────────────────────────────────────────────────────────────
 * En esta unidad avanzada aprenderás:
 *
 * ✅ Crear y organizar paquetes reales (modelo, servicio, util, app).
 * ✅ Separar responsabilidades en distintas clases y archivos.
 * ✅ Usar imports absolutos y relativos entre paquetes.
 * ✅ Aplicar modificadores de acceso (public, private, protected, package-private).
 * ✅ Compilar y ejecutar proyectos modulares desde línea de comandos.
 * ✅ Comprender el classpath y la estructura de carpetas.
 * ✅ Buenas prácticas: nombres de paquetes (lowercase), convenciones, encapsulación.
 *
 * 🔎 CONTEXTO:
 * ──────────────────────────────────────────────────────────────
 * Un proyecto modular organiza el código en capas:
 *  - modelo: clases que representan datos (POJOs, entidades).
 *  - servicio: lógica de negocio, operaciones CRUD, validaciones.
 *  - util: funciones auxiliares, constantes, helpers.
 *  - app: punto de entrada (main), controladores, menús.
 *
 * Esta estructura permite:
 *  - Escalabilidad: agregar funcionalidades sin tocar otras capas.
 *  - Mantenibilidad: encontrar código rápidamente.
 *  - Testeo: probar servicios de forma aislada.
 *  - Reutilización: usar clases util en otros proyectos.
 *
 * 🧩 EJEMPLO DE PROYECTO MODULAR:
 * ──────────────────────────────────────────────────────────────
 * src/
 *   modelo/
 *     Producto.java       (representa un producto con id, nombre, precio)
 *     Cliente.java        (representa un cliente)
 *   servicio/
 *     ProductoService.java (CRUD de productos)
 *     ClienteService.java  (CRUD de clientes)
 *   util/
 *     Validador.java       (validaciones comunes)
 *     Logger.java          (logs simples)
 *   app/
 *     Main.java            (punto de entrada)
 *
 * 🔹 Cada archivo .java comienza con `package <nombre>;` indicando su paquete.
 * 🔹 Para usar una clase de otro paquete, se usa `import <paquete.Clase>;`
 * 🔹 Las clases públicas deben tener el mismo nombre que el archivo.
 *
 * 🛠️ COMPILACIÓN Y EJECUCIÓN:
 * ──────────────────────────────────────────────────────────────
 * # Compilar todos los archivos desde la raíz del proyecto:
 * javac -d bin src/modelo/*.java src/servicio/*.java src/util/*.java src/app/*.java
 *
 * # Ejecutar el Main (indicando classpath):
 * java -cp bin app.Main
 *
 * 📌 Nota: en este archivo UT15 avanzado, simularemos la estructura con clases
 * en un solo archivo para facilitar la demo, pero al final incluiremos instrucciones
 * completas para separar en archivos y carpetas reales.
 ******************************************************************************************
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// * ======================================================================
// * PAQUETE MODELO: CLASES QUE REPRESENTAN DATOS (POJOs)
// * ======================================================================
// ? En un proyecto real, estaría en: src/modelo/Producto.java
// ? Primera línea sería: package modelo;

class Producto {
    private int id;
    private String nombre;
    private double precio;

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    // * Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return String.format("#%d | %-15s | %.2f€", id, nombre, precio);
    }
}

// ? En un proyecto real, estaría en: src/modelo/Cliente.java
class Cliente {
    private int id;
    private String nombre;
    private String email;

    public Cliente(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("Cliente #%d: %s (%s)", id, nombre, email);
    }

    // ! ✅ TAREA ALUMNO:
    // * Añade validación en el constructor para que el email contenga "@".
    // * Lanza IllegalArgumentException si el email es inválido.
}

// * ======================================================================
// * PAQUETE UTIL: UTILIDADES Y HELPERS REUTILIZABLES
// * ======================================================================
// ? En un proyecto real, estaría en: src/util/Validador.java

class Validador {
    // * Evitar instanciación (clase de utilidad)
    private Validador() {}

    // * Valida que un String no sea nulo ni vacío
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    // * Valida que un número sea positivo
    public static boolean esPositivo(double numero) {
        return numero > 0;
    }

    // * Valida formato de email básico
    public static boolean esEmailValido(String email) {
        return esTextoValido(email) && email.contains("@") && email.contains(".");
    }

    // ! ✅ TAREA ALUMNO:
    // * Añade un método `esTelefonoValido(String)` que valide formato español (9 dígitos).
}

// ? En un proyecto real, estaría en: src/util/Logger.java
class Logger {
    private Logger() {}

    public static void info(String mensaje) {
        System.out.println("ℹ️ [INFO] " + mensaje);
    }

    public static void error(String mensaje) {
        System.err.println("❌ [ERROR] " + mensaje);
    }

    public static void exito(String mensaje) {
        System.out.println("✅ [OK] " + mensaje);
    }
}

// * ======================================================================
// * PAQUETE SERVICIO: LÓGICA DE NEGOCIO (CRUD, VALIDACIONES)
// * ======================================================================
// ? En un proyecto real, estaría en: src/servicio/ProductoService.java
// ? Primera línea sería: package servicio;
// ? Importaría: import modelo.Producto; import util.Validador; import util.Logger;

class ProductoService {
    private final List<Producto> productos = new ArrayList<>();
    private int secuenciaId = 1;

    // * Crear producto con validaciones
    public void crear(String nombre, double precio) {
        if (!Validador.esTextoValido(nombre)) {
            Logger.error("Nombre de producto inválido.");
            throw new IllegalArgumentException("Nombre inválido");
        }
        if (!Validador.esPositivo(precio)) {
            Logger.error("Precio debe ser positivo.");
            throw new IllegalArgumentException("Precio inválido");
        }
        Producto p = new Producto(secuenciaId++, nombre, precio);
        productos.add(p);
        Logger.exito("Producto creado: " + p.getNombre());
    }

    // * Listar todos los productos
    public void listar() {
        if (productos.isEmpty()) {
            Logger.info("No hay productos.");
        } else {
            Logger.info("Listado de productos:");
            for (Producto p : productos) System.out.println("  " + p);
        }
    }

    // * Buscar producto por ID
    public Producto buscarPorId(int id) {
        for (Producto p : productos) if (p.getId() == id) return p;
        return null;
    }

    // * Actualizar precio
    public void actualizarPrecio(int id, double nuevoPrecio) {
        Producto p = buscarPorId(id);
        if (p == null) {
            Logger.error("Producto no encontrado: ID " + id);
            return;
        }
        if (!Validador.esPositivo(nuevoPrecio)) {
            Logger.error("Precio inválido.");
            return;
        }
        p.setPrecio(nuevoPrecio);
        Logger.exito("Precio actualizado para: " + p.getNombre());
    }

    // * Eliminar producto
    public void eliminar(int id) {
        Producto p = buscarPorId(id);
        if (p != null && productos.remove(p)) {
            Logger.exito("Producto eliminado: " + p.getNombre());
        } else {
            Logger.error("No se pudo eliminar producto ID " + id);
        }
    }

    // ! ✅ TAREA ALUMNO:
    // * Implementa un método `buscarPorNombre(String nombre)` que devuelva lista de coincidencias.
    // * Añade método `aplicarDescuento(int id, double porcentaje)` que reduzca el precio.
}

// ? En un proyecto real, estaría en: src/servicio/ClienteService.java
class ClienteService {
    private final List<Cliente> clientes = new ArrayList<>();
    private int secuenciaId = 1;

    public void crear(String nombre, String email) {
        if (!Validador.esTextoValido(nombre)) {
            Logger.error("Nombre inválido.");
            throw new IllegalArgumentException("Nombre inválido");
        }
        if (!Validador.esEmailValido(email)) {
            Logger.error("Email inválido.");
            throw new IllegalArgumentException("Email inválido");
        }
        Cliente c = new Cliente(secuenciaId++, nombre, email);
        clientes.add(c);
        Logger.exito("Cliente creado: " + c.getNombre());
    }

    public void listar() {
        if (clientes.isEmpty()) {
            Logger.info("No hay clientes.");
        } else {
            Logger.info("Listado de clientes:");
            for (Cliente c : clientes) System.out.println("  " + c);
        }
    }

    // ! ✅ TAREA ALUMNO:
    // * Implementa `buscarPorId(int id)` y `eliminar(int id)`.
}

// * ======================================================================
// * PAQUETE APP: PUNTO DE ENTRADA Y MENÚ PRINCIPAL
// * ======================================================================
// ? En un proyecto real, estaría en: src/app/Main.java
// ? Primera línea sería: package app;
// ? Importaría: import servicio.ProductoService; import servicio.ClienteService;

public class UT15_ModularidadYPaquetesAvanzado {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductoService productoService = new ProductoService();
        ClienteService clienteService = new ClienteService();

        // * Precarga de datos de ejemplo
        productoService.crear("Laptop", 899.99);
        productoService.crear("Mouse", 15.50);
        productoService.crear("Teclado", 45.00);
        clienteService.crear("Ana García", "ana@example.com");
        clienteService.crear("Carlos López", "carlos@example.com");

        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  📦 SISTEMA MODULAR - GESTIÓN DE PRODUCTOS Y CLIENTES    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = sc.nextInt(); sc.nextLine();
                switch (opcion) {
                    case 1 -> menuProductos(sc, productoService);
                    case 2 -> menuClientes(sc, clienteService);
                    case 0 -> Logger.info("Saliendo del sistema. ¡Hasta luego!");
                    default -> Logger.error("Opción no válida.");
                }
            } catch (Exception e) {
                Logger.error("Error: " + e.getMessage());
                sc.nextLine(); // limpiar buffer
            }
        }

        sc.close();

        // ! ✅ TAREA ALUMNO:
        // * Crea un menú de "Reportes" que muestre estadísticas (total productos, clientes, etc.).
        // * Implementa persistencia en CSV para productos y clientes (usa util.FileUtil del UT21).
    }

    // * Menú principal
    private static void mostrarMenu() {
        System.out.println("\n🔹 MENÚ PRINCIPAL:");
        System.out.println("1. Gestión de Productos");
        System.out.println("2. Gestión de Clientes");
        System.out.println("0. Salir");
        System.out.print("👉 Opción: ");
    }

    // * Submenú de productos
    private static void menuProductos(Scanner sc, ProductoService service) {
        int op;
        do {
            System.out.println("\n📦 GESTIÓN DE PRODUCTOS:");
            System.out.println("1. Crear producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Actualizar precio");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Volver");
            System.out.print("👉 Opción: ");
            op = sc.nextInt(); sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Precio: "); double precio = sc.nextDouble(); sc.nextLine();
                        service.crear(nombre, precio);
                    }
                    case 2 -> service.listar();
                    case 3 -> {
                        System.out.print("ID del producto: "); int id = sc.nextInt();
                        System.out.print("Nuevo precio: "); double precio = sc.nextDouble(); sc.nextLine();
                        service.actualizarPrecio(id, precio);
                    }
                    case 4 -> {
                        System.out.print("ID del producto: "); int id = sc.nextInt(); sc.nextLine();
                        service.eliminar(id);
                    }
                    case 0 -> Logger.info("Volviendo al menú principal...");
                    default -> Logger.error("Opción no válida.");
                }
            } catch (Exception e) {
                Logger.error(e.getMessage());
                sc.nextLine();
            }
        } while (op != 0);

        // ! ✅ TAREA ALUMNO:
        // * Añade una opción para buscar productos por nombre (parcial/contiene).
        // * Implementa filtro por rango de precios (mínimo y máximo).
    }

    // * Submenú de clientes
    private static void menuClientes(Scanner sc, ClienteService service) {
        int op;
        do {
            System.out.println("\n👥 GESTIÓN DE CLIENTES:");
            System.out.println("1. Crear cliente");
            System.out.println("2. Listar clientes");
            System.out.println("0. Volver");
            System.out.print("👉 Opción: ");
            op = sc.nextInt(); sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Nombre: "); String nombre = sc.nextLine();
                        System.out.print("Email: "); String email = sc.nextLine();
                        service.crear(nombre, email);
                    }
                    case 2 -> service.listar();
                    case 0 -> Logger.info("Volviendo al menú principal...");
                    default -> Logger.error("Opción no válida.");
                }
            } catch (Exception e) {
                Logger.error(e.getMessage());
                sc.nextLine();
            }
        } while (op != 0);

        // ! ✅ TAREA ALUMNO:
        // * Implementa opciones para buscar cliente por ID y eliminar cliente.
        // * Añade un campo "teléfono" a Cliente y valídalo con Validador.
    }
}

/*
 * ******************************************************************************************
 * 🛠️ CÓMO SEPARAR ESTE ARCHIVO EN ESTRUCTURA MODULAR REAL
 * ──────────────────────────────────────────────────────────────
 * 1. Crea la siguiente estructura de carpetas:
 *    proyecto/
 *      src/
 *        modelo/
 *          Producto.java
 *          Cliente.java
 *        servicio/
 *          ProductoService.java
 *          ClienteService.java
 *        util/
 *          Validador.java
 *          Logger.java
 *        app/
 *          Main.java
 *
 * 2. Copia cada clase a su archivo correspondiente.
 *
 * 3. Añade la declaración de paquete al inicio de cada archivo:
 *    - En Producto.java y Cliente.java: package modelo;
 *    - En ProductoService.java y ClienteService.java: package servicio;
 *    - En Validador.java y Logger.java: package util;
 *    - En Main.java: package app;
 *
 * 4. Añade imports necesarios en cada archivo:
 *    - ProductoService.java:
 *      import modelo.Producto;
 *      import util.Validador;
 *      import util.Logger;
 *    - ClienteService.java:
 *      import modelo.Cliente;
 *      import util.Validador;
 *      import util.Logger;
 *    - Main.java:
 *      import servicio.ProductoService;
 *      import servicio.ClienteService;
 *      import util.Logger;
 *
 * 5. Compilar desde la raíz del proyecto:
 *    cd proyecto
 *    javac -d bin src/modelo/*.java src/servicio/*.java src/util/*.java src/app/*.java
 *
 * 6. Ejecutar:
 *    java -cp bin app.Main
 *
 * 📌 NOTA IMPORTANTE:
 * - Los paquetes deben coincidir con la estructura de carpetas.
 * - Las clases públicas deben tener el mismo nombre que el archivo.
 * - Para acceder a clases de otros paquetes, usa import o el nombre completo (ej. util.Logger.info()).
 *
 * 🔧 MODIFICADORES DE ACCESO:
 * ──────────────────────────────────────────────────────────────
 * - public: accesible desde cualquier paquete (requiere import).
 * - protected: accesible en el mismo paquete y subclases.
 * - (sin modificador/package-private): solo accesible en el mismo paquete.
 * - private: solo accesible dentro de la misma clase.
 *
 * 💡 BUENAS PRÁCTICAS:
 * ──────────────────────────────────────────────────────────────
 * ✅ Nombres de paquetes en minúsculas (modelo, servicio, util, app).
 * ✅ Estructura de carpetas reflejando paquetes.
 * ✅ Una clase pública por archivo.
 * ✅ Separación de responsabilidades (modelo ≠ servicio ≠ util).
 * ✅ Clases de utilidad con constructor privado y métodos estáticos.
 * ✅ Validaciones en servicios, no en modelos.
 ******************************************************************************************
 */

/*
 * ******************************************************************************************
 * ✅ TAREAS AVANZADAS PARA EL ALUMNO
 * ──────────────────────────────────────────────────────────────
 * 1️⃣ Separa este archivo en la estructura modular real (carpetas y archivos).
 * 2️⃣ Compila y ejecuta desde línea de comandos usando javac y java con classpath.
 * 3️⃣ Añade persistencia CSV para productos y clientes (usa FileUtil del UT21).
 * 4️⃣ Crea un paquete `tests` y escribe pruebas simples para Validador.
 * 5️⃣ Implementa un sistema de logging avanzado que escriba en archivo.
 * 6️⃣ Añade un paquete `excepciones` con excepciones personalizadas (ProductoNoEncontradoException).
 * 7️⃣ Refactoriza para usar interfaces (IProductoService, IClienteService).
 * 8️⃣ Implementa un patrón Repository para separar acceso a datos del servicio.
 * 9️⃣ Crea un README.md con instrucciones de compilación y ejecución.
 * 🔟 Exporta el proyecto a Maven o Gradle para gestión de dependencias.
 *
 * 🧪 EXTRA (OPCIONAL): Crea un paquete `config` con una clase Configuration que lea
 * propiedades desde un archivo config.properties (rutas de CSV, idioma, etc.).
 ******************************************************************************************
 */
