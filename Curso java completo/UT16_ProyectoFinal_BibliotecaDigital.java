/******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - AUTOR: Joaquín Rodríguez Llanes
 *  📅 FECHA: 2025
 *  🔹 UNIDAD 16: PROYECTO FINAL - BIBLIOTECA DIGITAL (CONSOLA)
 *  🔐 REPOSITORIO PRIVADO EN GITHUB (USO EDUCATIVO EXCLUSIVO)
 ******************************************************************************************/

/*
 * ******************************************************************************************
 * 🧠 OBJETIVO DEL PROYECTO (ESTRUCTURA "BETTER COMMENTS")
 * ──────────────────────────────────────────────────────────────
 * ✅ Crear una app de consola para gestionar una biblioteca digital realista.
 * ✅ Aplicar: clases, colecciones, composición, enums, E/S de ficheros, validaciones y menú interactivo.
 * ✅ Módulos: Libros, Usuarios, Préstamos, Reservas, Multas y Persistencia simple (CSV).
 * ✅ Menú interactivo con feedback claro y validaciones robustas.
 *
 * 🔎 Resumen de conceptos clave:
 * - Encapsulación: atributos privados + getters/setters con validación.
 * - Composición: Usuario tiene préstamos, Libro tiene reservas.
 * - Enums: estado del libro, tipo de usuario, estado del préstamo.
 * - Persistencia: CSV para libros, usuarios y préstamos.
 * - Manejo de errores: try/catch, InputMismatch, IOException.
 *
 * 🧪 Recomendación didáctica:
 * - Compila y ejecuta este archivo por separado para evitar colisiones de nombres con otras UTs.
 * - Trabaja iterativamente: prueba cada opción del menú tras modificarla.
 ******************************************************************************************
 */

import java.io.*;
import java.util.*;

// =========================================================================================
// 🧠 TEORÍA: ENUMS EN JAVA
// -----------------------------------------------------------------------------------------
// Los enums permiten definir conjuntos de valores constantes y seguros.
// Ejemplo real: EstadoLibro.DISPONIBLE, TipoUsuario.ALUMNO
// Ventajas: legibilidad, seguridad, autocompletado y menos errores de tipeo.
// =========================================================================================
enum EstadoLibro { DISPONIBLE, PRESTADO, RESERVADO, BAJA }
enum TipoUsuario { ALUMNO, PROFESOR, EXTERNO }
enum EstadoPrestamo { ACTIVO, DEVUELTO, ATRASADO }

// =========================================================================================
// 🧠 TEORÍA: CLASE LIBRO (MODELO DE DATOS)
// -----------------------------------------------------------------------------------------
// Un Libro tiene:
//   - id único (ISBN o código)
//   - título, autor, año, estado
//   - lista de reservas (composición)
// =========================================================================================
class Libro {
    private String id;
    private String titulo;
    private String autor;
    private int anio;
    private EstadoLibro estado;
    private final List<String> reservas = new ArrayList<>(); // IDs de usuarios

    public Libro(String id, String titulo, String autor, int anio) {
        setId(id); setTitulo(titulo); setAutor(autor); setAnio(anio); this.estado = EstadoLibro.DISPONIBLE;
    }
    public String getId() { return id; }
    public void setId(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID de libro inválido");
        this.id = id.trim();
    }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("Título inválido");
        this.titulo = titulo.trim();
    }
    public String getAutor() { return autor; }
    public void setAutor(String autor) {
        if (autor == null || autor.isBlank()) throw new IllegalArgumentException("Autor inválido");
        this.autor = autor.trim();
    }
    public int getAnio() { return anio; }
    public void setAnio(int anio) {
        if (anio < 1000 || anio > 2100) throw new IllegalArgumentException("Año inválido");
        this.anio = anio;
    }
    public EstadoLibro getEstado() { return estado; }
    public void setEstado(EstadoLibro estado) { this.estado = (estado != null) ? estado : EstadoLibro.DISPONIBLE; }
    public List<String> getReservas() { return reservas; }
    public void agregarReserva(String userId) { reservas.add(userId); }

    // * Conversión a CSV y desde CSV
    public String toCsv() {
        return String.format(Locale.US, "%s;%s;%s;%d;%s", id, titulo.replace(';', ','), autor.replace(';', ','), anio, estado.name());
    }
    public static Libro fromCsv(String csv) {
        String[] p = csv.split(";");
        if (p.length < 5) throw new IllegalArgumentException("Línea CSV inválida: " + csv);
        Libro l = new Libro(p[0], p[1], p[2], Integer.parseInt(p[3]));
        l.setEstado(EstadoLibro.valueOf(p[4]));
        return l;
    }
    @Override
    public String toString() {
        return String.format(Locale.US, "#%s | %-20s | %-15s | %d | %-10s | Reservas: %d", id, titulo, autor, anio, estado, reservas.size());
    }
}

// =========================================================================================
// 🧠 TEORÍA: CLASE USUARIO (MODELO DE DATOS)
// -----------------------------------------------------------------------------------------
// Un Usuario tiene:
//   - id único (DNI, email, etc.)
//   - nombre, tipo (enum)
//   - lista de préstamos activos
// =========================================================================================
class Usuario {
    private String id;
    private String nombre;
    private TipoUsuario tipo;
    private final List<String> prestamos = new ArrayList<>(); // IDs de préstamos

    public Usuario(String id, String nombre, TipoUsuario tipo) {
        setId(id); setNombre(nombre); setTipo(tipo);
    }
    public String getId() { return id; }
    public void setId(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID de usuario inválido");
        this.id = id.trim();
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        this.nombre = nombre.trim();
    }
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = (tipo != null) ? tipo : TipoUsuario.ALUMNO; }
    public List<String> getPrestamos() { return prestamos; }
    public void agregarPrestamo(String prestamoId) { prestamos.add(prestamoId); }

    public String toCsv() {
        return String.format(Locale.US, "%s;%s;%s", id, nombre.replace(';', ','), tipo.name());
    }
    public static Usuario fromCsv(String csv) {
        String[] p = csv.split(";");
        if (p.length < 3) throw new IllegalArgumentException("Línea CSV inválida: " + csv);
        return new Usuario(p[0], p[1], TipoUsuario.valueOf(p[2]));
    }
    @Override
    public String toString() {
        return String.format(Locale.US, "#%s | %-15s | %-8s | Préstamos: %d", id, nombre, tipo, prestamos.size());
    }
}

// =========================================================================================
// 🧠 TEORÍA: CLASE PRÉSTAMO (RELACIÓN USUARIO-LIBRO)
// -----------------------------------------------------------------------------------------
// Un Préstamo tiene:
//   - id único
//   - idUsuario, idLibro
//   - fecha de inicio, fecha de devolución, estado
// =========================================================================================
class Prestamo {
    private String id;
    private String idUsuario;
    private String idLibro;
    private String fechaInicio;
    private String fechaDevolucion;
    private EstadoPrestamo estado;

    public Prestamo(String id, String idUsuario, String idLibro, String fechaInicio, String fechaDevolucion) {
        setId(id); setIdUsuario(idUsuario); setIdLibro(idLibro); setFechaInicio(fechaInicio); setFechaDevolucion(fechaDevolucion); this.estado = EstadoPrestamo.ACTIVO;
    }
    public String getId() { return id; }
    public void setId(String id) { if (id == null || id.isBlank()) throw new IllegalArgumentException("ID préstamo inválido"); this.id = id.trim(); }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { if (idUsuario == null || idUsuario.isBlank()) throw new IllegalArgumentException("ID usuario inválido"); this.idUsuario = idUsuario.trim(); }
    public String getIdLibro() { return idLibro; }
    public void setIdLibro(String idLibro) { if (idLibro == null || idLibro.isBlank()) throw new IllegalArgumentException("ID libro inválido"); this.idLibro = idLibro.trim(); }
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public String getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(String fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public EstadoPrestamo getEstado() { return estado; }
    public void setEstado(EstadoPrestamo estado) { this.estado = (estado != null) ? estado : EstadoPrestamo.ACTIVO; }

    public String toCsv() {
        return String.format(Locale.US, "%s;%s;%s;%s;%s;%s", id, idUsuario, idLibro, fechaInicio, fechaDevolucion, estado.name());
    }
    public static Prestamo fromCsv(String csv) {
        String[] p = csv.split(";");
        if (p.length < 6) throw new IllegalArgumentException("Línea CSV inválida: " + csv);
        Prestamo pr = new Prestamo(p[0], p[1], p[2], p[3], p[4]);
        pr.setEstado(EstadoPrestamo.valueOf(p[5]));
        return pr;
    }
    @Override
    public String toString() {
        return String.format(Locale.US, "#%s | Usuario: %s | Libro: %s | %s → %s | %-8s", id, idUsuario, idLibro, fechaInicio, fechaDevolucion, estado);
    }
}

// =========================================================================================
// 🧠 TEORÍA: GESTOR DE BIBLIOTECA (SERVICIO)
// -----------------------------------------------------------------------------------------
// Centraliza la lógica de negocio:
//   - CRUD de libros, usuarios, préstamos
//   - Persistencia en CSV
//   - Búsqueda, validación, informes
// =========================================================================================
class BibliotecaManager {
    private final List<Libro> libros = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Prestamo> prestamos = new ArrayList<>();
    private final String archivoLibros = "libros.csv";
    private final String archivoUsuarios = "usuarios.csv";
    private final String archivoPrestamos = "prestamos.csv";

    // CRUD Libros
    public List<Libro> listarLibros() { return libros; }
    public void agregarLibro(Libro l) { libros.add(l); }
    public Libro buscarLibroPorId(String id) { for (Libro l : libros) if (l.getId().equals(id)) return l; return null; }
    public boolean eliminarLibro(String id) { Libro l = buscarLibroPorId(id); return (l != null) && libros.remove(l); }

    // CRUD Usuarios
    public List<Usuario> listarUsuarios() { return usuarios; }
    public void agregarUsuario(Usuario u) { usuarios.add(u); }
    public Usuario buscarUsuarioPorId(String id) { for (Usuario u : usuarios) if (u.getId().equals(id)) return u; return null; }
    public boolean eliminarUsuario(String id) { Usuario u = buscarUsuarioPorId(id); return (u != null) && usuarios.remove(u); }

    // CRUD Préstamos
    public List<Prestamo> listarPrestamos() { return prestamos; }
    public void agregarPrestamo(Prestamo p) { prestamos.add(p); }
    public Prestamo buscarPrestamoPorId(String id) { for (Prestamo p : prestamos) if (p.getId().equals(id)) return p; return null; }
    public boolean eliminarPrestamo(String id) { Prestamo p = buscarPrestamoPorId(id); return (p != null) && prestamos.remove(p); }

    // Persistencia
    public void guardarTodo() {
        guardarCsv(archivoLibros, libros, l -> l.toCsv());
        guardarCsv(archivoUsuarios, usuarios, u -> u.toCsv());
        guardarCsv(archivoPrestamos, prestamos, p -> p.toCsv());
        System.out.println("💾 Datos guardados en CSV.");
    }
    public void cargarTodo() {
        libros.clear(); usuarios.clear(); prestamos.clear();
        cargarCsv(archivoLibros, l -> libros.add(Libro.fromCsv(l)));
        cargarCsv(archivoUsuarios, u -> usuarios.add(Usuario.fromCsv(u)));
        cargarCsv(archivoPrestamos, p -> prestamos.add(Prestamo.fromCsv(p)));
        System.out.println("📥 Datos cargados desde CSV.");
    }
    private static <T> void guardarCsv(String archivo, List<T> lista, java.util.function.Function<T, String> toCsv) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (T t : lista) { bw.write(toCsv.apply(t)); bw.newLine(); }
        } catch (IOException e) { System.out.println("❌ Error guardando " + archivo + ": " + e.getMessage()); }
    }
    private static void cargarCsv(String archivo, java.util.function.Consumer<String> add) {
        File f = new File(archivo);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea; while ((linea = br.readLine()) != null) add.accept(linea);
        } catch (IOException e) { System.out.println("❌ Error cargando " + archivo + ": " + e.getMessage()); }
    }
}

// =========================================================================================
// 🧑‍🏫 MAIN: FLUJO PRINCIPAL DE LA APP (IDEAL PARA EXPLICAR EN CLASE)
// -----------------------------------------------------------------------------------------
// 1. Carga datos
// 2. Muestra menú principal
// 3. Permite gestionar libros, usuarios y préstamos
// 4. Guarda datos antes de salir
// =========================================================================================
public class UT16_ProyectoFinal_BibliotecaDigital {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BibliotecaManager gestor = new BibliotecaManager();
        gestor.cargarTodo();

        int opcion = -1;
        while (opcion != 0) {
            try {
                mostrarMenuPrincipal();
                opcion = sc.nextInt(); sc.nextLine();
                switch (opcion) {
                    case 1 -> menuLibros(sc, gestor);
                    case 2 -> menuUsuarios(sc, gestor);
                    case 3 -> menuPrestamos(sc, gestor);
                    case 4 -> gestor.guardarTodo();
                    case 0 -> System.out.println("👋 ¡Gracias por usar el sistema!");
                    default -> System.out.println("⚠️ Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Debes ingresar un número.");
                sc.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n📚 MENÚ PRINCIPAL - BIBLIOTECA DIGITAL");
        System.out.println("1. Gestionar Libros");
        System.out.println("2. Gestionar Usuarios");
        System.out.println("3. Gestionar Préstamos");
        System.out.println("4. Guardar Todo en CSV");
        System.out.println("0. Salir");
        System.out.print("👉 Opción: ");
    }

    // * Submenú de libros
    private static void menuLibros(Scanner sc, BibliotecaManager gestor) {
        int op;
        do {
            System.out.println("\n📚 GESTIÓN DE LIBROS");
            System.out.println("1. Agregar libro");
            System.out.println("2. Eliminar libro");
            System.out.println("3. Listar libros");
            System.out.println("0. Volver");
            System.out.print("👉 Opción: ");
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1 -> agregarLibro(sc, gestor);
                case 2 -> eliminarLibro(sc, gestor);
                case 3 -> listarLibros(gestor);
                case 0 -> System.out.println("↩️ Volviendo al menú principal...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (op != 0);
    }
    private static void agregarLibro(Scanner sc, BibliotecaManager gestor) {
        try {
            System.out.print("📖 ID (ISBN): "); String id = sc.nextLine();
            System.out.print("Título: "); String titulo = sc.nextLine();
            System.out.print("Autor: "); String autor = sc.nextLine();
            System.out.print("Año: "); int anio = sc.nextInt(); sc.nextLine();
            gestor.agregarLibro(new Libro(id, titulo, autor, anio));
            System.out.println("✅ Libro agregado.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Datos inválidos: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("❌ Formato incorrecto."); sc.nextLine();
        }
    }
    private static void eliminarLibro(Scanner sc, BibliotecaManager gestor) {
        System.out.print("📖 ID del libro a eliminar: "); String id = sc.nextLine();
        if (gestor.eliminarLibro(id)) System.out.println("🗑️ Libro eliminado."); else System.out.println("❌ No encontrado.");
    }
    private static void listarLibros(BibliotecaManager gestor) {
        System.out.println("\n📃 LISTA DE LIBROS:");
        if (gestor.listarLibros().isEmpty()) System.out.println("(vacío)");
        for (Libro l : gestor.listarLibros()) System.out.println(" - " + l);
    }

    // * Submenú de usuarios
    private static void menuUsuarios(Scanner sc, BibliotecaManager gestor) {
        int op;
        do {
            System.out.println("\n👤 GESTIÓN DE USUARIOS");
            System.out.println("1. Agregar usuario");
            System.out.println("2. Eliminar usuario");
            System.out.println("3. Listar usuarios");
            System.out.println("0. Volver");
            System.out.print("👉 Opción: ");
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1 -> agregarUsuario(sc, gestor);
                case 2 -> eliminarUsuario(sc, gestor);
                case 3 -> listarUsuarios(gestor);
                case 0 -> System.out.println("↩️ Volviendo al menú principal...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (op != 0);
    }
    private static void agregarUsuario(Scanner sc, BibliotecaManager gestor) {
        try {
            System.out.print("👤 ID (DNI/email): "); String id = sc.nextLine();
            System.out.print("Nombre: "); String nombre = sc.nextLine();
            System.out.print("Tipo (ALUMNO, PROFESOR, EXTERNO): ");
            TipoUsuario tipo = TipoUsuario.valueOf(sc.nextLine().trim().toUpperCase());
            gestor.agregarUsuario(new Usuario(id, nombre, tipo));
            System.out.println("✅ Usuario agregado.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Datos inválidos: " + e.getMessage());
        }
    }
    private static void eliminarUsuario(Scanner sc, BibliotecaManager gestor) {
        System.out.print("👤 ID del usuario a eliminar: "); String id = sc.nextLine();
        if (gestor.eliminarUsuario(id)) System.out.println("🗑️ Usuario eliminado."); else System.out.println("❌ No encontrado.");
    }
    private static void listarUsuarios(BibliotecaManager gestor) {
        System.out.println("\n📃 LISTA DE USUARIOS:");
        if (gestor.listarUsuarios().isEmpty()) System.out.println("(vacío)");
        for (Usuario u : gestor.listarUsuarios()) System.out.println(" - " + u);
    }

    // * Submenú de préstamos
    private static void menuPrestamos(Scanner sc, BibliotecaManager gestor) {
        int op;
        do {
            System.out.println("\n📚 GESTIÓN DE PRÉSTAMOS");
            System.out.println("1. Agregar préstamo");
            System.out.println("2. Devolver préstamo");
            System.out.println("3. Listar préstamos");
            System.out.println("0. Volver");
            System.out.print("👉 Opción: ");
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1 -> agregarPrestamo(sc, gestor);
                case 2 -> devolverPrestamo(sc, gestor);
                case 3 -> listarPrestamos(gestor);
                case 0 -> System.out.println("↩️ Volviendo al menú principal...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (op != 0);
    }
    private static void agregarPrestamo(Scanner sc, BibliotecaManager gestor) {
        try {
            System.out.print("ID usuario: "); String idUsuario = sc.nextLine();
            System.out.print("ID libro: "); String idLibro = sc.nextLine();
            System.out.print("Fecha inicio (YYYY-MM-DD): "); String fechaInicio = sc.nextLine();
            System.out.print("Fecha devolución (YYYY-MM-DD): "); String fechaDevolucion = sc.nextLine();
            String id = UUID.randomUUID().toString();
            gestor.agregarPrestamo(new Prestamo(id, idUsuario, idLibro, fechaInicio, fechaDevolucion));
            System.out.println("✅ Préstamo agregado.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Datos inválidos: " + e.getMessage());
        }
    }
    private static void devolverPrestamo(Scanner sc, BibliotecaManager gestor) {
        System.out.print("ID del préstamo a devolver: "); String id = sc.nextLine();
        Prestamo p = gestor.buscarPrestamoPorId(id);
        if (p == null) { System.out.println("❌ No encontrado."); return; }
        p.setEstado(EstadoPrestamo.DEVUELTO);
        System.out.println("✅ Préstamo marcado como devuelto.");
    }
    private static void listarPrestamos(BibliotecaManager gestor) {
        System.out.println("\n📃 LISTA DE PRÉSTAMOS:");
        if (gestor.listarPrestamos().isEmpty()) System.out.println("(vacío)");
        for (Prestamo p : gestor.listarPrestamos()) System.out.println(" - " + p);
    }
}

/*
 * ******************************************************************************************
 * ✅ TAREAS PARA EL ALUMNO (AVANZADAS)
 * ──────────────────────────────────────────────────────────────
 * 1️⃣ Añade gestión de reservas y cola de espera para libros prestados.
 * 2️⃣ Implementa búsqueda de libros por texto (contiene) y por autor.
 * 3️⃣ Añade cálculo de multas por retraso y muestra el usuario con más sanciones.
 * 4️⃣ Evita IDs duplicados al crear libros o usuarios (validación).
 * 5️⃣ Crea un reporte de préstamos activos y atrasados.
 * 6️⃣ Refactoriza el código separando en paquetes: modelo, servicio, util, app.
 * 7️⃣ Añade pruebas unitarias simples para fromCsv() y gestión de préstamos.
 * 8️⃣ Implementa un backup automático de datos al cerrar el programa.
 * 9️⃣ Soporta importación/exportación de datos en formato JSON.
 * 🔟 Agrega autenticación sencilla (PIN) para entrar en el menú de gestión de usuarios.
 *
 * 🧩 EXTRA (OPCIONAL): Añade gestión de libros electrónicos y control de descargas.
 ******************************************************************************************/