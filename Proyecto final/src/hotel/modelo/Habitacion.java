package hotel.modelo;

import hotel.excepciones.ReservaInvalidaException;

/**
 * ============================================================
 * 🏠 CLASE: Habitacion (implementa Reservable)
 * ============================================================
 * 
 * 📚 IMPLEMENTACIÓN DE INTERFACES
 * ─────────────────────────────────
 * Cuando una clase "implementa" una interfaz:
 *   • Se compromete a proporcionar código para TODOS los métodos
 *   • Si no implementa algún método, la clase debe ser abstracta
 *   • Puede implementar múltiples interfaces separadas por coma
 * 
 * 📌 SINTAXIS:
 * ─────────────────────────────────
 *   public class MiClase implements Interfaz1, Interfaz2 { ... }
 * 
 * 🔗 RELACIÓN "PUEDE HACER":
 * ─────────────────────────────────
 * Mientras que la herencia representa "ES UN/UNA",
 * las interfaces representan "PUEDE HACER":
 *   • Una Habitacion ES UN objeto reservable (puede reservarse)
 *   • Una Habitacion PUEDE SER reservada
 * 
 * ============================================================
 */
public class Habitacion implements Reservable {
    
    // ════════════════════════════════════════════════════════
    // 📦 ATRIBUTOS
    // ════════════════════════════════════════════════════════
    
    private int id;                 // Identificador único de la habitación
    private String tipo;            // Tipo: "individual", "doble", "suite"
    private double precioPorNoche;  // Precio en euros por noche
    private boolean disponible;     // true = libre, false = ocupada
    
    // ════════════════════════════════════════════════════════
    // 🔧 CONSTRUCTOR
    // ════════════════════════════════════════════════════════
    /**
     * Constructor de la clase Habitacion.
     * Inicializa todos los atributos de la habitación.
     * 
     * @param id             Identificador único
     * @param tipo           Tipo de habitación (individual/doble/suite)
     * @param precioPorNoche Precio por noche en euros
     * @param disponible     Estado de disponibilidad
     */
    public Habitacion(int id, String tipo, double precioPorNoche, boolean disponible) {
        this.id = id;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.disponible = disponible;
    }
    
    // ════════════════════════════════════════════════════════
    // 🔄 MÉTODOS DE LA INTERFAZ RESERVABLE
    // ════════════════════════════════════════════════════════
    // Estos métodos SON OBLIGATORIOS porque implementamos Reservable.
    // Si no los definimos, el código no compilará.
    
    /**
     * Reserva la habitación (la marca como no disponible).
     * 
     * 📌 IMPLEMENTACIÓN:
     * ─────────────────────────────────
     *   1. Comprobamos si la habitación está disponible
     *   2. Si NO está disponible → Lanzamos excepción
     *   3. Si SÍ está disponible → Cambiamos disponible a false
     * 
     * 📌 ¿POR QUÉ throws ReservaInvalidaException?
     * ─────────────────────────────────
     * Cuando un método puede lanzar una excepción "checked",
     * debemos declararlo en la firma del método con "throws".
     * Esto obliga al código que llama a este método a manejar
     * la excepción (try-catch) o propagarla.
     * 
     * @throws ReservaInvalidaException Si la habitación no está disponible
     */
    @Override
    public void reservar() throws ReservaInvalidaException {
        // Verificamos si la habitación está disponible
        if (!disponible) {
            // Si ya está ocupada, lanzamos nuestra excepción personalizada
            throw new ReservaInvalidaException(
                "❌ La habitación " + id + " no está disponible. " +
                "Ya se encuentra ocupada."
            );
        }
        
        // Si llegamos aquí, la habitación está disponible
        // La marcamos como ocupada
        this.disponible = false;
        System.out.println("✅ Habitación " + id + " reservada correctamente.");
    }
    
    /**
     * Cancela la reserva (libera la habitación).
     * 
     * 📌 IMPLEMENTACIÓN:
     * ─────────────────────────────────
     * Simplemente cambiamos disponible a true.
     * La habitación vuelve a estar libre para nuevas reservas.
     */
    @Override
    public void cancelarReserva() {
        this.disponible = true;
        System.out.println("✅ Habitación " + id + " liberada correctamente.");
    }
    
    // ════════════════════════════════════════════════════════
    // 📝 MÉTODO toString()
    // ════════════════════════════════════════════════════════
    /**
     * Devuelve una representación en texto de la habitación.
     * 
     * 📌 FORMATO DE SALIDA:
     * ─────────────────────────────────
     * Habitación 1 | Tipo: doble | Precio: 70.00€/noche | Disponible: ✅
     */
    @Override
    public String toString() {
        // Usamos el operador ternario para mostrar un emoji según disponibilidad
        // Sintaxis: condicion ? valorSiTrue : valorSiFalse
        String estadoEmoji = disponible ? "✅ Disponible" : "❌ Ocupada";
        
        // String.format permite formatear texto con marcadores de posición
        // %d = entero, %s = string, %.2f = decimal con 2 decimales
        return String.format(
            "Habitación %d | Tipo: %-10s | Precio: %.2f€/noche | Estado: %s",
            id, tipo, precioPorNoche, estadoEmoji
        );
    }
    
    // ════════════════════════════════════════════════════════
    // 📤 GETTERS
    // ════════════════════════════════════════════════════════
    
    /**
     * Obtiene el ID de la habitación.
     * @return El ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene el tipo de habitación.
     * @return El tipo (individual/doble/suite)
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Obtiene el precio por noche.
     * @return El precio en euros
     */
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }
    
    /**
     * Indica si la habitación está disponible.
     * @return true si está libre, false si está ocupada
     */
    public boolean isDisponible() {
        // Para booleanos, el getter se llama "isXxx" en lugar de "getXxx"
        return disponible;
    }
    
    // ════════════════════════════════════════════════════════
    // 📥 SETTERS
    // ════════════════════════════════════════════════════════
    
    /**
     * Establece el ID de la habitación.
     * @param id El nuevo ID
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Establece el tipo de habitación.
     * @param tipo El nuevo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Establece el precio por noche.
     * @param precioPorNoche El nuevo precio
     */
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }
    
    /**
     * Establece la disponibilidad de la habitación.
     * @param disponible El nuevo estado
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
