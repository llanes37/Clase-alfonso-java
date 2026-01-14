package com.curso.proyectobasico.controller;

import com.curso.proyectobasico.exception.ValidationException;
import com.curso.proyectobasico.model.Cita;
import com.curso.proyectobasico.model.EstadoCita;
import com.curso.proyectobasico.repository.CitaRepository;
import com.curso.proyectobasico.repository.ClienteRepository;
import com.curso.proyectobasico.util.DateUtils;
import com.curso.proyectobasico.util.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/*
 * Logica de negocio para citas.
 * - Verifica que el cliente existe.
 * - Valida fecha y estado.
 */
public class CitaController {
    private final CitaRepository citaRepo;
    private final ClienteRepository clienteRepo;

    public CitaController(CitaRepository citaRepo, ClienteRepository clienteRepo) {
        this.citaRepo = citaRepo;
        this.clienteRepo = clienteRepo;
    }

    public List<Cita> listar() {
        return citaRepo.findAll();
    }

    public Cita crear(String clienteId, String fechaStr, String descripcion) {
        Validator.requireNotBlank(clienteId, "ClienteId");
        Validator.requireNotBlank(fechaStr, "Fecha");
        clienteRepo.findById(clienteId)
                .orElseThrow(() -> new ValidationException("Cliente no encontrado: " + clienteId));

        LocalDate fecha = DateUtils.parse(fechaStr);
        if (fecha.isBefore(LocalDate.now())) {
            throw new ValidationException("La fecha no puede estar en el pasado");
        }

        String id = UUID.randomUUID().toString();
        Cita cita = new Cita(id, clienteId, fecha, EstadoCita.PENDIENTE,
                descripcion == null ? "" : descripcion.trim());
        return citaRepo.save(cita);
    }

    public boolean marcarRealizada(String citaId) {
        Validator.requireNotBlank(citaId, "Id cita");
        return citaRepo.findById(citaId)
                .map(cita -> {
                    cita.setEstado(EstadoCita.REALIZADA);
                    citaRepo.update(cita);
                    return true;
                })
                .orElse(false);
    }

    public boolean borrar(String citaId) {
        Validator.requireNotBlank(citaId, "Id cita");
        return citaRepo.delete(citaId);
    }
}

