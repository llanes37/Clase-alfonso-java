package hotel.dao;

import hotel.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;

/**
 * ============================================================
 * 👤 CLASE: ClienteDAO (Data Access Object para Clientes)
 * ============================================================
 * 
 * 📚 ¿QUÉ ES EL PATRÓN DAO?
 * ─────────────────────────────────
 * DAO (Data Access Object) es un patrón de diseño que:
 *   • Separa la lógica de acceso a datos de la lógica de negocio
 *   • Encapsula todas las operaciones de base de datos
 *   • Hace el código más mantenible y testeable
 * 
 * 📌 BENEFICIOS:
 * ─────────────────────────────────
 *   • Si cambiamos de base de datos, solo modificamos el DAO
 *   • La lógica de negocio no conoce SQL ni JDBC
 *   • Código más organizado y profesional
 * 
 * 📚 OPERACIONES CRUD:
 * ─────────────────────────────────
 *   C = Create → INSERT INTO
 *   R = Read   → SELECT
 *   U = Update → UPDATE
 *   D = Delete → DELETE
 * 
 * ============================================================
 */
public class ClienteDAO {
    
    // ════════════════════════════════════════════════════════
    // 📥 MÉTODO: insertarCliente (CREATE)
    // ════════════════════════════════════════════════════════
    /**
     * Inserta un nuevo cliente en la base de datos.
     * 
     * 📌 ¿QUÉ ES PreparedStatement?
     * ─────────────────────────────────
     * PreparedStatement es una forma segura de ejecutar SQL.
     * Usa marcadores de posición (?) en lugar de concatenar strings.
     * 
     * ⚠️ NUNCA hagas esto (vulnerable a SQL Injection):
     *   String sql = "INSERT INTO Clientes VALUES ('" + nombre + "')";
     * 
     * ✅ SIEMPRE usa PreparedStatement:
     *   String sql = "INSERT INTO Clientes (nombre) VALUES (?)";
     *   stmt.setString(1, nombre);
     * 
     * 📌 STATEMENT.RETURN_GENERATED_KEYS:
     * ─────────────────────────────────
     * Esta constante indica que queremos recuperar el ID generado
     * automáticamente por la base de datos (IDENTITY).
     * 
     * @param c El cliente a insertar
     */
    public void insertarCliente(Cliente c) {
        // SQL con marcadores de posición (?)
        // No incluimos 'id' porque es IDENTITY (se genera automáticamente)
        String sql = "INSERT INTO Clientes (nombre, telefono, email) VALUES (?, ?, ?)";
        
        // try-with-resources: cierra automáticamente los recursos al terminar
        // Sintaxis: try (recurso1; recurso2) { ... }
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            // --------------------------------------------------------
            // PASO 1: Establecer los valores de los parámetros
            // --------------------------------------------------------
            // setString(1, valor) → Primer parámetro (?)
            // setString(2, valor) → Segundo parámetro (?)
            // etc.
            
            stmt.setString(1, c.getNombre());    // Primer ?
            stmt.setString(2, c.getTelefono());  // Segundo ?
            stmt.setString(3, c.getEmail());     // Tercer ?
            
            // --------------------------------------------------------
            // PASO 2: Ejecutar la sentencia
            // --------------------------------------------------------
            // executeUpdate() → Para INSERT, UPDATE, DELETE
            // executeQuery()  → Para SELECT
            //
            // Devuelve el número de filas afectadas
            
            int filasAfectadas = stmt.executeUpdate();
            
            // --------------------------------------------------------
            // PASO 3: Obtener el ID generado
            // --------------------------------------------------------
            // getGeneratedKeys() devuelve un ResultSet con los IDs generados
            
            if (filasAfectadas > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    // Actualizamos el objeto cliente con su nuevo ID
                    c.setId(generatedKeys.getInt(1));
                }
                System.out.println("✅ Cliente insertado con ID: " + c.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ════════════════════════════════════════════════════════
    // 🔍 MÉTODO: buscarPorId (READ)
    // ════════════════════════════════════════════════════════
    /**
     * Busca un cliente por su ID.
     * 
     * 📌 ¿QUÉ ES ResultSet?
     * ─────────────────────────────────
     * ResultSet es el objeto que contiene los resultados de un SELECT.
     * Es como una tabla con filas y columnas.
     * 
     * Métodos principales:
     *   • next() → Avanza a la siguiente fila (devuelve false si no hay más)
     *   • getInt("columna") → Obtiene un entero
     *   • getString("columna") → Obtiene un String
     *   • getDouble("columna") → Obtiene un decimal
     *   • getDate("columna") → Obtiene una fecha
     * 
     * @param id El ID del cliente a buscar
     * @return El cliente encontrado, o null si no existe
     */
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM Clientes WHERE id = ?";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Establecemos el parámetro
            stmt.setInt(1, id);
            
            // executeQuery() devuelve un ResultSet
            ResultSet rs = stmt.executeQuery();
            
            // Si hay resultados, creamos el objeto Cliente
            if (rs.next()) {
                return new Cliente(
                    rs.getInt("id"),           // Columna 'id'
                    rs.getString("nombre"),    // Columna 'nombre'
                    rs.getString("telefono"),  // Columna 'telefono'
                    rs.getString("email")      // Columna 'email'
                );
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar cliente: " + e.getMessage());
        }
        
        return null;  // No se encontró el cliente
    }
    
    // ════════════════════════════════════════════════════════
    // 📋 MÉTODO: listarTodos (READ ALL)
    // ════════════════════════════════════════════════════════
    /**
     * Obtiene todos los clientes de la base de datos.
     * 
     * 📌 RECORRIENDO UN ResultSet:
     * ─────────────────────────────────
     * Usamos un bucle while para recorrer todas las filas:
     * 
     *   while (rs.next()) {
     *       // Procesar cada fila
     *   }
     * 
     * @return Lista con todos los clientes
     */
    public ArrayList<Cliente> listarTodos() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes ORDER BY nombre";
        
        try (
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();  // Statement simple, sin parámetros
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            // Recorremos todas las filas del resultado
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("email")
                );
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar clientes: " + e.getMessage());
        }
        
        return clientes;
    }
    
    // ════════════════════════════════════════════════════════
    // 🔄 MÉTODO: actualizar (UPDATE)
    // ════════════════════════════════════════════════════════
    /**
     * Actualiza los datos de un cliente existente.
     * 
     * @param cliente El cliente con los datos actualizados
     * @return true si se actualizó correctamente
     */
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE Clientes SET nombre = ?, telefono = ?, email = ? WHERE id = ?";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getTelefono());
            stmt.setString(3, cliente.getEmail());
            stmt.setInt(4, cliente.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Cliente actualizado correctamente.");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar cliente: " + e.getMessage());
        }
        
        return false;
    }
    
    // ════════════════════════════════════════════════════════
    // 🗑️ MÉTODO: eliminar (DELETE)
    // ════════════════════════════════════════════════════════
    /**
     * Elimina un cliente de la base de datos.
     * 
     * ⚠️ CUIDADO: Si el cliente tiene reservas asociadas,
     * la eliminación fallará por la restricción de clave foránea.
     * 
     * @param id El ID del cliente a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Clientes WHERE id = ?";
        
        try (
            Connection conn = ConexionBD.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Cliente eliminado correctamente.");
                return true;
            } else {
                System.out.println("⚠️ No se encontró el cliente con ID: " + id);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            System.err.println("   ¿El cliente tiene reservas asociadas?");
        }
        
        return false;
    }
}
