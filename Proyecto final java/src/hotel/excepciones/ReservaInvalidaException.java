package hotel.excepciones;

/**
 * Excepcion personalizada para errores de reserva
 */
public class ReservaInvalidaException extends Exception {
    
    public ReservaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
