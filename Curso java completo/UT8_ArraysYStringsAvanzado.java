/******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - AUTOR: Joaquín Rodríguez Llanes
 *  📅 FECHA: 2025
 *  🔹 UNIDAD 8 AVANZADO: ARRAYS + STRINGS
 *  🔐 REPOSITORIO PRIVADO PARA USO EDUCATIVO
 ******************************************************************************************/

import java.util.*;

public class UT8_ArraysYStringsAvanzado {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // * 🧠 TEORÍA AVANZADA: ARRAYS Y STRINGS
        // -----------------------------------------------------
        // ? Un array puede ser de cualquier tipo, incluso de objetos o arrays (multidimensional).
        // ? Métodos útiles: Arrays.sort, Arrays.binarySearch, Arrays.equals, Arrays.fill, Arrays.copyOf.
        // ? Para manipular colecciones dinámicas, usa ArrayList o List.
        // ? Los Strings pueden almacenarse, ordenarse y buscarse en arrays.
        // ! ⚠️ Recuerda: los arrays tienen tamaño fijo, pero puedes crear nuevos arrays a partir de otros.

        int opcion = -1;
        do {
            System.out.println("\n🧭 MENÚ UNIDAD 8 AVANZADO: ARRAYS + STRINGS");
            System.out.println("1️⃣ - Array multidimensional de Strings");
            System.out.println("2️⃣ - Búsqueda binaria en array ordenado");
            System.out.println("3️⃣ - Ordenar con Comparator (longitud)");
            System.out.println("4️⃣ - Manipulación avanzada de Strings en arrays");
            System.out.println("0️⃣ - Salir");
            System.out.print("👉 Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> arrayMultidimensionalStrings(sc);
                case 2 -> busquedaBinariaArray(sc);
                case 3 -> ordenarPorLongitud(sc);
                case 4 -> manipulacionAvanzadaStrings(sc);
                case 0 -> System.out.println("🚪 Saliendo del programa...");
                default -> System.out.println("❌ Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
    }

    // * 🔵 EJEMPLO 1: Array multidimensional de Strings
    public static void arrayMultidimensionalStrings(Scanner sc) {
        /*
         * 📖 TEORÍA:
         * - Un array bidimensional es una "tabla" de datos (matriz).
         * - Se accede con dos índices: matriz[fila][columna].
         * - Muy útil para representar tablas, tableros, etc.
         */
        System.out.println("\n📌 EJEMPLO 1: Array multidimensional de Strings");

        String[][] tabla = {
            {"Nombre", "Apellido", "Ciudad"},
            {"Ana", "López", "Madrid"},
            {"Luis", "Pérez", "Sevilla"},
            {"Marta", "García", "Valencia"}
        };

        // ? Mostrar la tabla
        for (String[] fila : tabla) {
            System.out.println(Arrays.toString(fila));
        }

        // ! ✅ TAREA: Pide al usuario que añada una nueva fila y muéstrala.
    }

    // * 🔵 EJEMPLO 2: Búsqueda binaria en array ordenado
    public static void busquedaBinariaArray(Scanner sc) {
        /*
         * 📖 TEORÍA:
         * - Arrays.binarySearch permite buscar rápidamente en arrays ORDENADOS.
         * - Devuelve el índice si encuentra el elemento, si no, un valor negativo.
         */
        System.out.println("\n📌 EJEMPLO 2: Búsqueda binaria en array ordenado");

        String[] palabras = {"java", "python", "c++", "kotlin", "go", "ruby"};
        Arrays.sort(palabras); // ? Debe estar ordenado
        System.out.println("Array ordenado: " + Arrays.toString(palabras));

        System.out.print("🔎 Palabra a buscar: ");
        String buscar = sc.nextLine();
        int idx = Arrays.binarySearch(palabras, buscar);
        if (idx >= 0) {
            System.out.println("✅ Encontrada en la posición " + idx);
        } else {
            System.out.println("❌ No encontrada. Si se insertara, iría en posición " + (-idx - 1));
        }

        // ! ✅ TAREA: Haz que la búsqueda ignore mayúsculas y acentos (usa Comparator).
    }

    // * 🔵 EJEMPLO 3: Ordenar con Comparator (por longitud)
    public static void ordenarPorLongitud(Scanner sc) {
        /*
         * 📖 TEORÍA:
         * - Arrays.sort puede recibir un Comparator para ordenar según cualquier criterio.
         * - Aquí ordenamos palabras por su longitud (de menor a mayor).
         */
        System.out.println("\n📌 EJEMPLO 3: Ordenar palabras por longitud");

        System.out.print("¿Cuántas palabras vas a ingresar? ");
        int n = sc.nextInt();
        sc.nextLine();
        String[] palabras = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Palabra " + (i + 1) + ": ");
            palabras[i] = sc.nextLine();
        }

        Arrays.sort(palabras, Comparator.comparingInt(String::length));
        System.out.println("Palabras ordenadas por longitud: " + Arrays.toString(palabras));

        // ! ✅ TAREA: Ordena de mayor a menor longitud y muestra la palabra más larga.
    }

    // * 🔵 EJEMPLO 4: Manipulación avanzada de Strings en arrays
    public static void manipulacionAvanzadaStrings(Scanner sc) {
        /*
         * 📖 TEORÍA:
         * - Puedes transformar todos los elementos de un array usando bucles o streams.
         * - Métodos útiles: toUpperCase, trim, replace, split, join.
         */
        System.out.println("\n📌 EJEMPLO 4: Manipulación avanzada de Strings en arrays");

        System.out.print("Introduce una frase: ");
        String frase = sc.nextLine();
        String[] palabras = frase.trim().split("\\s+");

        // ? Convertir todas las palabras a mayúsculas y unirlas con guiones
        for (int i = 0; i < palabras.length; i++) {
            palabras[i] = palabras[i].toUpperCase();
        }
        String resultado = String.join("-", palabras);
        System.out.println("Resultado: " + resultado);

        // ! ✅ TAREA: Reemplaza todas las vocales por '*' en cada palabra y muestra el array resultante.
    }

}