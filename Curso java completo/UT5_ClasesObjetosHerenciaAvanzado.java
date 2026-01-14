/******************************************************************************************
 *                        📚 UT5 AVANZADO: CLASES, OBJETOS, HERENCIA Y MÁS EN JAVA
 * ──────────────────────────────────────────────────────────────────────────────
 * En esta unidad se sintetizan y amplían los conceptos vistos previamente:
 *
 * ✅ Programación Orientada a Objetos completa (POO).
 * ✅ Encapsulación, constructores, sobrecarga y patrón Builder básico.
 * ✅ Herencia, polimorfismo (métodos sobrescritos) y composición.
 * ✅ Interfaces múltiples y segregación de responsabilidades.
 * ✅ Clases abstractas vs interfaces.
 * ✅ Enums para estados/roles.
 * ✅ Métodos estáticos de utilidad y genéricos simples.
 * ✅ Buenas prácticas: inmutabilidad parcial, validaciones y documentación.
 *
 * 🚀 Objetivo: Tener una visión más rica y práctica de cómo modelar un dominio.
 ******************************************************************************************/

/*
 * 🧠 VISIÓN GLOBAL
 * ──────────────────────────────────────────────────────────────────────────────
 * Una aplicación real combina varios principios:
 *  - CLASES: Plantillas de objetos.
 *  - OBJETOS: Instancias con estado propio.
 *  - HERENCIA: Reutiliza y especializa comportamiento.
 *  - POLIMORFISMO: Muchas formas para una misma operación (sobrescritura / sobrecarga).
 *  - INTERFACES: Contratos que definen capacidades ("qué" debe hacer).
 *  - ABSTRACT: Base común con implementación parcial.
 *  - ENUM: Conjunto finito de valores semánticos.
 *  - COMPOSICIÓN: Un objeto contiene otros para delegar responsabilidades.
 *  - GENÉRICOS: Reutilización segura para distintos tipos.
 *  - BUILDER: Facilita la construcción de objetos con muchos parámetros.
 *
 * 🔎 Mini-guía rápida (con ejemplos):
 * ------------------------------------------------------------
 * 1) Encapsulación + getters/setters
 *    - Mantén los atributos privados y expón acceso controlado.
 *    - Valida datos en setters o constructores.
 *
 * 2) Herencia + @Override
 *    - Subclase "especializa" a la superclase.
 *    - Sobrescribe métodos para comportamiento específico.
 *
 * 3) Interfaces (contratos)
 *    - Definen "qué" debe hacerse, no "cómo".
 *    - Permiten múltiples capacidades (ej. Nadador y Volador).
 *
 * 4) Clases abstractas
 *    - Tienen implementación parcial y métodos abstractos a completar.
 *
 * 5) Composición
 *    - Un objeto contiene otros (Curso contiene Personas).
 *
 * 6) Genéricos
 *    - Evitan casts inseguros y mejoran reutilización (Box<T>).
 *
 * 7) Builder
 *    - Construye objetos complejos con llamadas encadenadas legibles.
 *
 * 8) Overloading vs Overriding
 *    - Overloading (sobrecarga): mismo nombre, distinta firma (en la misma clase).
 *    - Overriding (sobrescritura): redefinir método de la superclase (polimorfismo).
 *
 * 🧩 Ejemplo comparativo (pseudocódigo):
 * ```java
 * class Figura { double area() { return 0; } } // Base
 * class Circulo extends Figura {
 *   @Override double area() { return Math.PI * r * r; } // Overriding
 *   double area(double escala) { return area() * escala; } // Overloading
 * }
 * ```
 */

import java.util.ArrayList;
import java.util.List;

// * =============================================================
// * ENUM PARA ROLES DE USUARIOS
// * =============================================================
// ? Enum: conjunto cerrado de valores. Útil para evitar "magic strings".
enum Rol {
    ADMIN, PROFESOR, ALUMNO
}

// * =============================================================
// * INTERFACES: CAPACIDADES / CONTRATOS
// * =============================================================
interface Identificable {
    String getId();
}

interface Pagable {
    double calcularPago();
}

interface Volador {
    void volar();
}

interface Nadador {
    void nadar();
}

// * =============================================================
// * CLASE PERSONA (BASE) CON ENCAPSULACIÓN + BUILDER
// * =============================================================
class Persona implements Identificable {
    // * Atributos privados (encapsulación)
    private final String id;          // Inmutable una vez creado
    private String nombre;
    private int edad;
    private Rol rol;                  // Enum para rol

    // * Constructor principal (privado para forzar uso del Builder opcional)
    private Persona(String id, String nombre, int edad, Rol rol) {
        this.id = id;
        setNombre(nombre); // Reutilizamos validaciones
        setEdad(edad);
        this.rol = rol;
    }

    // * Constructor público simple (sobrecarga)
    public Persona(String nombre, int edad) {
        this("P-" + System.nanoTime(), nombre, edad, Rol.ALUMNO);
    }

    // * Patrón Builder básico (clase estática interna)
    public static class Builder {
        private String id = "P-" + System.nanoTime();
        private String nombre;
        private int edad;
        private Rol rol = Rol.ALUMNO;

        public Builder nombre(String nombre) { this.nombre = nombre; return this; }
        public Builder edad(int edad) { this.edad = edad; return this; }
        public Builder rol(Rol rol) { this.rol = rol; return this; }
        public Builder id(String id) { this.id = id; return this; }

        public Persona build() { return new Persona(id, nombre, edad, rol); }
    }

    // * Getters (exponemos lectura controlada)
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public Rol getRol() { return rol; }
    @Override
    public String getId() { return id; }

    // * Setters con validación
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        this.nombre = nombre.trim();
    }

    public void setEdad(int edad) {
        if (edad < 0) throw new IllegalArgumentException("Edad no puede ser negativa");
        this.edad = edad;
    }

    public void setRol(Rol rol) { this.rol = rol; }

    // * Método de comportamiento
    public void saludar() {
        System.out.println("👋 Hola, soy " + nombre + " (" + rol + ")");
    }

    // * toString (representación legible)
    @Override
    public String toString() {
        return "Persona{" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' + ", edad=" + edad + ", rol=" + rol + '}';
    }

    // * equals/hashCode basados en id (identidad de dominio)
    // ? Contrato: dos Personas son "iguales" si comparten el mismo id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return id.equals(persona.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }
}

// * =============================================================
// * SUBCLASES DE PERSONA PARA HERENCIA
// * =============================================================
class Estudiante extends Persona implements Pagable {
    private String cursoActual;
    private double cuotaMensual;

    public Estudiante(String nombre, int edad, String cursoActual, double cuotaMensual) {
        super(nombre, edad);
        this.cursoActual = cursoActual;
        this.cuotaMensual = cuotaMensual;
        setRol(Rol.ALUMNO);
    }

    @Override
    public double calcularPago() { return cuotaMensual; }

    public void estudiar() { System.out.println("📘 Estudiando en el curso: " + cursoActual); }
}

class Empleado extends Persona implements Pagable {
    private double salarioBase;
    private double bonus;

    public Empleado(String nombre, int edad, double salarioBase, double bonus) {
        super(nombre, edad);
        this.salarioBase = salarioBase;
        this.bonus = bonus;
        setRol(Rol.PROFESOR);
    }

    @Override
    public double calcularPago() { return salarioBase + bonus; }

    public void impartir() { System.out.println("🎤 Impartiendo clase..."); }
}

// * =============================================================
// * CLASE ABSTRACTA ANIMAL + POLIMORFISMO
// * =============================================================
abstract class Animal implements Identificable {
    private final String id = "A-" + System.nanoTime();
    protected String nombre; // protected permite acceso en subclases

    public Animal(String nombre) { this.nombre = nombre; }

    public abstract void hacerSonido(); // Método abstracto

    @Override
    public String getId() { return id; }

    public void info() {
        System.out.println("Animal: " + nombre + " (id=" + id + ")");
    }

    // * Hook method (opcional) que subclases pueden usar
    // ? Útil para extender comportamiento sin modificar esta clase
    protected void onAfterSound() {
        // implementación vacía
    }
}

class Perro extends Animal {
    public Perro(String nombre) { super(nombre); }
    @Override
    public void hacerSonido() {
        System.out.println(nombre + " ladra: Guau!");
        onAfterSound();
    }
}

class Gato extends Animal {
    public Gato(String nombre) { super(nombre); }
    @Override
    public void hacerSonido() {
        System.out.println(nombre + " maúlla: Miau!");
        onAfterSound();
    }
}

class Ave extends Animal implements Volador {
    public Ave(String nombre) { super(nombre); }
    @Override
    public void hacerSonido() {
        System.out.println(nombre + " canta: Pío pío!");
        onAfterSound();
    }
    @Override
    public void volar() { System.out.println(nombre + " está volando 🕊️"); }
}

class Pez extends Animal implements Nadador {
    public Pez(String nombre) { super(nombre); }
    @Override
    public void hacerSonido() {
        System.out.println(nombre + " hace burbujas: blub blub!");
        onAfterSound();
    }
    @Override
    public void nadar() { System.out.println(nombre + " nada velozmente 🐟"); }
}

// * =============================================================
// * COMPOSICIÓN: CURSO CONTIENE PERSONAS
// * =============================================================
class Curso {
    private final String nombre;
    private final List<Persona> participantes = new ArrayList<>();

    public Curso(String nombre) { this.nombre = nombre; }

    public void agregarParticipante(Persona p) { participantes.add(p); }

    public void listarParticipantes() {
        System.out.println("👥 Participantes en curso " + nombre + ":");
        for (Persona p : participantes) {
            System.out.println("  - " + p.getNombre() + " (" + p.getRol() + ")");
        }
    }

    // * Contrato
    // - Entrada: ninguna
    // - Salida: double con suma de todos los Pagable
    // - Errores: ignora participantes que no implementan Pagable
    public double ingresosTotales() {
        double total = 0;
        for (Persona p : participantes) {
            if (p instanceof Pagable) {
                total += ((Pagable) p).calcularPago();
            }
        }
        return total;
    }
}

// * =============================================================
// * UTILIDADES ESTÁTICAS + GENÉRICOS
// * =============================================================
class Estadisticas {
    private Estadisticas() {} // Evitar instanciación

    public static double promedioEdad(List<Persona> personas) {
        if (personas.isEmpty()) return 0.0;
        int suma = 0;
        for (Persona p : personas) suma += p.getEdad();
        return suma / (double) personas.size();
    }
}

// ? Clase genérica simple para envolver un valor
class Box<T> {
    private T valor;
    public Box(T valor) { this.valor = valor; }
    public T get() { return valor; }
    public void set(T valor) { this.valor = valor; }
    @Override public String toString() { return "Box{" + valor + '}'; }
}

// * =============================================================
// * MÉTODOS SOBRE CARGADOS (OVERLOADING) EN UTILIDAD
// * =============================================================
class Printer {
    public static void imprimir(String s) { System.out.println("[String] " + s); }
    public static void imprimir(int n) { System.out.println("[int] " + n); }
    public static void imprimir(Persona p) { System.out.println("[Persona] " + p); }
}

// * =============================================================
// * CLASE PRINCIPAL (MAIN): DEMOSTRACIÓN INTEGRAL
// * =============================================================
public class UT5_ClasesObjetosHerenciaAvanzado {
    public static void main(String[] args) {
    // 🧭 Guía de la demo:
    // 1) Creación de objetos (Builder y constructor)
    // 2) Herencia + polimorfismo (Personas y Animales)
    // 3) Interfaces (capacidades Volador/Nadador y Pagable)
    // 4) Composición (Curso) y utilidades (Estadisticas)
    // 5) Genéricos (Box<T>) y sobrecarga (Printer)

        // ✅ Builder + constructor
        Persona personaBuilt = new Persona.Builder()
                .nombre("Laura")
                .edad(28)
                .rol(Rol.ADMIN)
                .build();

        Estudiante est = new Estudiante("Carlos", 22, "Java Básico", 120.0);
        Empleado prof = new Empleado("María", 35, 1500.0, 350.0);

        personaBuilt.saludar();
        est.saludar();
        est.estudiar();
        prof.saludar();
        prof.impartir();
        System.out.println("💰 Pago estudiante: " + est.calcularPago());
        System.out.println("💰 Pago empleado: " + prof.calcularPago());

        // 🐾 Polimorfismo con animales
        List<Animal> zoologico = List.of(
                new Perro("Rex"),
                new Gato("Misu"),
                new Ave("Golondrina"),
                new Pez("Nemo")
        );
        System.out.println("\n🔊 Sonidos en el zoológico:");
        for (Animal a : zoologico) a.hacerSonido();

        // Capacidades específicas por interfaces
        System.out.println("\n🌊 / 🕊️ Acciones especiales:");
        for (Animal a : zoologico) {
            if (a instanceof Volador v) v.volar();
            if (a instanceof Nadador n) n.nadar();
        }

        // 🎓 Composición: Curso con participantes
        Curso cursoJava = new Curso("Java Avanzado");
        cursoJava.agregarParticipante(personaBuilt);
        cursoJava.agregarParticipante(est);
        cursoJava.agregarParticipante(prof);
        cursoJava.listarParticipantes();
        System.out.println("Ingresos totales curso: " + cursoJava.ingresosTotales());

        // 📊 Estadísticas y genéricos
        List<Persona> personas = List.of(personaBuilt, est, prof);
        System.out.println("Promedio edad: " + Estadisticas.promedioEdad(personas));

        Box<String> boxTexto = new Box<>("Mensaje en caja");
        Box<Integer> boxNumero = new Box<>(42);
        System.out.println(boxTexto);
        System.out.println(boxNumero);

        // 🧪 Overloading
        Printer.imprimir("Hola");
        Printer.imprimir(123);
        Printer.imprimir(est);

        // ! Manejo de excepción de validación (demostración rápida)
        try {
            est.setEdad(-5); // Disparará excepción
        } catch (IllegalArgumentException ex) {
            System.out.println("⚠️ Error capturado: " + ex.getMessage());
        }

        System.out.println("\n✅ Demostración completa finalizada.");

        // ! Nota pedagógica:
        // Si compilas TODOS los archivos de la carpeta a la vez, podrían existir
        // colisiones de nombres (Animal, Perro, Gato) con otras unidades UT5.
        // ▶ Recomendación: compila/ejecuta cada archivo de forma independiente.
    }
}

/*
 * ⚡ TAREAS AVANZADAS PARA EL ALUMNO
 * ──────────────────────────────────────────────────────────────────────────────
 * 1. Crear una nueva subclase de Animal llamada "Leon" que tenga un método extra rugir().
 * 2. Añadir una interfaz "Runnable" (no confundir con java.lang.Runnable) propia con método correr() e implementarla en Perro y Leon.
 * 3. Extender el Builder de Persona para permitir opcionalmente asignar una lista de "habilidades" (List<String>) y mostrarlas en toString.
 * 4. Crear una clase genérica Par<A,B> y usarla para emparejar Estudiante con Curso.
 * 5. Agregar validación adicional: impedir que cuotaMensual en Estudiante sea <= 0.
 * 6. Crear un método estático en Estadisticas para obtener el máximo pago entre una lista de Pagable.
 * 7. Implementar una subclase EmpleadoHoras que calcule pago = horas * tarifa y pruebe polimorfismo usando Pagable.
 * 8. Añadir sobrecarga adicional en Printer para List<?> imprimiendo cada elemento.
 * 9. Crear una clase RegistroActividad que reciba Identificable y guarde un log (simulado en consola).
 * 10. Refactorizar Curso para usar generics: Curso<T extends Persona> y limitar participantes.
 *
 * 🧪 EXTRA (OPCIONAL): Implementar un pequeño menú textual en main para interactuar con algunas funciones.
 *
 * 🚀 CONSEJO: Aborda uno a uno, prueba y refactoriza. ¡Documenta tus decisiones!.
 */
