# Cuaderno de ejercicios — Proyecto Final Basico (Agenda de Citas)

Estos ejercicios estan pensados para reforzar poco a poco el proyecto. La idea es que puedas completarlos incluso si es tu primer proyecto grande en Java.

---

## Nivel 1 — Modelo y repositorios

1. **Repasar modelos**
   - Abre `Cliente` y `Cita` en el paquete `model`.
   - Escribe con tus palabras que representa cada campo y por que es `String`, `LocalDate` o `enum`.

2. **CSV de clientes**
   - Abre `ClienteRepository`.
   - Identifica donde se lee el fichero `clientes.csv` y donde se escribe.
   - Cambia el orden de las columnas para que el telefono vaya antes del email (ajusta lectura y escritura).

3. **CSV de citas**
   - Abre `CitaRepository`.
   - Añade un campo nuevo `duracionMinutos` en `Cita` y haz que se guarde/cargue en el CSV.

---

## Nivel 2 — Controladores y reglas de negocio

4. **Validar telefono**
   - En `ClienteController.crear(...)`, usa `Validator.requireNotBlank` para obligar a que el telefono no sea vacio.
   - Si quieres, añade una validacion sencilla de longitud minima (por ejemplo, >= 9 caracteres).

5. **Evitar citas en el pasado**
   - En `CitaController.crear(...)`, impide crear citas para fechas anteriores a la fecha actual.
   - Lanza `ValidationException` con un mensaje claro si se incumple esta regla.

6. **Marcar como cancelada**
   - Añade un metodo `cancelar(String citaId)` en `CitaController`.
   - Cambia el estado de la cita a `CANCELADA` y guarda los cambios.

---

## Nivel 3 — Vista de consola y menus

7. **Confirmar borrado**
   - Crea en `ConsoleView` un metodo `confirm(String pregunta)` que devuelva `true` o `false`.
   - Usalo en `Application` antes de borrar clientes o citas.

8. **Mejorar listados**
   - En los metodos que listan clientes y citas, mejora el `toString` de los modelos para que el resultado sea mas legible.
   - Opcional: implementa un pequeño metodo en `ConsoleView` para mostrar una “tabla” con columnas alineadas.

9. **Filtrar citas**
   - Añade una opcion de menu para listar solo citas pendientes.
   - Implementa un metodo `listarPendientes()` en `CitaController` que devuelva solo las que tienen estado `PENDIENTE`.

---

## Nivel 4 — Extras (opcional)

10. **Exportar resumen**
    - Crea un metodo que exporte un resumen de clientes y citas a un fichero `resources/data/resumen.txt`.
    - Incluye: numero total de clientes, numero de citas pendientes y realizadas.

11. **Tests unitarios basicos**
    - Añade una clase de pruebas (JUnit 5) para `Validator` y `DateUtils`.
    - Crea casos sencillos: email valido/invalido, fechas mal formateadas, etc.

12. **Refactor y paquetes**
    - Compara este proyecto basico con el proyecto completo `proyecto-final`.
    - Anota en un comentario las principales diferencias de arquitectura y complejidad.

---

## Consejos de trabajo

- Marca en el codigo los TODO que vayas completando con comentarios `// DONE:` para seguir tu progreso.
- Compila frecuentemente con `build.bat` y prueba cada cambio con `run.bat`.
- Si algo se rompe, vuelve al ultimo cambio que hiciste, revisa y corrige antes de seguir.

