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
 * Clase principal del servicio del hotel
 * Contiene el menu y la logica de negocio
 */
public class ServicioHotel {
    
    private ClienteDAO clienteDAO;
    private HabitacionDAO habitacionDAO;
    private ReservaDAO reservaDAO;
    private Scanner scanner;
    private SimpleDateFormat formatoFecha;
    
    public ServicioHotel() {
        this.clienteDAO = new ClienteDAO();
        this.habitacionDAO = new HabitacionDAO();
        this.reservaDAO = new ReservaDAO();
        this.scanner = new Scanner(System.in);
        this.formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    // Metodo principal que inicia el sistema
    public void iniciar() {
        System.out.println("\n========================================");
        System.out.println("   SISTEMA DE GESTION DE HOTEL");
        System.out.println("========================================");
        
        int opcion;
        
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            
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
                    System.out.println("\nSaliendo del sistema...");
                    break;
                default:
                    System.out.println("\nOpcion no valida");
            }
            
        } while (opcion != 0);
        
        scanner.close();
        ConexionBD.cerrarConexion();
    }
    
    // Mostrar menu
    private void mostrarMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Listar habitaciones disponibles");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Crear reserva");
        System.out.println("4. Cancelar reserva");
        System.out.println("5. Mostrar todas las reservas");
        System.out.println("0. Salir");
    }
    
    // Opcion 1: Listar habitaciones disponibles
    private void listarHabitacionesDisponibles() {
        System.out.println("\n--- HABITACIONES DISPONIBLES ---");
        
        ArrayList<Habitacion> habitaciones = habitacionDAO.listarDisponibles();
        
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones disponibles");
            return;
        }
        
        System.out.println("ID\tTipo\t\tPrecio/Noche");
        System.out.println("----------------------------------");
        
        for (Habitacion h : habitaciones) {
            System.out.println(h.getId() + "\t" + h.getTipo() + "\t\t" + h.getPrecioPorNoche() + " euros");
        }
        
        System.out.println("\nTotal: " + habitaciones.size() + " habitaciones");
    }
    
    // Opcion 2: Registrar cliente
    private void registrarCliente() {
        System.out.println("\n--- REGISTRAR CLIENTE ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        if (nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacio");
            return;
        }
        
        Cliente cliente = new Cliente(nombre, telefono, email);
        clienteDAO.insertarCliente(cliente);
        
        if (cliente.getId() > 0) {
            System.out.println("Cliente registrado correctamente");
        }
    }
    
    // Opcion 3: Crear reserva
    private void crearReserva() {
        System.out.println("\n--- CREAR RESERVA ---");
        
        // Mostrar habitaciones disponibles
        ArrayList<Habitacion> habitaciones = habitacionDAO.listarDisponibles();
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones disponibles");
            return;
        }
        
        System.out.println("\nHabitaciones disponibles:");
        for (Habitacion h : habitaciones) {
            System.out.println(h.getId() + " - " + h.getTipo() + " (" + h.getPrecioPorNoche() + " euros/noche)");
        }
        
        // Pedir datos
        int idHabitacion = leerEntero("\nID de habitacion: ");
        Habitacion habitacion = habitacionDAO.obtenerPorId(idHabitacion);
        
        if (habitacion == null || !habitacion.isDisponible()) {
            System.out.println("Habitacion no disponible");
            return;
        }
        
        int idCliente = leerEntero("ID del cliente: ");
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        
        if (cliente == null) {
            System.out.println("Cliente no encontrado. Registrelo primero.");
            return;
        }
        
        System.out.print("Fecha entrada (dd/MM/yyyy): ");
        Date fechaEntrada = leerFecha(scanner.nextLine());
        
        System.out.print("Fecha salida (dd/MM/yyyy): ");
        Date fechaSalida = leerFecha(scanner.nextLine());
        
        if (fechaEntrada == null || fechaSalida == null) {
            System.out.println("Error en las fechas");
            return;
        }
        
        if (fechaSalida.before(fechaEntrada)) {
            System.out.println("La fecha de salida debe ser posterior a la entrada");
            return;
        }
        
        // Crear reserva
        try {
            habitacion.reservar();
            habitacionDAO.actualizarDisponibilidad(habitacion.getId(), false);
            
            Reserva reserva = new Reserva(cliente, habitacion, fechaEntrada, fechaSalida);
            reservaDAO.insertarReserva(reserva);
            
            System.out.println("\nReserva creada:");
            System.out.println("Cliente: " + cliente.getNombre());
            System.out.println("Habitacion: " + habitacion.getTipo());
            System.out.println("Total: " + reserva.getImporteTotal() + " euros");
            
        } catch (ReservaInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Opcion 4: Cancelar reserva
    private void cancelarReserva() {
        System.out.println("\n--- CANCELAR RESERVA ---");
        
        // Mostrar reservas existentes
        ArrayList<Reserva> reservas = reservaDAO.listarReservas();
        
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas para cancelar");
            return;
        }
        
        System.out.println("Reservas actuales:");
        for (Reserva r : reservas) {
            System.out.println("#" + r.getId() + " - " + r.getCliente().getNombre() + 
                             " - Hab " + r.getHabitacion().getId());
        }
        
        int idReserva = leerEntero("\nID de reserva a cancelar: ");
        Reserva reserva = reservaDAO.buscarPorId(idReserva);
        
        if (reserva == null) {
            System.out.println("Reserva no encontrada");
            return;
        }
        
        // Cancelar
        reservaDAO.eliminarReserva(idReserva);
        habitacionDAO.actualizarDisponibilidad(reserva.getHabitacion().getId(), true);
        
        System.out.println("Reserva cancelada. Habitacion " + reserva.getHabitacion().getId() + " disponible");
    }
    
    // Opcion 5: Mostrar todas las reservas
    private void mostrarTodasReservas() {
        System.out.println("\n--- TODAS LAS RESERVAS ---");
        
        ArrayList<Reserva> reservas = reservaDAO.listarReservas();
        
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas");
            return;
        }
        
        for (Reserva r : reservas) {
            System.out.println("----------------------------------------");
            System.out.println("Reserva #" + r.getId());
            System.out.println("Cliente: " + r.getCliente().getNombre());
            System.out.println("Habitacion: " + r.getHabitacion().getId() + " (" + r.getHabitacion().getTipo() + ")");
            System.out.println("Entrada: " + formatoFecha.format(r.getFechaEntrada()));
            System.out.println("Salida: " + formatoFecha.format(r.getFechaSalida()));
            System.out.println("Total: " + r.getImporteTotal() + " euros");
        }
        
        System.out.println("----------------------------------------");
        System.out.println("Total reservas: " + reservas.size());
    }
    
    // Metodo auxiliar para leer enteros
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Introduce un numero valido");
            scanner.next();
            System.out.print(mensaje);
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        return num;
    }
    
    // Metodo auxiliar para leer fechas
    private Date leerFecha(String texto) {
        try {
            return formatoFecha.parse(texto);
        } catch (ParseException e) {
            return null;
        }
    }
}
