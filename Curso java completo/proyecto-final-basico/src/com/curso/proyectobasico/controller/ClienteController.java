package com.curso.proyectobasico.controller;

import com.curso.proyectobasico.exception.ValidationException;
import com.curso.proyectobasico.model.Cliente;
import com.curso.proyectobasico.repository.ClienteRepository;
import com.curso.proyectobasico.util.Validator;

import java.util.List;
import java.util.UUID;

/*
 * Logica de negocio para clientes.
 * - Valida nombre y email.
 * - Evita duplicados por email.
 */
public class ClienteController {
    private final ClienteRepository repo;

    public ClienteController(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listar() {
        return repo.findAll();
    }

    public Cliente crear(String nombre, String email, String telefono) {
        Validator.requireNotBlank(nombre, "Nombre");
        Validator.requireEmail(email);
        if (repo.findByEmail(email).isPresent()) {
            throw new ValidationException("Ya existe un cliente con ese email");
        }
        String id = UUID.randomUUID().toString();
        Cliente c = new Cliente(id,
                nombre.trim(),
                email == null ? "" : email.trim().toLowerCase(),
                telefono == null ? "" : telefono.trim());
        return repo.save(c);
    }

    public boolean borrar(String id) {
        Validator.requireNotBlank(id, "Id");
        return repo.delete(id);
    }
}

