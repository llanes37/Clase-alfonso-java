/******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - AUTOR: Joaquín Rodríguez Llanes
 *  📅 FECHA: 2025
 *  🔹 UNIDAD 13 AVANZADO: COLECCIONES (Set, Map, Queue, Stack, PriorityQueue)
 *  🔐 REPOSITORIO PRIVADO USO EDUCATIVO
 ******************************************************************************************/

import java.util.*;

public class UT13_ColeccionesAvanzado {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        // * 🧠 TEORÍA AVANZADA: COLECCIONES EN JAVA
        // -----------------------------------------------------
        // ? Las colecciones permiten almacenar, buscar y manipular datos de forma flexible.
        // ? Set: elementos únicos (HashSet, TreeSet). Map: pares clave-valor (HashMap, TreeMap).
        // ? Queue: estructuras FIFO (colas), Stack: LIFO (pilas), PriorityQueue: colas con prioridad.
        // ! ⚠️ Elige la colección según la operación que más vayas a realizar (búsqueda, orden, inserción, etc).

        do {
            System.out.println("\n📦 MENÚ - COLECCIONES AVANZADAS:");
            System.out.println("1. HashSet (únicos)");
            System.out.println("2. HashMap (clave-valor)");
            System.out.println("3. TreeSet (ordenado)");
            System.out.println("4. Queue (cola FIFO)");
            System.out.println("5. Stack (pila LIFO)");
            System.out.println("6. PriorityQueue (cola de prioridad)");
            System.out.println("7. ArrayList y LinkedList");
            System.out.println("8. TreeMap (mapa ordenado)");
            System.out.println("9. Colecciones de objetos y Comparator");
            System.out.println("10. Map anidado y streams");
            System.out.println("0. Salir");
            System.out.print("👉 Elige una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> demoHashSet(sc);
                case 2 -> demoHashMap(sc);
                case 3 -> demoTreeSet(sc);
                case 4 -> demoQueue(sc);
                case 5 -> demoStack(sc);
                case 6 -> demoPriorityQueue(sc);
                case 7 -> demoArrayListLinkedList(sc);
                case 8 -> demoTreeMap(sc);
                case 9 -> demoColeccionesObjetosComparator(sc);
                case 10 -> demoMapAnidadoStreams(sc);
                case 0 -> System.out.println("👋 Saliendo...");
                default -> System.out.println("❌ Opción no válida.");
            }
        } while (opcion != 0);
    }

    // * 📖 TEORÍA: ArrayList y LinkedList
    // ──────────────────────────────────────────────────────────────
    // ? ArrayList: lista dinámica, acceso rápido por índice, inserciones/eliminaciones lentas en medio.
    // ? LinkedList: lista doblemente enlazada, inserciones/eliminaciones rápidas en extremos, acceso lento por índice.
    public static void demoArrayListLinkedList(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE ArrayList y LinkedList");

        ArrayList<String> lista = new ArrayList<>();
        lista.add("uno"); lista.add("dos"); lista.add("tres");
        System.out.println("ArrayList: " + lista);

        LinkedList<String> linked = new LinkedList<>(lista);
        linked.addFirst("cero");
        linked.addLast("cuatro");
        System.out.println("LinkedList: " + linked);

        // ? Iterar con Iterator
        System.out.print("Iterando con Iterator: ");
        Iterator<String> it = linked.iterator();
        while (it.hasNext()) System.out.print(it.next() + " ");
        System.out.println();

        // ! ✅ TAREA: Elimina todos los elementos que contengan la letra 'o' usando Iterator.
    }

    // * 📖 TEORÍA: TreeMap
    // ──────────────────────────────────────────────────────────────
    // ? TreeMap es un mapa ordenado por clave (Comparable o Comparator).
    // ? Útil para rankings, agendas, diccionarios ordenados.
    // * 📖 TEORÍA: TreeMap
    // ──────────────────────────────────────────────────────────────
    // ? TreeMap es un mapa ordenado por clave (Comparable o Comparator).
    // ? Útil para rankings, agendas, diccionarios ordenados.
    public static void demoTreeMap(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE TreeMap (mapa ordenado)");

        TreeMap<String, Integer> ranking = new TreeMap<>();
        ranking.put("Ana", 90);
        ranking.put("Luis", 85);
        ranking.put("Marta", 95);
        ranking.put("Pedro", 80);

        System.out.println("Ranking ordenado: " + ranking);
        System.out.println("Primero: " + ranking.firstEntry());
        System.out.println("Último: " + ranking.lastEntry());

        // ! ✅ TAREA: Pide al usuario nombres y puntuaciones y muestra el ranking descendente.
    }

    // * 📖 TEORÍA: Colecciones de objetos y Comparator
    // ──────────────────────────────────────────────────────────────
    // ? Puedes almacenar objetos en colecciones y ordenarlos con Comparator.
    // ? Ejemplo: lista de alumnos ordenada por nota y por nombre.
    // * 📖 TEORÍA: Colecciones de objetos y Comparator
    // ──────────────────────────────────────────────────────────────
    // ? Puedes almacenar objetos en colecciones y ordenarlos con Comparator.
    // ? Ejemplo: lista de alumnos ordenada por nota y por nombre.
    public static void demoColeccionesObjetosComparator(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE Colecciones de objetos y Comparator");

        List<Alumno> alumnos = new ArrayList<>();
        alumnos.add(new Alumno("Ana", 8));
        alumnos.add(new Alumno("Luis", 7));
        alumnos.add(new Alumno("Marta", 9));
        alumnos.add(new Alumno("Pedro", 7));

        // ? Ordenar por nota descendente
        alumnos.sort(Comparator.comparingInt((Alumno a) -> a.nota).reversed());
        System.out.println("Por nota descendente: " + alumnos);

        // ? Ordenar por nombre alfabético
        alumnos.sort(Comparator.comparing(a -> a.nombre));
        System.out.println("Por nombre: " + alumnos);

        // ! ✅ TAREA: Busca el alumno con nota máxima usando streams.
    }

    // Clase Alumno para demoColeccionesObjetosComparator
    static class Alumno {
        String nombre; int nota;
        Alumno(String n, int no) { nombre = n; nota = no; }
        public String toString() { return nombre + "(" + nota + ")"; }
    }

    // * 📖 TEORÍA: Map anidado y streams
    // ──────────────────────────────────────────────────────────────
    // ? Puedes tener Map<String, Map<String, Integer>> para representar, por ejemplo, notas por asignatura.
    // ? Los streams permiten filtrar, mapear y reducir colecciones de forma funcional.
    // * 📖 TEORÍA: Map anidado y streams
    // ──────────────────────────────────────────────────────────────
    // ? Puedes tener Map<String, Map<String, Integer>> para representar, por ejemplo, notas por asignatura.
    // ? Los streams permiten filtrar, mapear y reducir colecciones de forma funcional.
    public static void demoMapAnidadoStreams(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE Map anidado y streams");

        Map<String, Map<String, Integer>> notas = new HashMap<>();
        Map<String, Integer> ana = new HashMap<>(); ana.put("Prog", 8); ana.put("BBDD", 7);
        Map<String, Integer> luis = new HashMap<>(); luis.put("Prog", 6); luis.put("BBDD", 9);
        notas.put("Ana", ana); notas.put("Luis", luis);

        // ? Mostrar todas las notas
        for (String alumno : notas.keySet()) {
            System.out.println(alumno + ": " + notas.get(alumno));
        }

        // ? Buscar la nota máxima de BBDD usando streams
        int maxBBDD = notas.values().stream().mapToInt(m -> m.getOrDefault("BBDD", 0)).max().orElse(0);
        System.out.println("Nota máxima en BBDD: " + maxBBDD);

        // ! ✅ TAREA: Muestra el nombre del alumno con mejor nota en Prog usando streams.
    }



    // * 📖 TEORÍA: HashSet
    // ──────────────────────────────────────────────────────────────
    // ? HashSet es una colección que NO permite elementos duplicados.
    // ? No garantiza el orden de los elementos.
    // ? Ideal para almacenar elementos únicos como DNI, emails, etc.
    public static void demoHashSet(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE HashSet");

        HashSet<String> conjunto = new HashSet<>();
        conjunto.add("Java");
        conjunto.add("Python");
        conjunto.add("C++");
        conjunto.add("Java"); // ❌ Duplicado

        System.out.println("📋 Elementos del conjunto:");
        for (String lenguaje : conjunto) {
            System.out.println("👉 " + lenguaje);
        }

        // ! ✅ TAREA: Pide al usuario nombres y muestra cuántos únicos hay.
    }

    // * 📖 TEORÍA: HashMap
    // ──────────────────────────────────────────────────────────────
    // ? HashMap almacena datos como pares clave-valor (key-value).
    // ? Ideal para representar diccionarios o relaciones como nombre → edad.
    public static void demoHashMap(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE HashMap");

        HashMap<String, Integer> edades = new HashMap<>();
        edades.put("Joaquín", 30);
        edades.put("Ana", 25);
        edades.put("Luis", 40);

        for (Map.Entry<String, Integer> entry : edades.entrySet()) {
            System.out.println("👤 " + entry.getKey() + " tiene " + entry.getValue() + " años.");
        }
        System.out.println("🎯 Edad de Ana: " + edades.get("Ana"));

        // ! ✅ TAREA: Haz un HashMap producto → precio y muestra los >10€.
    }

    // * 📖 TEORÍA: TreeSet
    // ──────────────────────────────────────────────────────────────
    // ? TreeSet es una colección ordenada que NO permite duplicados.
    // ? Ordena automáticamente los elementos de menor a mayor.
    public static void demoTreeSet(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE TreeSet");

        TreeSet<Integer> numeros = new TreeSet<>();
        numeros.add(5);
        numeros.add(2);
        numeros.add(10);
        numeros.add(5); // ❌ Duplicado

        System.out.println("📊 Números ordenados:");
        for (int num : numeros) {
            System.out.println("🔹 " + num);
        }
        System.out.println("📉 Mínimo: " + numeros.first());
        System.out.println("📈 Máximo: " + numeros.last());

        // ! ✅ TAREA: Pide 5 números por teclado, sin repetir, y muéstralos ordenados.
    }

    // * 📖 TEORÍA: Queue (Cola FIFO)
    // ──────────────────────────────────────────────────────────────
    // ? Una Queue es una estructura FIFO: el primero en entrar es el primero en salir.
    // ? LinkedList implementa Queue en Java.
    public static void demoQueue(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE Queue (Cola FIFO)");

        Queue<String> cola = new LinkedList<>();
        cola.offer("Tarea 1");
        cola.offer("Tarea 2");
        cola.offer("Tarea 3");

        System.out.println("📋 Cola inicial: " + cola);
        System.out.println("🚚 Atendiendo: " + cola.poll()); // Quita el primero
        System.out.println("📋 Cola tras atender: " + cola);

        // ! ✅ TAREA: Simula una cola de clientes y atiende a los 3 primeros.
    }

    // * 📖 TEORÍA: Stack (Pila LIFO)
    // ──────────────────────────────────────────────────────────────
    // ? Una pila (Stack) es LIFO: el último en entrar es el primero en salir.
    // ? Stack es una clase antigua, pero útil para ejemplos sencillos.
    public static void demoStack(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE Stack (Pila LIFO)");

        Stack<String> pila = new Stack<>();
        pila.push("Página 1");
        pila.push("Página 2");
        pila.push("Página 3");

        System.out.println("📋 Pila actual: " + pila);
        System.out.println("⬆️ Saliendo de: " + pila.pop()); // Quita el último
        System.out.println("📋 Pila tras pop: " + pila);

        // ! ✅ TAREA: Simula el historial de navegación de un navegador.
    }

    // * 📖 TEORÍA: PriorityQueue (Cola de prioridad)
    // ──────────────────────────────────────────────────────────────
    // ? Una PriorityQueue atiende primero al elemento con mayor prioridad (menor valor por defecto).
    // ? Útil para simulaciones, algoritmos de caminos mínimos, etc.
    public static void demoPriorityQueue(Scanner sc) {
        System.out.println("\n📦 DEMOSTRACIÓN DE PriorityQueue (Cola de prioridad)");

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(20);
        pq.offer(5);
        pq.offer(15);
        pq.offer(10);

        System.out.println("📋 Cola de prioridad inicial: " + pq);
        System.out.println("🏆 Atendiendo: " + pq.poll()); // Atiende el menor
        System.out.println("📋 Cola tras poll: " + pq);

        // ! ✅ TAREA: Crea una PriorityQueue de Strings y ordénala por longitud.
    }

}