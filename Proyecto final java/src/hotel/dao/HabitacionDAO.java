package hotel.dao;

import hotel.modelo.Habitacion;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para operaciones de Habitacion
 */
public class HabitacionDAO {
    
    // Listar habitaciones disponibles
    public ArrayList<Habitacion> listarDisponibles() {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        String sql = "SELECT * FROM Habitaciones WHERE disponible = 1 ORDER BY id";
        
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Habitacion h = new Habitacion(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible")
                );
                habitaciones.add(h);
            }
            
            System.out.println("Habitaciones disponibles: " + habitaciones.size());
            
        } catch (SQLException e) {
            System.err.println("Error al listar habitaciones: " + e.getMessage());
        }
        
        return habitaciones;
    }
    
    // Listar todas las habitaciones
    public ArrayList<Habitacion> listarTodas() {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        String sql = "SELECT * FROM Habitaciones ORDER BY id";
        
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Habitacion h = new Habitacion(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible")
                );
                habitaciones.add(h);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar habitaciones: " + e.getMessage());
        }
        
        return habitaciones;
    }
    
    // Obtener habitacion por ID
    public Habitacion obtenerPorId(int id) {
        String sql = "SELECT * FROM Habitaciones WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Habitacion(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar habitacion: " + e.getMessage());
        }
        
        return null;
    }
    
    // Actualizar disponibilidad
    public void actualizarDisponibilidad(int id, boolean disponible) {
        String sql = "UPDATE Habitaciones SET disponible = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, disponible);
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar disponibilidad: " + e.getMessage());
        }
    }
}
