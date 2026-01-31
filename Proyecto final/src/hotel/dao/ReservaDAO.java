package hotel.dao;

import hotel.modelo.Reserva;
import hotel.modelo.Cliente;
import hotel.modelo.Habitacion;
import java.sql.*;
import java.util.ArrayList;

/**
 * ============================================================
 * 📅 CLASE: ReservaDAO (Data Access Object para Reservas)
 * ============================================================
 * 
 * Esta clase gestiona todas las operaciones de base de datos
 * relacionadas con las reservas del hotel.
 * 
 * 📌 COMPLEJIDAD ADICIONAL:
 * ─────────────────────────────────
 * La tabla Reservas tiene FOREIGN KEYS a Clientes y Habitaciones.
 * Esto significa que al leer una reserva, debemos:
 *   1. Leer los datos de la tabla Reservas
 *   2. Buscar el Cliente asociado
 *   3. Buscar la Habitación asociada
 *   4. Construir el objeto Reserva completo
 * 
 * Podríamos usar JOIN en SQL, pero para mantenerlo didáctico
 * usamos consultas separadas con los otros DAOs.
 * 
 * ============================================================
 */
public class ReservaDAO {
    
    // Necesitamos los otros DAOs para obtener Cliente y Habitacion
    private ClienteDAO clienteDAO;
    private HabitacionDAO habitacionDAO;
    
    // ════════════════════════════════════════════════════════
    // 🔧 CONSTRUCTOR
    // ════════════════════════════════════════════════════════
    /**
     * Constructor que inicializa los DAOs necesarios.
     * 
     * 📌 DEPENDENCIAS:
     * ─────────────────────────────────
     * ReservaDAO depende de ClienteDAO y HabitacionDAO porque
     * necesita obtener los objetos completos al leer reservas.
     */
    public ReservaDAO() {
        this.clienteDAO = new ClienteDAO();
        this.habitacionDAO = new HabitacionDAO();
    }
    
    // ════════════════════════════════════════════════════════
    // ➕ MÉTODO: insertarReserva (CREATE)
    // ════════════════════════════════════════════════════════
    /**
     * Inserta una nueva reserva en la base de datos.
     * 
     * 📌 IMPORTANTE:
     * ─────────────────────────────────
     * Este método solo inserta en la tabla Reservas.
     * La actualización de disponibilidad de la habitación
     * debe hacerse ANTES de llamar a este método (en ServicioHotel).
     * 
     * 📌 CONVERSIÓN DE FECHAS:
     * ─────────────────────────────────
     * java.util.Date → java.sql.Date
     * 
     * JDBC usa java.sql.Date, que es diferente de java.util.Date.
     * La conversión se hace con:
     *   new java.sql.Date(utilDate.getTime())
     * 
     * @param r La reserva a insertar
     */
    public void insertarReserva(Reserva r) {
        String sql = "INSERT INTO Reservas (idCliente, idHabitacion, fechaEntrada, fechaSalida, total) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            // Establecemos los parámetros
            stmt.setInt(1, r.getCliente().getId());      // ID del cliente
            stmt.setInt(2, r.getHabitacion().getId());   // ID de la habitación
            
            // --------------------------------------------------------
            // Conversión de java.util.Date a java.sql.Date
            // --------------------------------------------------------
            // getTime() devuelve los milisegundos desde 1/1/1970
            // Lo usamos para crear un java.sql.Date
            
            java.sql.Date fechaEntradaSQL = new java.sql.Date(r.getFechaEntrada().getTime());
            java.sql.Date fechaSalidaSQL = new java.sql.Date(r.getFechaSalida().getTime());
            
            stmt.setDate(3, fechaEntradaSQL);   // Fecha de entrada
            stmt.setDate(4, fechaSalidaSQL);    // Fecha de salida
            stmt.setDouble(5, r.getImporteTotal());  // Total calculado
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtenemos el ID generado
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    r.setId(generatedKeys.getInt(1));
                }
                System.out.println("✅ Reserva creada con ID: " + r.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar reserva: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ════════════════════════════════════════════════════════
    // 🗑️ MÉTODO: eliminarReserva (DELETE)
    // ════════════════════════════════════════════════════════
    /**
     * Elimina una reserva de la base de datos.
     * 
     * 📌 IMPORTANTE:
     * ─────────────────────────────────
     * Este método solo elimina el registro de la tabla Reservas.
     * La actualización de disponibilidad de la habitación
     * (ponerla disponible de nuevo) debe hacerse DESPUÉS
     * de llamar a este método (en ServicioHotel).
     * 
     * @param id El ID de la reserva a eliminar
     */
    public void eliminarReserva(int id) {
        String sql = "DELETE FROM Reservas WHERE id = ?";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Reserva #" + id + " eliminada correctamente.");
            } else {
                System.out.println("⚠️ No se encontró la reserva con ID: " + id);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar reserva: " + e.getMessage());
        }
    }
    
    // ════════════════════════════════════════════════════════
    // 📋 MÉTODO: listarReservas (READ ALL)
    // ════════════════════════════════════════════════════════
    /**
     * Obtiene todas las reservas de la base de datos.
     * 
     * 📌 PROCESO:
     * ─────────────────────────────────
     *   1. Leemos todos los registros de Reservas
     *   2. Para cada reserva, buscamos el Cliente por ID
     *   3. Para cada reserva, buscamos la Habitación por ID
     *   4. Construimos el objeto Reserva completo
     * 
     * @return Lista de todas las reservas
     */
    public ArrayList<Reserva> listarReservas() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas ORDER BY fechaEntrada DESC";
        
        try (
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                // Obtenemos los datos básicos
                int idReserva = rs.getInt("id");
                int idCliente = rs.getInt("idCliente");
                int idHabitacion = rs.getInt("idHabitacion");
                
                // Buscamos el cliente y la habitación
                Cliente cliente = clienteDAO.buscarPorId(idCliente);
                Habitacion habitacion = habitacionDAO.obtenerPorId(idHabitacion);
                
                // Verificamos que existan
                if (cliente != null && habitacion != null) {
                    // Creamos el objeto Reserva
                    Reserva reserva = new Reserva(
                        idReserva,
                        cliente,
                        habitacion,
                        rs.getDate("fechaEntrada"),  // java.sql.Date se convierte auto
                        rs.getDate("fechaSalida")
                    );
                    
                    // Establecemos el total de la BD (por si difiere del calculado)
                    reserva.setImporteTotal(rs.getDouble("total"));
                    
                    reservas.add(reserva);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar reservas: " + e.getMessage());
        }
        
        return reservas;
    }
    
    // ════════════════════════════════════════════════════════
    // 🔍 MÉTODO: buscarPorId (READ ONE)
    // ════════════════════════════════════════════════════════
    /**
     * Busca una reserva por su ID.
     * 
     * @param id El ID de la reserva a buscar
     * @return La reserva encontrada, o null si no existe
     */
    public Reserva buscarPorId(int id) {
        String sql = "SELECT * FROM Reservas WHERE id = ?";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                int idHabitacion = rs.getInt("idHabitacion");
                
                Cliente cliente = clienteDAO.buscarPorId(idCliente);
                Habitacion habitacion = habitacionDAO.obtenerPorId(idHabitacion);
                
                if (cliente != null && habitacion != null) {
                    Reserva reserva = new Reserva(
                        id,
                        cliente,
                        habitacion,
                        rs.getDate("fechaEntrada"),
                        rs.getDate("fechaSalida")
                    );
                    reserva.setImporteTotal(rs.getDouble("total"));
                    return reserva;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar reserva: " + e.getMessage());
        }
        
        return null;
    }
    
    // ════════════════════════════════════════════════════════
    // 📋 MÉTODO: listarPorCliente
    // ════════════════════════════════════════════════════════
    /**
     * Obtiene todas las reservas de un cliente específico.
     * 
     * @param idCliente El ID del cliente
     * @return Lista de reservas del cliente
     */
    public ArrayList<Reserva> listarPorCliente(int idCliente) {
        ArrayList<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas WHERE idCliente = ? ORDER BY fechaEntrada";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            
            Cliente cliente = clienteDAO.buscarPorId(idCliente);
            
            while (rs.next()) {
                Habitacion habitacion = habitacionDAO.obtenerPorId(rs.getInt("idHabitacion"));
                
                if (cliente != null && habitacion != null) {
                    Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        cliente,
                        habitacion,
                        rs.getDate("fechaEntrada"),
                        rs.getDate("fechaSalida")
                    );
                    reserva.setImporteTotal(rs.getDouble("total"));
                    reservas.add(reserva);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar reservas del cliente: " + e.getMessage());
        }
        
        return reservas;
    }
}
