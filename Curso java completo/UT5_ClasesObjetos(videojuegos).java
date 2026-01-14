/*
 * ******************************************************************************************
 * (Programación Orientada a Objetos - POO)
 *                  📚 **TEORÍA Y CONCEPTOS: CLASES Y OBJETOS EN JAVA**
 *                          🎮 SISTEMA DE GESTIÓN DE VIDEOJUEGOS 🎮
 * ──────────────────────────────────────────────────
 * En esta práctica aprenderás a:
 *
 * ✅ **Comprender la programación orientada a objetos en Java.**
 * ✅ **Crear y utilizar clases en Java.**
 * ✅ **Definir atributos y métodos en una clase.**
 * ✅ **Crear objetos a partir de una clase.**
 * ✅ **Usar constructores para inicializar objetos.**
 * ✅ **Comprender la encapsulación y el uso de getters y setters.**
 * ✅ **Implementar métodos con lógica de negocio.**
 * ✅ **Sobrecarga de constructores.**
 *
 * 🚀 **¡Explora, experimenta y mejora el código!**
 ******************************************************************************************/

/*
 * 🧠 **TEORÍA GLOBAL: CLASES Y OBJETOS EN JAVA**
 * ────────────────────────────────────────────
 *
 * 🟢 **¿Qué es una Clase?**
 *  - Una **clase** es una plantilla o modelo que define las características (**atributos**) y
 *    comportamientos (**métodos**) de un objeto.
 *  - Es como un "plano" para construir objetos.
 *
 * 🟠 **¿Qué es un Objeto?**
 *  - Un **objeto** es una instancia concreta de una clase, que tiene valores específicos.
 *  - En Java, un objeto se crea con la palabra clave `new`.
 *
 * 🔵 **Encapsulación:**
 *  - Consiste en ocultar los datos internos de un objeto y proporcionar acceso controlado
 *    mediante métodos públicos (getters/setters).
 *  - Los atributos se declaran como `private` para protegerlos.
 *
 * 🟣 **Constructor:**
 *  - Método especial que se ejecuta automáticamente al crear un objeto.
 *  - Tiene el mismo nombre que la clase y no devuelve ningún valor.
 *  - Se puede sobrecargar (tener varios constructores con diferentes parámetros).
 *
 * 🔹 **Ejemplo básico de una Clase y su Objeto:**
 * ```java
 * class Personaje {
 *     private String nombre;
 *     private int vida;
 *
 *     public Personaje(String nombre, int vida) {
 *         this.nombre = nombre;
 *         this.vida = vida;
 *     }
 *
 *     public void atacar() {
 *         System.out.println(nombre + " ataca!");
 *     }
 * }
 *
 * public class Main {
 *     public static void main(String[] args) {
 *         Personaje heroe = new Personaje("Link", 100);
 *         heroe.atacar();
 *     }
 * }
 * ```
 */

// (Programación Orientada a Objetos - POO)
// Definimos la clase principal
public class UT5_ClasesObjetos {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║   🎮 SISTEMA DE GESTIÓN DE VIDEOJUEGOS 🎮    ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * CREACIÓN DE OBJETOS VIDEOJUEGO
        // * ─────────────────────────────────────────────────────

        // ! ✅ Crear videojuegos usando diferentes constructores
        Videojuego juego1 = new Videojuego("The Legend of Zelda", "Nintendo", 59.99, "Aventura");
        Videojuego juego2 = new Videojuego("Minecraft", "Mojang", 29.99);
        Videojuego juego3 = new Videojuego("Dark Souls III");

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * MODIFICACIÓN DE ATRIBUTOS CON SETTERS
        // * ─────────────────────────────────────────────────────
        juego2.setGenero("Sandbox");
        juego3.setDesarrolladora("FromSoftware");
        juego3.setPrecio(49.99);
        juego3.setGenero("Acción/RPG");

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * MOSTRAR INFORMACIÓN DE LOS VIDEOJUEGOS
        // * ─────────────────────────────────────────────────────
        System.out.println("📋 CATÁLOGO DE VIDEOJUEGOS:\n");
        juego1.mostrarInformacion();
        System.out.println();
        juego2.mostrarInformacion();
        System.out.println();
        juego3.mostrarInformacion();
        System.out.println();

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * APLICAR DESCUENTOS
        // * ─────────────────────────────────────────────────────
        System.out.println("💰 APLICANDO DESCUENTOS:\n");
        juego1.aplicarDescuento(10);  // 10% de descuento
        juego2.aplicarDescuento(25);  // 25% de descuento
        juego3.aplicarDescuento(15);  // 15% de descuento

        System.out.println("\n📋 CATÁLOGO ACTUALIZADO CON DESCUENTOS:\n");
        juego1.mostrarInformacion();
        System.out.println();
        juego2.mostrarInformacion();
        System.out.println();
        juego3.mostrarInformacion();
        System.out.println();

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * VENDER COPIAS Y CALCULAR INGRESOS
        // * ─────────────────────────────────────────────────────
        System.out.println("💵 REGISTRO DE VENTAS:\n");
        juego1.venderCopias(1500);
        juego2.venderCopias(3200);
        juego3.venderCopias(850);

        System.out.println("\n📊 ESTADÍSTICAS DE VENTAS:\n");
        juego1.mostrarInformacion();
        System.out.println();
        juego2.mostrarInformacion();
        System.out.println();
        juego3.mostrarInformacion();
        System.out.println();

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * CREAR Y GESTIONAR JUGADORES
        // * ─────────────────────────────────────────────────────
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║         👤 GESTIÓN DE JUGADORES 👤           ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");

        Jugador jugador1 = new Jugador("ProGamer99", "Mario", "Rossi");
        Jugador jugador2 = new Jugador("ShadowNinja", "Laura", "Gómez");

        // * Agregar horas jugadas y puntos
        jugador1.agregarHorasJugadas(150);
        jugador1.agregarPuntos(5000);

        jugador2.agregarHorasJugadas(320);
        jugador2.agregarPuntos(12500);

        // * Mostrar información de jugadores
        System.out.println();
        jugador1.mostrarPerfil();
        System.out.println();
        jugador2.mostrarPerfil();
        System.out.println();

        // * Calcular nivel basado en puntos
        System.out.println("🏆 NIVELES Y RANGOS DE JUGADORES:");
        System.out.println(jugador1.getNickname() + " - Nivel: " + jugador1.calcularNivel() + " | Rango: " + jugador1.obtenerRango());
        System.out.println(jugador2.getNickname() + " - Nivel: " + jugador2.calcularNivel() + " | Rango: " + jugador2.obtenerRango());

        // (Programación Orientada a Objetos - POO)
        // ! ✅ TAREA PARA EL ALUMNO:
        // * 1. Crea una clase `Consola` con atributos: nombre, fabricante, precioLanzamiento, añoLanzamiento
        // * 2. Implementa constructores sobrecargados (al menos 2 versiones)
        // * 3. Crea métodos: calcularAntiguedad(), aplicarDepreciacion(porcentaje)
        // * 4. Añade getters y setters con validaciones apropiadas
        // * 5. En main(), crea 3 objetos Consola y prueba todos los métodos
        // * 6. BONUS: Crea un método que compare dos consolas y determine cuál es más antigua
    }
}

// (Programación Orientada a Objetos - POO)
// Definimos la clase Videojuego
class Videojuego {
    // (Programación Orientada a Objetos - POO)
    // * ⚠️ Atributos (variables de instancia) - PRIVATE para encapsulación
    private String titulo;
    private String desarrolladora;
    private double precio;
    private String genero;
    private int copiasVendidas;

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor completo (4 parámetros)
    // * Inicializa todos los atributos principales al crear el objeto
    public Videojuego(String titulo, String desarrolladora, double precio, String genero) {
        this.titulo = titulo;
        this.desarrolladora = desarrolladora;
        this.precio = precio;
        this.genero = genero;
        this.copiasVendidas = 0; // * Inicialmente no hay copias vendidas
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor sobrecargado (3 parámetros)
    // * Permite crear un videojuego sin especificar el género
    public Videojuego(String titulo, String desarrolladora, double precio) {
        this.titulo = titulo;
        this.desarrolladora = desarrolladora;
        this.precio = precio;
        this.genero = "No especificado";
        this.copiasVendidas = 0;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor sobrecargado (1 parámetro)
    // * Permite crear un videojuego solo con el título
    public Videojuego(String titulo) {
        this.titulo = titulo;
        this.desarrolladora = "Desarrolladora desconocida";
        this.precio = 0.0;
        this.genero = "No especificado";
        this.copiasVendidas = 0;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Getters - Permiten acceder a los atributos privados
    public String getTitulo() {
        return titulo;
    }

    public String getDesarrolladora() {
        return desarrolladora;
    }

    public double getPrecio() {
        return precio;
    }

    public String getGenero() {
        return genero;
    }

    public int getCopiasVendidas() {
        return copiasVendidas;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Setters - Permiten modificar los atributos privados con validación
    public void setTitulo(String titulo) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            this.titulo = titulo;
        } else {
            System.out.println("⚠️ El título no puede estar vacío.");
        }
    }

    public void setDesarrolladora(String desarrolladora) {
        if (desarrolladora != null && !desarrolladora.trim().isEmpty()) {
            this.desarrolladora = desarrolladora;
        } else {
            System.out.println("⚠️ La desarrolladora no puede estar vacía.");
        }
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            System.out.println("⚠️ El precio no puede ser negativo.");
        }
    }

    public void setGenero(String genero) {
        if (genero != null && !genero.trim().isEmpty()) {
            this.genero = genero;
        } else {
            System.out.println("⚠️ El género no puede estar vacío.");
        }
    }

    public void setCopiasVendidas(int copiasVendidas) {
        if (copiasVendidas >= 0) {
            this.copiasVendidas = copiasVendidas;
        } else {
            System.out.println("⚠️ Las copias vendidas no pueden ser negativas.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: aplicarDescuento
    // * Aplica un descuento al precio del videojuego
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje > 0 && porcentaje <= 100) {
            double precioAnterior = precio;
            double descuento = precio * (porcentaje / 100);
            precio = precio - descuento;
            System.out.println("✅ Se aplicó un descuento del " + porcentaje + "% a " + titulo);
            System.out.println("   Precio anterior: " + String.format("%.2f", precioAnterior) + "€ → Precio nuevo: " + String.format("%.2f", precio) + "€");
        } else {
            System.out.println("⚠️ El porcentaje de descuento debe estar entre 0 y 100.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: venderCopias
    // * Registra la venta de copias del videojuego
    public void venderCopias(int cantidad) {
        if (cantidad > 0) {
            copiasVendidas += cantidad;
            System.out.println("✅ Se vendieron " + cantidad + " copias de " + titulo);
        } else {
            System.out.println("⚠️ La cantidad debe ser mayor a 0.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: calcularIngresos
    // * Calcula los ingresos totales basándose en las copias vendidas
    public double calcularIngresos() {
        return copiasVendidas * precio;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: esPopular
    // * Determina si el videojuego es popular basándose en las ventas
    public boolean esPopular() {
        return copiasVendidas >= 1000;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: mostrarInformacion
    // * Muestra toda la información del videojuego de forma formateada
    public void mostrarInformacion() {
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│ 🎮 INFORMACIÓN DEL VIDEOJUEGO              │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ Título:          " + titulo);
        System.out.println("│ Desarrolladora:  " + desarrolladora);
        System.out.println("│ Género:          " + genero);
        System.out.println("│ Precio:          " + String.format("%.2f", precio) + "€");
        System.out.println("│ Copias vendidas: " + copiasVendidas);
        System.out.println("│ Popularidad:     " + (esPopular() ? "⭐ POPULAR" : "📊 En crecimiento"));
        if (copiasVendidas > 0) {
            System.out.println("│ Ingresos:        " + String.format("%.2f", calcularIngresos()) + "€");
        }
        System.out.println("└─────────────────────────────────────────────┘");
    }
}

// (Programación Orientada a Objetos - POO)
// Definimos la clase Jugador
class Jugador {
    // (Programación Orientada a Objetos - POO)
    // * ⚠️ Atributos (variables de instancia)
    private String nickname;
    private String nombre;
    private String apellido;
    private int horasJugadas;
    private int puntos;

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor completo (3 parámetros)
    public Jugador(String nickname, String nombre, String apellido) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.horasJugadas = 0;
        this.puntos = 0;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor sobrecargado (solo nickname)
    public Jugador(String nickname) {
        this.nickname = nickname;
        this.nombre = "Anónimo";
        this.apellido = "";
        this.horasJugadas = 0;
        this.puntos = 0;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Getters
    public String getNickname() {
        return nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getHorasJugadas() {
        return horasJugadas;
    }

    public int getPuntos() {
        return puntos;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Setters con validación
    public void setNickname(String nickname) {
        if (nickname != null && !nickname.trim().isEmpty()) {
            this.nickname = nickname;
        } else {
            System.out.println("⚠️ El nickname no puede estar vacío.");
        }
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: agregarHorasJugadas
    // * Incrementa las horas jugadas del jugador
    public void agregarHorasJugadas(int horas) {
        if (horas > 0) {
            this.horasJugadas += horas;
            System.out.println("✅ Se agregaron " + horas + " horas a " + nickname);
        } else {
            System.out.println("⚠️ Las horas deben ser positivas.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: agregarPuntos
    // * Incrementa los puntos del jugador
    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntos += puntos;
            System.out.println("✅ " + nickname + " ganó " + puntos + " puntos!");
        } else {
            System.out.println("⚠️ Los puntos deben ser positivos.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: calcularNivel
    // * Calcula el nivel del jugador basándose en sus puntos
    // * Cada 1000 puntos = 1 nivel
    public int calcularNivel() {
        return puntos / 1000;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: esJugadorVeterano
    // * Determina si es un jugador veterano (más de 200 horas)
    public boolean esJugadorVeterano() {
        return horasJugadas >= 200;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: mostrarPerfil
    // * Muestra el perfil completo del jugador
    public void mostrarPerfil() {
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│ 👤 PERFIL DE JUGADOR                       │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│ Nickname:        " + nickname);
        System.out.println("│ Nombre real:     " + nombre + " " + apellido);
        System.out.println("│ Horas jugadas:   " + horasJugadas + " h");
        System.out.println("│ Puntos totales:  " + puntos + " pts");
        System.out.println("│ Nivel:           " + calcularNivel());
        System.out.println("│ Estado:          " + (esJugadorVeterano() ? "🏆 VETERANO" : "🌱 Novato"));
        System.out.println("│ Rango:           " + obtenerRango());
        System.out.println("└─────────────────────────────────────────────┘");
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: obtenerRango
    // * Devuelve el rango del jugador según sus puntos
    public String obtenerRango() {
        if (puntos < 1000) {
            return "🥉 Bronce";
        } else if (puntos < 5000) {
            return "🥈 Plata";
        } else if (puntos < 10000) {
            return "🥇 Oro";
        } else if (puntos < 20000) {
            return "💎 Platino";
        } else {
            return "👑 Diamante";
        }
    }
}

/*
 * ⚡ **TAREAS PARA EL ALUMNO:**
 * ════════════════════════════════════════════════════════════════
 *
 * 🎯 TAREA 1: Clase Consola
 * ──────────────────────────────────────────────────────────────
 * 1. Crea una clase `Consola` con los siguientes atributos:
 *    - nombre (String)
 *    - fabricante (String)
 *    - precioLanzamiento (double)
 *    - añoLanzamiento (int)
 *    - unidadesVendidas (int)
 *
 * 2. Implementa al menos 3 constructores sobrecargados.
 *
 * 3. Crea los siguientes métodos:
 *    - calcularAntiguedad(): devuelve los años desde su lanzamiento
 *    - aplicarDepreciacion(porcentaje): reduce el precio actual
 *    - esRetro(): devuelve true si tiene más de 15 años
 *    - mostrarEspecificaciones(): muestra toda la información
 *
 * 4. Añade getters y setters con validaciones apropiadas.
 *
 * 5. En main(), crea 3 objetos Consola y prueba todos los métodos.
 *
 * 🎯 TAREA 2: Mejoras a las clases existentes
 * ──────────────────────────────────────────────────────────────
 * 1. En la clase Videojuego, añade un método `compararPrecio(Videojuego otro)`
 *    que indique cuál videojuego es más caro.
 *
 * 2. En la clase Jugador, añade un método `compararNivel(Jugador otro)`
 *    que compare dos jugadores e indique cuál tiene mayor nivel.
 *
 * 🎯 TAREA 3: Sistema de Reseñas (AVANZADO)
 * ──────────────────────────────────────────────────────────────
 * 1. Crea una clase `Reseña` con atributos:
 *    - nombreJugador (String)
 *    - calificacion (int, de 1 a 10)
 *    - comentario (String)
 *    - fecha (String)
 *
 * 2. Añade un método en Videojuego para agregar reseñas.
 *
 * 3. Implementa un método que calcule la calificación promedio.
 *
 * 🚀 **¡Explora, experimenta y mejora el código!**
 *
 * 💡 CONCEPTOS CLAVE APRENDIDOS:
 * ══════════════════════════════════════════════════════════════
 * ✅ Clases y Objetos
 * ✅ Atributos (variables de instancia)
 * ✅ Métodos (comportamientos)
 * ✅ Constructores y sobrecarga de constructores
 * ✅ Encapsulación (private + getters/setters)
 * ✅ Validación de datos
 * ✅ Métodos con lógica de negocio
 * ✅ Formateo de salida profesional
 *
 */
