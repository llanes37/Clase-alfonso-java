package hotel.excepciones;

/**
 * ============================================================
 * 🔴 EXCEPCIÓN PERSONALIZADA: ReservaInvalidaException
 * ============================================================
 * 
 * 📚 ¿QUÉ SON LAS EXCEPCIONES?
 * ─────────────────────────────────
 * Las excepciones son eventos que ocurren durante la ejecución
 * de un programa y que interrumpen el flujo normal de instrucciones.
 * 
 * Ejemplos de excepciones comunes:
 *   • NullPointerException → Intentar usar un objeto null
 *   • ArrayIndexOutOfBoundsException → Índice fuera del array
 *   • FileNotFoundException → El archivo no existe
 * 
 * 📚 ¿POR QUÉ CREAR EXCEPCIONES PROPIAS?
 * ─────────────────────────────────
 * Crear excepciones personalizadas nos permite:
 *   • Dar mensajes de error más claros y específicos
 *   • Capturar solo los errores de nuestro dominio
 *   • Hacer el código más profesional y mantenible
 *   • Distinguir entre diferentes tipos de errores
 * 
 * 📌 JERARQUÍA DE EXCEPCIONES EN JAVA:
 * ─────────────────────────────────
 * 
 *                    Throwable
 *                   /         \
 *              Error          Exception
 *                            /         \
 *              RuntimeException    IOException, SQLException, etc.
 *              (Unchecked)              (Checked)
 * 
 * 📌 CHECKED vs UNCHECKED:
 * ─────────────────────────────────
 * 
 *   CHECKED (extends Exception):
 *   • El compilador OBLIGA a manejarlas
 *   • Hay que usar try-catch o throws
 *   • Representan errores "recuperables"
 * 
 *   UNCHECKED (extends RuntimeException):
 *   • El compilador NO obliga a manejarlas
 *   • Generalmente son errores de programación
 *   • Ejemplo: NullPointerException
 * 
 * 📌 NUESTRA ELECCIÓN:
 * ─────────────────────────────────
 * ReservaInvalidaException extiende de Exception (checked) porque:
 *   • Queremos que el programador esté OBLIGADO a manejarla
 *   • Es un error esperado y recuperable del dominio de negocio
 * 
 * ============================================================
 */
public class ReservaInvalidaException extends Exception {
    
    // ════════════════════════════════════════════════════════
    // 🔧 CONSTRUCTORES
    // ════════════════════════════════════════════════════════
    
    /**
     * Constructor con mensaje de error.
     * 
     * 📌 USO DE super():
     * ─────────────────────────────────
     * Llamamos a super(mensaje) para pasar el mensaje de error
     * a la clase padre (Exception). Este mensaje se puede
     * recuperar luego con getMessage().
     * 
     * 📌 EJEMPLO DE USO:
     * ─────────────────────────────────
     *   throw new ReservaInvalidaException("La habitación está ocupada");
     *   
     *   // Capturar:
     *   catch (ReservaInvalidaException e) {
     *       System.out.println(e.getMessage()); // "La habitación está ocupada"
     *   }
     * 
     * @param mensaje El mensaje descriptivo del error
     */
    public ReservaInvalidaException(String mensaje) {
        super(mensaje);
    }
    
    /**
     * Constructor con mensaje y causa.
     * 
     * 📌 ¿QUÉ ES LA "CAUSA"?
     * ─────────────────────────────────
     * La causa es otra excepción que originó este error.
     * Es útil cuando "envolvemos" una excepción en otra.
     * 
     * 📌 EJEMPLO DE USO:
     * ─────────────────────────────────
     *   try {
     *       // Operación que lanza SQLException
     *   } catch (SQLException e) {
     *       // "Envolvemos" la SQLException en nuestra excepción
     *       throw new ReservaInvalidaException("Error al reservar", e);
     *   }
     *   
     *   // Después podemos obtener la causa original:
     *   catch (ReservaInvalidaException e) {
     *       e.getCause(); // Devuelve la SQLException original
     *   }
     * 
     * @param mensaje El mensaje descriptivo del error
     * @param causa   La excepción que causó este error
     */
    public ReservaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    /**
     * Constructor solo con causa.
     * 
     * @param causa La excepción que causó este error
     */
    public ReservaInvalidaException(Throwable causa) {
        super(causa);
    }
}

/*
 * ════════════════════════════════════════════════════════════
 * 📝 EJEMPLOS DE USO DE LA EXCEPCIÓN
 * ════════════════════════════════════════════════════════════
 * 
 * // EJEMPLO 1: Lanzar la excepción cuando la habitación está ocupada
 * public void reservar() throws ReservaInvalidaException {
 *     if (!disponible) {
 *         throw new ReservaInvalidaException(
 *             "La habitación " + id + " no está disponible"
 *         );
 *     }
 *     disponible = false;
 * }
 * 
 * // EJEMPLO 2: Capturar la excepción
 * try {
 *     habitacion.reservar();
 *     System.out.println("Reserva realizada con éxito");
 * } catch (ReservaInvalidaException e) {
 *     System.out.println("Error: " + e.getMessage());
 *     // Mostrar al usuario que la habitación no está disponible
 * }
 * 
 * // EJEMPLO 3: Validar fechas
 * public Reserva crearReserva(Date entrada, Date salida) 
 *         throws ReservaInvalidaException {
 *     
 *     if (entrada.after(salida)) {
 *         throw new ReservaInvalidaException(
 *             "La fecha de entrada no puede ser posterior a la de salida"
 *         );
 *     }
 *     // ... crear la reserva
 * }
 * 
 * // EJEMPLO 4: Propagar la excepción
 * public void procesarReserva() throws ReservaInvalidaException {
 *     // No usamos try-catch, dejamos que la excepción "suba"
 *     // al método que llamó a procesarReserva()
 *     habitacion.reservar();
 * }
 */
