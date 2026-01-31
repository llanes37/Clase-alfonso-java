package hotel.dao;

import hotel.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para operaciones CRUD de Cliente
 */
public class ClienteDAO {
    
    // Insertar cliente
    public void insertarCliente(Cliente c) {
        String sql = "INSERT INTO Clientes (nombre, telefono, email) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getTelefono());
            stmt.setString(3, c.getEmail());
            
            int filas = stmt.executeUpdate();
            
            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    c.setId(rs.getInt(1));
                }
                System.out.println("Cliente insertado con ID: " + c.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
        }
    }
    
    // Buscar cliente por ID
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM Clientes WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        
        return null;
    }
    
    // Listar todos los clientes
    public ArrayList<Cliente> listarTodos() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes ORDER BY id";
        
        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                clientes.add(c);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        
        return clientes;
    }
    
    // Actualizar cliente
    public void actualizar(Cliente c) {
        String sql = "UPDATE Clientes SET nombre = ?, telefono = ?, email = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, c.getNombre());
            stmt.setString(2, c.getTelefono());
            stmt.setString(3, c.getEmail());
            stmt.setInt(4, c.getId());
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Cliente actualizado");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }
    
    // Eliminar cliente
    public void eliminar(int id) {
        String sql = "DELETE FROM Clientes WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Cliente eliminado");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
}
