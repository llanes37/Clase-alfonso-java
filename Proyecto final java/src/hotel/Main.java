package hotel;

import hotel.servicio.ServicioHotel;

/**
 * Clase principal - Punto de entrada del programa
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            ServicioHotel servicio = new ServicioHotel();
            servicio.iniciar();
        } catch (Exception e) {
            System.err.println("Error al iniciar el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
