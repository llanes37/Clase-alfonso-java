package hotel.dao;

import hotel.modelo.Reserva;
import hotel.modelo.Cliente;
import hotel.modelo.Habitacion;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para operaciones CRUD de Reserva
 */
public class ReservaDAO {
    
    private ClienteDAO clienteDAO;
    private HabitacionDAO habitacionDAO;
    
    public ReservaDAO() {
        this.clienteDAO = new ClienteDAO();
        this.habitacionDAO = new HabitacionDAO();
    }
    
    // Insertar reserva
    public void insertarReserva(Reserva r) {
        String sql = "INSERT INTO Reservas (idCliente, idHabitacion, fechaEntrada, fechaSalida, total) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, r.getCliente().getId());
            stmt.setInt(2, r.getHabitacion().getId());
            
            // Convertir java.util.Date a java.sql.Date
            java.sql.Date fechaEntradaSQL = new java.sql.Date(r.getFechaEntrada().getTime());
            java.sql.Date fechaSalidaSQL = new java.sql.Date(r.getFechaSalida().getTime());
            
            stmt.setDate(3, fechaEntradaSQL);
            stmt.setDate(4, fechaSalidaSQL);
            stmt.setDouble(5, r.getImporteTotal());
            
            int filas = stmt.executeUpdate();
            
            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    r.setId(rs.getInt(1));
                }
                System.out.println("Reserva creada con ID: " + r.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("Error al insertar reserva: " + e.getMessage());
        }
    }
    
    // Eliminar reserva
    public void eliminarReserva(int id) {
        String sql = "DELETE FROM Reservas WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int filas = stmt.executeUpdate();
            
            if (filas > 0) {
                System.out.println("Reserva eliminada correctamente");
            } else {
                System.out.println("No se encontro la reserva con ID: " + id);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar reserva: " + e.getMessage());
        }
    }
    
    // Listar todas las reservas
    public ArrayList<Reserva> listarReservas() {
        ArrayList<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM Reservas ORDER BY fechaEntrada DESC";
        
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int idReserva = rs.getInt("id");
                int idCliente = rs.getInt("idCliente");
                int idHabitacion = rs.getInt("idHabitacion");
                
                // Buscar cliente y habitacion
                Cliente cliente = clienteDAO.buscarPorId(idCliente);
                Habitacion habitacion = habitacionDAO.obtenerPorId(idHabitacion);
                
                if (cliente != null && habitacion != null) {
                    Reserva reserva = new Reserva(
                        idReserva,
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
            System.err.println("Error al listar reservas: " + e.getMessage());
        }
        
        return reservas;
    }
    
    // Buscar reserva por ID
    public Reserva buscarPorId(int id) {
        String sql = "SELECT * FROM Reservas WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = clienteDAO.buscarPorId(rs.getInt("idCliente"));
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
                    return reserva;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar reserva: " + e.getMessage());
        }
        
        return null;
    }
}
