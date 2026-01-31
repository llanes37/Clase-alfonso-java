package hotel;

import hotel.servicio.ServicioHotel;

/**
 * ============================================================
 * 🚀 CLASE: Main (Punto de Entrada de la Aplicación)
 * ============================================================
 * 
 * 📚 ¿QUÉ ES EL PUNTO DE ENTRADA?
 * ─────────────────────────────────
 * El punto de entrada es donde Java empieza a ejecutar el programa.
 * Debe ser un método público, estático, llamado "main" y con un
 * parámetro String[] para los argumentos de línea de comandos.
 * 
 * 📌 FIRMA DEL MÉTODO main:
 * ─────────────────────────────────
 *   public static void main(String[] args)
 * 
 *   • public → Accesible desde cualquier lugar
 *   • static → No necesita instancia de la clase
 *   • void → No devuelve ningún valor
 *   • String[] args → Argumentos pasados al ejecutar
 * 
 * 📚 RESPONSABILIDAD DEL MAIN:
 * ─────────────────────────────────
 * El main debe ser lo más simple posible. Su única responsabilidad
 * es INICIAR la aplicación. Toda la lógica está en ServicioHotel.
 * 
 * Esto sigue el principio de "Responsabilidad Única" (SRP):
 *   • Main → Inicia la aplicación
 *   • ServicioHotel → Gestiona la lógica de negocio
 *   • DAOs → Acceden a la base de datos
 * 
 * ============================================================
 */
public class Main {
    
    /**
     * Método principal - Punto de entrada de la aplicación.
     * 
     * 📌 ¿QUÉ HACE?
     * ─────────────────────────────────
     *   1. Crea una instancia de ServicioHotel
     *   2. Llama al método iniciar() que muestra el menú
     *   3. El programa termina cuando el usuario elige "Salir"
     * 
     * @param args Argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        
        // Mostramos información inicial
        System.out.println("\n");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  🏨 SISTEMA DE GESTIÓN DE RESERVAS DE HOTEL");
        System.out.println("  📚 Proyecto Final - Módulo de Programación");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  Iniciando el sistema...");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        try {
            // --------------------------------------------------------
            // Creamos el servicio principal
            // --------------------------------------------------------
            // ServicioHotel es la clase que contiene toda la lógica.
            // Al crear la instancia, se inicializan los DAOs.
            
            ServicioHotel servicio = new ServicioHotel();
            
            // --------------------------------------------------------
            // Iniciamos la aplicación
            // --------------------------------------------------------
            // El método iniciar() muestra el menú y gestiona
            // todas las operaciones hasta que el usuario salga.
            
            servicio.iniciar();
            
        } catch (Exception e) {
            // --------------------------------------------------------
            // Capturamos cualquier error inesperado
            // --------------------------------------------------------
            // En producción, esto se registraría en un archivo de log.
            
            System.err.println("\n");
            System.err.println("═══════════════════════════════════════════════════════════════");
            System.err.println("  ❌ ERROR CRÍTICO EN LA APLICACIÓN");
            System.err.println("═══════════════════════════════════════════════════════════════");
            System.err.println("  Mensaje: " + e.getMessage());
            System.err.println("  Tipo: " + e.getClass().getSimpleName());
            System.err.println("═══════════════════════════════════════════════════════════════");
            System.err.println("\n  Stack trace para depuración:");
            e.printStackTrace();
        }
        
        // --------------------------------------------------------
        // Mensaje final
        // --------------------------------------------------------
        System.out.println("\n");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  🏁 Aplicación finalizada correctamente");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("\n");
    }
}

/*
 * ════════════════════════════════════════════════════════════════
 * 📝 NOTAS ADICIONALES
 * ════════════════════════════════════════════════════════════════
 * 
 * PARA EJECUTAR ESTE PROGRAMA:
 * ─────────────────────────────────
 * 1. Asegúrate de tener configurada la base de datos (script_base_datos.sql)
 * 2. Edita ConexionBD.java con tus credenciales
 * 3. Compila con: javac -d bin -cp "lib/*" src/hotel/*.java src/hotel/**
 * 4. Ejecuta con: java -cp "bin;lib/*" hotel.Main
 * 
 * O simplemente usa los scripts compilar.bat y ejecutar.bat
 * 
 * ESTRUCTURA DE EJECUCIÓN:
 * ─────────────────────────────────
 * 
 *   main()
 *     │
 *     └──▶ ServicioHotel.iniciar()
 *              │
 *              ├──▶ mostrarMenu()
 *              │
 *              ├──▶ listarHabitacionesDisponibles()
 *              │         └──▶ HabitacionDAO.listarDisponibles()
 *              │
 *              ├──▶ registrarCliente()
 *              │         └──▶ ClienteDAO.insertarCliente()
 *              │
 *              ├──▶ crearReserva()
 *              │         ├──▶ ClienteDAO.buscarPorId()
 *              │         ├──▶ HabitacionDAO.obtenerPorId()
 *              │         ├──▶ HabitacionDAO.actualizarDisponibilidad()
 *              │         └──▶ ReservaDAO.insertarReserva()
 *              │
 *              ├──▶ cancelarReserva()
 *              │         ├──▶ ReservaDAO.buscarPorId()
 *              │         ├──▶ ReservaDAO.eliminarReserva()
 *              │         └──▶ HabitacionDAO.actualizarDisponibilidad()
 *              │
 *              └──▶ mostrarTodasReservas()
 *                        └──▶ ReservaDAO.listarReservas()
 */
