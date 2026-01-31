package hotel.modelo;

/**
 * Interfaz Reservable
 * Define el contrato para objetos que se pueden reservar
 */
public interface Reservable {
    
    // Metodo para reservar (puede lanzar excepcion si no esta disponible)
    void reservar() throws Exception;
    
    // Metodo para cancelar la reserva
    void cancelarReserva();
}
