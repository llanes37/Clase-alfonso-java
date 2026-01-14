package com.curso.ut20.controller;

import com.curso.ut20.model.Usuario;
import com.curso.ut20.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * //! CONTROLADOR REST - USUARIOS
 * ? Este controlador expone una API REST para gestionar usuarios (CRUD completo)
 *
 * * Endpoints disponibles:
 *   - GET    /api/usuarios       → Listar todos los usuarios
 *   - GET    /api/usuarios/{id}  → Obtener un usuario por ID
 *   - POST   /api/usuarios       → Crear un nuevo usuario
 *   - PUT    /api/usuarios/{id}  → Actualizar un usuario existente
 *   - DELETE /api/usuarios/{id}  → Eliminar un usuario
 *
 * TODO: Considera añadir paginación (Pageable), búsqueda por nombre, ordenación, etc.
 */
@RestController // * Combina @Controller + @ResponseBody (todas las respuestas son JSON)
@RequestMapping("/api/usuarios") // * Prefijo de ruta para todos los endpoints de este controlador
public class UsuarioController {

    // ========================================
    // INYECCIÓN DE DEPENDENCIAS
    // ========================================

    /**
     * ! REPOSITORIO INYECTADO VÍA CONSTRUCTOR
     * ? Spring inyecta automáticamente el repositorio (no hace falta @Autowired)
     * * El repositorio proporciona métodos CRUD: findAll(), findById(), save(), deleteById(), etc.
     * * final asegura que el repositorio no pueda ser reasignado después de la construcción
     */
    private final UsuarioRepository repo;

    /**
     * ? Constructor con inyección de dependencias
     * * Spring llama a este constructor automáticamente y pasa el repositorio
     * @param repo Repositorio JPA para acceder a la base de datos
     */
    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    // ========================================
    // ENDPOINTS CRUD
    // ========================================

    /**
     * ! GET /api/usuarios - LISTAR TODOS LOS USUARIOS
     * ? Retorna una lista con todos los usuarios almacenados en la base de datos
     *
     * * HTTP Status: 200 OK
     * * Respuesta: Array JSON con todos los usuarios
     *
     * Ejemplo de respuesta:
     * [
     *   { "id": 1, "nombre": "Juan Pérez", "edad": 25 },
     *   { "id": 2, "nombre": "María García", "edad": 30 }
     * ]
     *
     * @return Lista de usuarios (puede estar vacía si no hay usuarios)
     */
    @GetMapping // * Mapea GET /api/usuarios
    public List<Usuario> listar() {
        return repo.findAll(); // * Consulta SELECT * FROM usuario
    }

    /**
     * ! GET /api/usuarios/{id} - OBTENER UN USUARIO POR ID
     * ? Busca un usuario específico por su ID
     *
     * * HTTP Status: 200 OK si existe, 404 Not Found si no existe
     *
     * ? Ejemplo de uso: GET /api/usuarios/5
     *
     * Flujo de ejecución:
     * 1. repo.findById(id) retorna Optional<Usuario>
     * 2. .map() transforma el Optional si contiene valor
     * 3. ResponseEntity.ok() crea respuesta 200 OK con el usuario
     * 4. .orElse() retorna 404 si el Optional está vacío
     *
     * @param id ID del usuario a buscar (extraído de la URL)
     * @return ResponseEntity con el usuario (200) o vacío (404)
     */
    @GetMapping("/{id}") // * {id} es una variable de ruta
    public ResponseEntity<Usuario> uno(@PathVariable Long id) {
        // * Programación funcional con Optional para evitar null checks
        return repo.findById(id)
                .map(ResponseEntity::ok) // * Si existe → 200 OK
                .orElse(ResponseEntity.notFound().build()); // * Si no existe → 404 Not Found
    }

    /**
     * ! POST /api/usuarios - CREAR NUEVO USUARIO
     * ? Recibe un usuario en el body de la petición y lo guarda en la base de datos
     *
     * * HTTP Status: 201 Created (incluye header Location con la URL del nuevo recurso)
     * * Validaciones: @Valid activa las validaciones de Bean Validation
     *   - nombre: @NotBlank
     *   - edad: @Min(0)
     *
     * ? Ejemplo de petición:
     * POST /api/usuarios
     * Content-Type: application/json
     * {
     *   "nombre": "Carlos López",
     *   "edad": 28
     * }
     *
     * ! Si la validación falla, GlobalExceptionHandler retorna 400 Bad Request con los errores
     *
     * @param u Usuario a crear (deserializado desde JSON)
     * @return ResponseEntity con el usuario creado (incluye ID generado) y header Location
     */
    @PostMapping // * Mapea POST /api/usuarios
    public ResponseEntity<Usuario> crear(@Valid @RequestBody Usuario u) {
        // * @Valid activa validaciones antes de ejecutar este método
        // * @RequestBody deserializa el JSON del body a un objeto Usuario

        Usuario saved = repo.save(u); // * INSERT INTO usuario (...) VALUES (...)
        // * Hibernate genera el ID automáticamente y lo asigna a 'saved'

        // * Retorna 201 Created con header "Location: /api/usuarios/{id}"
        return ResponseEntity
                .created(URI.create("/api/usuarios/" + saved.getId()))
                .body(saved); // * Incluye el usuario creado en el body de la respuesta
    }

    /**
     * ! PUT /api/usuarios/{id} - ACTUALIZAR USUARIO EXISTENTE
     * ? Actualiza los datos de un usuario existente
     *
     * * HTTP Status: 200 OK si existe y se actualiza, 404 Not Found si no existe
     *
     * ? Ejemplo de uso:
     * PUT /api/usuarios/3
     * Content-Type: application/json
     * {
     *   "nombre": "Carlos López García",
     *   "edad": 29
     * }
     *
     * Flujo de ejecución:
     * 1. Buscar el usuario por ID
     * 2. Si existe, copiar los datos del body al usuario encontrado
     * 3. Guardar los cambios en la base de datos
     * 4. Retornar 200 OK con el usuario actualizado
     * 5. Si no existe, retornar 404 Not Found
     *
     * ! Las validaciones se aplican a 'datos' (@Valid)
     *
     * @param id ID del usuario a actualizar
     * @param datos Nuevos datos del usuario (deserializados desde JSON)
     * @return ResponseEntity con el usuario actualizado (200) o vacío (404)
     */
    @PutMapping("/{id}") // * Mapea PUT /api/usuarios/{id}
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @Valid @RequestBody Usuario datos) {
        // * Programación funcional con Optional
        return repo.findById(id).map(u -> {
            // * Si el usuario existe, actualizar sus campos
            u.setNombre(datos.getNombre());
            u.setEdad(datos.getEdad());
            // * UPDATE usuario SET nombre=?, edad=? WHERE id=?
            return ResponseEntity.ok(repo.save(u)); // * 200 OK
        }).orElse(ResponseEntity.notFound().build()); // * 404 Not Found
    }

    /**
     * ! DELETE /api/usuarios/{id} - ELIMINAR USUARIO
     * ? Elimina un usuario de la base de datos
     *
     * * HTTP Status: 204 No Content si se elimina, 404 Not Found si no existe
     *
     * ? Ejemplo de uso: DELETE /api/usuarios/7
     *
     * Flujo de ejecución:
     * 1. Verificar si el usuario existe
     * 2. Si no existe, retornar 404 Not Found
     * 3. Si existe, eliminarlo de la base de datos
     * 4. Retornar 204 No Content (sin body en la respuesta)
     *
     * ! 204 No Content es el código estándar para eliminaciones exitosas
     *
     * @param id ID del usuario a eliminar
     * @return ResponseEntity vacío con código 204 o 404
     */
    @DeleteMapping("/{id}") // * Mapea DELETE /api/usuarios/{id}
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        // * Primero verificamos si existe para retornar 404 en caso negativo
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build(); // * 404 Not Found
        }

        repo.deleteById(id); // * DELETE FROM usuario WHERE id=?
        return ResponseEntity.noContent().build(); // * 204 No Content (eliminación exitosa)
    }
}
