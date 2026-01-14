# 📘 UT0 — Guía completa de instalación y preparación del entorno Java
> Punto de partida del curso. Sigue estos pasos y tendrás todo listo para avanzar por las unidades (UT1…UT20).

---
## 🎯 Objetivo de esta guía
Dejar tu equipo Windows listo para programar en Java usando **Visual Studio Code**, con:
- JDK instalado y funcionando (java / javac).
- VS Code configurado con extensiones esenciales.
- Ejecución de programas en terminal integrada.
- Herramientas futuras: SQLite (JDBC), Maven y Spring Boot.

---
## 🧠 ¿Qué es Java y para qué sirve?
Java es un **lenguaje de programación multiplataforma** ("Write once, run anywhere"), orientado a objetos y muy usado en:
- Aplicaciones empresariales y bancarias.
- Desarrollo web (backends con Spring Boot).
- Android (bases históricas y parte de su SDK).
- Sistemas embebidos y microservicios.
- Big Data (Hadoop, Spark) y procesamiento.

Características clave:
- Gestión automática de memoria (garbage collector).
- Amplio ecosistema (librerías, frameworks, herramientas).
- Enfoque en seguridad y estabilidad.

---
## ✅ Requisitos fundamentales
| Herramienta | ¿Por qué? | Estado tras instalar |
|-------------|-----------|----------------------|
| JDK 17+     | Compilar y ejecutar Java | `java -version`, `javac -version` |
| VS Code     | Editor ligero y extensible | Abrir .java con resaltado |
| Extensión Java | Soporte de compilación, depuración y refactor | Acciones en barra lateral |
| Better Comments | Lectura didáctica del curso | Comentarios coloreados |
| Code Runner (opcional) | Ejecutar rápido demos | Botón ▶️ en editor |
| SQLite JDBC JAR (UT17+) | Acceso a BD embebida | Archivo `.jar` en `libs/` |
| Maven (UT19+) | Gestión de dependencias/proyectos | `mvn -v` |
| Spring Boot (UT20+) | APIs REST y proyectos modernos | Ejecutar `mvn spring-boot:run` |

---
## 1. Instalar el JDK (Windows)
### Opción recomendada: Microsoft Build of OpenJDK o Eclipse Temurin
1. Ve a: https://learn.microsoft.com/java/openjdk/download o https://adoptium.net
2. Descarga el instalador MSI de **Java 17 LTS** (x64).
3. Ejecuta el instalador (marca "Add to PATH" si aparece).
4. Verifica en PowerShell:
```powershell
java -version
javac -version
```
Salida esperada (ejemplo):
```
openjdk version "17.x" ...
```
Si falla:
- Cierra y reabre la terminal (actualiza PATH).
- Comprueba que la carpeta `C:\Program Files\Java\...\bin` está en PATH.

### Comprobar JAVA_HOME (opcional)
```powershell
$env:JAVA_HOME
```
Si está vacío y lo necesitas (para algunas herramientas), crea la variable:
1. Panel de Control → Sistema → Configuración avanzada → Variables de entorno.
2. Nueva variable de usuario: `JAVA_HOME` apuntando a la carpeta raíz del JDK (sin \bin).

---
## 2. Instalar Visual Studio Code
1. Descarga desde https://code.visualstudio.com/
2. Instala aceptando opciones por defecto.
3. Abre VS Code y añade la carpeta del curso (`cursos/Curso java completo`).

---
## 3. Extensiones imprescindibles en VS Code
Abre la vista de extensiones (Ctrl+Shift+X) e instala:
| Extensión | Id / Nombre | Uso |
|-----------|-------------|-----|
| Java Extension Pack | `vscjava.vscode-java-pack` | Incluye soporte Java, debugger, test runner, Maven, project manager |
| Better Comments | `aaron-bond.better-comments` | Colores para comentarios //!, ?, *, TODO, NOTE |
| Code Runner (opcional) | `formulahendry.code-runner` | Ejecutar archivos rápidamente |
| Thunder Client (opcional) | `rangav.vscode-thunder-client` | Probar APIs REST (UT20) |
| EditorConfig (opcional) | `EditorConfig.EditorConfig` | Consistencia de estilo |

### Configurar Code Runner para Scanner
En ajustes (Ctrl+,):
- Busca `code-runner.runInTerminal` y actívalo.
- Desactiva `code-runner.clearPreviousOutput` si quieres ver historial.

---
## 4. Primera ejecución (Hello World)
Crea o abre `UT0_IntroduccionJava.java` y asegúrate que contiene un `main`. Ejecuta con:
- Botón ▶️ (Code Runner), o
- Terminal:
```powershell
javac UT0_IntroduccionJava.java
java UT0_IntroduccionJava
```
Si ves la salida esperada, tu entorno funciona.

---
## 5. Organización de carpetas del curso
Estructura base (resumida):
```
Curso java completo/
  UT0_IntroduccionJava.java
  UT1_VariablesTiposOperadores.java
  ...
  UT16_ProyectoFinal.java
  UT17_JavaSQL_Basico_Sqlite.java
  UT18_JavaSQL_Intermedio_Sqlite.java
  UT19_ArquitecturaCapas_JDBC/ (Maven)
  UT20_SpringBoot_API_REST_JPA/ (Spring Boot)
  📘_GUÍA_DEL_CURSO.md
  📘_UT17_JavaSQL_Basico_Sqlite.md
  📘_UT18_JavaSQL_Intermedio_Sqlite.md
  📘_UT19_ArquitecturaCapas_JDBC_JUnit_Maven_Logging.md
  📘_UT20_SpringBoot_API_REST_JPA.md
```
Mantén esta estructura para que las guías y ejemplos coincidan.

---
## 6. Añadir el driver SQLite (a partir de UT17)
Para prácticas JDBC (UT17–UT18):
1. Crea carpeta `libs/` en la raíz del curso si no existe.
2. Descarga el JAR desde: https://github.com/xerial/sqlite-jdbc/releases
3. Copia p.ej. `sqlite-jdbc-3.36.0.3.jar` dentro de `libs/`.
4. Ejecuta unido al classpath:
```powershell
javac UT18_JavaSQL_Intermedio_Sqlite.java
java -cp ".;libs\sqlite-jdbc-3.36.0.3.jar" UT18_JavaSQL_Intermedio_Sqlite
```
> Nota: En Linux/Mac usa `:` en vez de `;` para separar rutas.

---
## 7. Instalar Maven (para UT19 y UT20)
1. Descarga Maven: https://maven.apache.org/download.cgi (zip binario).
2. Descomprime en `C:\Dev\maven` (por ejemplo).
3. Añade `C:\Dev\maven\bin` al PATH.
4. Verifica:
```powershell
mvn -v
```
Salida esperada incluye versión de Maven y Java.

### Usar Maven en el curso
- UT19: `mvn test` para pruebas, ejecutar `Application` desde IDE.
- UT20: `mvn spring-boot:run` para levantar la API.

---
## 8. Spring Boot (UT20 introducción)
No requiere instalación aparte: Maven descarga dependencias.
Pasos:
```powershell
cd UT20_SpringBoot_API_REST_JPA
mvn spring-boot:run
```
Visita:
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Consola H2 (si activas): http://localhost:8080/h2-console

Si puerto ocupado:
- Añade en `application.properties`: `server.port=8081`

---
## 9. Verificación rápida del entorno (checklist)
| Comando | Esperado |
|---------|----------|
| `java -version` | Versión JDK mostrada |
| `javac -version` | Compilador activo |
| `mvn -v` | Maven detectado (UT19/20) |
| Compilar UT5 | Sin errores |
| Ejecutar UT17 con JAR | Crea/abre BD SQLite |
| Levantar UT20 | API activa con Swagger |

---
## 10. Problemas frecuentes y solución
| Problema | Causa | Solución |
|----------|-------|----------|
| `java` no se reconoce | PATH mal configurado | Reinstala JDK o añade carpeta /bin al PATH |
| Error con SQLite driver | JAR ausente | Verifica ruta y nombre en classpath |
| `ClassNotFoundException` | Clase no compilada o mal nombre | Comprueba coincidencia de archivo y clase pública |
| Maven fuera de PATH | Instalación incompleta | Añade carpeta `maven/bin` a PATH y reinicia terminal |
| Puerto 8080 ocupado (Spring) | Otro servicio activo | Cambia `server.port` en properties |
| FKs no funcionan (UT18) | PRAGMA no activado | Ejecuta "PRAGMA foreign_keys=ON" tras conectar |
| Scanner no espera entrada | Ejecución fuera de terminal | Activa `runInTerminal` en Code Runner |

---
## 11. Estilo de comentarios del curso (Better Comments)
Ejemplo dentro de código:
```java
//! TÍTULO IMPORTANTE
// ? Teoría explicando el concepto
// * Buenas prácticas o consejo
// TODO (Alumno): tarea a realizar
// NOTE: Nota adicional o matiz
```
No borres estas marcas: son parte del material didáctico.

---
## 12. Roadmap (qué aprenderás paso a paso)
1. Sintaxis básica y tipos
2. Control de flujo y funciones
3. POO (clases, objetos, encapsulación)
4. Excepciones y manejo de errores
5. Colecciones y cadenas avanzadas
6. Ficheros, fechas y organización modular
7. Proyecto final (menú + lógica + persistencia)
8. JDBC básico → Intermedio (SQLite)
9. Arquitectura por capas (Maven + tests + logging)
10. API REST profesional con Spring Boot

---
## 13. Próximos pasos tras esta guía
- Abre `📘_GUÍA_DEL_CURSO.md` para el mapa total.
- Empieza con `UT0_IntroduccionJava.java`.
- Sigue las UT en orden hasta UT16 (proyecto final).
- Después: UT17–UT20 para base de datos, arquitectura y APIs.

---
## 14. Glosario rápido
| Concepto | Significado corto |
|----------|-------------------|
| JDK | Java Development Kit (herramientas + JVM) |
| JVM | Máquina Virtual Java, ejecuta bytecode |
| JDBC | API para bases de datos relacionales |
| Maven | Gestor de dependencias y build tool |
| JAR | Archivo empaquetado Java (librería o aplicación) |
| API REST | Interfaz HTTP para servicios web |
| DTO | Objeto de transferencia de datos |
| FK | Foreign Key (clave foránea) |
| CRUD | Create, Read, Update, Delete |

---
## 15. Checklist final (confirmación de entorno listo)
Marca cada punto:
- [ ] JDK instalado y verificado
- [ ] VS Code abierto en carpeta del curso
- [ ] Extensiones instaladas (Java Pack, Better Comments)
- [ ] Code Runner configurado en terminal (si lo usas)
- [ ] SQLite JAR descargado en `libs/` (para UT17+)
- [ ] Maven funcional (UT19/UT20)
- [ ] Spring Boot iniciado una vez (UT20)

---
> ✅ Con esto tu entorno está listo. Ya puedes avanzar por las unidades y construir desde los fundamentos hasta aplicaciones con BD y APIs.
> 🚀 ¡Empieza ahora y vuelve a esta guía si necesitas repasar instalación!
