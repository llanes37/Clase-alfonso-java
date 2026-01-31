package hotel.modelo;

/**
 * ============================================================
 * 🟢 CLASE: Cliente (hereda de Persona)
 * ============================================================
 * 
 * 📚 ¿QUÉ ES LA HERENCIA?
 * ─────────────────────────────────
 * La herencia es un mecanismo de la POO que permite:
 *   • Crear una clase nueva basada en otra existente
 *   • La clase hija "hereda" todos los atributos y métodos de la padre
 *   • La clase hija puede añadir sus propios atributos y métodos
 *   • La clase hija puede "sobreescribir" métodos de la padre
 * 
 * 📌 SINTAXIS:
 * ─────────────────────────────────
 *   public class ClaseHija extends ClasePadre { ... }
 * 
 * 🔗 RELACIÓN "ES UN/UNA":
 * ─────────────────────────────────
 * Cuando usamos herencia, decimos que:
 *   "Un Cliente ES UNA Persona"
 *   "Un Cliente ES UN tipo de Persona"
 * 
 * 🎯 VENTAJAS:
 * ─────────────────────────────────
 *   • Reutilización de código (no repetimos nombre y telefono)
 *   • Código más organizado y mantenible
 *   • Facilita la extensibilidad (podríamos crear Empleado, Proveedor, etc.)
 * 
 * ============================================================
 */
public class Cliente extends Persona {
    
    // ════════════════════════════════════════════════════════
    // 📦 ATRIBUTOS ADICIONALES
    // ════════════════════════════════════════════════════════
    // Cliente hereda 'nombre' y 'telefono' de Persona.
    // Aquí añadimos los atributos específicos de un cliente.
    
    private int id;        // ID del cliente en la base de datos
    private String email;  // Correo electrónico del cliente
    
    // ════════════════════════════════════════════════════════
    // 🔧 CONSTRUCTOR
    // ════════════════════════════════════════════════════════
    /**
     * Constructor de la clase Cliente.
     * 
     * 📌 PALABRA CLAVE: super()
     * ─────────────────────────────────
     * 'super()' llama al constructor de la clase padre (Persona).
     * Esto es OBLIGATORIO cuando la clase padre tiene un constructor
     * con parámetros y no tiene constructor vacío.
     * 
     * 🔄 FLUJO DE EJECUCIÓN:
     * ─────────────────────────────────
     *   1. Se llama a super(nombre, telefono)
     *   2. El constructor de Persona inicializa nombre y telefono
     *   3. Volvemos aquí e inicializamos email
     * 
     * @param nombre   Nombre del cliente (heredado de Persona)
     * @param telefono Teléfono del cliente (heredado de Persona)
     * @param email    Correo electrónico del cliente
     */
    public Cliente(String nombre, String telefono, String email) {
        // Llamamos al constructor de la clase padre (Persona)
        super(nombre, telefono);
        
        // Inicializamos el atributo propio de Cliente
        this.email = email;
    }
    
    /**
     * Constructor completo con ID.
     * Útil cuando recuperamos un cliente de la base de datos.
     * 
     * @param id       ID del cliente en la BD
     * @param nombre   Nombre del cliente
     * @param telefono Teléfono del cliente
     * @param email    Correo electrónico
     */
    public Cliente(int id, String nombre, String telefono, String email) {
        super(nombre, telefono);
        this.id = id;
        this.email = email;
    }
    
    // ════════════════════════════════════════════════════════
    // 🔄 MÉTODO SOBREESCRITO: mostrarInfo()
    // ════════════════════════════════════════════════════════
    /**
     * Muestra la información completa del cliente por consola.
     * 
     * 📌 ANOTACIÓN @Override
     * ─────────────────────────────────
     * @Override indica que estamos "sobreescribiendo" un método
     * de la clase padre. Es opcional pero MUY RECOMENDABLE porque:
     *   • El compilador verifica que realmente existe en la padre
     *   • Hace el código más legible
     *   • Evita errores por typos en el nombre del método
     * 
     * 📌 ¿QUÉ ES SOBREESCRIBIR (Override)?
     * ─────────────────────────────────
     * Es proporcionar una implementación específica de un método
     * que ya existe en la clase padre (o que es abstracto).
     * 
     * 🎭 POLIMORFISMO:
     * ─────────────────────────────────
     * Si tenemos:
     *   Persona p = new Cliente("Juan", "666", "juan@mail.com");
     *   p.mostrarInfo();  // ← Ejecuta el mostrarInfo() de CLIENTE
     * 
     * Esto es el POLIMORFISMO: mismo método, diferente comportamiento
     * según el tipo real del objeto.
     */
    @Override
    public void mostrarInfo() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║         📋 DATOS DEL CLIENTE         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ ID:       " + id);
        System.out.println("║ Nombre:   " + nombre);      // Heredado de Persona
        System.out.println("║ Teléfono: " + telefono);    // Heredado de Persona
        System.out.println("║ Email:    " + email);       // Propio de Cliente
        System.out.println("╚══════════════════════════════════════╝");
    }
    
    // ════════════════════════════════════════════════════════
    // 📤 GETTERS
    // ════════════════════════════════════════════════════════
    // Nota: getNombre() y getTelefono() ya están en Persona,
    // no necesitamos redefinirlos. Cliente los hereda automáticamente.
    
    /**
     * Obtiene el ID del cliente.
     * @return El ID
     */
    public int getId() {
        return id;
    }
    
    /**
     * Obtiene el email del cliente.
     * @return El email
     */
    public String getEmail() {
        return email;
    }
    
    // ════════════════════════════════════════════════════════
    // 📥 SETTERS
    // ════════════════════════════════════════════════════════
    
    /**
     * Establece el ID del cliente.
     * @param id El nuevo ID
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Establece el email del cliente.
     * @param email El nuevo email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    // ════════════════════════════════════════════════════════
    // 📝 MÉTODO toString()
    // ════════════════════════════════════════════════════════
    /**
     * Devuelve una representación en texto del cliente.
     * 
     * 📌 ¿CUÁNDO SE USA toString()?
     * ─────────────────────────────────
     * Se llama automáticamente cuando:
     *   • Concatenamos el objeto con un String: "Cliente: " + cliente
     *   • Usamos System.out.println(cliente)
     *   • Llamamos explícitamente: cliente.toString()
     */
    @Override
    public String toString() {
        return "Cliente [ID=" + id + ", Nombre=" + nombre + 
               ", Teléfono=" + telefono + ", Email=" + email + "]";
    }
}
