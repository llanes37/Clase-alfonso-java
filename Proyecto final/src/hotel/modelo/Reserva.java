package hotel.modelo;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * ============================================================
 * 📅 CLASE: Reserva
 * ============================================================
 * 
 * Representa una reserva en el sistema del hotel.
 * Una reserva vincula:
 *   • Un CLIENTE (quién reserva)
 *   • Una HABITACIÓN (qué se reserva)
 *   • Un PERÍODO (cuándo)
 *   • Un IMPORTE (cuánto cuesta)
 * 
 * 📚 COMPOSICIÓN vs HERENCIA
 * ─────────────────────────────────
 * Aquí usamos COMPOSICIÓN: Reserva "tiene un" Cliente y "tiene una" Habitación.
 * Es diferente de la herencia ("es un").
 * 
 * La composición se usa cuando una clase contiene objetos de otras clases
 * como atributos. Es la relación "tiene un" o "está compuesto por".
 * 
 * Ejemplo:
 *   • Reserva TIENE UN Cliente (composición)
 *   • Cliente ES UNA Persona (herencia)
 * 
 * ============================================================
 */
public class Reserva {
    
    // ════════════════════════════════════════════════════════
    // 📦 ATRIBUTOS
    // ════════════════════════════════════════════════════════
    
    private int id;                 // ID único de la reserva
    private Cliente cliente;        // El cliente que hace la reserva (COMPOSICIÓN)
    private Habitacion habitacion;  // La habitación reservada (COMPOSICIÓN)
    private Date fechaEntrada;      // Fecha de check-in
    private Date fechaSalida;       // Fecha de check-out
    private double importeTotal;    // Coste total de la reserva
    
    // ════════════════════════════════════════════════════════
    // 🔧 CONSTRUCTOR
    // ════════════════════════════════════════════════════════
    /**
     * Constructor de la clase Reserva.
     * 
     * 📌 NOTA IMPORTANTE:
     * ─────────────────────────────────
     * El constructor NO recibe el importeTotal. En su lugar,
     * lo CALCULA automáticamente llamando a calcularImporteTotal().
     * 
     * Esto es una buena práctica porque:
     *   • Evita errores de cálculo manual
     *   • El importe siempre será consistente con las fechas y precio
     * 
     * @param id           ID de la reserva
     * @param cliente      Cliente que realiza la reserva
     * @param habitacion   Habitación reservada
     * @param fechaEntrada Fecha de entrada (check-in)
     * @param fechaSalida  Fecha de salida (check-out)
     */
    public Reserva(int id, Cliente cliente, Habitacion habitacion, 
                   Date fechaEntrada, Date fechaSalida) {
        this.id = id;
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        
        // Calculamos el importe automáticamente
        this.importeTotal = calcularImporteTotal();
    }
    
    /**
     * Constructor simplificado (sin ID, para nuevas reservas).
     * El ID se asignará cuando se guarde en la base de datos.
     */
    public Reserva(Cliente cliente, Habitacion habitacion, 
                   Date fechaEntrada, Date fechaSalida) {
        this(0, cliente, habitacion, fechaEntrada, fechaSalida);
    }
    
    // ════════════════════════════════════════════════════════
    // 🧮 MÉTODO DE CÁLCULO
    // ════════════════════════════════════════════════════════
    /**
     * Calcula el importe total de la reserva.
     * 
     * 📌 FÓRMULA:
     * ─────────────────────────────────
     *   Importe = Número de noches × Precio por noche
     * 
     * 📌 CÁLCULO DE DÍAS ENTRE FECHAS:
     * ─────────────────────────────────
     * Para calcular la diferencia entre dos fechas en Java:
     *   1. Obtenemos los milisegundos de cada fecha (.getTime())
     *   2. Restamos los milisegundos
     *   3. Convertimos a días dividiendo por milisegundos/día
     * 
     * @return El importe total calculado
     */
    public double calcularImporteTotal() {
        // Verificamos que las fechas no sean null
        if (fechaEntrada == null || fechaSalida == null) {
            return 0;
        }
        
        // Calculamos la diferencia en milisegundos
        long diferenciaMs = fechaSalida.getTime() - fechaEntrada.getTime();
        
        // Convertimos milisegundos a días
        // TimeUnit.DAYS.convert() es más legible que dividir manualmente
        long numeroNoches = TimeUnit.DAYS.convert(diferenciaMs, TimeUnit.MILLISECONDS);
        
        // Si el número de noches es 0 o negativo, al menos cobramos 1 noche
        if (numeroNoches <= 0) {
            numeroNoches = 1;
        }
        
        // Calculamos el importe total
        // Usamos el precio por noche de la habitación
        double total = numeroNoches * habitacion.getPrecioPorNoche();
        
        return total;
    }
    
    // ════════════════════════════════════════════════════════
    // 📝 MÉTODO toString()
    // ════════════════════════════════════════════════════════
    /**
     * Devuelve una representación completa de la reserva.
     * 
     * 📌 FORMATO DE SALIDA:
     * ─────────────────────────────────
     * Muestra toda la información de la reserva de forma legible,
     * incluyendo datos del cliente, habitación, fechas e importe.
     */
    @Override
    public String toString() {
        // Creamos un StringBuilder para construir el texto de forma eficiente
        StringBuilder sb = new StringBuilder();
        
        sb.append("\n╔══════════════════════════════════════════════════════════╗\n");
        sb.append("║                    📋 RESERVA #" + String.format("%-5d", id) + "                       ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║ 👤 CLIENTE:                                              ║\n");
        sb.append("║    • Nombre:   " + String.format("%-40s", cliente.getNombre()) + " ║\n");
        sb.append("║    • Email:    " + String.format("%-40s", cliente.getEmail()) + " ║\n");
        sb.append("║    • Teléfono: " + String.format("%-40s", cliente.getTelefono()) + " ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║ 🏠 HABITACIÓN:                                           ║\n");
        sb.append("║    • Número:   " + String.format("%-40s", habitacion.getId()) + " ║\n");
        sb.append("║    • Tipo:     " + String.format("%-40s", habitacion.getTipo()) + " ║\n");
        sb.append("║    • Precio:   " + String.format("%-40s", habitacion.getPrecioPorNoche() + "€/noche") + " ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║ 📅 FECHAS:                                               ║\n");
        sb.append("║    • Entrada:  " + String.format("%-40s", fechaEntrada) + " ║\n");
        sb.append("║    • Salida:   " + String.format("%-40s", fechaSalida) + " ║\n");
        sb.append("╠══════════════════════════════════════════════════════════╣\n");
        sb.append("║ 💰 IMPORTE TOTAL: " + String.format("%38s", String.format("%.2f€", importeTotal)) + " ║\n");
        sb.append("╚══════════════════════════════════════════════════════════╝\n");
        
        return sb.toString();
    }
    
    // ════════════════════════════════════════════════════════
    // 📤 GETTERS
    // ════════════════════════════════════════════════════════
    
    public int getId() {
        return id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public Habitacion getHabitacion() {
        return habitacion;
    }
    
    public Date getFechaEntrada() {
        return fechaEntrada;
    }
    
    public Date getFechaSalida() {
        return fechaSalida;
    }
    
    public double getImporteTotal() {
        return importeTotal;
    }
    
    // ════════════════════════════════════════════════════════
    // 📥 SETTERS
    // ════════════════════════════════════════════════════════
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
    
    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
        // Recalculamos el importe cuando cambian las fechas
        this.importeTotal = calcularImporteTotal();
    }
    
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
        // Recalculamos el importe cuando cambian las fechas
        this.importeTotal = calcularImporteTotal();
    }
    
    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }
}
