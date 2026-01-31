package hotel.modelo;

/**
 * ============================================================
 * 🔷 INTERFAZ: Reservable
 * ============================================================
 * 
 * 📚 ¿QUÉ ES UNA INTERFAZ?
 * ─────────────────────────────────
 * Una interfaz es un "contrato" que define qué métodos debe tener
 * una clase, pero NO define cómo implementarlos.
 * 
 * Piensa en una interfaz como un "acuerdo":
 *   "Si dices que eres Reservable, DEBES poder reservar() y cancelarReserva()"
 * 
 * 📌 DIFERENCIAS CON CLASE ABSTRACTA:
 * ─────────────────────────────────
 * 
 *   INTERFAZ                          CLASE ABSTRACTA
 *   ─────────────────────────────     ─────────────────────────────
 *   • Solo métodos abstractos (*)     • Puede tener métodos con código
 *   • No tiene atributos (**)         • Puede tener atributos
 *   • Una clase puede implementar     • Una clase solo puede extender
 *     MÚLTIPLES interfaces              UNA clase abstracta
 *   • Se usa "implements"             • Se usa "extends"
 * 
 *   (*) Desde Java 8, las interfaces pueden tener métodos default
 *   (**) Puede tener constantes (public static final)
 * 
 * 📌 SINTAXIS:
 * ─────────────────────────────────
 *   public interface NombreInterfaz {
 *       void metodo1();  // Abstracto implícitamente
 *       void metodo2();
 *   }
 * 
 *   public class MiClase implements NombreInterfaz {
 *       @Override
 *       public void metodo1() { ... }  // OBLIGATORIO implementar
 *       @Override
 *       public void metodo2() { ... }  // OBLIGATORIO implementar
 *   }
 * 
 * 🎯 ¿CUÁNDO USAR INTERFACES?
 * ─────────────────────────────────
 * Usa interfaces cuando quieras definir un comportamiento que
 * pueden tener clases muy diferentes. Por ejemplo:
 *   • Reservable → Habitación, Sala de reuniones, Mesa de restaurante
 *   • Comparable → Cualquier cosa que se pueda comparar
 *   • Serializable → Cualquier cosa que se pueda guardar en archivo
 * 
 * ============================================================
 */
public interface Reservable {
    
    // ════════════════════════════════════════════════════════
    // 📋 MÉTODOS DE LA INTERFAZ
    // ════════════════════════════════════════════════════════
    // En una interfaz, los métodos son implícitamente:
    //   • public (accesibles desde cualquier lugar)
    //   • abstract (sin implementación)
    // Por eso no hace falta escribir "public abstract"
    
    /**
     * Reserva el objeto (habitación, mesa, sala, etc.)
     * 
     * 📌 COMPORTAMIENTO ESPERADO:
     * ─────────────────────────────────
     * Al llamar a este método:
     *   • El objeto pasa a estar "no disponible"
     *   • Si ya estaba ocupado, debería lanzar una excepción
     * 
     * @throws Exception Si el objeto no está disponible para reservar
     */
    void reservar() throws Exception;
    
    /**
     * Cancela la reserva y libera el objeto.
     * 
     * 📌 COMPORTAMIENTO ESPERADO:
     * ─────────────────────────────────
     * Al llamar a este método:
     *   • El objeto vuelve a estar "disponible"
     */
    void cancelarReserva();
    
    // ════════════════════════════════════════════════════════
    // 💡 EJEMPLO DE MÉTODO DEFAULT (Java 8+)
    // ════════════════════════════════════════════════════════
    // Los métodos default tienen implementación en la interfaz.
    // Las clases que implementan la interfaz pueden usarlo
    // directamente o sobreescribirlo.
    //
    // default void metodoConImplementacion() {
    //     System.out.println("Implementación por defecto");
    // }
}

/*
 * ════════════════════════════════════════════════════════════
 * 📝 EJEMPLO DE USO DE LA INTERFAZ
 * ════════════════════════════════════════════════════════════
 * 
 * // Habitacion implementa Reservable
 * Habitacion hab = new Habitacion(1, "doble", 70.0, true);
 * 
 * // Podemos usar los métodos de la interfaz
 * hab.reservar();        // La habitación ahora no está disponible
 * hab.cancelarReserva(); // La habitación vuelve a estar disponible
 * 
 * // También podemos usar polimorfismo:
 * Reservable r = new Habitacion(2, "suite", 120.0, true);
 * r.reservar();  // Funciona igual
 * 
 * // Esto es útil cuando tenemos varios tipos de objetos reservables:
 * ArrayList<Reservable> elementosReservables = new ArrayList<>();
 * elementosReservables.add(new Habitacion(...));
 * // elementosReservables.add(new SalaReuniones(...)); // Si existiera
 * 
 * for (Reservable elemento : elementosReservables) {
 *     elemento.reservar();  // Cada uno ejecuta SU implementación
 * }
 */
