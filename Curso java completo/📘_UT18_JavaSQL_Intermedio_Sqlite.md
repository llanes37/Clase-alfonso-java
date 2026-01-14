# 📘 UT18 — Java + SQL intermedio con JDBC y SQLite

> Guía asociada a `UT18_JavaSQL_Intermedio_Sqlite.java`. Mantiene el diseño de UT17 con Better Comments y amplía el nivel: relaciones (FK), JOINs, validación y transacciones.

## 🎯 Objetivos de aprendizaje
- Consolidar JDBC con consultas preparadas y manejo de `ResultSet` más completo.
- Trabajar con varias tablas relacionadas: `usuarios`, `categorias`, `productos` (FK a `categorias`).
- Realizar JOINs para enriquecer listados.
- Aplicar transacciones (`commit`/`rollback`) para operaciones atómicas.

## 🧠 Teoría resumida
- Claves foráneas en SQLite: activar con `PRAGMA foreign_keys = ON`.
- Validación en BD: restricciones `CHECK` (precio ≥ 0, edad ≥ 0) y `UNIQUE` (nombre de categoría).
- LEFT JOIN: lista productos aunque no tengan categoría (muestra `NULL`).
- Transacciones: desactiva `autoCommit`, ejecuta múltiples operaciones y confirma o revierte el conjunto.

## 🧩 Requisitos
- Java 17+.
- Driver SQLite JDBC (por ejemplo `sqlite-jdbc-3.36.0.3.jar`) accesible en el classpath.

## ⚙️ Preparación del entorno (Windows PowerShell)
```powershell
# Compilar
javac "UT18_JavaSQL_Intermedio_Sqlite.java"

# Ejecutar (añadiendo el JAR del driver al classpath)
java -cp ".;libs\sqlite-jdbc-3.36.0.3.jar" UT18_JavaSQL_Intermedio_Sqlite
```

## 🧭 Menú del programa (contenido)
1. Conectar / Inicializar BD (crea/verifica tablas y activa FKs)
2. Insertar usuario
3. Listar usuarios
4. Actualizar usuario por ID
5. Eliminar usuario por ID
6. Insertar categoría
7. Listar categorías
8. Insertar producto (con categoría opcional)
9. Listar productos (con nombre de categoría)
10. Actualizar producto por ID
11. Eliminar producto por ID
12. Buscar usuarios por nombre (LIKE)
13. Demo de transacción (COMMIT/ROLLBACK)
14. Desconectar BD
15. Salir

## 🛠️ Detalle de funcionalidades
- Inicialización: crea tablas `usuarios`, `categorias` y `productos`, activa claves foráneas y añade restricciones de integridad.
- Usuarios (CRUD): todas las operaciones parametrizadas con `PreparedStatement` y validaciones de entrada.
- Categorías: inserción/listado; nombre `UNIQUE` para evitar duplicados.
- Productos (CRUD): categoría opcional; FK con `ON DELETE SET NULL`.
- Búsqueda LIKE: filtra usuarios por nombre parcial.
- Transacción: ejemplo que fuerza un error y revierte los cambios completos (rollback).

## 📝 Better Comments en el código
- `//!` Título de sección.
- `?` Teoría aplicada al bloque.
- `*` Consejos, buenas prácticas y anotaciones útiles.
- `TODO` Tareas del alumno para ampliar.
- `NOTE` Consideraciones o matices de uso.

## 🧪 Ejercicios guiados
1) CRUD completo de categorías
- Añade `UPDATE` y `DELETE` para `categorias` (ten en cuenta el efecto en productos al borrar).

2) Rango de búsqueda
- Extiende la búsqueda de usuarios para filtrar por edad mínima y máxima.

3) Validación extra
- Rechaza nombres de producto con menos de 3 caracteres y nombres de usuario con menos de 2.

4) Reports con JOINs
- Crea un listado de productos agrupados por categoría con totales y precio medio.

## 🧩 Tareas del alumno (para reforzar)
- TODO: Añadir paginación simple (OFFSET/LIMIT) a listados.
- TODO: Exportar listados a CSV.
- TODO: Implementar un menú para operaciones encadenadas dentro de una única transacción.

## 🧯 Solución de problemas
- "foreign keys disabled": asegúrate de ejecutar `PRAGMA foreign_keys = ON` tras conectar.
- Restricciones `CHECK` fallan: confirma que no intentas insertar precios/edades negativos.
- `NULL` en categoría: es normal si insertaste producto sin categoría o la categoría fue eliminada (por `ON DELETE SET NULL`).

## ✅ Criterios de evaluación (rúbrica corta)
- BD inicializada con FKs activas y tablas correctas (2 pt).
- CRUDs funcionando con validaciones y PreparedStatements (4 pt).
- JOIN correcto en listados y búsqueda LIKE operativa (3 pt).
- Transacción con rollback demostrada y explicada (1 pt).

## 🌱 Extensiones opcionales
- Índices en columnas buscadas frecuentemente.
- Logs de auditoría (insert/update/delete) en una tabla separada.
- Script de migraciones SQL (evolución de esquema) y datos semilla.
