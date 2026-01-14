# 📘 UT20 — Spring Boot + API REST + JPA + Validación + Swagger

## Objetivos
- Crear una API REST profesional con Spring Boot.
- Persistencia con JPA/Hibernate y base H2 en memoria.
- Validaciones con Bean Validation.
- Documentación con OpenAPI/Swagger UI.

## Teoría (resumen)
- Controladores REST (@RestController) exponen endpoints CRUD.
- Repositorios JPA abstraen el acceso a datos (save, findAll, findById...).
- Validación con anotaciones (@NotBlank, @Min, etc.).
- Swagger (springdoc) genera documentación y UI interactiva.

## Estructura del proyecto
```
UT20_SpringBoot_API_REST_JPA/
  pom.xml
  src/main/java/com/curso/ut20/
    Ut20Application.java
    model/{Usuario,Producto}.java
    repository/{UsuarioRepository,ProductoRepository}.java
    controller/{UsuarioController,ProductoController}.java
    exception/GlobalExceptionHandler.java
  src/main/resources/application.properties
```

## Cómo ejecutar
1) Desde la carpeta del proyecto UT20:
```
mvn spring-boot:run
```
2) Visita Swagger UI:
```
http://localhost:8080/swagger-ui/index.html
```

## Endpoints de ejemplo
- Usuarios: `GET /api/usuarios`, `POST /api/usuarios`, `GET/PUT/DELETE /api/usuarios/{id}`
- Productos: `GET /api/productos`, `POST /api/productos`, `GET/PUT/DELETE /api/productos/{id}`

## Prácticas guiadas
- Añade DTOs y mapeo (MapStruct opcional).
- Añade paginación y ordenación en listados.
- Filtrado (por nombre, rango de precios/edad).
- Manejo de errores con mensajes claros y códigos HTTP adecuados.

## Retos
- Integrar seguridad básica con Spring Security y JWT.
- Persistir en Postgres/MySQL con perfiles (dev/test/prod).
- Añadir tests de integración con Testcontainers.
