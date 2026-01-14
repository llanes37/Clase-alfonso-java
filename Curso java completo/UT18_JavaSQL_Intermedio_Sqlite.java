/******************************************************************************************
 *                      📚 GUÍA INTERMEDIA: SQL EN JAVA CON JDBC Y SQLITE — UT18
 * ──────────────────────────────────────────────────────────────────────────────
 * AUTOR: Joaquín (continuación y mejora del estilo de UT17)
 * FECHA: 06/11/2025
 *
 * INTRODUCCIÓN
 * ------------
 * Este módulo (UT18) continúa la práctica de UT17 con el MISMO DISEÑO de comentarios y
 * formato para Better Comments, pero sube el nivel didáctico y técnico:
 *   • Relaciones entre tablas (usuarios, categorias, productos ➜ FK a categorias).
 *   • CRUD seguro con PreparedStatement y validaciones.
 *   • Consultas con filtros (LIKE) y LEFT JOIN para enriquecer resultados.
 *   • Control transaccional (COMMIT/ROLLBACK) para operaciones atómicas.
 *   • Notas, teoría y tareas del alumno en cada sección con //!, ?, *, TODO y NOTE.
 *
 * OBJETIVOS
 * ---------
 * 1) Consolidar JDBC (Connection, Statement/PreparedStatement, ResultSet) de forma práctica.
 * 2) Entender claves foráneas y cómo activarlas en SQLite.
 * 3) Aplicar validaciones y evitar SQL Injection usando parámetros.
 * 4) Ejercitar transacciones para garantizar consistencia.
 *
 * REQUISITOS
 * ----------
 * • JAR del driver SQLite (p.ej. sqlite-jdbc-3.36.0.3.jar) añadido al classpath.
 * • No hay servidor: la base de datos es un archivo local "miBaseDatos.db".
 *
 * USO RÁPIDO
 * ----------
 * 1) Compila:   javac UT18_JavaSQL_Intermedio_Sqlite.java
 * 2) Ejecuta:   java -cp ".;libs/sqlite-jdbc-3.36.0.3.jar" UT18_JavaSQL_Intermedio_Sqlite
 *    (ajusta la ruta del JAR según su ubicación; en IDE añade el JAR al proyecto)
 *
 * MENÚ PRINCIPAL (contenido)
 * --------------------------
 *  1. Conectar / Inicializar BD (crea/verifica tablas y activa FKs)
 *  2. Insertar usuario     3. Listar usuarios
 *  4. Actualizar usuario   5. Eliminar usuario
 *  6. Insertar categoría   7. Listar categorías
 *  8. Insertar producto    9. Listar productos (con categoría)
 * 10. Actualizar producto 11. Eliminar producto
 * 12. Buscar usuarios por nombre (LIKE)
 * 13. Demo de transacción (COMMIT/ROLLBACK)
 * 14. Desconectar BD      15. Salir
 *
 * PUNTOS CLAVE
 * ------------
 * • PRAGMA foreign_keys = ON ➜ imprescindible para que se respeten FKs en SQLite.
 * • CHECK en columnas (edad, precio) ➜ validaciones a nivel BD.
 * • LEFT JOIN en listados ➜ información enriquecida sin perder registros.
 * • Try-with-resources ➜ cierra recursos automáticamente (excepto Connection global).
 *
 ******************************************************************************************/

import java.sql.*;      // API JDBC
import java.util.Scanner;

public class UT18_JavaSQL_Intermedio_Sqlite {

    // ! Conexión global a la base de datos (reutilizada por todas las operaciones)
    private static Connection conexion = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            // ================================
            // 📋 MENÚ PRINCIPAL — UT18 (estilo UT17 mejorado)
            // ================================
            System.out.println("\n==================================");
            System.out.println("  MENÚ DE PRÁCTICAS - SQL (SQLite)");
            System.out.println("           NIVEL INTERMEDIO (UT18)");
            System.out.println("==================================");
            System.out.println(" 1. Conectar / Inicializar BD");
            System.out.println(" 2. Insertar usuario");
            System.out.println(" 3. Listar usuarios");
            System.out.println(" 4. Actualizar usuario por ID");
            System.out.println(" 5. Eliminar usuario por ID");
            System.out.println(" 6. Insertar categoría");
            System.out.println(" 7. Listar categorías");
            System.out.println(" 8. Insertar producto (con categoría opcional)");
            System.out.println(" 9. Listar productos (con nombre de categoría)");
            System.out.println("10. Actualizar producto por ID");
            System.out.println("11. Eliminar producto por ID");
            System.out.println("12. Buscar usuarios por nombre (LIKE)");
            System.out.println("13. Demo TRANSACCIÓN (commit/rollback)");
            System.out.println("14. Desconectar BD");
            System.out.println("15. Salir");
            System.out.print("👉 Selecciona opción: ");
            opcion = readInt(sc);

            switch (opcion) {
                case 1: inicializarBD();                       break;
                case 2: insertarUsuario(sc);                   break;
                case 3: listarUsuarios();                      break;
                case 4: actualizarUsuario(sc);                 break;
                case 5: eliminarUsuario(sc);                   break;
                case 6: insertarCategoria(sc);                 break;
                case 7: listarCategorias();                    break;
                case 8: insertarProducto(sc);                  break;
                case 9: listarProductos();                     break;
                case 10: actualizarProducto(sc);               break;
                case 11: eliminarProducto(sc);                 break;
                case 12: buscarUsuariosPorNombre(sc);          break;
                case 13: demoTransaccion();                    break;
                case 14: desconectarBD();                      break;
                case 15: System.out.println("👋 Programa finalizado. ¡Hasta luego!"); break;
                default: System.out.println("❌ Opción inválida.");
            }
        } while (opcion != 15);

        sc.close();
    }

    // ------------------------------------------------------
    // 📌 UTILIDADES DE ENTRADA — Lectura segura
    // ------------------------------------------------------
    // ? Teoría: Es habitual que Scanner falle con entradas no válidas. Estas utilidades
    //   encapsulan la validación y el consumo de saltos de línea para evitar errores.
    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("❌ Entrada inválida. Introduce un número: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine(); // consumir salto
        return val;
    }

    private static int readInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        return readInt(sc);
    }

    private static double readDouble(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.print("❌ Número inválido. Intenta de nuevo: ");
            }
        }
    }

    private static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("❌ No puede estar vacío.");
        }
    }

    // ======================================================
    // 1) CONECTAR E INICIALIZAR BD (tablas y FKs)
    // ======================================================
    /**
     * //! CONEXIÓN E INICIALIZACIÓN
     *
     * ? Teoría:
     *   - SQLite requiere activar las claves foráneas con PRAGMA foreign_keys=ON.
     *   - La conexión se obtiene via DriverManager usando la URL JDBC de SQLite.
     *   - Try-with-resources en Statement simplifica el cierre de recursos.
     *
     * * Buenas prácticas:
     *   - Define CHECK en columnas para validaciones (edad >= 0, precio >= 0).
     *   - Usa nombres coherentes y evita espacios en nombres de tabla/columna.
     *
     * TODO (Alumno): Cambia el nombre del archivo de BD y observa que se crea otro fichero.
     */
    private static void inicializarBD() {
        try {
            // Cargar driver SQLite
            Class.forName("org.sqlite.JDBC");
            // Conectar (o crear) archivo miBaseDatos.db
            conexion = DriverManager.getConnection("jdbc:sqlite:miBaseDatos.db");
            System.out.println("✅ Conexión establecida en 'miBaseDatos.db'.");

            try (Statement st = conexion.createStatement()) {
                // Habilitar claves foráneas
                st.execute("PRAGMA foreign_keys = ON");

                // Crear tabla usuarios
                st.execute("CREATE TABLE IF NOT EXISTS usuarios (" +
                           " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                           " nombre TEXT NOT NULL," +
                           " edad INTEGER NOT NULL CHECK(edad >= 0)" +
                           ")");

                // Crear tabla categorias
                st.execute("CREATE TABLE IF NOT EXISTS categorias (" +
                           " id_categoria INTEGER PRIMARY KEY AUTOINCREMENT," +
                           " nombre TEXT NOT NULL UNIQUE" +
                           ")");

                // Crear tabla productos (FK a categorias)
                st.execute("CREATE TABLE IF NOT EXISTS productos (" +
                           " id_producto INTEGER PRIMARY KEY AUTOINCREMENT," +
                           " nombre TEXT NOT NULL," +
                           " precio REAL NOT NULL CHECK(precio >= 0)," +
                           " categoria_id INTEGER," +
                           " FOREIGN KEY(categoria_id) REFERENCES categorias(id_categoria)" +
                           "   ON UPDATE CASCADE ON DELETE SET NULL" +
                           ")");
            }

            System.out.println("✅ Tablas 'usuarios', 'categorias' y 'productos' listas.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver SQLite no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Error conectando/creando BD: " + e.getMessage());
        }
    }

    private static boolean checkConexion() {
        if (conexion == null) {
            System.out.println("⚠️ Primero conecta la BD (opción 1).");
            return false;
        }
        return true;
    }

    // ======================================================
    // 2-5) CRUD DE USUARIOS
    // ======================================================
    /**
     * //! INSERT USUARIO (CREATE)
     * ? Teoría: INSERT parametrizado con PreparedStatement evita SQL Injection y
     *   permite validar previamente los datos (p. ej., edad >= 0).
     * TODO (Alumno): Añade validación para no permitir nombres con menos de 2 caracteres.
     */
    private static void insertarUsuario(Scanner sc) {
        if (!checkConexion()) return;
        String nombre = readNonEmpty(sc, "Nombre del usuario: ");
        int edad = readInt(sc, "Edad: ");
        if (edad < 0) { System.out.println("❌ La edad no puede ser negativa."); return; }

        String sql = "INSERT INTO usuarios(nombre, edad) VALUES(?,?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, edad);
            ps.executeUpdate();
            System.out.println("✅ Usuario insertado.");
        } catch (SQLException e) {
            System.out.println("❌ Error insertando usuario: " + e.getMessage());
        }
    }

    /**
     * //! LISTADO DE USUARIOS (READ)
     * ? Teoría: Un Statement simple sirve para consultas sin parámetros. Si vas a
     *   filtrar dinámicamente, usa PreparedStatement.
     */
    private static void listarUsuarios() {
        if (!checkConexion()) return;
        String sql = "SELECT * FROM usuarios ORDER BY id";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("ID | Nombre           | Edad");
            System.out.println("---+------------------+-----");
            while (rs.next()) {
                System.out.printf("%2d | %-16s | %3d%n",
                    rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error listando usuarios: " + e.getMessage());
        }
    }

    /**
     * //! UPDATE USUARIO (UPDATE)
     * * Consejo: Muestra el número de filas afectadas para comprobar si el ID existía.
     * TODO (Alumno): Permite actualizar sólo el nombre o sólo la edad (campos opcionales).
     */
    private static void actualizarUsuario(Scanner sc) {
        if (!checkConexion()) return;
        int id = readInt(sc, "ID de usuario a actualizar: ");
        String nombre = readNonEmpty(sc, "Nuevo nombre: ");
        int edad = readInt(sc, "Nueva edad: ");

        String sql = "UPDATE usuarios SET nombre=?, edad=? WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, edad);
            ps.setInt(3, id);
            int afect = ps.executeUpdate();
            System.out.println(afect > 0 ? "✅ Usuario actualizado." : "⚠️ ID no encontrado.");
        } catch (SQLException e) {
            System.out.println("❌ Error actualizando usuario: " + e.getMessage());
        }
    }

    /**
     * //! DELETE USUARIO (DELETE)
     * NOTE: Si más adelante añades FKs desde otras tablas a usuarios, considera ON DELETE
     *   RESTRICT/SET NULL/CASCADE según la regla de negocio.
     */
    private static void eliminarUsuario(Scanner sc) {
        if (!checkConexion()) return;
        int id = readInt(sc, "ID de usuario a eliminar: ");
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int afect = ps.executeUpdate();
            System.out.println(afect > 0 ? "✅ Usuario eliminado." : "⚠️ ID no encontrado.");
        } catch (SQLException e) {
            System.out.println("❌ Error eliminando usuario: " + e.getMessage());
        }
    }

    // ======================================================
    // 6-7) CATEGORÍAS (insertar y listar)
    // ======================================================
    /**
     * //! INSERT CATEGORÍA
     * ? Teoría: La columna nombre es UNIQUE para evitar categorías duplicadas.
     * TODO (Alumno): Implementa actualización y eliminación de categorías.
     */
    private static void insertarCategoria(Scanner sc) {
        if (!checkConexion()) return;
        String nombre = readNonEmpty(sc, "Nombre de la categoría: ");
        String sql = "INSERT INTO categorias(nombre) VALUES(?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
            System.out.println("✅ Categoría insertada.");
        } catch (SQLException e) {
            System.out.println("❌ Error insertando categoría: " + e.getMessage());
        }
    }

    /**
     * //! LISTAR CATEGORÍAS
     */
    private static void listarCategorias() {
        if (!checkConexion()) return;
        String sql = "SELECT * FROM categorias ORDER BY id_categoria";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("ID | Nombre de categoría");
            System.out.println("---+--------------------");
            while (rs.next()) {
                System.out.printf("%2d | %s%n", rs.getInt("id_categoria"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error listando categorías: " + e.getMessage());
        }
    }

    // ======================================================
    // 8-11) CRUD de PRODUCTOS
    // ======================================================
    /**
     * //! INSERT PRODUCTO
     * ? Teoría: Permite categoría opcional. La FK usa ON DELETE SET NULL para mantener
     *   productos aunque se elimine la categoría.
     * TODO (Alumno): Valida que el nombre de producto tenga al menos 3 caracteres.
     */
    private static void insertarProducto(Scanner sc) {
        if (!checkConexion()) return;
        String nombre = readNonEmpty(sc, "Nombre del producto: ");
        double precio = readDouble(sc, "Precio (>= 0): ");
        if (precio < 0) { System.out.println("❌ El precio no puede ser negativo."); return; }

        listarCategorias();
        System.out.println("(Introduce 0 si no quieres asignar categoría)");
        int catId = readInt(sc, "ID de categoría: ");
        Integer categoriaId = (catId <= 0 ? null : Integer.valueOf(catId));

        String sql = "INSERT INTO productos(nombre, precio, categoria_id) VALUES(?,?,?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            if (categoriaId == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, categoriaId);
            ps.executeUpdate();
            System.out.println("✅ Producto insertado.");
        } catch (SQLException e) {
            System.out.println("❌ Error insertando producto: " + e.getMessage());
        }
    }

    /**
     * //! LISTAR PRODUCTOS (JOIN con categorías)
     * * Consejo: Usa LEFT JOIN para no perder productos que aún no tengan categoría.
     */
    private static void listarProductos() {
        if (!checkConexion()) return;
        String sql = "SELECT p.id_producto, p.nombre, p.precio, c.nombre AS categoria" +
                     " FROM productos p LEFT JOIN categorias c ON p.categoria_id = c.id_categoria" +
                     " ORDER BY p.id_producto";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("ID | Nombre               | Precio  | Categoría");
            System.out.println("---+----------------------+--------+----------------");
            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nom = rs.getString("nombre");
                double pr = rs.getDouble("precio");
                String cat = rs.getString("categoria");
                System.out.printf("%2d | %-20s | %6.2f | %s%n", id, nom, pr, (cat == null ? "(sin)" : cat));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error listando productos: " + e.getMessage());
        }
    }

    /**
     * //! UPDATE PRODUCTO
     * NOTE: Si pasas 0 como categoría, se deja sin categoría (NULL) respetando la FK.
     */
    private static void actualizarProducto(Scanner sc) {
        if (!checkConexion()) return;
        int id = readInt(sc, "ID producto a actualizar: ");
        String nombre = readNonEmpty(sc, "Nuevo nombre: ");
        double precio = readDouble(sc, "Nuevo precio (>= 0): ");
        if (precio < 0) { System.out.println("❌ El precio no puede ser negativo."); return; }

        listarCategorias();
        System.out.println("(Introduce 0 si no quieres asignar categoría)");
        int catId = readInt(sc, "Nuevo ID de categoría: ");
        Integer categoriaId = (catId <= 0 ? null : Integer.valueOf(catId));

        String sql = "UPDATE productos SET nombre=?, precio=?, categoria_id=? WHERE id_producto=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            if (categoriaId == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, categoriaId);
            ps.setInt(4, id);
            int afect = ps.executeUpdate();
            System.out.println(afect > 0 ? "✅ Producto actualizado." : "⚠️ ID no encontrado.");
        } catch (SQLException e) {
            System.out.println("❌ Error actualizando producto: " + e.getMessage());
        }
    }

    /**
     * //! DELETE PRODUCTO
     */
    private static void eliminarProducto(Scanner sc) {
        if (!checkConexion()) return;
        int id = readInt(sc, "ID producto a eliminar: ");
        String sql = "DELETE FROM productos WHERE id_producto=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int afect = ps.executeUpdate();
            System.out.println(afect > 0 ? "✅ Producto eliminado." : "⚠️ ID no encontrado.");
        } catch (SQLException e) {
            System.out.println("❌ Error eliminando producto: " + e.getMessage());
        }
    }

    // ======================================================
    // 12) Búsqueda de usuarios con LIKE
    // ======================================================
    /**
     * //! BÚSQUEDA POR LIKE
     * ? Teoría: LIKE permite búsquedas parciales. Ojo con mayúsculas/minúsculas según colación.
     * TODO (Alumno): Añade búsqueda por rango de edad (mínima y/o máxima).
     */
    private static void buscarUsuariosPorNombre(Scanner sc) {
        if (!checkConexion()) return;
        String texto = readNonEmpty(sc, "Texto a buscar en nombre: ");
        String sql = "SELECT * FROM usuarios WHERE nombre LIKE ? ORDER BY id";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("ID | Nombre           | Edad");
                System.out.println("---+------------------+-----");
                while (rs.next()) {
                    System.out.printf("%2d | %-16s | %3d%n",
                        rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en la búsqueda: " + e.getMessage());
        }
    }

    // ======================================================
    // 13) Ejemplo de TRANSACCIÓN
    // ======================================================
    /**
     * //! TRANSACCIONES (COMMIT/ROLLBACK)
     * ? Teoría: Desactiva autocommit, agrupa operaciones; si algo falla, ROLLBACK.
     * * Buenas prácticas: Guarda/restaura el estado previo de autocommit al terminar.
     * TODO (Alumno): Crea una transacción que inserte un usuario y 2 productos; fuerza un
     *   error en el segundo producto y verifica que no se insertó nada.
     */
    private static void demoTransaccion() {
        if (!checkConexion()) return;
        System.out.println("🧪 Iniciando demo de transacción: insertaremos 2 productos y forzaremos un error en el 2º para hacer ROLLBACK.");
        String sql = "INSERT INTO productos(nombre, precio, categoria_id) VALUES(?,?,?)";

        try {
            boolean prev = conexion.getAutoCommit();
            conexion.setAutoCommit(false); // Comenzar transacción

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                // 1º insert correcto
                ps.setString(1, "Producto OK");
                ps.setDouble(2, 10.0);
                ps.setNull(3, Types.INTEGER);
                ps.executeUpdate();

                // 2º insert con error (precio negativo viola CHECK)
                ps.setString(1, "Producto ERROR");
                ps.setDouble(2, -5.0); // fuerza error
                ps.setNull(3, Types.INTEGER);
                ps.executeUpdate();

                // Si llegase aquí, commit (pero no llegará)
                conexion.commit();
            } catch (SQLException e) {
                System.out.println("⚠️ Ocurrió un error: " + e.getMessage());
                System.out.println("↩️ Haciendo ROLLBACK de toda la transacción...");
                conexion.rollback();
            } finally {
                conexion.setAutoCommit(prev);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en transacción: " + e.getMessage());
        }
    }

    // ======================================================
    // 14) Desconexión y limpieza
    // ======================================================
    /**
     * //! DESCONECTAR Y LIMPIAR RECURSOS
     * * Consejo: Cierra la conexión en una zona centralizada para evitar fugas.
     */
    private static void desconectarBD() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("✅ Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("❌ Error cerrando BD: " + e.getMessage());
            } finally {
                conexion = null;
            }
        } else {
            System.out.println("⚠️ No había conexión activa.");
        }
    }
}
