package hotel.dao;

import hotel.modelo.Habitacion;
import java.sql.*;
import java.util.ArrayList;

/**
 * ============================================================
 * 🏠 CLASE: HabitacionDAO (Data Access Object para Habitaciones)
 * ============================================================
 * 
 * Esta clase gestiona todas las operaciones de base de datos
 * relacionadas con las habitaciones del hotel.
 * 
 * 📌 MÉTODOS PRINCIPALES:
 * ─────────────────────────────────
 *   • listarDisponibles() → Obtiene habitaciones libres
 *   • obtenerPorId()      → Busca una habitación por su ID
 *   • actualizarDisponibilidad() → Cambia el estado disponible/ocupada
 * 
 * ============================================================
 */
public class HabitacionDAO {
    
    // ════════════════════════════════════════════════════════
    // 📋 MÉTODO: listarDisponibles
    // ════════════════════════════════════════════════════════
    /**
     * Obtiene todas las habitaciones que están disponibles.
     * 
     * 📌 CONSULTA SQL:
     * ─────────────────────────────────
     *   SELECT * FROM Habitaciones WHERE disponible = 1
     * 
     * En SQL Server, BIT usa 1 para true y 0 para false.
     * 
     * @return Lista de habitaciones disponibles
     */
    public ArrayList<Habitacion> listarDisponibles() {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        
        // Filtramos por disponible = 1 (true en SQL Server BIT)
        String sql = "SELECT * FROM Habitaciones WHERE disponible = 1 ORDER BY id";
        
        try (
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            // Recorremos los resultados
            while (rs.next()) {
                Habitacion hab = new Habitacion(
                    rs.getInt("id"),              // ID de la habitación
                    rs.getString("tipo"),          // Tipo (individual/doble/suite)
                    rs.getDouble("precio"),        // Precio por noche
                    rs.getBoolean("disponible")    // Estado de disponibilidad
                );
                habitaciones.add(hab);
            }
            
            System.out.println("📋 Se encontraron " + habitaciones.size() + 
                             " habitaciones disponibles.");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar habitaciones disponibles: " + e.getMessage());
        }
        
        return habitaciones;
    }
    
    // ════════════════════════════════════════════════════════
    // 📋 MÉTODO: listarTodas
    // ════════════════════════════════════════════════════════
    /**
     * Obtiene TODAS las habitaciones (disponibles y ocupadas).
     * 
     * @return Lista completa de habitaciones
     */
    public ArrayList<Habitacion> listarTodas() {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        String sql = "SELECT * FROM Habitaciones ORDER BY id";
        
        try (
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Habitacion hab = new Habitacion(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible")
                );
                habitaciones.add(hab);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar habitaciones: " + e.getMessage());
        }
        
        return habitaciones;
    }
    
    // ════════════════════════════════════════════════════════
    // 🔍 MÉTODO: obtenerPorId
    // ════════════════════════════════════════════════════════
    /**
     * Busca una habitación por su ID.
     * 
     * 📌 EJEMPLO DE USO:
     * ─────────────────────────────────
     *   HabitacionDAO dao = new HabitacionDAO();
     *   Habitacion hab = dao.obtenerPorId(1);
     *   if (hab != null) {
     *       System.out.println(hab.getTipo());  // "individual"
     *   }
     * 
     * @param id El ID de la habitación a buscar
     * @return La habitación encontrada, o null si no existe
     */
    public Habitacion obtenerPorId(int id) {
        String sql = "SELECT * FROM Habitaciones WHERE id = ?";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
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
            System.err.println("❌ Error al buscar habitación: " + e.getMessage());
        }
        
        return null;  // No se encontró
    }
    
    // ════════════════════════════════════════════════════════
    // 🔄 MÉTODO: actualizarDisponibilidad
    // ════════════════════════════════════════════════════════
    /**
     * Cambia el estado de disponibilidad de una habitación.
     * 
     * 📌 USO TÍPICO:
     * ─────────────────────────────────
     *   // Al crear una reserva → marcar como ocupada
     *   dao.actualizarDisponibilidad(5, false);
     *   
     *   // Al cancelar una reserva → marcar como disponible
     *   dao.actualizarDisponibilidad(5, true);
     * 
     * 📌 SQL EJECUTADO:
     * ─────────────────────────────────
     *   UPDATE Habitaciones SET disponible = ? WHERE id = ?
     * 
     * @param idHabitacion ID de la habitación a actualizar
     * @param disponible   Nuevo estado (true = libre, false = ocupada)
     * @return true si se actualizó correctamente
     */
    public boolean actualizarDisponibilidad(int idHabitacion, boolean disponible) {
        String sql = "UPDATE Habitaciones SET disponible = ? WHERE id = ?";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // setBoolean convierte true/false a 1/0 para SQL Server BIT
            stmt.setBoolean(1, disponible);
            stmt.setInt(2, idHabitacion);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                String estado = disponible ? "disponible ✅" : "ocupada ❌";
                System.out.println("🔄 Habitación " + idHabitacion + " marcada como " + estado);
                return true;
            } else {
                System.out.println("⚠️ No se encontró la habitación con ID: " + idHabitacion);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar disponibilidad: " + e.getMessage());
        }
        
        return false;
    }
    
    // ════════════════════════════════════════════════════════
    // ➕ MÉTODO: insertar (para pruebas/administración)
    // ════════════════════════════════════════════════════════
    /**
     * Inserta una nueva habitación en la base de datos.
     * 
     * @param habitacion La habitación a insertar
     * @return true si se insertó correctamente
     */
    public boolean insertar(Habitacion habitacion) {
        String sql = "INSERT INTO Habitaciones (tipo, precio, disponible) VALUES (?, ?, ?)";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, habitacion.getTipo());
            stmt.setDouble(2, habitacion.getPrecioPorNoche());
            stmt.setBoolean(3, habitacion.isDisponible());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    habitacion.setId(generatedKeys.getInt(1));
                }
                System.out.println("✅ Habitación insertada con ID: " + habitacion.getId());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar habitación: " + e.getMessage());
        }
        
        return false;
    }
}
