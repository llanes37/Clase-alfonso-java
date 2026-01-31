package hotel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ============================================================
 * 🔌 CLASE: ConexionBD (Conexión a Base de Datos)
 * ============================================================
 * 
 * 📚 ¿QUÉ ES JDBC?
 * ─────────────────────────────────
 * JDBC (Java Database Connectivity) es la API de Java para
 * conectar con bases de datos relacionales.
 * 
 * Permite ejecutar:
 *   • Consultas SELECT → Obtener datos
 *   • INSERT, UPDATE, DELETE → Modificar datos
 *   • CREATE, DROP → Crear/eliminar tablas
 * 
 * 📚 COMPONENTES PRINCIPALES DE JDBC:
 * ─────────────────────────────────
 * 
 *   ┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
 *   │   Connection    │────▶│   Statement     │────▶│   ResultSet     │
 *   │  (la conexión)  │     │  (la consulta)  │     │ (los resultados)│
 *   └─────────────────┘     └─────────────────┘     └─────────────────┘
 * 
 * 📚 URL DE CONEXIÓN:
 * ─────────────────────────────────
 * La URL de conexión tiene el formato:
 *   jdbc:sqlserver://servidor:puerto;databaseName=nombreBD
 * 
 * Ejemplos:
 *   • jdbc:sqlserver://localhost:1433;databaseName=HotelDB
 *   • jdbc:sqlserver://192.168.1.100:1433;databaseName=HotelDB
 * 
 * 📌 PATRÓN SINGLETON (Simplificado):
 * ─────────────────────────────────
 * Esta clase proporciona un único punto de acceso a la conexión.
 * Esto evita crear múltiples conexiones innecesarias.
 * 
 * ============================================================
 */
public class ConexionBD {
    
    // ════════════════════════════════════════════════════════
    // 📋 CONSTANTES DE CONFIGURACIÓN
    // ════════════════════════════════════════════════════════
    // Estos valores deben ajustarse según tu configuración de SQL Server
    
    /**
     * URL de conexión a SQL Server.
     * 
     * 📌 PARÁMETROS:
     * ─────────────────────────────────
     *   • localhost:1433 → Servidor y puerto (1433 es el puerto por defecto)
     *   • databaseName=HotelDB → Nombre de la base de datos
     *   • encrypt=false → Desactiva el cifrado (para desarrollo local)
     *   • trustServerCertificate=true → Confía en el certificado del servidor
     * 
     * ⚠️ IMPORTANTE: Para producción, usa encrypt=true y configura certificados
     */
    private static final String URL = 
        "jdbc:sqlserver://localhost:1433;" +
        "databaseName=HotelDB;" +
        "encrypt=false;" +
        "trustServerCertificate=true";
    
    /**
     * Usuario de la base de datos.
     * 
     * 📌 OPCIONES DE AUTENTICACIÓN:
     * ─────────────────────────────────
     *   1. SQL Server Authentication: usuario/contraseña
     *   2. Windows Authentication: integratedSecurity=true
     * 
     * Si usas Windows Authentication, añade a la URL:
     *   integratedSecurity=true
     * y deja USUARIO y PASSWORD vacíos.
     */
    private static final String USUARIO = "sa";  // Cambia esto por tu usuario
    
    /**
     * Contraseña de la base de datos.
     * 
     * ⚠️ SEGURIDAD: En un proyecto real, NUNCA guardes contraseñas
     * en el código fuente. Usa variables de entorno o archivos de configuración.
     */
    private static final String PASSWORD = "tu_contraseña";  // Cambia esto
    
    // ════════════════════════════════════════════════════════
    // 🔗 INSTANCIA DE CONEXIÓN
    // ════════════════════════════════════════════════════════
    
    private static Connection conexion = null;
    
    // ════════════════════════════════════════════════════════
    // 🔧 MÉTODO PRINCIPAL: getConexion()
    // ════════════════════════════════════════════════════════
    /**
     * Obtiene una conexión a la base de datos.
     * 
     * 📌 ¿QUÉ HACE ESTE MÉTODO?
     * ─────────────────────────────────
     *   1. Verifica si ya hay una conexión abierta
     *   2. Si no hay conexión (o está cerrada), crea una nueva
     *   3. Devuelve el objeto Connection
     * 
     * 📌 ¿QUÉ ES DriverManager?
     * ─────────────────────────────────
     * DriverManager es la clase de Java que gestiona los drivers JDBC.
     * El método getConnection() crea la conexión física con la BD.
     * 
     * 📌 MANEJO DE ERRORES:
     * ─────────────────────────────────
     * Si no puede conectar, lanza SQLException con información del error.
     * Causas comunes:
     *   • SQL Server no está iniciado
     *   • Puerto incorrecto
     *   • Usuario/contraseña incorrectos
     *   • La base de datos no existe
     * 
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si hay error al conectar
     */
    public static Connection getConexion() throws SQLException {
        
        // Verificamos si necesitamos crear una nueva conexión
        // conexion == null → nunca se ha creado
        // conexion.isClosed() → se creó pero se cerró
        if (conexion == null || conexion.isClosed()) {
            
            try {
                // --------------------------------------------------------
                // PASO 1: Cargar el driver de SQL Server
                // --------------------------------------------------------
                // Class.forName() carga la clase del driver en memoria.
                // El driver se registra automáticamente con DriverManager.
                // 
                // Para SQL Server, el driver es:
                //   com.microsoft.sqlserver.jdbc.SQLServerDriver
                //
                // NOTA: Desde JDBC 4.0 (Java 6+), esto es opcional si
                // el driver está en el classpath, pero lo dejamos por
                // compatibilidad y claridad.
                
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                
                // --------------------------------------------------------
                // PASO 2: Establecer la conexión
                // --------------------------------------------------------
                // DriverManager.getConnection() crea la conexión física.
                // Usa la URL, usuario y contraseña que definimos arriba.
                
                System.out.println("📡 Conectando a la base de datos...");
                
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                
                System.out.println("✅ Conexión establecida correctamente.");
                
            } catch (ClassNotFoundException e) {
                // El driver no se encontró en el classpath
                System.err.println("❌ ERROR: Driver de SQL Server no encontrado.");
                System.err.println("   Asegúrate de tener el archivo .jar del driver");
                System.err.println("   en la carpeta lib/ del proyecto.");
                throw new SQLException("Driver no encontrado: " + e.getMessage());
                
            } catch (SQLException e) {
                // Error al conectar con la base de datos
                System.err.println("❌ ERROR: No se pudo conectar a la base de datos.");
                System.err.println("   URL: " + URL);
                System.err.println("   Usuario: " + USUARIO);
                System.err.println("   Mensaje: " + e.getMessage());
                throw e;  // Re-lanzamos la excepción
            }
        }
        
        return conexion;
    }
    
    // ════════════════════════════════════════════════════════
    // 🔒 MÉTODO: cerrarConexion()
    // ════════════════════════════════════════════════════════
    /**
     * Cierra la conexión a la base de datos.
     * 
     * 📌 ¿POR QUÉ CERRAR LA CONEXIÓN?
     * ─────────────────────────────────
     * Las conexiones a base de datos son recursos limitados.
     * Si no las cerramos:
     *   • Consumimos memoria innecesariamente
     *   • Podemos agotar el pool de conexiones
     *   • Pueden quedar transacciones abiertas
     * 
     * 📌 ¿CUÁNDO CERRAR?
     * ─────────────────────────────────
     * En este proyecto, cerramos al salir de la aplicación.
     * En aplicaciones web, normalmente se usa un pool de conexiones.
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("🔒 Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("⚠️ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    // ════════════════════════════════════════════════════════
    // 🧪 MÉTODO: probarConexion()
    // ════════════════════════════════════════════════════════
    /**
     * Prueba la conexión a la base de datos.
     * Útil para verificar la configuración.
     * 
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean probarConexion() {
        try {
            Connection conn = getConexion();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}

/*
 * ════════════════════════════════════════════════════════════
 * 📝 SOLUCIÓN DE PROBLEMAS COMUNES
 * ════════════════════════════════════════════════════════════
 * 
 * ERROR: "TCP/IP not enabled"
 * ─────────────────────────────────
 *   1. Abrir SQL Server Configuration Manager
 *   2. SQL Server Network Configuration → Protocols
 *   3. Habilitar TCP/IP
 *   4. Reiniciar SQL Server
 * 
 * ERROR: "Login failed"
 * ─────────────────────────────────
 *   1. Verificar usuario y contraseña
 *   2. En SSMS, verificar que SQL Server Authentication está habilitada
 *   3. En propiedades del servidor → Security → SQL Server and Windows Auth
 * 
 * ERROR: "Cannot connect to localhost"
 * ─────────────────────────────────
 *   1. Verificar que SQL Server está ejecutándose (services.msc)
 *   2. Verificar el puerto (por defecto 1433)
 *   3. Verificar firewall
 * 
 * ERROR: "Driver not found"
 * ─────────────────────────────────
 *   1. Descargar mssql-jdbc de Microsoft
 *   2. Copiar el .jar a la carpeta lib/
 *   3. Incluir en el classpath al compilar y ejecutar
 */
