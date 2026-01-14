/*
 * ******************************************************************************************
 *                        📚 TEORÍA Y CONCEPTOS: FUNCIONES AVANZADAS EN JAVA
 * ──────────────────────────────────────────────────────────────────────────────
 * En esta unidad avanzada aprenderás a:
 * 
 * ✅ Métodos estáticos vs de instancia (contexto de uso avanzado).
 * ✅ Parámetros varargs (argumentos de longitud variable).
 * ✅ Métodos sobrecargados y resolución de sobrecarga.
 * ✅ Recursión avanzada: pilas de llamadas, optimización.
 * ✅ Funciones de orden superior (paso de funciones como parámetros en Java).
 * ✅ Expresiones lambda y Streams (Java 8+).
 * ✅ Validación y manejo de errores en métodos.
 * ✅ Patrones de diseño: Builder, Factory, Strategy en métodos.
 * ✅ Tester y depuración de funciones.
 * 
 * 🚀 ¡Explora, experimenta y mejora el código!
 *
 * 🔎 CONTEXTO AVANZADO:
 * ──────────────────────────────────────────────────────────────
 * Las funciones en Java no son "ciudadanos de primera clase" como en lenguajes funcionales,
 * pero desde Java 8 podemos simular esto con interfaces funcionales y lambdas.
 * Veremos cómo escribir funciones más robustas, reutilizables y expresivas.
 ******************************************************************************************
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// * ======================================================================
// * INTERFACES FUNCIONALES: PASO DE COMPORTAMIENTO COMO PARÁMETRO
// * ======================================================================
// ? Una interfaz funcional tiene UN ÚNICO método abstracto.
// ? Permite pasar "funciones" como parámetros usando lambdas.

@FunctionalInterface
interface Operacion {
    double ejecutar(double a, double b);
}

@FunctionalInterface
interface Validador {
    boolean validar(int numero);
}

@FunctionalInterface
interface Transformador<T> {
    T transformar(T entrada);
}

// * ======================================================================
// * CLASE DE UTILIDAD: MÉTODOS ESTÁTICOS AVANZADOS
// * ======================================================================
class MathAvanzado {
    private MathAvanzado() {} // Constructor privado para evitar instanciación

    // * 📖 TEORÍA: Parámetros varargs (argumentos de longitud variable)
    // ──────────────────────────────────────────────────────────────────
    // ? Permite pasar un número variable de argumentos del mismo tipo.
    // ? Se define con ... (tres puntos) después del tipo.
    // ? Internamente se trata como un array.
    public static double sumarTodos(double... numeros) {
        double suma = 0.0;
        for (double num : numeros) suma += num;
        return suma;
    }

    // ! ✅ TAREA ALUMNO:
    // * Crea un método `promedioTodos(double... numeros)` que devuelva el promedio.
    // * Usa el método sumarTodos() internamente.

    public static double promedio(double... numeros) {
        if (numeros.length == 0) throw new IllegalArgumentException("Se requiere al menos un número");
        return sumarTodos(numeros) / numeros.length;
    }

    // * 📖 TEORÍA: Métodos con interfaces funcionales (paso de comportamiento)
    // ──────────────────────────────────────────────────────────────────
    // ? Permite pasar una "función" como parámetro.
    // ? La lambda define QUÉ hacer, no el método.
    public static double aplicarOperacion(double a, double b, Operacion op) {
        return op.ejecutar(a, b);
    }

    // * Aplicar validador a una lista
    public static List<Integer> filtrarConValidador(List<Integer> numeros, Validador v) {
        List<Integer> resultado = new ArrayList<>();
        for (int num : numeros) {
            if (v.validar(num)) resultado.add(num);
        }
        return resultado;
    }

    // * 📖 TEORÍA: Recursión avanzada - Fibonacci con memoización
    // ──────────────────────────────────────────────────────────────────
    // ? La memoización optimiza recursión almacenando resultados ya calculados.
    private static final java.util.Map<Integer, Long> memoFib = new java.util.HashMap<>();

    public static long fibonacciConMemo(int n) {
        if (n < 0) throw new IllegalArgumentException("n debe ser >= 0");
        if (n <= 1) return n;
        if (memoFib.containsKey(n)) return memoFib.get(n);

        long resultado = fibonacciConMemo(n - 1) + fibonacciConMemo(n - 2);
        memoFib.put(n, resultado);
        return resultado;
    }

    // ! ✅ TAREA ALUMNO:
    // * Implementa `factorialConMemo(int n)` usando la misma técnica.
    // * Compara tiempos de ejecución con factorial simple.

    public static long factorialConMemo(int n) {
        if (n < 0) throw new IllegalArgumentException("n debe ser >= 0");
        if (n == 0) return 1;
        return n * factorialConMemo(n - 1);
    }

    // * 📖 TEORÍA: Recursión avanzada - Búsqueda binaria
    // ──────────────────────────────────────────────────────────────────
    // ? Búsqueda eficiente en listas ordenadas.
    public static int busquedaBinaria(int[] arr, int objetivo, int izq, int der) {
        if (izq > der) return -1; // No encontrado
        int mid = izq + (der - izq) / 2;
        if (arr[mid] == objetivo) return mid;
        if (arr[mid] < objetivo) return busquedaBinaria(arr, objetivo, mid + 1, der);
        return busquedaBinaria(arr, objetivo, izq, mid - 1);
    }

    public static int busquedaBinaria(int[] arr, int objetivo) {
        Arrays.sort(arr);
        return busquedaBinaria(arr, objetivo, 0, arr.length - 1);
    }

    // * 📖 TEORÍA: Composición de funciones (encadenamiento)
    // ──────────────────────────────────────────────────────────────────
    // ? Aplicar múltiples transformaciones en secuencia.
    public static <T> T aplicarTransformaciones(T valor, List<Transformador<T>> transformadores) {
        for (Transformador<T> t : transformadores) valor = t.transformar(valor);
        return valor;
    }

    // ! ✅ TAREA ALUMNO:
    // * Crea transformadores lambda que: dupliquen, sumen 10, dividan por 2.
    // * Aplícalos en secuencia a un número y muestra resultado.
}

// * ======================================================================
// * CLASE DE ESTADÍSTICAS: MÉTODOS CON STREAMS Y LAMBDAS
// * ======================================================================
class Estadisticas {
    private Estadisticas() {}

    // * 📖 TEORÍA: Streams y operaciones funcionales
    // ──────────────────────────────────────────────────────────────────
    // ? Los Streams permiten operaciones funcionales sobre colecciones.
    public static double mediaDeNumeros(List<Double> numeros) {
        return numeros.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public static double sumaDeNumeros(List<Double> numeros) {
        return numeros.stream().mapToDouble(Double::doubleValue).sum();
    }

    // * Filtar, mapear y reducir
    public static List<Integer> filtrarPares(List<Integer> numeros) {
        return numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
    }

    public static List<Integer> elevarAlCuadrado(List<Integer> numeros) {
        return numeros.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    public static int sumaFiltrada(List<Integer> numeros) {
        return numeros.stream()
                .filter(n -> n > 0)
                .reduce(0, Integer::sum);
    }

    // ! ✅ TAREA ALUMNO:
    // * Implementa `contar(List<Integer> numeros, Validador v)` usando Stream.
    // * Implementa `transformarConStream(List<Integer>, Transformador)` genéricamente.
}

// * ======================================================================
// * PATRÓN BUILDER PARA VALIDACIÓN DE MÉTODOS
// * ======================================================================
class CalculadoraBuilder {
    private double valor;
    private List<String> historial = new ArrayList<>();

    public CalculadoraBuilder(double inicial) {
        this.valor = inicial;
        historial.add("Inicial: " + inicial);
    }

    // * Métodos encadenables que devuelven 'this'
    public CalculadoraBuilder sumar(double x) {
        valor += x;
        historial.add("+ " + x + " = " + valor);
        return this;
    }

    public CalculadoraBuilder restar(double x) {
        valor -= x;
        historial.add("- " + x + " = " + valor);
        return this;
    }

    public CalculadoraBuilder multiplicar(double x) {
        if (x == 0) throw new IllegalArgumentException("No se puede multiplicar por 0 en este contexto");
        valor *= x;
        historial.add("* " + x + " = " + valor);
        return this;
    }

    public CalculadoraBuilder dividir(double x) {
        if (x == 0) throw new ArithmeticException("División por cero");
        valor /= x;
        historial.add("/ " + x + " = " + valor);
        return this;
    }

    public double obtener() { return valor; }

    public void mostrarHistorial() {
        System.out.println("📋 Historial de operaciones:");
        for (String h : historial) System.out.println("  " + h);
    }

    // ! ✅ TAREA ALUMNO:
    // * Añade método `potencia(double x)` que eleve valor a la potencia x.
    // * Agrega `raizCuadrada()` y `raizCubica()`.
}

// * ======================================================================
// * PATRÓN ESTRATEGIA CON INTERFACES FUNCIONALES
// * ======================================================================
class ComparadorNumeros {
    private ComparadorNumeros() {}

    @FunctionalInterface
    interface Estrategia {
        int comparar(int a, int b);
    }

    // * Distintas estrategias de comparación
    public static final Estrategia MAYOR = (a, b) -> a > b ? 1 : (a < b ? -1 : 0);
    public static final Estrategia MENOR = (a, b) -> a < b ? 1 : (a > b ? -1 : 0);
    public static final Estrategia SUMA = (a, b) -> Integer.compare(a + b, 0);

    public static void ordenarConEstrategia(int[] arr, Estrategia est) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (est.comparar(arr[j], arr[j + 1]) > 0) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // ! ✅ TAREA ALUMNO:
    // * Crea una estrategia que ordene por valor absoluto.
    // * Crea otra que ordene por número de dígitos.
}

// * ======================================================================
// * MÉTODO PRINCIPAL CON DEMOSTRACIONES
// * ======================================================================
public class UT4_FuncionesAvanzado {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║       📚 FUNCIONES AVANZADAS EN JAVA - DEMOSTRACIONES    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        // ✅ 1. VARARGS
        System.out.println("\n🔹 VARARGS: Sumar múltiples números");
        double sumaTotal = MathAvanzado.sumarTodos(1.5, 2.3, 3.7, 4.1, 5.9);
        System.out.println("  Suma: " + sumaTotal);
        double prom = MathAvanzado.promedio(10, 20, 30, 40, 50);
        System.out.println("  Promedio: " + prom);

        // ✅ 2. INTERFACES FUNCIONALES Y LAMBDAS
        System.out.println("\n🔹 INTERFACES FUNCIONALES: Operaciones con lambdas");
        Operacion suma = (a, b) -> a + b;
        Operacion multiplicacion = (a, b) -> a * b;
        Operacion potencia = (a, b) -> Math.pow(a, b);

        System.out.println("  10 + 5 = " + MathAvanzado.aplicarOperacion(10, 5, suma));
        System.out.println("  10 * 5 = " + MathAvanzado.aplicarOperacion(10, 5, multiplicacion));
        System.out.println("  2 ^ 8 = " + MathAvanzado.aplicarOperacion(2, 8, potencia));

        // ✅ 3. FILTRADO CON VALIDADOR
        System.out.println("\n🔹 VALIDADORES: Filtrar números según criterio");
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Validador esPar = n -> n % 2 == 0;
        Validador esMayorQue5 = n -> n > 5;
        Validador esPrimo = n -> {
            if (n < 2) return false;
            for (int i = 2; i <= Math.sqrt(n); i++) if (n % i == 0) return false;
            return true;
        };

        System.out.println("  Números pares: " + MathAvanzado.filtrarConValidador(numeros, esPar));
        System.out.println("  Números > 5: " + MathAvanzado.filtrarConValidador(numeros, esMayorQue5));
        System.out.println("  Números primos: " + MathAvanzado.filtrarConValidador(numeros, esPrimo));

        // ✅ 4. RECURSIÓN CON MEMOIZACIÓN
        System.out.println("\n🔹 MEMOIZACIÓN: Fibonacci optimizado");
        long inicio = System.currentTimeMillis();
        long fib30 = MathAvanzado.fibonacciConMemo(30);
        long tiempo = System.currentTimeMillis() - inicio;
        System.out.println("  Fibonacci(30) = " + fib30 + " (tiempo: " + tiempo + "ms)");

        // ✅ 5. BÚSQUEDA BINARIA
        System.out.println("\n🔹 BÚSQUEDA BINARIA: Encontrar elemento en array");
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        int posicion = MathAvanzado.busquedaBinaria(arr, 5);
        System.out.println("  Posición de 5 en array ordenado: " + posicion);

        // ✅ 6. STREAMS Y OPERACIONES FUNCIONALES
        System.out.println("\n🔹 STREAMS: Operaciones funcionales sobre listas");
        List<Double> valores = Arrays.asList(2.5, 3.7, 1.2, 9.8, 4.5);
        System.out.println("  Media: " + Estadisticas.mediaDeNumeros(valores));
        System.out.println("  Suma: " + Estadisticas.sumaDeNumeros(valores));

        List<Integer> numeros2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println("  Pares de [1..6]: " + Estadisticas.filtrarPares(numeros2));
        System.out.println("  Cuadrados: " + Estadisticas.elevarAlCuadrado(numeros2));

        // ✅ 7. PATRÓN BUILDER CON FLUJO ENCADENADO
        System.out.println("\n🔹 BUILDER PATTERN: Calculadora con historial");
        try {
            double resultado = new CalculadoraBuilder(100)
                    .sumar(50)
                    .multiplicar(2)
                    .restar(30)
                    .dividir(5)
                    .obtener();
            System.out.println("  Resultado final: " + resultado);
            new CalculadoraBuilder(100)
                    .sumar(50)
                    .multiplicar(2)
                    .restar(30)
                    .dividir(5)
                    .mostrarHistorial();
        } catch (Exception e) {
            System.out.println("  ❌ Error: " + e.getMessage());
        }

        // ✅ 8. PATRÓN ESTRATEGIA
        System.out.println("\n🔹 ESTRATEGIA: Ordenar array de distintas formas");
        int[] arr1 = {5, 2, 9, 1, 7};
        ComparadorNumeros.ordenarConEstrategia(arr1, ComparadorNumeros.MAYOR);
        System.out.println("  Ordenado (mayor primero): " + Arrays.toString(arr1));

        int[] arr2 = {5, 2, 9, 1, 7};
        ComparadorNumeros.ordenarConEstrategia(arr2, ComparadorNumeros.MENOR);
        System.out.println("  Ordenado (menor primero): " + Arrays.toString(arr2));

        // ! ✅ TAREA ALUMNO INTEGRADA:
        System.out.println("\n🔹 DESAFÍO INTEGRADO (tú lo haces):");
        System.out.println("  1. Crea un método `aplicarMultiplesFiltros()` que use varargs de Validadores.");
        System.out.println("  2. Implementa `componer(Transformador<T>... transformadores)` para pipeline.");
        System.out.println("  3. Añade método estadístico `desviacionEstandar(List<Double>)` con Stream.");

        System.out.println("\n✅ Demostraciones completadas.");
    }
}

/*
 * ******************************************************************************************
 * ✅ TAREAS AVANZADAS PARA EL ALUMNO
 * ──────────────────────────────────────────────────────────────
 * 1️⃣ Crea un método `generarSecuencia(int n, Transformador<Integer> t)` que aplique
 *    una transformación n veces y devuelva una lista de resultados.
 *
 * 2️⃣ Implementa `reducir(List<T>, T inicial, BiFunction<T,T,T> operacion)` genéricamente.
 *    Ejemplo: reducir([1,2,3], 0, (a,b) -> a+b) = 6
 *
 * 3️⃣ Crea una interfaz `Predicado<T>` y un método `contar(List<T>, Predicado<T>)`.
 *
 * 4️⃣ Implementa `quickSort(int[] arr, Estrategia est)` recursivo usando patrón estrategia.
 *
 * 5️⃣ Optimiza Fibonacci con matriz exponencial (O(log n)) en lugar de memoización.
 *
 * 6️⃣ Crea un método `temporizador(Runnable tarea, String nombre)` que mida tiempo de ejecución.
 *
 * 7️⃣ Implementa `cache(Transformador<T> t)` que almacene resultados ya calculados.
 *
 * 8️⃣ Añade decoradores: `logarear(Operacion)`, `cronometrar(Operacion)` que envuelvan
 *    una operación y añadan comportamiento.
 *
 * 9️⃣ Crea una calculadora que interprete expresiones (ej: "3 + 5 * 2") usando recursión
 *    descendente (parser).
 *
 * 🔟 Refactoriza todos los ejemplos para usar java.util.function (Function, BiFunction,
 *    Predicate, Consumer) en lugar de interfaces propias.
 *
 * 🧪 EXTRA (OPCIONAL): Implementa un mini-framework de validación encadenada:
 *    new Validador()
 *        .minimo(0).maximo(100).esNumero().conMensaje("Nota entre 0 y 100")
 *        .validar(entrada);
 **********************************************************************************
 */
