package hotel.servicio;

import hotel.dao.*;
import hotel.excepciones.ReservaInvalidaException;
import hotel.modelo.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * ============================================================
 * 🏨 CLASE: ServicioHotel (Capa de Servicio/Lógica de Negocio)
 * ============================================================
 * 
 * 📚 ¿QUÉ ES LA CAPA DE SERVICIO?
 * ─────────────────────────────────
 * La capa de servicio es donde reside la LÓGICA DE NEGOCIO.
 * Es el "cerebro" de la aplicación que:
 *   • Coordina las operaciones entre el usuario y los DAOs
 *   • Valida los datos de entrada
 *   • Aplica las reglas de negocio
 *   • Gestiona las transacciones
 * 
 * 📚 ARQUITECTURA EN CAPAS:
 * ─────────────────────────────────
 * 
 *   ┌─────────────────────────────────┐
 *   │     CAPA DE PRESENTACIÓN       │  ← Main, consola, GUI
 *   │         (Usuario)               │
 *   └────────────┬────────────────────┘
 *                │
 *   ┌────────────▼────────────────────┐
 *   │     CAPA DE SERVICIO           │  ← ServicioHotel (AQUÍ)
 *   │    (Lógica de Negocio)          │
 *   └────────────┬────────────────────┘
 *                │
 *   ┌────────────▼────────────────────┐
 *   │     CAPA DE ACCESO A DATOS     │  ← DAOs
 *   │           (DAO)                 │
 *   └────────────┬────────────────────┘
 *                │
 *   ┌────────────▼────────────────────┐
 *   │      BASE DE DATOS             │  ← SQL Server
 *   └─────────────────────────────────┘
 * 
 * ============================================================
 */
public class ServicioHotel {
    
    // ════════════════════════════════════════════════════════
    // 📦 ATRIBUTOS
    // ════════════════════════════════════════════════════════
    
    // DAOs para acceder a la base de datos
    private ClienteDAO clienteDAO;
    private HabitacionDAO habitacionDAO;
    private ReservaDAO reservaDAO;
    
    // Scanner para leer entrada del usuario
    private Scanner scanner;
    
    // Formato para las fechas (día/mes/año)
    private SimpleDateFormat formatoFecha;
    
    // ════════════════════════════════════════════════════════
    // 🔧 CONSTRUCTOR
    // ════════════════════════════════════════════════════════
    /**
     * Constructor que inicializa todos los componentes necesarios.
     */
    public ServicioHotel() {
        // Inicializamos los DAOs
        this.clienteDAO = new ClienteDAO();
        this.habitacionDAO = new HabitacionDAO();
        this.reservaDAO = new ReservaDAO();
        
        // Inicializamos el scanner para leer del teclado
        this.scanner = new Scanner(System.in);
        
        // Configuramos el formato de fecha (dd/MM/yyyy)
        // Ejemplo: 25/12/2024
        this.formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    // ════════════════════════════════════════════════════════
    // 🎯 MÉTODO PRINCIPAL: iniciar()
    // ════════════════════════════════════════════════════════
    /**
     * Inicia el sistema de gestión del hotel.
     * Muestra el menú y procesa las opciones del usuario.
     * 
     * 📌 BUCLE PRINCIPAL:
     * ─────────────────────────────────
     * El menú se muestra en un bucle infinito (while true)
     * hasta que el usuario selecciona la opción de salir (0).
     */
    public void iniciar() {
        
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                                                           ║");
        System.out.println("║   🏨 SISTEMA DE GESTIÓN DE RESERVAS DE HOTEL 🏨          ║");
        System.out.println("║                                                           ║");
        System.out.println("║   Bienvenido al sistema de gestión hotelera               ║");
        System.out.println("║                                                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        int opcion;
        
        // Bucle principal del menú
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            
            // Procesamos la opción con switch
            switch (opcion) {
                case 1:
                    listarHabitacionesDisponibles();
                    break;
                case 2:
                    registrarCliente();
                    break;
                case 3:
                    crearReserva();
                    break;
                case 4:
                    cancelarReserva();
                    break;
                case 5:
                    mostrarTodasReservas();
                    break;
                case 0:
                    System.out.println("\n👋 ¡Gracias por usar el sistema! Hasta pronto.\n");
                    break;
                default:
                    System.out.println("\n⚠️ Opción no válida. Por favor, seleccione del 0 al 5.\n");
            }
            
        } while (opcion != 0);
        
        // Cerramos recursos al salir
        scanner.close();
        ConexionBD.cerrarConexion();
    }
    
    // ════════════════════════════════════════════════════════
    // 📋 MÉTODO: mostrarMenu()
    // ════════════════════════════════════════════════════════
    /**
     * Muestra el menú principal por consola.
     */
    private void mostrarMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║        --- GESTIÓN DE HOTEL ---       ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║  1. 🛏️  Listar habitaciones disponibles║");
        System.out.println("║  2. 👤 Registrar cliente               ║");
        System.out.println("║  3. 📅 Crear reserva                   ║");
        System.out.println("║  4. ❌ Cancelar reserva                ║");
        System.out.println("║  5. 📋 Mostrar todas las reservas      ║");
        System.out.println("║  0. 🚪 Salir                           ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }
    
    // ════════════════════════════════════════════════════════
    // 1️⃣ OPCIÓN 1: Listar habitaciones disponibles
    // ════════════════════════════════════════════════════════
    /**
     * Consulta y muestra las habitaciones disponibles.
     * 
     * 📌 PROCESO:
     * ─────────────────────────────────
     *   1. Llamamos al DAO para obtener las habitaciones
     *   2. Verificamos si hay habitaciones disponibles
     *   3. Mostramos cada habitación con su información
     */
    private void listarHabitacionesDisponibles() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║           🛏️  HABITACIONES DISPONIBLES                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Obtenemos las habitaciones del DAO
        ArrayList<Habitacion> habitaciones = habitacionDAO.listarDisponibles();
        
        // Verificamos si hay habitaciones
        if (habitaciones.isEmpty()) {
            System.out.println("⚠️ No hay habitaciones disponibles en este momento.");
            return;
        }
        
        // Mostramos cada habitación
        System.out.println("┌────────┬────────────┬─────────────────┬─────────────┐");
        System.out.println("│   ID   │    Tipo    │  Precio/Noche   │   Estado    │");
        System.out.println("├────────┼────────────┼─────────────────┼─────────────┤");
        
        for (Habitacion hab : habitaciones) {
            System.out.printf("│  %3d   │ %-10s │   %8.2f €    │     ✅      │%n",
                hab.getId(),
                hab.getTipo(),
                hab.getPrecioPorNoche()
            );
        }
        
        System.out.println("└────────┴────────────┴─────────────────┴─────────────┘");
        System.out.println("\n📊 Total de habitaciones disponibles: " + habitaciones.size());
    }
    
    // ════════════════════════════════════════════════════════
    // 2️⃣ OPCIÓN 2: Registrar cliente
    // ════════════════════════════════════════════════════════
    /**
     * Solicita los datos de un nuevo cliente y lo guarda en la BD.
     * 
     * 📌 PROCESO:
     * ─────────────────────────────────
     *   1. Pedimos nombre, teléfono y email
     *   2. Creamos el objeto Cliente
     *   3. Lo insertamos en la BD usando el DAO
     *   4. Mostramos confirmación o error
     */
    private void registrarCliente() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║              👤 REGISTRAR NUEVO CLIENTE                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Solicitamos los datos del cliente
        System.out.print("📝 Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("📞 Teléfono: ");
        String telefono = scanner.nextLine();
        
        System.out.print("📧 Email: ");
        String email = scanner.nextLine();
        
        // Validación básica
        if (nombre.trim().isEmpty()) {
            System.out.println("\n❌ Error: El nombre no puede estar vacío.");
            return;
        }
        
        // Creamos el objeto Cliente
        Cliente cliente = new Cliente(nombre, telefono, email);
        
        // Insertamos en la base de datos
        clienteDAO.insertarCliente(cliente);
        
        // Si el cliente tiene ID asignado, la inserción fue exitosa
        if (cliente.getId() > 0) {
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║     ✅ CLIENTE REGISTRADO CON ÉXITO   ║");
            System.out.println("╚═══════════════════════════════════════╝");
            cliente.mostrarInfo();
        } else {
            System.out.println("\n❌ Error al registrar el cliente. Inténtelo de nuevo.");
        }
    }
    
    // ════════════════════════════════════════════════════════
    // 3️⃣ OPCIÓN 3: Crear reserva
    // ════════════════════════════════════════════════════════
    /**
     * Crea una nueva reserva en el sistema.
     * 
     * 📌 PROCESO COMPLETO:
     * ─────────────────────────────────
     *   1. Pedimos el ID del cliente
     *   2. Verificamos que el cliente existe
     *   3. Mostramos habitaciones disponibles
     *   4. Pedimos el ID de la habitación
     *   5. Verificamos que está disponible
     *   6. Pedimos fechas de entrada y salida
     *   7. Validamos las fechas
     *   8. Creamos la reserva y actualizamos disponibilidad
     * 
     * 📌 TRANSACCIÓN:
     * ─────────────────────────────────
     * Este proceso debería ser una transacción atómica:
     *   - O se crea la reserva Y se actualiza la habitación
     *   - O no se hace nada
     * 
     * Para simplificar, lo hacemos paso a paso.
     */
    private void crearReserva() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                 📅 CREAR NUEVA RESERVA                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        try {
            // --------------------------------------------------------
            // PASO 1: Obtener y validar el cliente
            // --------------------------------------------------------
            int idCliente = leerEntero("👤 ID del cliente: ");
            
            Cliente cliente = clienteDAO.buscarPorId(idCliente);
            if (cliente == null) {
                System.out.println("\n❌ No se encontró ningún cliente con ID: " + idCliente);
                System.out.println("   Por favor, registre primero al cliente (opción 2).");
                return;
            }
            
            System.out.println("\n✅ Cliente encontrado: " + cliente.getNombre());
            
            // --------------------------------------------------------
            // PASO 2: Mostrar habitaciones disponibles
            // --------------------------------------------------------
            System.out.println("\n📋 Habitaciones disponibles:");
            ArrayList<Habitacion> disponibles = habitacionDAO.listarDisponibles();
            
            if (disponibles.isEmpty()) {
                System.out.println("\n⚠️ No hay habitaciones disponibles en este momento.");
                return;
            }
            
            for (Habitacion hab : disponibles) {
                System.out.println("   " + hab);
            }
            
            // --------------------------------------------------------
            // PASO 3: Obtener y validar la habitación
            // --------------------------------------------------------
            int idHabitacion = leerEntero("\n🏠 ID de la habitación: ");
            
            Habitacion habitacion = habitacionDAO.obtenerPorId(idHabitacion);
            if (habitacion == null) {
                System.out.println("\n❌ No se encontró ninguna habitación con ID: " + idHabitacion);
                return;
            }
            
            if (!habitacion.isDisponible()) {
                System.out.println("\n❌ La habitación " + idHabitacion + " no está disponible.");
                return;
            }
            
            System.out.println("\n✅ Habitación seleccionada: " + habitacion.getTipo() + 
                             " - " + habitacion.getPrecioPorNoche() + "€/noche");
            
            // --------------------------------------------------------
            // PASO 4: Obtener y validar las fechas
            // --------------------------------------------------------
            System.out.println("\n📅 Introduce las fechas (formato: dd/MM/yyyy)");
            
            Date fechaEntrada = leerFecha("   Fecha de entrada: ");
            Date fechaSalida = leerFecha("   Fecha de salida: ");
            
            // Validamos que la fecha de entrada sea anterior a la de salida
            if (fechaEntrada.after(fechaSalida)) {
                throw new ReservaInvalidaException(
                    "La fecha de entrada no puede ser posterior a la fecha de salida."
                );
            }
            
            // Validamos que la fecha de entrada no sea anterior a hoy
            Date hoy = new Date();
            // Quitamos la hora para comparar solo fechas
            hoy = formatoFecha.parse(formatoFecha.format(hoy));
            if (fechaEntrada.before(hoy)) {
                throw new ReservaInvalidaException(
                    "La fecha de entrada no puede ser anterior a hoy."
                );
            }
            
            // --------------------------------------------------------
            // PASO 5: Crear la reserva
            // --------------------------------------------------------
            Reserva reserva = new Reserva(cliente, habitacion, fechaEntrada, fechaSalida);
            
            // Mostramos resumen antes de confirmar
            System.out.println("\n╔═══════════════════════════════════════╗");
            System.out.println("║         📋 RESUMEN DE RESERVA         ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println("║ Cliente: " + cliente.getNombre());
            System.out.println("║ Habitación: " + habitacion.getId() + " (" + habitacion.getTipo() + ")");
            System.out.println("║ Entrada: " + formatoFecha.format(fechaEntrada));
            System.out.println("║ Salida: " + formatoFecha.format(fechaSalida));
            System.out.println("║ TOTAL: " + String.format("%.2f", reserva.getImporteTotal()) + " €");
            System.out.println("╚═══════════════════════════════════════╝");
            
            System.out.print("\n¿Confirmar reserva? (S/N): ");
            String confirmacion = scanner.nextLine();
            
            if (!confirmacion.equalsIgnoreCase("S")) {
                System.out.println("\n❌ Reserva cancelada por el usuario.");
                return;
            }
            
            // --------------------------------------------------------
            // PASO 6: Guardar en la base de datos
            // --------------------------------------------------------
            // Primero marcamos la habitación como ocupada
            habitacion.reservar();  // Esto puede lanzar ReservaInvalidaException
            habitacionDAO.actualizarDisponibilidad(idHabitacion, false);
            
            // Luego insertamos la reserva
            reservaDAO.insertarReserva(reserva);
            
            // Si la reserva tiene ID asignado, fue exitosa
            if (reserva.getId() > 0) {
                System.out.println("\n╔═══════════════════════════════════════╗");
                System.out.println("║     ✅ RESERVA CREADA CON ÉXITO       ║");
                System.out.println("║     Número de reserva: #" + String.format("%-13d", reserva.getId()) + "║");
                System.out.println("╚═══════════════════════════════════════╝");
            } else {
                // Si falla, revertimos la disponibilidad
                habitacionDAO.actualizarDisponibilidad(idHabitacion, true);
                System.out.println("\n❌ Error al crear la reserva. Inténtelo de nuevo.");
            }
            
        } catch (ReservaInvalidaException e) {
            System.out.println("\n❌ Error de reserva: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("\n❌ Error en el formato de fecha. Use dd/MM/yyyy");
        }
    }
    
    // ════════════════════════════════════════════════════════
    // 4️⃣ OPCIÓN 4: Cancelar reserva
    // ════════════════════════════════════════════════════════
    /**
     * Cancela una reserva existente.
     * 
     * 📌 PROCESO:
     * ─────────────────────────────────
     *   1. Pedimos el ID de la reserva
     *   2. Buscamos la reserva en la BD
     *   3. Obtenemos la habitación asociada
     *   4. Eliminamos la reserva
     *   5. Marcamos la habitación como disponible
     */
    private void cancelarReserva() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                 ❌ CANCELAR RESERVA                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Primero mostramos las reservas existentes
        ArrayList<Reserva> reservas = reservaDAO.listarReservas();
        
        if (reservas.isEmpty()) {
            System.out.println("⚠️ No hay reservas en el sistema.");
            return;
        }
        
        System.out.println("📋 Reservas actuales:");
        System.out.println("┌────────┬──────────────────────┬────────────┬────────────┐");
        System.out.println("│   ID   │       Cliente        │  Entrada   │   Salida   │");
        System.out.println("├────────┼──────────────────────┼────────────┼────────────┤");
        
        for (Reserva r : reservas) {
            System.out.printf("│  %3d   │ %-20s │ %10s │ %10s │%n",
                r.getId(),
                r.getCliente().getNombre().substring(0, Math.min(20, r.getCliente().getNombre().length())),
                formatoFecha.format(r.getFechaEntrada()),
                formatoFecha.format(r.getFechaSalida())
            );
        }
        System.out.println("└────────┴──────────────────────┴────────────┴────────────┘");
        
        // Pedimos el ID de la reserva
        int idReserva = leerEntero("\n🔢 ID de la reserva a cancelar: ");
        
        // Buscamos la reserva
        Reserva reserva = reservaDAO.buscarPorId(idReserva);
        
        if (reserva == null) {
            System.out.println("\n❌ No se encontró ninguna reserva con ID: " + idReserva);
            return;
        }
        
        // Mostramos la reserva y pedimos confirmación
        System.out.println("\n📋 Reserva seleccionada:");
        System.out.println("   Cliente: " + reserva.getCliente().getNombre());
        System.out.println("   Habitación: " + reserva.getHabitacion().getId());
        System.out.println("   Fechas: " + formatoFecha.format(reserva.getFechaEntrada()) + 
                          " - " + formatoFecha.format(reserva.getFechaSalida()));
        
        System.out.print("\n⚠️ ¿Está seguro de cancelar esta reserva? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("\n❌ Operación cancelada.");
            return;
        }
        
        // Guardamos el ID de la habitación antes de eliminar
        int idHabitacion = reserva.getHabitacion().getId();
        
        // Eliminamos la reserva
        reservaDAO.eliminarReserva(idReserva);
        
        // Liberamos la habitación
        habitacionDAO.actualizarDisponibilidad(idHabitacion, true);
        
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║   ✅ RESERVA CANCELADA CORRECTAMENTE  ║");
        System.out.println("║   Habitación " + idHabitacion + " liberada              ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }
    
    // ════════════════════════════════════════════════════════
    // 5️⃣ OPCIÓN 5: Mostrar todas las reservas
    // ════════════════════════════════════════════════════════
    /**
     * Muestra todas las reservas registradas en el sistema.
     */
    private void mostrarTodasReservas() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║              📋 TODAS LAS RESERVAS                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        ArrayList<Reserva> reservas = reservaDAO.listarReservas();
        
        if (reservas.isEmpty()) {
            System.out.println("⚠️ No hay reservas registradas en el sistema.");
            return;
        }
        
        System.out.println("📊 Total de reservas: " + reservas.size());
        
        // Mostramos cada reserva con su toString()
        for (Reserva reserva : reservas) {
            System.out.println(reserva);
        }
    }
    
    // ════════════════════════════════════════════════════════
    // 🛠️ MÉTODOS AUXILIARES
    // ════════════════════════════════════════════════════════
    
    /**
     * Lee un número entero del teclado.
     * Maneja errores si el usuario introduce texto.
     * 
     * @param mensaje El mensaje a mostrar al usuario
     * @return El número introducido
     */
    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String linea = scanner.nextLine();
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, introduzca un número válido.");
            }
        }
    }
    
    /**
     * Lee una fecha del teclado en formato dd/MM/yyyy.
     * 
     * @param mensaje El mensaje a mostrar al usuario
     * @return La fecha introducida
     * @throws ParseException Si el formato es incorrecto
     */
    private Date leerFecha(String mensaje) throws ParseException {
        System.out.print(mensaje);
        String fechaStr = scanner.nextLine();
        return formatoFecha.parse(fechaStr);
    }
}
