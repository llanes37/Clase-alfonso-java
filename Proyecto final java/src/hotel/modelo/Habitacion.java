package hotel.modelo;

import hotel.excepciones.ReservaInvalidaException;

/**
 * Clase Habitacion que implementa la interfaz Reservable
 * Representa una habitacion del hotel
 */
public class Habitacion implements Reservable {
    
    private int id;
    private String tipo;
    private double precioPorNoche;
    private boolean disponible;
    
    // Constructor
    public Habitacion(int id, String tipo, double precioPorNoche, boolean disponible) {
        this.id = id;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.disponible = disponible;
    }
    
    // Implementacion de los metodos de la interfaz Reservable
    @Override
    public void reservar() throws ReservaInvalidaException {
        if (!disponible) {
            throw new ReservaInvalidaException("La habitacion " + id + " no esta disponible");
        }
        this.disponible = false;
        System.out.println("Habitacion " + id + " reservada correctamente");
    }
    
    @Override
    public void cancelarReserva() {
        this.disponible = true;
        System.out.println("Reserva de habitacion " + id + " cancelada");
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }
    
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    @Override
    public String toString() {
        return "Habitacion " + id + " (" + tipo + ") - " + precioPorNoche + " euros/noche - " + 
               (disponible ? "Disponible" : "Ocupada");
    }
}
