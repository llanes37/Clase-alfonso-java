# 📘 UT19 — Arquitectura en Capas con JDBC + JUnit + Maven + Logging

## Objetivos
- Separar la app en capas (modelo, repositorio, servicio y vista CLI).
- Refactorizar las prácticas de JDBC anteriores a un diseño profesional.
- Incorporar JUnit 5 (tests), logging con SLF4J/Logback y Maven.

## Teoría (resumen)
- JDBC y patrón Repository: aísla el acceso a datos (PreparedStatement, ResultSet → mapeo a objetos).
- Servicio: valida y aplica reglas de negocio (no edad negativa, etc.).
- Maven: gestión de dependencias; estructura estándar (src/main/java, src/test/java).
- Logging: SLF4J como API + Logback como implementación.

## Estructura del proyecto
```
UT19_ArquitecturaCapas_JDBC/
  pom.xml
  src/main/java/com/curso/ut19/
    Application.java            # Menú CLI estilo UT17/UT18
    model/Usuario.java
    repository/UsuarioRepository.java
    repository/jdbc/UsuarioRepositoryJdbc.java
    service/UsuarioService.java
    persistence/Db.java
    util/Validator.java
  src/main/resources/logback.xml
  src/test/java/com/curso/ut19/service/UsuarioServiceTest.java
```

## Cómo ejecutar
1) Ir a la carpeta del proyecto UT19 y compilar/ejecutar con Maven o desde IDE.
2) Asegúrate de tener Java 17 o superior.

Comandos (opcional):
```
# Compilar
mvn -q -e -DskipTests package

# Ejecutar (jar sin empaquetar ejecutable, ejecutar desde IDE o crear un main jar si lo deseas)
```

## Prácticas guiadas
1) Completa el CRUD de `Usuario` desde el menú.
2) Añade validaciones extra (longitud mínima de nombre, etc.).
3) Implementa repositorio y servicio para `Producto` y `Categoría` replicando el patrón.
4) Crea tests de servicio con Mockito para casos felices y errores.

## Retos
- Exportar listados a CSV.
- Añadir transacciones en operaciones múltiples.
- Extraer interfaz `Repository<T>` genérica y reutilizarla.
