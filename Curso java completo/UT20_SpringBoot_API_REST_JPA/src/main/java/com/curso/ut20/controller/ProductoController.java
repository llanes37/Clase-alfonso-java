package com.curso.ut20.controller;

import com.curso.ut20.model.Producto;
import com.curso.ut20.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * //! CONTROLADOR REST - PRODUCTOS
 * ? Este controlador expone una API REST para gestionar productos (CRUD completo)
 *
 * * Endpoints disponibles:
 *   - GET    /api/productos       → Listar todos los productos
 *   - GET    /api/productos/{id}  → Obtener un producto por ID
 *   - POST   /api/productos       → Crear un nuevo producto
 *   - PUT    /api/productos/{id}  → Actualizar un producto existente
 *   - DELETE /api/productos/{id}  → Eliminar un producto
 *
 * TODO: Considera añadir paginación (Pageable), búsqueda por nombre, filtros, etc.
 */
@RestController // * Combina @Controller + @ResponseBody (todas las respuestas son JSON)
@RequestMapping("/api/productos") // * Prefijo de ruta para todos los endpoints de este controlador
public class ProductoController {

    // ========================================
    // INYECCIÓN DE DEPENDENCIAS
    // ========================================

    /**
     * ! REPOSITORIO INYECTADO VÍA CONSTRUCTOR
     * ? Spring inyecta automáticamente el repositorio (no hace falta @Autowired)
     * * El repositorio proporciona métodos CRUD: findAll(), findById(), save(), deleteById(), etc.
     * * final asegura que el repositorio no pueda ser reasignado después de la construcción
     */
    private final ProductoRepository repo;

    /**
     * ? Constructor con inyección de dependencias
     * * Spring llama a este constructor automáticamente y pasa el repositorio
     * @param repo Repositorio JPA para acceder a la base de datos
     */
    public ProductoController(ProductoRepository repo) {
        this.repo = repo;
    }

    // ========================================
    // ENDPOINTS CRUD
    // ========================================

    /**
     * ! GET /api/productos - LISTAR TODOS LOS PRODUCTOS
     * ? Retorna una lista con todos los productos almacenados en la base de datos
     *
     * * HTTP Status: 200 OK
     * * Respuesta: Array JSON con todos los productos
     *
     * Ejemplo de respuesta:
     * [
     *   { "id": 1, "nombre": "Laptop", "precio": 999.99 },
     *   { "id": 2, "nombre": "Mouse", "precio": 25.50 }
     * ]
     *
     * @return Lista de productos (puede estar vacía si no hay productos)
     */
    @GetMapping // * Mapea GET /api/productos
    public List<Producto> listar() {
        return repo.findAll(); // * Consulta SELECT * FROM producto
    }

    /**
     * ! GET /api/productos/{id} - OBTENER UN PRODUCTO POR ID
     * ? Busca un producto específico por su ID
     *
     * * HTTP Status: 200 OK si existe, 404 Not Found si no existe
     *
     * ? Ejemplo de uso: GET /api/productos/5
     *
     * Flujo de ejecución:
     * 1. repo.findById(id) retorna Optional<Producto>
     * 2. .map() transforma el Optional si contiene valor
     * 3. ResponseEntity.ok() crea respuesta 200 OK con el producto
     * 4. .orElse() retorna 404 si el Optional está vacío
     *
     * @param id ID del producto a buscar (extraído de la URL)
     * @return ResponseEntity con el producto (200) o vacío (404)
     */
    @GetMapping("/{id}") // * {id} es una variable de ruta
    public ResponseEntity<Producto> uno(@PathVariable Long id) {
        // * Programación funcional con Optional para evitar null checks
        return repo.findById(id)
                .map(ResponseEntity::ok) // * Si existe → 200 OK
                .orElse(ResponseEntity.notFound().build()); // * Si no existe → 404 Not Found
    }

    /**
     * ! POST /api/productos - CREAR NUEVO PRODUCTO
     * ? Recibe un producto en el body de la petición y lo guarda en la base de datos
     *
     * * HTTP Status: 201 Created (incluye header Location con la URL del nuevo recurso)
     * * Validaciones: @Valid activa las validaciones de Bean Validation
     *   - nombre: @NotBlank
     *   - precio: @Min(0)
     *
     * ? Ejemplo de petición:
     * POST /api/productos
     * Content-Type: application/json
     * {
     *   "nombre": "Teclado Mecánico",
     *   "precio": 89.99
     * }
     *
     * ! Si la validación falla, GlobalExceptionHandler retorna 400 Bad Request con los errores
     *
     * @param p Producto a crear (deserializado desde JSON)
     * @return ResponseEntity con el producto creado (incluye ID generado) y header Location
     */
    @PostMapping // * Mapea POST /api/productos
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto p) {
        // * @Valid activa validaciones antes de ejecutar este método
        // * @RequestBody deserializa el JSON del body a un objeto Producto

        Producto saved = repo.save(p); // * INSERT INTO producto (...) VALUES (...)
        // * Hibernate genera el ID automáticamente y lo asigna a 'saved'

        // * Retorna 201 Created con header "Location: /api/productos/{id}"
        return ResponseEntity
                .created(URI.create("/api/productos/" + saved.getId()))
                .body(saved); // * Incluye el producto creado en el body de la respuesta
    }

    /**
     * ! PUT /api/productos/{id} - ACTUALIZAR PRODUCTO EXISTENTE
     * ? Actualiza los datos de un producto existente
     *
     * * HTTP Status: 200 OK si existe y se actualiza, 404 Not Found si no existe
     *
     * ? Ejemplo de uso:
     * PUT /api/productos/3
     * Content-Type: application/json
     * {
     *   "nombre": "Laptop Gamer",
     *   "precio": 1299.99
     * }
     *
     * Flujo de ejecución:
     * 1. Buscar el producto por ID
     * 2. Si existe, copiar los datos del body al producto encontrado
     * 3. Guardar los cambios en la base de datos
     * 4. Retornar 200 OK con el producto actualizado
     * 5. Si no existe, retornar 404 Not Found
     *
     * ! Las validaciones se aplican a 'datos' (@Valid)
     *
     * @param id ID del producto a actualizar
     * @param datos Nuevos datos del producto (deserializados desde JSON)
     * @return ResponseEntity con el producto actualizado (200) o vacío (404)
     */
    @PutMapping("/{id}") // * Mapea PUT /api/productos/{id}
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto datos) {
        // * Programación funcional con Optional
        return repo.findById(id).map(p -> {
            // * Si el producto existe, actualizar sus campos
            p.setNombre(datos.getNombre());
            p.setPrecio(datos.getPrecio());
            // * UPDATE producto SET nombre=?, precio=? WHERE id=?
            return ResponseEntity.ok(repo.save(p)); // * 200 OK
        }).orElse(ResponseEntity.notFound().build()); // * 404 Not Found
    }

    /**
     * ! DELETE /api/productos/{id} - ELIMINAR PRODUCTO
     * ? Elimina un producto de la base de datos
     *
     * * HTTP Status: 204 No Content si se elimina, 404 Not Found si no existe
     *
     * ? Ejemplo de uso: DELETE /api/productos/7
     *
     * Flujo de ejecución:
     * 1. Verificar si el producto existe
     * 2. Si no existe, retornar 404 Not Found
     * 3. Si existe, eliminarlo de la base de datos
     * 4. Retornar 204 No Content (sin body en la respuesta)
     *
     * ! 204 No Content es el código estándar para eliminaciones exitosas
     *
     * @param id ID del producto a eliminar
     * @return ResponseEntity vacío con código 204 o 404
     */
    @DeleteMapping("/{id}") // * Mapea DELETE /api/productos/{id}
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        // * Primero verificamos si existe para retornar 404 en caso negativo
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build(); // * 404 Not Found
        }

        repo.deleteById(id); // * DELETE FROM producto WHERE id=?
        return ResponseEntity.noContent().build(); // * 204 No Content (eliminación exitosa)
    }
}
