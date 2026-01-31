package hotel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexion a la base de datos
 */
public class ConexionBD {
    
    // Configuracion de conexion - CAMBIAR ESTOS DATOS
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HotelDB;encrypt=false;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String PASSWORD = "tu_contraseña"; // Cambiar por tu contraseña
    
    private static Connection conexion = null;
    
    // Obtener conexion
    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexion establecida correctamente");
            } catch (SQLException e) {
                System.err.println("Error al conectar: " + e.getMessage());
                throw e;
            }
        }
        return conexion;
    }
    
    // Cerrar conexion
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexion cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
    }
}
