/*
 * **************************************************************************************
 *  Proyecto final basico: Agenda de Citas en consola
 *
 *  // * Proposito
 *  Aplicacion principal (main). Crea controladores/repositorios y gestiona
 *  el ciclo de menus en consola.
 *
 *  // * Rol en MVC
 *  - Orquesta la navegacion (ConsoleView) y delega logica en controladores.
 *
 *  // * Flujo
 *  - run(): bucle principal -> submenus (Clientes, Citas) -> acciones.
 *
 *  // ? Nota didactica
 *  Mantener esta clase ligera: sin reglas de negocio, solo menus y lectura de entradas.
 * **************************************************************************************
 */
package com.curso.proyectobasico;

import com.curso.proyectobasico.controller.CitaController;
import com.curso.proyectobasico.controller.ClienteController;
import com.curso.proyectobasico.model.Cita;
import com.curso.proyectobasico.model.Cliente;
import com.curso.proyectobasico.repository.CitaRepository;
import com.curso.proyectobasico.repository.ClienteRepository;
import com.curso.proyectobasico.view.ConsoleView;

import java.util.List;

public class Application {
    private final ConsoleView view = new ConsoleView();
    private final ClienteRepository clienteRepo = new ClienteRepository();
    private final CitaRepository citaRepo = new CitaRepository();

    private final ClienteController clienteCtl = new ClienteController(clienteRepo);
    private final CitaController citaCtl = new CitaController(citaRepo, clienteRepo);

    public static void main(String[] args) {
        new Application().run();
    }

    // * Bucle principal del menu
    public void run() {
        while (true) {
            view.title("Agenda basica de citas - Menu principal");
            view.line("1) Clientes");
            view.line("2) Citas");
            view.line("0) Salir");
            String op = view.prompt("Opcion");
            try {
                switch (op) {
                    case "1" -> menuClientes();
                    case "2" -> menuCitas();
                    case "0" -> {
                        return;
                    }
                    default -> view.line("Opcion no valida");
                }
            } catch (Exception e) {
                view.line("[ERROR] " + e.getMessage());
            }
        }
    }

    // * Submenu: gestion de clientes
    private void menuClientes() {
        while (true) {
            view.title("Clientes");
            view.line("1) Listar");
            view.line("2) Crear");
            view.line("3) Borrar");
            view.line("0) Volver");
            String op = view.prompt("Opcion");
            if (op.equals("0")) return;
            try {
                switch (op) {
                    case "1" -> listarClientes();
                    case "2" -> crearCliente();
                    case "3" -> borrarCliente();
                    default -> view.line("Opcion no valida");
                }
            } catch (Exception e) {
                view.line("[ERROR] " + e.getMessage());
            }
            view.pause();
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteCtl.listar();
        view.line("-- Clientes --");
        for (Cliente c : clientes) {
            view.line(c.toString());
        }
    }

    private void crearCliente() {
        String nombre = view.prompt("Nombre");
        String email = view.prompt("Email (opcional, vacio si no tiene)");
        String telefono = view.prompt("Telefono (opcional)");
        Cliente c = clienteCtl.crear(nombre, email, telefono);
        view.line("Creado cliente con id: " + c.getId());
    }

    private void borrarCliente() {
        String id = view.prompt("Id de cliente a borrar");
        boolean ok = clienteCtl.borrar(id);
        view.line(ok ? "Cliente borrado" : "Cliente no encontrado");
    }

    // * Submenu: gestion de citas
    private void menuCitas() {
        while (true) {
            view.title("Citas");
            view.line("1) Listar todas");
            view.line("2) Crear nueva");
            view.line("3) Marcar como realizada");
            view.line("4) Borrar cita");
            view.line("0) Volver");
            String op = view.prompt("Opcion");
            if (op.equals("0")) return;
            try {
                switch (op) {
                    case "1" -> listarCitas();
                    case "2" -> crearCita();
                    case "3" -> marcarCitaRealizada();
                    case "4" -> borrarCita();
                    default -> view.line("Opcion no valida");
                }
            } catch (Exception e) {
                view.line("[ERROR] " + e.getMessage());
            }
            view.pause();
        }
    }

    private void listarCitas() {
        List<Cita> citas = citaCtl.listar();
        view.line("-- Citas --");
        for (Cita c : citas) {
            view.line(c.toString());
        }
    }

    private void crearCita() {
        String clienteId = view.prompt("Id de cliente");
        String fecha = view.prompt("Fecha (yyyy-MM-dd)");
        String descripcion = view.prompt("Descripcion breve");
        Cita cita = citaCtl.crear(clienteId, fecha, descripcion);
        view.line("Creada cita con id: " + cita.getId());
    }

    private void marcarCitaRealizada() {
        String citaId = view.prompt("Id de cita a marcar como realizada");
        boolean ok = citaCtl.marcarRealizada(citaId);
        view.line(ok ? "Cita marcada como REALIZADA" : "Cita no encontrada");
    }

    private void borrarCita() {
        String citaId = view.prompt("Id de cita a borrar");
        boolean ok = citaCtl.borrar(citaId);
        view.line(ok ? "Cita borrada" : "Cita no encontrada");
    }
}

