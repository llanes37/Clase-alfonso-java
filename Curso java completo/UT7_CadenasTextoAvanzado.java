/*
 * ******************************************************************************************
 *  📚 CURSO DE PROGRAMACIÓN EN JAVA - AUTOR: Joaquín Rodríguez Llanes
 *  📅 FECHA: 2025
 *  🔹 UNIDAD 7: Cadenas de Texto (Strings) - AVANZADO
 *  🔐 REPOSITORIO PRIVADO EN GITHUB (USO EDUCATIVO EXCLUSIVO)
 * ******************************************************************************************
 */

import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UT7_CadenasTextoAvanzado {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8.name());


    // * 🧠 TEORÍA AVANZADA: Cadenas en Java
    // -----------------------------------------------------
    // ? Una cadena (String) es un objeto inmutable: cada vez que la modificas, se crea una nueva.
    // ? Para manipular texto de forma eficiente (muchas modificaciones), usa StringBuilder o StringBuffer.
    // ? Temas avanzados:
    //      - Expresiones regulares (regex) para búsquedas y validaciones complejas.
    //      - Normalización Unicode: comparar y limpiar texto con acentos y símbolos.
    //      - Locales y Collator: ordenación y comparación según idioma.
    //      - Codificación (UTF-8, Base64) y formatos (String.format, MessageFormat).
    //      - Optimización de memoria: intern(), pooling de cadenas.
    //
    // ! ⚠️ Recuerda: los métodos de String NO modifican el original, siempre devuelven uno nuevo.


        System.out.println("\n🔹 UT7 - Cadenas de Texto (AVANZADO) - Demo rápida\n");


    // * 🔵 EJEMPLO 1: Métodos avanzados de String
    ejemploMetodosAvanzados();

    // * 🔵 EJEMPLO 2: Expresiones regulares (regex)
    ejemploRegex();

    // * 🔵 EJEMPLO 3: Normalización y comparación avanzada
    ejemploNormalizacionYLocales();

    // * 🔵 EJEMPLO 4: Rendimiento y concatenación
    ejemploStringBuilderYPerformance();

    // * 🔵 EJEMPLO 5: Codificación y Base64
    ejemploCodificacionBase64();

    // * 🔵 EJEMPLO 6: join, split y formato
    ejemplosJoinSplitFormat();


        // * � EJERCICIO FINAL:
        // ? Pide al usuario que ingrese una frase y luego:
        //      - Muestra cuántas palabras tiene.
        //      - Indica si contiene una palabra clave como "java" (ignorando mayúsculas y acentos).
        //      - Muestra la frase en mayúsculas y normalizada.
        // 🔽 Debajo puedes desarrollar el código:
        // -----------------------------------------------------
        System.out.println("👉 Ingresa una frase para analizarla: ");
        String frase = sc.nextLine();
        String fraseNorm = normalizar(frase);
        String[] palabras = fraseNorm.trim().split("\\s+");
        System.out.println("📌 Palabras detectadas: " + palabras.length);
        System.out.println("📌 ¿Contiene 'java'? " + (fraseNorm.toLowerCase().contains("java") ? "Sí" : "No"));
        System.out.println("📌 En mayúsculas: " + fraseNorm.toUpperCase());

        // ! ✅ TAREAS PARA EL ALUMNO:
        // * 1) Modifica el código para que la búsqueda de la palabra clave sea insensible a acentos y mayúsculas.
        // * 2) Haz que el usuario pueda introducir varias frases y se analicen todas.
        // * 3) Extrae todas las palabras únicas y muéstralas ordenadas alfabéticamente.

        sc.close();

    }

    // -----------------------------------------------------
    // Ejemplo: métodos y utilidades avanzadas de String
    // -----------------------------------------------------
    // * 🔵 EJEMPLO 1: Métodos avanzados de String
    public static void ejemploMetodosAvanzados() {
        System.out.println("\n🔹 EJEMPLO 1: Métodos avanzados de String");

        // ? Creamos una cadena con acentos, tabulador y salto de línea
        String s = " \u00E1rbol \t - Java \n"; // contiene acento, tab, newline
        System.out.println("📌 Original (raw): '" + s + "'");
        System.out.println("📌 trim() -> '" + s.trim() + "'");
        System.out.println("📌 replaceAll (regex para espacios múltiples) -> '" + s.replaceAll("\\s+", " ").trim() + "'");

        // ? substring seguro (evitar IndexOutOfBounds)
        int from = Math.max(0, Math.min(2, s.length()));
        int to = Math.max(from, Math.min(s.length(), from + 6));
        System.out.println("📌 substring seguro: '" + s.substring(from, to) + "'");

        // ? intern() - cuándo y por qué
        String a = new String("hola");
        String b = "hola";
        System.out.println("📌 a==b antes intern? " + (a == b));
        a = a.intern();
        System.out.println("📌 a==b después intern? " + (a == b));

        // ! ✅ TAREA PARA EL ALUMNO:
        // * Declara tu propio String con espacios y acentos, usa .trim(), .replaceAll y .substring.
        // * Explica en un párrafo cuándo usar intern() y sus riesgos (permgen/metaspace históricos).
    }

    // -----------------------------------------------------
    // Regex: Pattern y Matcher - ejemplos prácticos
    // -----------------------------------------------------
    // * 🔵 EJEMPLO 2: Expresiones regulares (Pattern, Matcher)
    public static void ejemploRegex() {
        System.out.println("\n🔹 EJEMPLO 2: Expresiones regulares (Pattern, Matcher)");

        // ? Texto de ejemplo con email, teléfono y URL
        String texto = "Contacto: Ana <ana@mail.com>, Juan (600-123-456), web: https://ejemplo.com";

        // ? Encontrar emails
        Pattern emailPat = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        Matcher m = emailPat.matcher(texto);
        System.out.println("📌 Emails encontrados:");
        while (m.find()) {
            System.out.println("   - " + m.group());
        }

        // ? Encontrar teléfonos (simple)
        Pattern telPat = Pattern.compile("\\b\\d{3}[- ]?\\d{3}[- ]?\\d{3,4}\\b");
        m = telPat.matcher(texto);
        System.out.println("📌 Teléfonos encontrados:");
        while (m.find()) {
            System.out.println("   - " + m.group());
        }

        // ? Uso de MatchResult para obtener posiciones
        System.out.println("📌 Matches con MatchResult:");
        m = emailPat.matcher(texto);
        while (m.find()) {
            MatchResult mr = m.toMatchResult();
            System.out.println(String.format("   start=%d end=%d value=%s", mr.start(), mr.end(), mr.group()));
        }

        // ? Reemplazo con grupos
        String nombreNormalizado = texto.replaceAll("(\\w+)\\s*<([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})>", "$1 [$2]");
        System.out.println("📌 Texto con emails normalizados: " + nombreNormalizado);

        // ! ✅ TAREA PARA EL ALUMNO:
        // * Escribe una regex que capture fechas en formato dd/mm/yyyy y valide valores válidos (día/mes).
        // * Modifica el patrón de teléfono para aceptar prefijos internacionales.
    }

    // -----------------------------------------------------
    // Normalización Unicode y Locales / Collator
    // -----------------------------------------------------
    // * 🔵 EJEMPLO 3: Normalización Unicode y Collator (local-aware)
    public static void ejemploNormalizacionYLocales() {
        System.out.println("\n🔹 EJEMPLO 3: Normalización Unicode y Collator (local-aware)");

        // ? Dos cadenas, una con acento y otra sin
        String s1 = "café"; // con acento
        String s2 = "cafe"; // sin acento

        System.out.println("📌 s1.equals(s2)? " + s1.equals(s2));
        System.out.println("📌 normalizar(s1).equals(normalizar(s2))? " + normalizar(s1).equals(normalizar(s2)));

        // ? Collator para comparación respetando reglas de idioma
        Collator colEs = Collator.getInstance(Locale.forLanguageTag("es-ES"));
        colEs.setStrength(Collator.PRIMARY); // ignora acentos
        System.out.println("📌 Collator (es) compare 'café' vs 'cafe' => " + colEs.compare(s1, s2));

        // ? Ordenar una lista con Collator
        List<String> nombres = new ArrayList<>();
        nombres.add("Óscar");
        nombres.add("Ana");
        nombres.add("álvaro");
        nombres.add("Zoe");
        nombres.sort(colEs);
        System.out.println("📌 Orden respetando collator (es): " + nombres);

        // ! ✅ TAREA PARA EL ALUMNO:
        // * Implementa una función 'equalsIgnorandoAcentos' usando Normalizer y Collator.
        // * Prueba a ordenar una lista de palabras con y sin acentos y observa el resultado.
    }

    // Normalizar (quita diacríticos y normaliza forma)
    public static String normalizar(String input) {
        if (input == null) return null;
        String n = Normalizer.normalize(input, Normalizer.Form.NFD);
        // eliminar marcas diacríticas
        n = n.replaceAll("\\p{M}", "");
        return n;
    }

    // -----------------------------------------------------
    // StringBuilder vs concatenación: ejemplo de rendimiento
    // -----------------------------------------------------
    // * 🔵 EJEMPLO 4: StringBuilder vs concatenación: rendimiento
    public static void ejemploStringBuilderYPerformance() {
        System.out.println("\n🔹 EJEMPLO 4: StringBuilder y prueba de rendimiento (concatenación)");

        // ? Concatenar cadenas con + es ineficiente en bucles grandes
        final int N = 100_000;

        long t1 = System.currentTimeMillis();
        String s = "";
        for (int i = 0; i < N; i++) s += "a"; // mala práctica: crea muchos objetos String
        long t2 = System.currentTimeMillis();
        System.out.println("📌 Concatenación con + tiempo(ms): " + (t2 - t1) + " (len=" + s.length() + ")");

        long t3 = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder(N);
        for (int i = 0; i < N; i++) sb.append('a');
        String r = sb.toString();
        long t4 = System.currentTimeMillis();
        System.out.println("📌 StringBuilder tiempo(ms): " + (t4 - t3) + " (len=" + r.length() + ")");

        // ! ⚠️ Observación: en bucles grandes siempre preferir StringBuilder o reservar capacidad.

        // ! ✅ TAREA PARA EL ALUMNO:
        // * Mide memoria y tiempos con N mayor (500k, 1M) y compara.
        // * Haz una gráfica de los resultados y explica por qué ocurre la diferencia.
    }

    // -----------------------------------------------------
    // Base64 y codificación de bytes
    // -----------------------------------------------------
    // * 🔵 EJEMPLO 5: Base64 y codificación de bytes
    public static void ejemploCodificacionBase64() {
        System.out.println("\n🔹 EJEMPLO 5: Codificación Base64 y manejo de bytes");

        // ? Codificamos una cadena con acentos y símbolos a Base64
        String texto = "Prueba con acentos: áéíóú y símbolos € - Java ";
        byte[] bytes = texto.getBytes(StandardCharsets.UTF_8);
        String b64 = Base64.getEncoder().encodeToString(bytes);
        System.out.println("📌 Base64: " + b64);

        // ? Decodificamos el Base64 de vuelta a texto
        byte[] dec = Base64.getDecoder().decode(b64);
        String decStr = new String(dec, StandardCharsets.UTF_8);
        System.out.println("📌 Decodificado: " + decStr);

        // ! ✅ TAREA PARA EL ALUMNO:
        // * Crea una función que detecte si una cadena está en Base64 válida o no (usa isBase64Valido).
        // * Prueba a codificar y decodificar otros textos con emojis y caracteres especiales.
    }

    // -----------------------------------------------------
    // Utilidades adicionales para alumnos
    // -----------------------------------------------------
    // Comparación que ignora mayúsculas y acentos
    public static boolean equalsIgnorandoAcentos(String a, String b) {
        if (a == null || b == null) return a == b;
        String na = normalizar(a).toLowerCase(Locale.ROOT);
        String nb = normalizar(b).toLowerCase(Locale.ROOT);
        return na.equals(nb);
    }

    // Detectar si una cadena parece Base64 válida (acepta con o sin padding)
    public static boolean isBase64Valido(String s) {
        if (s == null || s.isEmpty()) return false;
        // Patrón básico: grupos de 4 caracteres Base64, posibles '=' al final
        if (!s.matches("^[A-Za-z0-9+/]+={0,2}$")) return false;
        try {
            Base64.getDecoder().decode(s);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    // Ejemplo rápido: join, split y formato
    // - String.join: unir elementos
    // - split: partir en tokens
    // - String.format / MessageFormat: formateo
    // * 🔵 EJEMPLO 6: join, split y formato
    public static void ejemplosJoinSplitFormat() {
        System.out.println("\n🔹 EJEMPLO 6: join, split y formato");

        // ? Unir elementos de un array en una sola cadena
        String[] palabras = {"Java", "es", "genial"};
        String unido = String.join(" ", palabras);
        System.out.println("📌 join -> " + unido);

        // ? Separar una cadena en tokens usando split
        String linea = "uno,dos,tres,,cuatro";
        String[] tokens = linea.split(",");
        System.out.println("📌 split -> " + java.util.Arrays.toString(tokens));

        // ? Formateo de cadenas
        String f = String.format(Locale.forLanguageTag("es-ES"), "%d items - precio: %.2f €", 3, 15.5);
        System.out.println("📌 String.format -> " + f);

        String mf = MessageFormat.format("{0} - {1} - {2}", "A", 123, true);
        System.out.println("📌 MessageFormat -> " + mf);

        // ! ✅ TAREA PARA EL ALUMNO:
        // * Implementa un parser CSV simple que use split y trate comillas.
        // * Haz un join de una lista de números separados por punto y coma.
    }

}
