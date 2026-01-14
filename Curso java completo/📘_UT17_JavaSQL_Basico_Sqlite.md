# 📘 UT17 — Java + SQL básico con JDBC y SQLite

> Mantiene el estilo didáctico del curso (Better Comments: //!, ?, *, TODO, NOTE) y acompaña al archivo `UT17_JavaSQL_Basico_Sqlite.java`.

## 🎯 Objetivos de aprendizaje
- Conectar desde Java (JDBC) a una base de datos SQLite embebida.
- Crear tablas y ejecutar operaciones básicas: INSERT, SELECT, UPDATE, DELETE.
- Entender la diferencia entre `Statement` y `PreparedStatement`.
- Manejar entradas del usuario con `Scanner` y mostrar resultados formateados.

## 🧠 Teoría resumida
- JDBC: API de Java para bases de datos. Piezas clave: `DriverManager` → `Connection` → `Statement/PreparedStatement` → `ResultSet`.
- SQLite: base de datos en un archivo. No necesita servidor. Driver: `org.sqlite.JDBC`.
- Seguridad: `PreparedStatement` ayuda a prevenir inyecciones SQL.

## 🧩 Requisitos
- Java 17+.
- Driver SQLite JDBC (por ejemplo `sqlite-jdbc-3.36.0.3.jar`) en `libs/` o en el classpath.
- Extensiones recomendadas: Java Extension Pack y Better Comments.

## ⚙️ Preparación del entorno (Windows PowerShell)
Opcional pero recomendado si ejecutas desde consola en lugar de IDE:
```powershell
# Compilar
javac "UT17_JavaSQL_Basico_Sqlite.java"

# Ejecutar (añadiendo el JAR del driver al classpath)
java -cp ".;libs\sqlite-jdbc-3.36.0.3.jar" UT1_JavaSQL_Basico_Sqlite
```
Nota: La clase pública en el código se llama `UT1_JavaSQL_Basico_Sqlite`. Es normal ejecutar con ese nombre.

## 🧭 Menú del programa
1. Conectar a la Base de Datos
2. Insertar un Registro (Tabla "usuarios")
3. Ejecutar Consulta SELECT
4. Actualizar/Eliminar Registros (Edición)
5. Ejercicios Extra para Practicar
6. Desconectar de la Base de Datos
7. Salir

## 🛠️ Cómo usar cada opción
- 1) Conectar: Carga el driver, abre `miBaseDatos.db` y crea la tabla `usuarios` si no existe.
- 2) Insertar: Inserta un usuario (nombre, edad) usando `PreparedStatement`.
- 3) SELECT: Realiza una consulta y muestra resultados por consola.
- 4) Editar/Eliminar: Ejemplos de `UPDATE` y `DELETE` por `id`.
- 6) Desconectar: Cierra la conexión liberando recursos.

## 📝 Better Comments en el código
- `//!` Encabezados/títulos importantes.
- `?` Teoría o explicación conceptual.
- `*` Consejos y buenas prácticas.
- `TODO` Actividades propuestas para el alumno.
- `NOTE` Notas útiles o consideraciones especiales.

## 🧪 Ejercicios guiados (recomendados)
1) SELECT filtrado por edad
- Muestra sólo usuarios mayores de 18 años. Usa `PreparedStatement` con `WHERE edad >= ?`.

2) INSERT parametrizado desde consola
- Pide al usuario nombre y edad por `Scanner` y ejecuta el `INSERT` con parámetros.

3) UPDATE por id
- Cambia el nombre y/o la edad de un usuario dado su `id`. Muestra filas afectadas.

4) DELETE por id
- Elimina un usuario por `id` y muestra si se encontró o no.

## 🧩 Tareas del alumno (para reforzar)
- TODO: Cambia el nombre del archivo de BD (p. ej. `ut17.db`) y verifica que se crea.
- TODO: Añade validación de entradas (nombre no vacío, edad no negativa).
- TODO: Alinea la consulta de la opción 3 con la tabla `usuarios` (actualmente verás una consulta a `productos`, úsalo como práctica para corregirla y mostrar columnas correctas).
- TODO: Sincroniza el nombre de la clase con el archivo si lo prefieres (opcional para compilar/ejecutar, pero mejora coherencia).

## 🧯 Solución de problemas
- "No suitable driver": asegúrate de tener el JAR del driver en el classpath al ejecutar.
- "ClassNotFoundException: org.sqlite.JDBC": falta el JAR o la ruta del classpath es incorrecta.
- Problemas con `Scanner`: recuerda consumir saltos de línea (`nextLine()`) tras leer números.

## ✅ Criterios de evaluación (rúbrica corta)
- Conecta y crea tablas sin errores (2 pt).
- INSERT/SELECT funcionando y mostrando datos (3 pt).
- UPDATE/DELETE por id con feedback adecuado (3 pt).
- Buen uso de `PreparedStatement` y validaciones básicas (2 pt).

## 🌱 Extensiones opcionales
- Exportar listados a CSV.
- Añadir índices y restricciones (UNIQUE, CHECK).
- Registrar errores en un archivo de log.
