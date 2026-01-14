/******************************************************************************************
 *                        📚 **GUÍA BÁSICA: USO DE SQL EN JAVA CON JDBC Y SQLITE**
 * ──────────────────────────────────────────────────────────────────────────────
 * INTRODUCCIÓN:
 * -----------
 * SQL (Structured Query Language) es el lenguaje estándar para gestionar y manipular
 * bases de datos relacionales. Permite insertar, actualizar, eliminar y consultar datos de 
 * forma estructurada. Una base de datos es un sistema organizado de almacenamiento de 
 * información, compuesto de tablas con filas y columnas.
 * 
 * JAVA Y SQL:
 * ------------
 * JDBC (Java Database Connectivity) es la API que permite a Java conectarse y ejecutar 
 * operaciones en bases de datos. Con JDBC puedes:
 *   • Conectarte a una base de datos.
 *   • Ejecutar consultas SELECT y procesar los resultados.
 *   • Ejecutar instrucciones de actualización (INSERT, UPDATE, DELETE).
 *   • Manejar excepciones SQL para robustecer tu aplicación.
 * 
 * SQLite es ideal para principiantes porque:
 *   • Es una base de datos embebida (los datos se almacenan en un solo archivo).
 *   • No requiere configurar un servidor complejo.
 * 
 * 🔔 INSTRUCCIONES INICIALES:
 * --------------------------
 * 1. INSTALAR SQLITE:
 *    - Descarga el archivo JAR "sqlite-jdbc-3.36.0.3.jar" desde:
 *         https://github.com/xerial/sqlite-jdbc/releases/download/3.36.0.3/sqlite-jdbc-3.36.0.3.jar
 *    - Guarda el JAR en una carpeta (por ejemplo, "libs/") en tu proyecto.
 *    - Agrega el JAR al CLASSPATH de tu proyecto en tu IDE (VS Code, Eclipse, IntelliJ, etc.).
 * 
 * 2. CONFIGURAR EL ENTORNO:
 *    - Instala las extensiones "Java Extension Pack" y "Better Comments" en VS Code para
 *      compilar, ejecutar y visualizar los comentarios destacados con colores.
 * 
 * 3. EJECUTAR LA PRÁCTICA:
 *    - Compila este archivo: javac UT6_JavaSQL_Basico_Sqlite.java
 *    - Ejecuta el programa: java UT6_JavaSQL_Basico_Sqlite
 * 
 * 4. PROPÓSITO:
 *    - Conocer los conceptos básicos de JDBC y SQL.
 *    - Practicar operaciones simples: conectar, insertar, consultar, actualizar y eliminar.
 * 
 * 🚀 TAREA PARA EL ALUMNO:
 * Experimenta modificando las consultas SQL y crea nuevos ejercicios, tales como:
 *    • Insertar nuevos registros.
 *    • Actualizar y eliminar datos.
 *    • Crear tus propias tablas y consultas.
 * 
 * Autor: Joaquín
 * Fecha: 28/01/2025
 ******************************************************************************************/

 import java.sql.*;       // 🛠️ Clases necesarias para JDBC
 import java.util.Scanner; // 🛠️ Clase Scanner para leer datos del usuario
 
 public class UT1_JavaSQL_Basico_Sqlite {
 
     // ➡ Variable global para almacenar la conexión a la base de datos
     private static Connection conexion = null;
 
     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         int opcion;
         
         // =====================================================
         // 📌 MENÚ PRINCIPAL: PRÁCTICA BÁSICA CON SQLITE Y JDBC
         // =====================================================
         do {
             System.out.println("\n==============================================");
             System.out.println("      MENÚ DE PRÁCTICAS - SQL CON JAVA (SQLite)      ");
             System.out.println("==============================================");
             System.out.println("1. Conectar a la Base de Datos");
             System.out.println("2. Insertar un Registro (Tabla 'usuarios')");
             System.out.println("3. Ejecutar Consulta SELECT");
             System.out.println("4. Actualizar/Eliminar Registros (Edición)");
             System.out.println("5. Ejercicios Extra para Practicar");
             System.out.println("6. Desconectar de la Base de Datos");
             System.out.println("7. Salir");
             System.out.print("👉 Seleccione una opción: ");
             opcion = readInt(scanner);
             
             switch (opcion) {
                 case 1:
                     conectarBaseDatos();
                     break;
                 case 2:
                     insertarRegistro(scanner);
                     break;
                 case 3:
                     ejecutarConsulta();
                     break;
                 case 4:
                     editarYEliminarRegistros(scanner);
                     break;
                 case 5:
                     mostrarEjerciciosExtra();
                     break;
                 case 6:
                     desconectarBaseDatos();
                     break;
                 case 7:
                     System.out.println("👋 Saliendo del programa...");
                     break;
                 default:
                     System.out.println("❌ Opción inválida. Intente nuevamente.");
             }
         } while (opcion != 7);
         
         scanner.close();
     }
     
     // ---------------------------------------------------------------
     // 📌 MÉTODO: readInt
     // ---------------------------------------------------------------
     // ⭐ Lee de forma segura un número entero, gestionando entradas no válidas.
     public static int readInt(Scanner scanner) {
         while (true) {
             try {
                 return scanner.nextInt(); // ➡ Intenta leer un número entero
             } catch (Exception e) {
                 System.out.println("❌ Entrada no válida. Por favor, ingrese un número entero.");
                 scanner.next(); // Descarta la entrada errónea
             }
         }
     }
     
     // =====================================================
     // 📌 SECCIÓN 1: Conectar a la Base de Datos
     // =====================================================
     /**
      * //! CONECTAR A LA BASE DE DATOS
      *
      * ? TEORÍA:
      * - Utilizamos JDBC para conectarnos a una base de datos SQLite.
      * - SQLite es ideal para principiantes porque crea un archivo de base de datos (ej.: "miBaseDatos.db")
      *   sin necesidad de configurar un servidor.
      * - Se carga el driver "org.sqlite.JDBC" y se obtiene una Connection a través de la URL.
      *
      * * TAREA PARA EL ALUMNO:
      * Modifica la URL de conexión para usar otro nombre de archivo si lo deseas.
      */
      public static void conectarBaseDatos() {
        try {
            // 🧩 Cargar el driver de SQLite
            Class.forName("org.sqlite.JDBC");
    
            // 🔌 Conectar a la base de datos (creará el archivo si no existe)
            conexion = DriverManager.getConnection("jdbc:sqlite:miBaseDatos.db");
            System.out.println("✅ Conexión exitosa a la base de datos.");
    
            Statement stmt = conexion.createStatement();
    
            // ======================================================
            // 🗑️ BORRAR tabla 'productos' si existe (DROP TABLE)
            // ======================================================
            String sqlDropProductos = "DROP TABLE IF EXISTS productos";
            stmt.execute(sqlDropProductos); // ⚠️ Elimina la tabla y sus datos
            System.out.println("🗑️ Tabla 'productos' eliminada (si existía).");
    
            // ======================================================
            // ✅ Crear tabla 'usuarios' si no existe
            // ======================================================
            String sqlCrearTabla = "CREATE TABLE IF NOT EXISTS usuarios (" +
                                   "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                   "nombre TEXT NOT NULL, " +
                                   "edad INTEGER)";
            stmt.execute(sqlCrearTabla);
            System.out.println("✅ Tabla 'usuarios' verificada/creada.");
    
            stmt.close(); // 🔐 Cerrar el Statement
    
        } catch (Exception e) {
            System.out.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    
     
     // =====================================================
     // 📌 SECCIÓN 2: Insertar un Registro en la Tabla 'usuarios'
     // =====================================================
     /**
      * //! INSERTAR UN REGISTRO
      *
      * ? TEORÍA:
      * - Se utiliza un PreparedStatement para ejecutar una instrucción INSERT de forma segura.
      * - Esto previene inyecciones SQL y permite insertar datos dinámicamente.
      *
      * * TAREA PARA EL ALUMNO:
      * Modifica el ejemplo para insertar otros campos o experimenta usando UPDATE/DELETE.
      */
     public static void insertarRegistro(Scanner scanner) {
         if (conexion == null) { // Verifica si hay conexión activa
             System.out.println("❌ Conecta a la base de datos primero (opción 1).");
             return;
         }
         try {
             // Solicita al usuario el nombre y la edad para el registro
            
             
             // Define la sentencia SQL para insertar el registro
             String sqlInsert = "INSERT INTO usuarios (nombre, edad) VALUES (?, ?)";
             // Prepara la sentencia usando PreparedStatement para mayor seguridad
             PreparedStatement pstmt = conexion.prepareStatement(sqlInsert);
             pstmt.setString(1, "joaquin"); // Asigna el valor del nombre al primer parámetro
             pstmt.setInt(2, 31);      // Asigna el valor de la edad al segundo parámetro
             // Ejecuta la sentencia y obtiene el número de filas afectadas
            pstmt.executeUpdate();
             
             pstmt.close(); // Cierra el PreparedStatement para liberar recursos
         } catch (SQLException e) {
             System.out.println("❌ Error al insertar el registro: " + e.getMessage());
         }
     }
     
     // =====================================================
     // 📌 SECCIÓN 3: Ejecutar Consulta SELECT
     // =====================================================
     /**
      * //! EJECUTAR CONSULTA SELECT
      *
      * ? TEORÍA:
      * - Se utiliza un Statement para ejecutar una consulta SQL.
      * - Los resultados se obtienen en un ResultSet, el cual se recorre para mostrar cada registro.
      *
      * * TAREA PARA EL ALUMNO:
      * Experimenta modificando la consulta para filtrar registros o usa un PreparedStatement.
      */
     public static void ejecutarConsulta() {
         if (conexion == null) { // Verifica si hay conexión activa
             System.out.println("❌ Conecta a la base de datos primero (opción 1).");
             return;
         }
         try {
             // Define la consulta SQL para seleccionar todos los registros de la tabla 'usuarios'
             String sqlConsulta = "SELECT * FROM productos";
             Statement stmt = conexion.createStatement(); // Crea un Statement para ejecutar la consulta
             ResultSet rs = stmt.executeQuery(sqlConsulta); // Ejecuta la consulta y guarda el resultado en un ResultSet
             
             // Imprime los encabezados de las columnas
             System.out.println("ID\tNombre\tEdad");
             // Recorre el ResultSet y muestra cada registro
             while (rs.next()) {
                 int id = rs.getInt("id");           // Obtiene el valor del campo "id"
                 String nombre = rs.getString("nombre"); // Obtiene el valor del campo "nombre"
                 int peso = rs.getInt("peso"); 
                 int precio = rs.getInt("precio");         // Obtiene el valor del campo "edad"
                 System.out.println(id + "\t" + nombre + "\t" + peso); // Imprime los valores en formato tabular
             }
             rs.close();   // Cierra el ResultSet
             stmt.close(); // Cierra el Statement
         } catch (SQLException e) {
             System.out.println("❌ Error al ejecutar la consulta: " + e.getMessage());
         }
     }
     
     // =====================================================
     // 📌 SECCIÓN 4: Editar y Eliminar Registros (UPDATE y DELETE)
     // =====================================================
     /**
      * //! EDITAR Y ELIMINAR REGISTROS
      *
      * ? TEORÍA:
      * - Las instrucciones UPDATE y DELETE se usan para modificar y eliminar datos existentes.
      * - UPDATE permite cambiar el valor de uno o varios campos en registros que cumplen una condición.
      * - DELETE elimina registros que cumplen una condición específica.
      *
      * * TAREA PARA EL ALUMNO:
      *  • Implementa un UPDATE para cambiar el nombre de un usuario utilizando el ID como criterio.
      *  • Implementa un DELETE para eliminar un usuario por ID.
      */
     public static void editarYEliminarRegistros(Scanner scanner) {
         if (conexion == null) { // Verifica que exista una conexión activa
             System.out.println("❌ Conecta a la base de datos primero (opción 1).");
             return;
         }
         
         try {
             // -----------------------------
           
             
             // -----------------------------
             // Sección de Eliminación (DELETE)
             // -----------------------------
          
             
             // Define la sentencia SQL para eliminar el registro de la tabla 'usuarios'
             String sqlDelete = "DELETE FROM usuarios WHERE id = ?";
             PreparedStatement pstmtDelete = conexion.prepareStatement(sqlDelete);
             pstmtDelete.setInt(1, 1); // Asigna el ID al parámetro de la sentencia
             // Ejecuta la eliminación y obtiene el número de filas afectadas
             int filasEliminadas = pstmtDelete.executeUpdate();
             System.out.println("✅ Registros eliminados: " + filasEliminadas);
             pstmtDelete.close(); // Cierra el PreparedStatement
         } catch (SQLException e) {
             System.out.println("❌ Error al editar o eliminar registros: " + e.getMessage());
         }
     }
     
     // =====================================================
     // SECCIÓN 5: Desconectar de la Base de Datos
     // =====================================================
     /**
       //! DESCONEXIÓN DE LA BASE DE DATOS
      *
      * ? TEORÍA:
      * - Es fundamental cerrar la conexión a la base de datos para liberar recursos y evitar 
      *   fugas de memoria.
      * - Se utiliza el método close() del objeto Connection para cerrar la conexión.
      *
      * * TAREA PARA EL ALUMNO:
      * Asegúrate de ejecutar esta opción antes de finalizar el programa.
      */
     public static void desconectarBaseDeDatos() {
         if (conexion != null) { // Verifica si hay conexión activa
             try {
                 conexion.close(); // Cierra la conexión a la base de datos
                 conexion = null;  // Establece la variable a null para indicar que no hay conexión
                 System.out.println("✅ Conexión a la base de datos cerrada.");
             } catch (SQLException e) {
                 System.out.println("❌ Error al cerrar la conexión: " + e.getMessage());
             }
         } else {
             System.out.println("⚠️ No hay conexión abierta.");
         }
     }
     
     // Método para llamar al método desconectarBaseDeDatos (por consistencia en el menú)
     public static void desconectarBaseDatos() {
         desconectarBaseDeDatos();
     }
     
     // =====================================================
     // SECCIÓN 6: Ejercicios Extra para Practicar
     // =====================================================
     /**
       //! EJERCICIOS EXTRA:
      *
      * ACTIVIDADES RECOMENDADAS:
      * 1. Modifica la consulta SELECT para filtrar usuarios mayores de 18 años.
      * 2. Implementa un UPDATE para cambiar el nombre de un usuario.
      * 3. Implementa un DELETE para eliminar un usuario.
      * 4. Crea una nueva tabla "productos" y realiza operaciones CRUD (INSERT, SELECT, UPDATE, DELETE).
      * 5. Maneja las excepciones SQL de forma más específica y registra los errores en un archivo log.
      */
     public static void mostrarEjerciciosExtra() {
         System.out.println("\n--- EJERCICIOS EXTRA PARA PRACTICAR ---");
         System.out.println("1. Modifica la consulta SELECT para filtrar usuarios mayores de 18 años.");
         System.out.println("2. Implementa un UPDATE para cambiar el nombre de un usuario.");
         System.out.println("3. Implementa un DELETE para eliminar un usuario.");
         System.out.println("4. Crea una nueva tabla 'productos' y realiza operaciones CRUD sobre ella.");
         System.out.println("5. Registra los errores en un archivo log.");
     }
 }
 