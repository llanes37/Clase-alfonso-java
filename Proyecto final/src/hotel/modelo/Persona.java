package hotel.modelo;

/**
 * ============================================================
 * 🔵 CLASE ABSTRACTA: Persona
 * ============================================================
 * 
 * 📚 ¿QUÉ ES UNA CLASE ABSTRACTA?
 * ─────────────────────────────────
 * Una clase abstracta es una clase que:
 *   • NO se puede instanciar directamente (no puedes hacer new Persona())
 *   • Sirve como "plantilla" o "molde" para otras clases
 *   • Puede tener métodos abstractos (sin implementación)
 *   • Las clases hijas DEBEN implementar los métodos abstractos
 * 
 * 📌 ¿POR QUÉ USAMOS abstract AQUÍ?
 * ─────────────────────────────────
 * Porque "Persona" es un concepto genérico. En nuestro hotel,
 * no tenemos "personas" sueltas, tenemos "clientes" que son
 * un tipo específico de persona.
 * 
 * 🎯 EJEMPLO DE USO:
 * ─────────────────────────────────
 *   // ❌ ESTO NO SE PUEDE HACER:
 *   // Persona p = new Persona("Juan", "666123456");
 *   
 *   // ✅ ESTO SÍ SE PUEDE HACER:
 *   // Cliente c = new Cliente("Juan", "666123456", "juan@email.com");
 * 
 * ============================================================
 */
public abstract class Persona {
    
    // ════════════════════════════════════════════════════════
    // 📦 ATRIBUTOS (Variables de instancia)
    // ════════════════════════════════════════════════════════
    // Los declaramos como 'protected' para que las clases hijas
    // (como Cliente) puedan acceder a ellos directamente.
    // 
    // Niveles de acceso:
    //   • private   → Solo esta clase
    //   • protected → Esta clase + clases hijas
    //   • public    → Cualquier clase
    //   • (default) → Solo clases del mismo paquete
    
    protected String nombre;    // Nombre de la persona
    protected String telefono;  // Teléfono de contacto
    
    // ════════════════════════════════════════════════════════
    // 🔧 CONSTRUCTOR
    // ════════════════════════════════════════════════════════
    /**
     * Constructor de la clase Persona.
     * 
     * 📌 NOTA: Aunque la clase es abstracta, tiene constructor.
     * Este constructor será llamado por las clases hijas usando super().
     * 
     * @param nombre   Nombre completo de la persona
     * @param telefono Número de teléfono
     */
    public Persona(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }
    
    // ════════════════════════════════════════════════════════
    // 🎭 MÉTODO ABSTRACTO
    // ════════════════════════════════════════════════════════
    /**
     * Muestra la información de la persona por consola.
     * 
     * 📌 ¿QUÉ ES UN MÉTODO ABSTRACTO?
     * ─────────────────────────────────
     * Un método abstracto:
     *   • NO tiene cuerpo (no tiene las llaves {})
     *   • Solo define la "firma" del método
     *   • Las clases hijas DEBEN implementarlo
     * 
     * 🎯 ¿POR QUÉ ES ABSTRACTO?
     * ─────────────────────────────────
     * Porque cada tipo de persona mostrará su información de forma diferente.
     * Un Cliente mostrará su email, un Empleado mostraría su puesto, etc.
     */
    public abstract void mostrarInfo();
    
    // ════════════════════════════════════════════════════════
    // 📤 GETTERS - Métodos para OBTENER valores
    // ════════════════════════════════════════════════════════
    // Los getters permiten acceder a los atributos privados/protected
    // desde fuera de la clase, manteniendo el encapsulamiento.
    
    /**
     * Obtiene el nombre de la persona.
     * @return El nombre
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el teléfono de la persona.
     * @return El teléfono
     */
    public String getTelefono() {
        return telefono;
    }
    
    // ════════════════════════════════════════════════════════
    // 📥 SETTERS - Métodos para MODIFICAR valores
    // ════════════════════════════════════════════════════════
    // Los setters permiten modificar los atributos de forma controlada.
    // Aquí podríamos añadir validaciones si fuera necesario.
    
    /**
     * Establece el nombre de la persona.
     * @param nombre El nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Establece el teléfono de la persona.
     * @param telefono El nuevo teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
