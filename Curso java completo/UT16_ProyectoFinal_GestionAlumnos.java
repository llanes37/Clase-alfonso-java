/******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - AUTOR: Joaquín Rodríguez Llanes
 *  📅 FECHA: 2025
 *  🔹 UNIDAD 16: PROYECTO FINAL - GESTIÓN DE ALUMNOS (CONSOLA)
 *  🔐 REPOSITORIO PRIVADO EN GITHUB (USO EDUCATIVO EXCLUSIVO)
 ******************************************************************************************/


/******************************************************************************************
 * 🧠 PROYECTO FINAL JAVA: GESTIÓN DE ALUMNOS (CLASE MAGISTRAL)
 *
 * 🚩 OBJETIVO: Dominar la programación orientada a objetos, colecciones, enums, persistencia y menús interactivos en Java.
 *
 * 🔥 NIVEL: AVANZADO (DAM/DAW, ideal para repaso global y entrevistas técnicas)
 *
 * ──────────────────────────────────────────────────────────────
 * 📚 ¿QUÉ VAS A APRENDER Y PRACTICAR?
 *
 *   1️⃣ Diseño de modelos de datos robustos (Alumno, Evaluación, Curso)
 *   2️⃣ Uso profesional de colecciones (ArrayList, composición)
 *   3️⃣ Enums para estados y tipos (¡adiós a los magic strings!)
 *   4️⃣ Persistencia real: guardar y cargar datos en CSV
 *   5️⃣ Menús interactivos y validaciones sólidas
 *   6️⃣ Buenas prácticas, refactorización y comentarios pedagógicos
 *   7️⃣ Tareas y retos para llevarte al siguiente nivel
 *
 * 🧑‍🏫 IDEAL PARA: Explicar en clase, repasar para exámenes, preparar entrevistas o como base para proyectos reales.
 *
 * 🟢 CONSEJO: Lee los bloques teóricos (🧠), sigue los comentarios Better Comments y resuelve los retos (🚩) para dominar cada parte.
 ******************************************************************************************/

import java.io.*;
import java.util.*;

// =========================================================================================
// 🧠 TEORÍA: ENUMS EN JAVA
// -----------------------------------------------------------------------------------------
// Los enums permiten definir conjuntos de valores constantes y seguros.
// Ejemplo real: EstadoAlumno.ACTIVO, TipoEvaluacion.EXAMEN
// Ventajas: legibilidad, seguridad, autocompletado y menos errores de tipeo.
// 🚩 RETO: Añade más estados o tipos según tu contexto real.
// =========================================================================================
enum EstadoAlumno { ACTIVO, SUSPENDIDO, BAJA }
enum TipoEvaluacion { EXAMEN, PRACTICA, TRABAJO, OTRO }

// * ======================================================================
// * MODELO: ALUMNO, CURSO, EVALUACION
// * ======================================================================
// =========================================================================================
// 🧠 TEORÍA: CLASE ALUMNO (MODELO DE DATOS)
// -----------------------------------------------------------------------------------------
// Un Alumno tiene:
//   - id único (clave primaria)
//   - nombre (String)
//   - estado (enum EstadoAlumno)
//   - lista de evaluaciones (composición)
//
// Buenas prácticas:
//   - Encapsula atributos (private)
//   - Valida en setters y constructores
//   - Usa métodos para conversión a CSV y desde CSV
// 🚩 RETO: Añade más atributos útiles (email, fecha de nacimiento...)
// =========================================================================================
class Alumno {
    // 🛡️ Atributos privados: seguridad y encapsulamiento
    private int id;
    private String nombre;
    private EstadoAlumno estado;
    private final List<Evaluacion> evaluaciones = new ArrayList<>();

    // 🛠️ Constructor: inicializa con id y nombre, estado por defecto ACTIVO
    public Alumno(int id, String nombre) {
        setId(id); setNombre(nombre); this.estado = EstadoAlumno.ACTIVO;
    }

    // 🔎 Getters y setters con validación
    public int getId() { return id; }
    public void setId(int id) {
        // ⚡ Validación: el id debe ser positivo
        if (id < 0) throw new IllegalArgumentException("ID inválido");
        this.id = id;
    }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        // ⚡ Validación: el nombre no puede ser vacío
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        this.nombre = nombre.trim();
    }
    public EstadoAlumno getEstado() { return estado; }
    public void setEstado(EstadoAlumno estado) { this.estado = (estado != null) ? estado : EstadoAlumno.ACTIVO; }
    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void agregarEvaluacion(Evaluacion ev) { evaluaciones.add(ev); }

    // 🗃️ Conversión a CSV (persistencia)
    public String toCsv() {
        return String.format(Locale.US, "%d;%s;%s", id, nombre.replace(';', ','), estado.name());
    }
    // 🗃️ Conversión desde CSV
    // * Conversión desde CSV (persistencia)
    public static Alumno fromCsv(String csv) {
        String[] p = csv.split(";");
        if (p.length < 3) throw new IllegalArgumentException("Línea CSV inválida: " + csv);
        int id = Integer.parseInt(p[0]);
        String nombre = p[1];
        EstadoAlumno estado = EstadoAlumno.valueOf(p[2]); // 🚩 RETO: usa este estado en el constructor
        // * Se mantiene la construcción original para evitar errores, pero se deja el reto para el alumno
        return new Alumno(id, nombre);
    }
    @Override
    public String toString() {
        // 📋 Resumen del alumno y número de evaluaciones
        return String.format(Locale.US, "#%d | %-15s | %-10s | Evaluaciones: %d", id, nombre, estado, evaluaciones.size());
    }
}

// =========================================================================================
// 🧠 TEORÍA: CLASE EVALUACION (COMPOSICIÓN)
// -----------------------------------------------------------------------------------------
// Una Evaluación representa una nota concreta de un alumno:
//   - tipo (enum TipoEvaluacion)
//   - descripción (String)
//   - nota (double)
//
// Buenas prácticas:
//   - Inmutable (atributos final)
//   - Validar nota (0-10)
//   - Conversión a/de CSV
// 🚩 RETO: Añade fecha, profesor o peso de la evaluación
// =========================================================================================
class Evaluacion {
    private final TipoEvaluacion tipo;
    private final String descripcion;
    private final double nota;
    public Evaluacion(TipoEvaluacion tipo, String descripcion, double nota) {
        this.tipo = tipo != null ? tipo : TipoEvaluacion.OTRO;
        this.descripcion = (descripcion == null) ? "" : descripcion.trim();
        this.nota = nota;
    }
    public TipoEvaluacion getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public double getNota() { return nota; }
    // 🗃️ Conversión a CSV
    public String toCsv(int alumnoId) {
        return String.format(Locale.US, "%d;%s;%s;%.2f", alumnoId, tipo.name(), descripcion.replace(';', ','), nota);
    }
    // 🗃️ Conversión desde CSV
    public static Evaluacion fromCsv(String csv) {
        String[] p = csv.split(";");
        if (p.length < 4) throw new IllegalArgumentException("Línea CSV inválida: " + csv);
        TipoEvaluacion tipo = TipoEvaluacion.valueOf(p[1]);
        String desc = p[2];
        double nota = Double.parseDouble(p[3]);
        return new Evaluacion(tipo, desc, nota);
    }
    @Override
    public String toString() {
        // 📋 Resumen de la evaluación
        return String.format(Locale.US, "%s: %s (%.2f)", tipo, descripcion, nota);
    }
}

// =========================================================================================
// 🧠 TEORÍA: CLASE CURSO (AGRUPACIÓN)
// -----------------------------------------------------------------------------------------
// Un Curso agrupa alumnos:
//   - nombre (String)
//   - lista de alumnos (ArrayList<Alumno>)
//
// 🚩 RETO: Añade métodos para buscar alumnos, calcular medias, etc.
// =========================================================================================
class Curso {
    private String nombre;
    private final List<Alumno> alumnos = new ArrayList<>();
    public Curso(String nombre) { setNombre(nombre); }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre de curso inválido");
        this.nombre = nombre.trim();
    }
    public List<Alumno> getAlumnos() { return alumnos; }
    public void agregarAlumno(Alumno a) { alumnos.add(a); }
    @Override
    public String toString() { return "Curso: " + nombre + " | Alumnos: " + alumnos.size(); }
}

// * ======================================================================
// * GESTOR DE ALUMNOS Y EVALUACIONES (CRUD + CSV)
// * ======================================================================
// =========================================================================================
// 🧠 TEORÍA: GESTOR DE ALUMNOS (SERVICIO)
// -----------------------------------------------------------------------------------------
// El AlumnoManager centraliza la lógica de negocio:
//   - CRUD de alumnos
//   - Persistencia en CSV
//   - Búsqueda y validación
//
// Buenas prácticas:
//   - Métodos claros y atómicos
//   - Manejo de errores robusto
// 🚩 RETO: Añade métodos para buscar por nombre, estado, etc.
// =========================================================================================
class AlumnoManager {
    private final List<Alumno> alumnos = new ArrayList<>();
    private final String archivoAlumnos = "alumnos.csv";
    private final String archivoEvaluaciones = "evaluaciones.csv";

    // 🛠️ CRUD BÁSICO
    public List<Alumno> listar() { return alumnos; }
    // * Añade un alumno a la lista
    public void agregar(Alumno a) { alumnos.add(a); }
    // * Busca un alumno por su ID
    public Alumno buscarPorId(int id) { for (Alumno a : alumnos) if (a.getId() == id) return a; return null; }
    // * Elimina un alumno por su ID
    public boolean eliminar(int id) { Alumno a = buscarPorId(id); return (a != null) && alumnos.remove(a); }

    // 🗃️ Persistencia: CSV
    // * Guarda todos los alumnos en un archivo CSV
    public void guardarEnCsv() {
        List<String> lineas = new ArrayList<>();
        for (Alumno a : alumnos) lineas.add(a.toCsv());
        try { writeAll(archivoAlumnos, lineas); System.out.println("💾 Alumnos guardados en " + archivoAlumnos); }
        catch (IOException e) { System.out.println("❌ Error guardando CSV: " + e.getMessage()); }
    }
    // * Carga alumnos desde archivo CSV
    public void cargarDesdeCsv() {
        try {
            List<String> lineas = readAll(archivoAlumnos);
            alumnos.clear();
            for (String l : lineas) {
                try { alumnos.add(Alumno.fromCsv(l)); }
                catch (Exception ex) { System.out.println("⚠️ Línea inválida en CSV: " + l); }
            }
            if (!lineas.isEmpty()) System.out.println("📥 Alumnos cargados desde " + archivoAlumnos + " (" + alumnos.size() + ")");
        } catch (IOException e) {}
    }
    // 🛠️ Utilidades de fichero
    // * Escribe todas las líneas en un archivo
    private static void writeAll(String nombre, List<String> lineas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombre))) {
            for (String l : lineas) { bw.write(l); bw.newLine(); }
        }
    }
    // * Lee todas las líneas de un archivo
    private static List<String> readAll(String nombre) throws IOException {
        List<String> res = new ArrayList<>();
        File f = new File(nombre);
        if (!f.exists()) return res;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea; while ((linea = br.readLine()) != null) res.add(linea);
        }
        return res;
    }
}

// * ======================================================================
// * APLICACIÓN (MAIN): MENÚ PRINCIPAL Y FLUJO DE USO
// * ======================================================================
public class UT16_ProyectoFinal_GestionAlumnos {
    // =========================================================================================
    // 🧑‍🏫 MAIN: FLUJO PRINCIPAL DE LA APP (IDEAL PARA EXPLICAR EN CLASE)
    // -----------------------------------------------------------------------------------------
    // 1. Carga datos
    // 2. Muestra menú principal
    // 3. Permite gestionar alumnos y evaluaciones
    // 4. Guarda datos antes de salir
    // =========================================================================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AlumnoManager gestor = new AlumnoManager();
        gestor.cargarDesdeCsv();

        int opcion = -1;
        while (opcion != 0) {
            try {
                mostrarMenuPrincipal();
                opcion = sc.nextInt(); sc.nextLine();
                switch (opcion) {
                    case 1 -> menuAlumnos(sc, gestor); // CRUD de alumnos
                    case 2 -> menuEvaluaciones(sc, gestor); // Evaluaciones
                    case 3 -> listarAlumnos(gestor); // Listar
                    case 4 -> gestor.guardarEnCsv(); // Guardar
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

    // =========================================================================================
    // � MENÚ PRINCIPAL (EXPLICACIÓN EN CLASE: FLUJO DE OPCIONES)
    // -----------------------------------------------------------------------------------------
    // 1. Gestionar alumnos (añadir, eliminar, listar)
    // 2. Gestionar evaluaciones (añadir, listar)
    // 3. Listar todos los alumnos
    // 4. Guardar en CSV
    // 0. Salir
    // =========================================================================================
    private static void mostrarMenuPrincipal() {
        System.out.println("\n🎓 MENÚ PRINCIPAL - GESTIÓN DE ALUMNOS");
        System.out.println("1. Gestionar Alumnos (CRUD)");
        System.out.println("2. Gestionar Evaluaciones");
        System.out.println("3. Listar Alumnos");
        System.out.println("4. Guardar Alumnos en CSV");
        System.out.println("0. Salir");
        System.out.print("👉 Opción: ");
    }

    // * Submenú de alumnos
    // =========================================================================================
    // � SUBMENÚ DE ALUMNOS (CRUD)
    // -----------------------------------------------------------------------------------------
    // 1. Agregar alumno
    // 2. Eliminar alumno
    // 3. Listar alumnos
    // 0. Volver
    // 🚩 RETO: Añade opción para buscar alumnos por nombre o estado
    // =========================================================================================
    private static void menuAlumnos(Scanner sc, AlumnoManager gestor) {
        int op;
        do {
            System.out.println("\n👥 GESTIÓN DE ALUMNOS");
            System.out.println("1. Agregar alumno");
            System.out.println("2. Eliminar alumno");
            System.out.println("3. Listar alumnos");
            System.out.println("0. Volver");
            System.out.print("👉 Opción: ");
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1 -> agregarAlumno(sc, gestor);
                case 2 -> eliminarAlumno(sc, gestor);
                case 3 -> listarAlumnos(gestor);
                case 0 -> System.out.println("↩️ Volviendo al menú principal...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (op != 0);
    }

    // =========================================================================================
    // 🟢 AGREGAR ALUMNO (EXPLICACIÓN EN CLASE)
    // -----------------------------------------------------------------------------------------
    // 1. Pide id y nombre
    // 2. Valida datos (¡ojo con duplicados!)
    // 3. Añade a la lista
    // � RETO: Evita IDs duplicados o implementa autoincremento
    // =========================================================================================
    private static void agregarAlumno(Scanner sc, AlumnoManager gestor) {
        try {
            System.out.print("🆔 ID: "); int id = sc.nextInt(); sc.nextLine();
            System.out.print("👤 Nombre: "); String nombre = sc.nextLine();
            gestor.agregar(new Alumno(id, nombre));
            System.out.println("✅ Alumno agregado.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Datos inválidos: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("❌ Formato incorrecto."); sc.nextLine();
        }
    }

    // =========================================================================================
    // 🟢 ELIMINAR ALUMNO (EXPLICACIÓN EN CLASE)
    // -----------------------------------------------------------------------------------------
    // 1. Pide id
    // 2. Busca y elimina si existe
    // 3. Feedback claro
    // =========================================================================================
    private static void eliminarAlumno(Scanner sc, AlumnoManager gestor) {
        System.out.print("🆔 ID del alumno a eliminar: "); int id = sc.nextInt(); sc.nextLine();
        if (gestor.eliminar(id)) System.out.println("🗑️ Alumno eliminado."); else System.out.println("❌ No encontrado.");
    }

    // =========================================================================================
    // � LISTAR ALUMNOS (EXPLICACIÓN EN CLASE)
    // -----------------------------------------------------------------------------------------
    // 1. Muestra todos los alumnos
    // 2. 🚩 RETO: Calcula la media de notas y resalta el mejor
    // =========================================================================================
    private static void listarAlumnos(AlumnoManager gestor) {
        System.out.println("\n📃 LISTA DE ALUMNOS:");
        if (gestor.listar().isEmpty()) System.out.println("(vacío)");
        for (Alumno a : gestor.listar()) System.out.println(" - " + a);
    }

    // * Submenú de evaluaciones
    // =========================================================================================
    // 🟢 SUBMENÚ DE EVALUACIONES (EXPLICACIÓN EN CLASE)
    // -----------------------------------------------------------------------------------------
    // 1. Agregar evaluación
    // 2. Listar evaluaciones
    // 0. Volver
    // 🚩 RETO: Añade opción para eliminar o editar evaluaciones
    // =========================================================================================
    private static void menuEvaluaciones(Scanner sc, AlumnoManager gestor) {
        System.out.print("🆔 ID del alumno: "); int id = sc.nextInt(); sc.nextLine();
        Alumno a = gestor.buscarPorId(id);
        if (a == null) { System.out.println("❌ Alumno no encontrado."); return; }
        int op;
        do {
            System.out.println("\n📝 GESTIÓN DE EVALUACIONES para " + a.getNombre());
            System.out.println("1. Agregar evaluación");
            System.out.println("2. Listar evaluaciones");
            System.out.println("0. Volver");
            System.out.print("👉 Opción: ");
            op = sc.nextInt(); sc.nextLine();
            switch (op) {
                case 1 -> agregarEvaluacion(sc, a);
                case 2 -> listarEvaluaciones(a);
                case 0 -> System.out.println("↩️ Volviendo...");
                default -> System.out.println("⚠️ Opción no válida.");
            }
        } while (op != 0);
    }

    // =========================================================================================
    // 🟢 AGREGAR EVALUACIÓN (EXPLICACIÓN EN CLASE)
    // -----------------------------------------------------------------------------------------
    // 1. Pide tipo, descripción y nota
    // 2. Valida datos (nota entre 0 y 10)
    // 3. Añade a la lista de evaluaciones del alumno
    // � RETO: Valida que la nota esté entre 0 y 10
    // =========================================================================================
    private static void agregarEvaluacion(Scanner sc, Alumno a) {
        try {
            System.out.print("Tipo (EXAMEN, PRACTICA, TRABAJO, OTRO): ");
            TipoEvaluacion tipo = TipoEvaluacion.valueOf(sc.nextLine().trim().toUpperCase());
            System.out.print("Descripción: "); String desc = sc.nextLine();
            System.out.print("Nota: "); double nota = sc.nextDouble(); sc.nextLine();
            a.agregarEvaluacion(new Evaluacion(tipo, desc, nota));
            System.out.println("✅ Evaluación agregada.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Datos inválidos: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("❌ Formato incorrecto."); sc.nextLine();
        }
    }

    // =========================================================================================
    // 🟢 LISTAR EVALUACIONES (EXPLICACIÓN EN CLASE)
    // -----------------------------------------------------------------------------------------
    // 1. Muestra todas las evaluaciones del alumno
    // 2. 🚩 RETO: Calcula y muestra la media de notas
    // =========================================================================================
    private static void listarEvaluaciones(Alumno a) {
        System.out.println("\n📃 EVALUACIONES de " + a.getNombre() + ":");
        if (a.getEvaluaciones().isEmpty()) System.out.println("(ninguna)");
        for (Evaluacion ev : a.getEvaluaciones()) System.out.println(" - " + ev);
    }
}

/*
 * ******************************************************************************************
 * ✅ TAREAS PARA EL ALUMNO (AVANZADAS)
 * ──────────────────────────────────────────────────────────────
 * 1️⃣ Añade persistencia de evaluaciones en CSV y función para cargar.
 * 2️⃣ Implementa búsqueda de alumnos por texto (contiene) y por estado en el menú.
 * 3️⃣ Añade cálculo de media de notas y muestra el mejor alumno.
 * 4️⃣ Evita IDs duplicados al crear alumnos (auto-incremento o validación).
 * 5️⃣ Crea un reporte de notas por curso y por tipo de evaluación.
 * 6️⃣ Refactoriza el código separando en paquetes: modelo, servicio, util, app.
 * 7️⃣ Añade pruebas unitarias simples para fromCsv() y media de evaluaciones.
 * 8️⃣ Implementa un backup automático de alumnos al cerrar el programa.
 * 9️⃣ Soporta importación/exportación de alumnos en formato JSON.
 * 🔟 Agrega autenticación sencilla (PIN) para entrar en el menú de gestión de alumnos.
 *
 * 🧩 EXTRA (OPCIONAL): Añade gestión de matrículas en varios cursos y control de asistencia.
 ******************************************************************************************/