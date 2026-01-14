/******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - AUTOR: Joaquín Rodríguez Llanes
 *  📅 FECHA: 2025
 *  🔹 UNIDAD 16: PROYECTO FINAL - SISTEMA DE GESTIÓN DE ALUMNOS (CLASE MAGISTRAL)
 *  🔐 REPOSITORIO PRIVADO EN GITHUB (USO EDUCATIVO EXCLUSIVO)
 ******************************************************************************************/

/*
 * ******************************************************************************************
 * 🧠 OBJETIVO DEL PROYECTO (NIVEL AVANZADO - DAM/DAW):
 * ──────────────────────────────────────────────────────────────
 * ✅ Crear una aplicación de consola profesional que gestione alumnos.
 * ✅ Aplicar: clases, colecciones, POO, excepciones, validaciones, menú interactivo.
 * ✅ Funcionalidades: agregar, listar, buscar, eliminar, persistencia (CSV).
 * ✅ Buenas prácticas: encapsulación, separación de responsabilidades, reutilización de código.
 *
 * 🔥 NIVEL: AVANZADO (Ideal para repasar todo lo aprendido en el curso)
 *
 * 📚 ¿QUÉ VAS A APRENDER Y PRACTICAR?
 *
 *   1️⃣ Diseño de clases robusto (POJO con getters/setters)
 *   2️⃣ Colecciones: ArrayList, iteración, búsqueda
 *   3️⃣ Menú interactivo y validaciones sólidas
 *   4️⃣ Manejo de excepciones (try/catch)
 *   5️⃣ Persistencia: guardar y cargar desde CSV
 *   6️⃣ Buenas prácticas y comentarios pedagógicos
 *
 * 🧑‍🏫 IDEAL PARA: Clase magistral, repaso global, entrevistas técnicas, proyecto real.
 *
 * 🟢 CONSEJO: Lee los bloques teóricos (🔷), sigue los Better Comments y resuelve los retos (🚩).
 ******************************************************************************************
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// =========================================================================================
// 🔷 TEORÍA: FLUJO PRINCIPAL DE LA APP
// -----------------------------------------------------------------------------------------
// 1. Carga datos desde CSV (si existen)
// 2. Muestra menú principal
// 3. Permite gestionar alumnos
// 4. Guarda datos antes de salir
// Buenas prácticas: separar lógica de presentación y negocio
// =========================================================================================
public class UT16_ProyectoFinal {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestorAlumnos gestor = new GestorAlumnos();
        
        // ? Cargamos alumnos desde CSV si el archivo existe
        gestor.cargarDesdeCsv();

        int opcion;

        // * 🔁 Bucle de menú principal (do-while: al menos una ejecución)
        do {
            mostrarMenuPrincipal();

            try {
                opcion = sc.nextInt();
                sc.nextLine(); // 🧹 Limpia buffer tras leer número

                // * Switch: distribuye la lógica según opción elegida
                switch (opcion) {
                    case 1 -> gestor.agregarAlumno(sc);
                    case 2 -> gestor.listarAlumnos();
                    case 3 -> gestor.buscarAlumno(sc);
                    case 4 -> gestor.eliminarAlumno(sc);
                    case 5 -> {
                        // ! Antes de salir, guardamos los datos
                        gestor.guardarEnCsv();
                        System.out.println("👋 ¡Gracias por usar el sistema!");
                    }
                    default -> System.out.println("⚠️ Opción no válida.");
                }
            } catch (InputMismatchException e) {
                // ! ⚠️ Manejo de error: entrada no válida
                System.out.println("❌ Error: Debes ingresar un número.");
                sc.nextLine(); // 🧹 Limpiar buffer
                opcion = 0; // Reiniciamos para que el bucle continúe
            }
        } while (opcion != 5);

        sc.close();
    }

    // =========================================================================================
    // 🔷 MENÚ PRINCIPAL (EXPLICACIÓN EN CLASE)
    // -----------------------------------------------------------------------------------------
    // Muestra las opciones disponibles de forma clara y atractiva
    // =========================================================================================
    private static void mostrarMenuPrincipal() {
        System.out.println("\n🎓 MENÚ PRINCIPAL - GESTIÓN DE ALUMNOS");
        System.out.println("1. Agregar Alumno");
        System.out.println("2. Listar Alumnos");
        System.out.println("3. Buscar Alumno");
        System.out.println("4. Eliminar Alumno");
        System.out.println("5. Guardar y Salir");
        System.out.print("👉 Opción: ");
    }
}
 

// =========================================================================================
// 🔷 TEORÍA: CLASE ALUMNO (POJO - Plain Old Java Object)
// -----------------------------------------------------------------------------------------
// Un Alumno tiene:
//   - nombre (String)
//   - edad (int)
//   - nota (double)
// Buenas prácticas:
//   - Atributos privados (encapsulación)
//   - Getters para acceder a los datos
//   - Constructor para inicializar
//   - toString() para mostrar información
// 🚩 RETO: Añade atributos como ID, email, o fecha de inscripción
// =========================================================================================
class Alumno {
    // 🛡️ Atributos privados: seguridad y encapsulamiento
    private String nombre;
    private int edad;
    private double nota;

    // 🛠️ Constructor: inicializa el alumno con todos los datos
    public Alumno(String nombre, int edad, double nota) {
        this.nombre = nombre;
        this.edad = edad;
        this.nota = nota;
    }

    // 🔎 Getters: acceso controlado a los atributos
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getNota() {
        return nota;
    }

    // * Conversión a CSV para persistencia
    public String toCsv() {
        return nombre + ";" + edad + ";" + nota;
    }

    // * Conversión desde CSV
    public static Alumno fromCsv(String csv) {
        String[] partes = csv.split(";");
        if (partes.length != 3) throw new IllegalArgumentException("Línea CSV inválida");
        return new Alumno(partes[0], Integer.parseInt(partes[1]), Double.parseDouble(partes[2]));
    }

    @Override
    public String toString() {
        // 📋 Muestra información formateada del alumno
        return String.format("👨‍🎓 Nombre: %-15s | Edad: %2d | Nota: %.2f", nombre, edad, nota);
    }
}
 

// =========================================================================================
// 🔷 TEORÍA: CLASE GESTOR DE ALUMNOS (SERVICIO)
// -----------------------------------------------------------------------------------------
// Centraliza la lógica de negocio:
//   - CRUD (Create, Read, Update, Delete) de alumnos
//   - Búsqueda y filtrado
//   - Persistencia en CSV
// Buenas prácticas:
//   - Métodos claros y atómicos
//   - Manejo de errores robusto
// 🚩 RETO: Añade métodos para buscar por edad, nota mínima, o calcular promedio
// =========================================================================================
class GestorAlumnos {
    // ? Lista en memoria: simulamos una base de datos
    private ArrayList<Alumno> lista = new ArrayList<>();
    private final String archivoAlumnos = "alumnos.csv";

    // =========================================================================================
    // 🟢 AGREGAR ALUMNO (CREATE)
    // -----------------------------------------------------------------------------------------
    // 1. Pide datos del usuario (nombre, edad, nota)
    // 2. Valida que no estén vacíos
    // 3. Crea un nuevo alumno y lo añade a la lista
    // =========================================================================================
    public void agregarAlumno(Scanner sc) {
        try {
            System.out.print("📝 Nombre del alumno: ");
            String nombre = sc.nextLine().trim();
            // ! ⚠️ Validación: nombre no puede estar vacío
            if (nombre.isBlank()) {
                System.out.println("❌ El nombre no puede estar vacío.");
                return;
            }

            System.out.print("🎂 Edad: ");
            int edad = sc.nextInt();
            // ! ⚠️ Validación: edad debe ser positiva
            if (edad < 0 || edad > 120) {
                System.out.println("❌ La edad debe ser entre 0 y 120.");
                return;
            }

            System.out.print("📊 Nota final: ");
            double nota = sc.nextDouble();
            sc.nextLine();
            // ! ⚠️ Validación: nota debe estar entre 0 y 10
            if (nota < 0 || nota > 10) {
                System.out.println("❌ La nota debe estar entre 0 y 10.");
                return;
            }

            Alumno nuevo = new Alumno(nombre, edad, nota);
            lista.add(nuevo);

            System.out.println("✅ Alumno agregado correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("❌ Error: Debes ingresar un número válido.");
        }
    }

    // =========================================================================================
    // 🟢 LISTAR ALUMNOS (READ)
    // -----------------------------------------------------------------------------------------
    // 1. Verifica si hay alumnos registrados
    // 2. Si está vacío, muestra mensaje
    // 3. Si hay datos, itera y muestra cada alumno
    // =========================================================================================
    public void listarAlumnos() {
        if (lista.isEmpty()) {
            // ? Lista vacía: feedback claro
            System.out.println("📭 No hay alumnos registrados.");
        } else {
            System.out.println("📚 Lista de alumnos (" + lista.size() + "):");
            // * Iteración con for-each: recorre cada alumno
            for (Alumno a : lista) {
                System.out.println(a);
            }
        }
    }

    // =========================================================================================
    // 🟢 BUSCAR ALUMNO (READ ESPECÍFICO)
    // -----------------------------------------------------------------------------------------
    // 1. Pide nombre a buscar
    // 2. Itera la lista comparando nombres (case-insensitive)
    // 3. Muestra resultado o error
    // 🚩 RETO: Implementa búsqueda por edad o nota mínima
    // =========================================================================================
    public void buscarAlumno(Scanner sc) {
        System.out.print("🔍 Nombre a buscar: ");
        String nombre = sc.nextLine().trim();

        boolean encontrado = false;
        // * Iteración para buscar
        for (Alumno a : lista) {
            // ? equalsIgnoreCase: comparación sin importar mayúsculas
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("✅ Alumno encontrado: " + a);
                encontrado = true;
                break; // ? Salimos al encontrar el primero
            }
        }

        if (!encontrado) {
            System.out.println("❌ Alumno no encontrado.");
        }
    }

    // =========================================================================================
    // 🟢 ELIMINAR ALUMNO (DELETE)
    // -----------------------------------------------------------------------------------------
    // 1. Pide nombre a eliminar
    // 2. Busca el alumno en la lista
    // 3. Si lo encuentra, lo elimina
    // 4. Si no, muestra error
    // =========================================================================================
    public void eliminarAlumno(Scanner sc) {
        System.out.print("🗑️ Nombre del alumno a eliminar: ");
        String nombre = sc.nextLine().trim();

        Alumno alumnoEliminar = null;

        // * Búsqueda del alumno a eliminar
        for (Alumno a : lista) {
            if (a.getNombre().equalsIgnoreCase(nombre)) {
                alumnoEliminar = a;
                break;
            }
        }

        if (alumnoEliminar != null) {
            lista.remove(alumnoEliminar);
            System.out.println("🗑️ Alumno eliminado correctamente.");
        } else {
            System.out.println("❌ Alumno no encontrado.");
        }
    }

    // =========================================================================================
    // 🟢 GUARDAR EN CSV (PERSISTENCIA)
    // -----------------------------------------------------------------------------------------
    // Guarda todos los alumnos en un archivo CSV para persistencia
    // Formato: nombre;edad;nota
    // =========================================================================================
    public void guardarEnCsv() {
        try {
            // ? java.io.FileWriter: escribir en archivo
            java.io.FileWriter writer = new java.io.FileWriter(archivoAlumnos);
            for (Alumno a : lista) {
                writer.write(a.toCsv() + "\n");
            }
            writer.close();
            System.out.println("💾 Alumnos guardados en " + archivoAlumnos);
        } catch (java.io.IOException e) {
            System.out.println("❌ Error guardando archivo: " + e.getMessage());
        }
    }

    // =========================================================================================
    // 🟢 CARGAR DESDE CSV (PERSISTENCIA)
    // -----------------------------------------------------------------------------------------
    // Carga alumnos guardados en CSV al iniciar la aplicación
    // Valida cada línea y recupera los datos
    // =========================================================================================
    public void cargarDesdeCsv() {
        try {
            java.io.File archivo = new java.io.File(archivoAlumnos);
            if (!archivo.exists()) {
                // ? Archivo no existe: primera ejecución
                return;
            }

            // ? java.io.BufferedReader: leer línea por línea
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(archivo));
            String linea;
            int contador = 0;
            while ((linea = reader.readLine()) != null) {
                try {
                    Alumno a = Alumno.fromCsv(linea);
                    lista.add(a);
                    contador++;
                } catch (Exception e) {
                    System.out.println("⚠️ Línea inválida en CSV: " + linea);
                }
            }
            reader.close();
            if (contador > 0) {
                System.out.println("📥 Se cargaron " + contador + " alumnos desde " + archivoAlumnos);
            }
        } catch (java.io.IOException e) {
            System.out.println("❌ Error cargando archivo: " + e.getMessage());
        }
    }
}
 

/*
 * ******************************************************************************************
 * ✅ TAREAS PARA EL ALUMNO (AVANZADAS)
 * ──────────────────────────────────────────────────────────────
 * 1️⃣ Añade validaciones más estrictas: nombres sin números, edad realista, notas 0-10.
 * 2️⃣ Implementa búsqueda de alumnos por edad mínima o nota mínima.
 * 3️⃣ Calcula y muestra el promedio de notas de todos los alumnos.
 * 4️⃣ Crea una subclase `AlumnoBecado` que herede de `Alumno` y añada atributo `tipoBeca`.
 * 5️⃣ Usa polimorfismo para mostrar si el alumno tiene beca o no.
 * 6️⃣ Refactoriza separando en paquetes: `modelo`, `servicio`, `main`.
 * 7️⃣ Exporta lista a archivo de texto (.txt) con formato tabla.
 * 8️⃣ Importa alumnos desde un archivo CSV existente.
 * 9️⃣ Añade un método para editar datos de un alumno existente.
 * 🔟 Agrega estadísticas: alumno con mejor nota, peor nota, edad promedio.
 *
 * 🧩 EXTRA (OPCIONAL): Integra persistencia en JSON, serialización con ObjectOutputStream.
 ******************************************************************************************
 */
 