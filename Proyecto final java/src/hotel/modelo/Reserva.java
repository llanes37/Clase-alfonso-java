package hotel.modelo;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Clase Reserva
 * Representa una reserva de hotel (composicion con Cliente y Habitacion)
 */
public class Reserva {
    
    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private Date fechaEntrada;
    private Date fechaSalida;
    private double importeTotal;
    
    // Constructor completo
    public Reserva(int id, Cliente cliente, Habitacion habitacion, Date fechaEntrada, Date fechaSalida) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.importeTotal = calcularImporteTotal();
    }
    
    // Constructor sin id (para reservas nuevas)
    public Reserva(Cliente cliente, Habitacion habitacion, Date fechaEntrada, Date fechaSalida) {
        this(0, cliente, habitacion, fechaEntrada, fechaSalida);
    }
    
    // Calcula el importe total de la reserva
    public double calcularImporteTotal() {
        if (fechaEntrada == null || fechaSalida == null) {
            return 0;
        }
        
        // Calcular diferencia en dias
        long diferencia = fechaSalida.getTime() - fechaEntrada.getTime();
        long noches = TimeUnit.DAYS.convert(diferencia, TimeUnit.MILLISECONDS);
        
        if (noches <= 0) {
            noches = 1; // Minimo 1 noche
        }
        
        return noches * habitacion.getPrecioPorNoche();
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Habitacion getHabitacion() {
        return habitacion;
    }
    
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
    
    public Date getFechaEntrada() {
        return fechaEntrada;
    }
    
    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    
    public Date getFechaSalida() {
        return fechaSalida;
    }
    
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    
    public double getImporteTotal() {
        return importeTotal;
    }
    
    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }
    
    @Override
    public String toString() {
        return "Reserva #" + id + " - Cliente: " + cliente.getNombre() + 
               " - Hab: " + habitacion.getId() + " - Total: " + importeTotal + " euros";
    }
}
