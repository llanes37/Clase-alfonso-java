/*
 * ******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - AUTOR: Joaquín Rodríguez Llanes
 *  📅 FECHA: 2025
 *  🔹 UNIDAD 3 AVANZADO: BUCLES EN JAVA (EJERCICIOS Y RETOS)
 *  � REPOSITORIO PRIVADO EN GITHUB (USO EDUCATIVO EXCLUSIVO)
 *  ❌ PROHIBIDA SU DISTRIBUCIÓN SIN PERMISO DEL AUTOR
 * ******************************************************************************************
 */

/*
 * ******************************************************************************************
 *                        📚 **TEORÍA Y CONCEPTOS: BUCLES AVANZADOS EN JAVA**
 * ──────────────────────────────────────────────────────────────────────────────
 * En esta práctica avanzada trabajaremos en profundidad los bucles y patrones comunes:
 *
 * ✅ Bucles anidados: técnicas y costes (complejidad O(n*m)).
 * ✅ Control de flujo avanzado: `break`, `continue`, etiquetas (labels) y su uso responsable.
 * ✅ Iteradores vs for-each: cuándo usar Iterator para eliminar elementos de forma segura.
 * ✅ Patrones de salida y optimización: StringBuilder, evitar concatenaciones en bucles.
 * ✅ Matrices y submatrices: recorrido, rotación y búsqueda eficiente.
 * ✅ Problemas clásicos con enfoque eficiente: FizzBuzz extendido, cribado, Kadane, rotación.
 * ✅ Buenas prácticas: validación de entrada, manejo de excepciones y modularización de funciones.
 *
 * 🚀 Objetivo: dominar estructuras repetitivas y aplicar técnicas para resolver problemas
 * de complejidad media con código limpio y comentado.
 *
 * 🧭 Nivel: intermedio-avanzado — ejercicios con un pequeño aumento de dificultad respecto al UT3 básico.
 ******************************************************************************************
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/*
 * 🔵 ¿CÓMO EJECUTAR SOLO UN FRAGMENTO DE CÓDIGO? (Consejos prácticos)
 * ──────────────────────────────────────────────────────────────
 * - Visual Studio Code: usa la extensión "Code Runner" o ejecuta el archivo .java.
 *   Para probar solo una parte, comenta el resto con /* ... * / o usa `System.exit(0)`.
 * - IntelliJ IDEA / NetBeans: configura Run Configuration para ejecutar solo la clase actual.
 * - Consejo: en pruebas, crea métodos pequeños y ejecuta sólo el método desde `main`.
 */

public class UT3_BuclesAvanzado {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   📚 BUCLES AVANZADO - DEMOSTRACIONES Y EJERCICIOS  ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        // Demostraciones rápidas
        demoForAnidado();
        demoForEachAndIterator();
        demoEtiquetasBreakContinue();

        // Ejercicios interactivos y retos
        System.out.println("\n🔸 EJERCICIO 1: Pirámide de asteriscos (variable)");
        System.out.print("Tamaño de la pirámide (entero positivo): ");
        int n = leerEnteroPositivo(sc);
        imprimirPiramide(n);

        System.out.println("\n🔸 EJERCICIO 2: FizzBuzz extendido (3,5,7)");
        System.out.print("Límite (entero positivo): ");
        int lim = leerEnteroPositivo(sc);
        fizzBuzzExtendido(lim);

        System.out.println("\n🔸 EJERCICIO 3: Criba de Eratóstenes (primos hasta N)");
        System.out.print("Calcular primos hasta (entero >=2): ");
        int upTo = leerEnteroMinimo(sc, 2);
        List<Integer> primos = cribaEratostenes(upTo);
        System.out.println("Primos hasta " + upTo + ": " + primos);

        System.out.println("\n🔸 EJERCICIO 4: Buscar secuencia en matriz (sub-matriz)");
        int[][] matriz = {
                {1,2,3,4,5},
                {6,7,8,9,10},
                {11,12,13,14,15},
                {16,17,18,19,20}
        };
        System.out.println("Matriz de ejemplo:");
        imprimirMatriz(matriz);
        System.out.println("Buscaremos la submatriz 2x2 [7,8;12,13]");
        int[][] sub = {{7,8},{12,13}};
        boolean encontrada = buscarSubMatriz(matriz, sub);
        System.out.println("Submatriz encontrada: " + encontrada);

    // DEMO de utilidades implementadas
    System.out.println("\n🔹 DEMO EXTRA: pirámide invertida y operaciones con matrices");
    imprimirPiramideInvertida(n);

    System.out.println("\nGenerando matriz aleatoria 4x5 (max 20):");
    int[][] aleat = generarMatrizAleatoria(4,5,20);
    imprimirMatriz(aleat);
    System.out.println("Buscar valor 10 optimizado (asumiendo filas ordenadas): " + buscarValorEnMatrizOptima(aleat, 10));

    int[] ejemplo = { -2, -3, 4, -1, -2, 1, 5, -3 };
    int[] resKadane = kadaneMaxSubarray(ejemplo);
    System.out.println("Kadane -> maxSum: " + resKadane[0] + " inicio=" + resKadane[1] + " fin=" + resKadane[2]);

    System.out.println("\nRotando matriz de ejemplo 90 grados:");
    int[][] rotada = rotarMatriz90(matriz);
    imprimirMatriz(rotada);

        // Ejercicio de rendimiento/optimización (pequeño reto)
        System.out.println("\n🔸 RETO: Comparar búsqueda lineal y búsqueda optimizada (ver comentarios)");
        // ! ✅ TAREA ALUMNO:
        // * Implementa `buscarValorEnMatrizOptima(int[][], int)` que aproveche orden si la matriz está ordenada.
        // * Mide tiempos con System.nanoTime() y compara.

        // Cerrar scanner
        sc.close();

        System.out.println("\n✅ Práctica avanzada finalizada. Revisa los métodos y tareas para el alumno.");
    }

    // * ------------------------- HELPERS DE LECTURA -------------------------
    private static int leerEnteroPositivo(Scanner sc) {
        // Bucle hasta que el usuario introduzca un entero válido > 0
        while (true) {
            try {
                // sc.nextLine() lee la línea completa; trim() quita espacios alrededor
                int v = Integer.parseInt(sc.nextLine().trim());

                // Validación: si v <= 0 pedimos de nuevo, en caso contrario devolvemos
                if (v <= 0) System.out.print("Introduce un entero POSITIVO: ");
                else return v;
            } catch (NumberFormatException e) {
                // Capturamos formatos no numéricos y solicitamos reintento
                System.out.print("Entrada inválida. Introduce un entero: ");
            }
        }
    }

    private static int leerEnteroMinimo(Scanner sc, int min) {
        // Bucle hasta que el usuario introduzca un entero >= min
        while (true) {
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v < min) System.out.print("Introduce un entero >= " + min + ": ");
                else return v;
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Introduce un entero: ");
            }
        }
    }

    // * ------------------------- DEMOS -------------------------
    // Demo: bucle for anidado y construcción eficiente con StringBuilder
    private static void demoForAnidado() {
        // Mostramos un encabezado para identificar la demo
        System.out.println("🔹 DEMO: for anidado (tabla de multiplicar 1..5)");

        // StringBuilder es más eficiente que concatenar Strings en un bucle
        // porque evita crear muchos objetos intermedios.
        StringBuilder sb = new StringBuilder();

        // Bucle exterior: para cada fila (1..5)
        for (int i = 1; i <= 5; i++) {
            // Bucle interior: para cada columna (1..5)
            for (int j = 1; j <= 5; j++) {
                // Calculamos el producto y lo formateamos con ancho 2
                // String.format("%2d ", ...) deja alineado el número
                sb.append(String.format("%2d ", i * j));
            }
            // Añadimos un salto de línea al terminar la fila
            sb.append("\n");
        }

        // Imprimimos la tabla completa construida en el StringBuilder
        System.out.println(sb.toString());
    }

    // Demo: for-each y uso de Iterator cuando necesitamos remover elementos durante iteración
    private static void demoForEachAndIterator() {
        System.out.println("🔹 DEMO: for-each vs Iterator (remover pares)");

        // Creamos una lista con números del 1 al 10
        List<Integer> nums = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        System.out.println("Original: " + nums);

        // Si queremos eliminar elementos durante la iteración, Iterator.remove() es la forma segura.
        Iterator<Integer> it = nums.iterator();
        while (it.hasNext()) {
            int v = it.next();      // obtenemos siguiente elemento
            if (v % 2 == 0) it.remove(); // eliminamos si es par (seguro con Iterator)
        }

        System.out.println("Después de remover pares con Iterator: " + nums);

        // Nota: intentar eliminar con for-each produce ConcurrentModificationException
        // porque el for-each oculta el Iterator y la estructura se modifica mientras se recorre.
        // ! TAREA: intenta remover usando for-each en un programa separado y observa el error.
    }

    // Demo: etiquetas (labels) para controlar bucles anidados
    private static void demoEtiquetasBreakContinue() {
        System.out.println("🔹 DEMO: break/continue con etiquetas (labels)");

        // Uso de etiqueta (label) para poder salir de bucles anidados desde el interior
        outer:
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 5; j++) {
                // Caso de control: cuando i==2 y j==3 hacemos break con etiqueta
                if (i == 2 && j == 3) {
                    System.out.println("  Encontrado i=2,j=3 -> break outer");
                    // break outer rompe el bucle marcado con 'outer' (sale de ambos bucles)
                    break outer;
                }
                // Si no se cumple la condición, imprimimos indices i y j
                System.out.println("  i=" + i + ", j=" + j);
            }
        }
        // Observación: etiquetar bucles puede mejorar claridad en casos concretos, pero abusar
        // de labels reduce legibilidad; úsalo con moderación.
    }

    // * ------------------------- EJERCICIO 1 -------------------------
    // Imprime una pirámide de asteriscos de altura n
    private static void imprimirPiramide(int n) {
        // Cabecera
        System.out.println("PIRÁMIDE (altura " + n + "):");

        // Bucle por nivel (1..n). Para cada nivel:
        // - imprimimos (n-i) espacios a la izquierda para centrar
        // - imprimimos (2*i-1) asteriscos para formar la fila
        for (int i = 1; i <= n; i++) {
            // Imprime espacios iniciales (alineación)
            for (int s = 0; s < n - i; s++) System.out.print(" ");

            // Imprime la cantidad correcta de asteriscos para la fila i
            for (int k = 0; k < (2 * i - 1); k++) System.out.print("*");

            // Salto a la siguiente línea tras completar la fila
            System.out.println();
        }
    }

    // * ------------------------- EJERCICIO 2 -------------------------
    // FizzBuzz extendido: si divisible por 3->Fizz, 5->Buzz, 7->Bazz; combinar etiquetas concatenadas.
    private static void fizzBuzzExtendido(int lim) {
        // Recorremos los números del 1 al límite
        for (int i = 1; i <= lim; i++) {
            // Construimos la salida en una cadena (evitamos múltiples prints para legibilidad)
            String out = "";

            // Concatenamos etiquetas según divisibilidad
            if (i % 3 == 0) out += "Fizz";   // divisible por 3
            if (i % 5 == 0) out += "Buzz";   // divisible por 5
            if (i % 7 == 0) out += "Bazz";   // divisible por 7

            // Si no hemos añadido etiquetas, imprimimos el número
            if (out.isEmpty()) System.out.print(i + " "); else System.out.print(out + " ");

            // Cada 20 elementos imprimimos un salto para mejorar legibilidad en consola
            if (i % 20 == 0) System.out.println();
        }
        System.out.println();
    }

    // * ------------------------- EJERCICIO 3 -------------------------
    // Criba de Eratóstenes: devolver lista de primos hasta n (sencillo y eficiente)
    private static List<Integer> cribaEratostenes(int n) {
        // Marcado de no-primos (false = potencial primo)
        boolean[] marcado = new boolean[n + 1];
        Arrays.fill(marcado, false);

        // Empezamos en p=2 y llegamos hasta sqrt(n)
        // Observación: los múltiplos menores que p*p ya fueron marcados por primos menores
        for (int p = 2; p * p <= n; p++) {
            if (!marcado[p]) {
                // Marcamos todos los múltiplos de p comenzando en p*p
                for (int mult = p * p; mult <= n; mult += p) marcado[mult] = true;
            }
        }

        // Recolectamos los no-marcados (primos)
        List<Integer> primos = new ArrayList<>();
        for (int i = 2; i <= n; i++) if (!marcado[i]) primos.add(i);
        return primos; // Complejidad aproximada: O(n log log n)
    }

    // * ------------------------- EJERCICIO 4 -------------------------
    // Buscar submatriz: comprobación simple de igualdad de bloques contiguos
    private static boolean buscarSubMatriz(int[][] matriz, int[][] sub) {
        // Tamaños de matriz principal y submatriz
        int m = matriz.length;       // filas matriz principal
        int n = matriz[0].length;    // columnas matriz principal
        int a = sub.length;          // filas submatriz
        int b = sub[0].length;       // columnas submatriz

        // Recorremos todas las posiciones posibles donde la submatriz puede encajar
        for (int i = 0; i <= m - a; i++) {
            for (int j = 0; j <= n - b; j++) {
                boolean ok = true; // supondremos que la submatriz coincide en (i,j)

                // Comprobamos todos los elementos de la submatriz
                for (int x = 0; x < a && ok; x++) {
                    for (int y = 0; y < b; y++) {
                        // Si cualquier celda no coincide, marcamos ok=false y salimos
                        if (matriz[i + x][j + y] != sub[x][y]) { ok = false; break; }
                    }
                }

                // Si ok se mantiene true, hemos encontrado la submatriz
                if (ok) return true;
            }
        }
        // No encontrada
        return false;
    }

    private static void imprimirMatriz(int[][] mat) {
        for (int[] row : mat) {
            for (int v : row) System.out.printf("%3d", v);
            System.out.println();
        }
    }

    // * ------------------------- UTILIDADES Y RETOS -------------------------
    // Buscar un valor en matriz (versión sencilla)
    private static boolean buscarValorEnMatriz(int[][] mat, int valor) {
        // Recorrido completo O(rows*cols). Devuelve true en la primera coincidencia.
        for (int i = 0; i < mat.length; i++)
            for (int j = 0; j < mat[0].length; j++)
                if (mat[i][j] == valor) return true; // early return evita trabajo innecesario
        return false; // no encontrado tras recorrer toda la matriz
    }

    // * ------------------------- IMPLEMENTACIONES ADICIONALES -------------------------
    // Buscar valor en matriz optimizada: asumimos cada fila está ordenada y usamos búsqueda binaria por fila
    private static boolean buscarValorEnMatrizOptima(int[][] mat, int valor) {
        // Esta versión asume que cada fila está previamente ordenada.
        // Recorre filas y usa Arrays.binarySearch (O(log cols) por fila), coste total O(rows * log cols).
        for (int i = 0; i < mat.length; i++) {
            int idx = Arrays.binarySearch(mat[i], valor);
            if (idx >= 0) return true; // encontrado en la fila i
        }
        return false; // no encontrado
    }

    // Generar matriz aleatoria
    private static int[][] generarMatrizAleatoria(int filas, int cols, int max) {
        // Generamos valores aleatorios en rango [0..max]
        int[][] res = new int[filas][cols];
        for (int i = 0; i < filas; i++)
            for (int j = 0; j < cols; j++)
                res[i][j] = (int) (Math.random() * (max + 1));
        return res;
    }

    // Imprimir pirámide invertida
    private static void imprimirPiramideInvertida(int n) {
        // Cabecera
        System.out.println("PIRÁMIDE INVERTIDA (altura " + n + "):");

        // Recorremos desde la fila superior (n) a la inferior (1)
        for (int i = n; i >= 1; i--) {
            // Espacios iniciales: aumentan conforme disminuye i
            for (int s = 0; s < n - i; s++) System.out.print(" ");
            // Asteriscos: (2*i - 1)
            for (int k = 0; k < (2 * i - 1); k++) System.out.print("*");
            System.out.println();
        }
    }

    // Kadane: subarray de suma máxima (retorna {maxSum, inicio, fin})
    private static int[] kadaneMaxSubarray(int[] arr) {
        // maxSoFar: mejor suma encontrada hasta ahora
        // maxEndingHere: suma del subarray que termina en la posición actual
        int maxSoFar = Integer.MIN_VALUE, maxEndingHere = 0;

        // start/end: índices del subarray óptimo; s es índice candidato para inicio
        int start = 0, end = 0, s = 0;

        for (int i = 0; i < arr.length; i++) {
            // expandimos la suma actual con arr[i]
            maxEndingHere += arr[i];

            // si la suma actual mejora la mejor conocida, actualizamos
            if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
                start = s; end = i; // guardamos el intervalo
            }

            // si la suma actual cae por debajo de 0, la reiniciamos y movemos el candidato de inicio
            if (maxEndingHere < 0) {
                maxEndingHere = 0; s = i + 1;
            }
        }
        return new int[]{maxSoFar, start, end};
    }

    // Rotar matriz 90 grados (devuelve nueva matriz)
    private static int[][] rotarMatriz90(int[][] mat) {
        int r = mat.length;       // filas
        int c = mat[0].length;    // columnas

        // La matriz rotada tendrá dimensiones c x r
        int[][] res = new int[c][r];

        // mapeo de índices: (i,j) -> (j, r-1-i)
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                res[j][r - 1 - i] = mat[i][j];
        return res;
    }

    // ! ✅ TAREAS PARA EL ALUMNO (INTERCALADAS):
    // 1. Optimización: implementa `buscarValorEnMatrizOptima(int[][], int)` asumiendo que cada fila
    //    está ordenada y usa búsqueda binaria por fila.
    // 2. Crea una función `generarMatrizAleatoria(int filas, int cols, int max)` y mide tiempo de búsqueda.
    // 3. Implementa `imprimirPirámideInvertida(int n)` y usa loops anidados de forma diferente.
    // 4. Resuelve el problema "subarray con suma máxima" (Kadane) usando bucles y obtiene índice inicio/fin.
    // 5. Implementa `rotarMatriz90(int[][])` que rote la matriz en 90 grados (in-place si te atreves).
    // 6. Añade tests simples (método main que llame a funciones y valide resultados esperados).

    /*
     * RECOMENDACIONES (lectura rápida):
     * - Evita concatenar Strings en bucles; usa StringBuilder para colecciones grandes.
     * - Comprueba condiciones límite antes de entrar en bucles anidados para ahorrar trabajo.
     * - Para matrices grandes, piensa en la complejidad O(n*m) y cuándo puedes aplicar heurísticas.
     */
}
