# 📘 UT19 — Maven (guía completa): instalación, `pom.xml` y flujo de trabajo

> Guía práctica para **entender Maven de verdad**: qué es, cómo se instala, cómo funciona el ciclo de vida, cómo se estructura un proyecto y cómo se usan dependencias, plugins, tests y perfiles. Ideal para preparar UT19 (Arquitectura en capas con JDBC + JUnit + Logging).

## 🧭 Temario (ruta de aprendizaje)
1. Qué es Maven y por qué se usa
2. Instalación (Windows) y verificación
3. Estructura estándar de un proyecto Maven
4. El `pom.xml`: coordenadas, dependencias y plugins
5. Ciclo de vida y comandos esenciales (`clean`, `test`, `package`, `install`)
6. Dependencias: scopes, transitivas, exclusiones y versiones
7. Plugins habituales (Compiler, Surefire, Shade/Jar, Enforcer)
8. Recursos y configuración: `src/main/resources`, `application.properties`, `logback.xml`
9. Perfiles (`profiles`) y variables (properties)
10. Repositorios, caché local (`~/.m2`) y `settings.xml`
11. Integración con IDE (IntelliJ/Eclipse/VSCode)
12. Solución de problemas típica
13. Ejercicios guiados + retos

---

## 🎯 Objetivos de aprendizaje
- Entender **qué problemas resuelve Maven** (build + dependencias + estándar de proyecto).
- Saber **instalarlo y comprobarlo** en Windows.
- Aprender a leer y escribir un `pom.xml` con confianza (sin copiar/pegar a ciegas).
- Ejecutar los comandos clave del día a día: `mvn test`, `mvn package`, `mvn install`.
- Resolver errores comunes (JAVA_HOME, versiones, repositorios, proxies).

---

## 🧠 ¿Qué es Maven?
Maven es una herramienta de **build** y **gestión de dependencias** para proyectos Java.

En la práctica, Maven te da:
- Un **estándar de carpetas** (dónde va el código, tests, recursos).
- Un fichero de configuración (`pom.xml`) con:
  - Dependencias (JUnit, Mockito, SLF4J, drivers JDBC, etc.).
  - Plugins (compilar, ejecutar tests, empaquetar).
- Un **ciclo de vida** (pasos predefinidos) para compilar, testear y empaquetar.

Si en UT17/UT18 añadías JARs “a mano”, Maven automatiza eso descargando y versionando librerías desde repositorios.

---

## ✅ Requisitos
- **Java JDK** instalado (recomendado: **Java 17**).
- `JAVA_HOME` configurado (ideal, aunque algunos IDEs lo gestionan).
- Acceso a internet (para descargar dependencias la primera vez).

Comprobar Java:
```powershell
java -version
javac -version
```

---

## 🧰 Instalación de Maven en Windows

### Opción A) Instalación manual (recomendada si no usas gestores)
1. Descarga Maven desde: `https://maven.apache.org/download.cgi`
2. Descomprime, por ejemplo en: `C:\tools\apache-maven-3.x.x\`
3. Añade al `PATH` el directorio `bin`:
   - `C:\tools\apache-maven-3.x.x\bin`
4. Configura `JAVA_HOME` (apuntando al JDK), por ejemplo:
   - `C:\Program Files\Java\jdk-17`

Verificación:
```powershell
where mvn
echo $env:JAVA_HOME
mvn -v
```

Debe mostrar versión de Maven y el Java que está usando.

### Opción B) Chocolatey (si lo tienes instalado)
```powershell
choco install maven -y
mvn -v
```

### Opción C) Scoop (si lo usas)
```powershell
scoop install maven
mvn -v
```

### Extra recomendado) Maven Wrapper (para fijar versión por proyecto)
El *wrapper* añade scripts `mvnw`/`mvnw.cmd` para que el proyecto use una versión concreta de Maven aunque el equipo no la tenga instalada.

Generarlo (dentro del proyecto):
```powershell
mvn -N io.takari:maven:wrapper
.\mvnw.cmd -v
```

---

## 🏗️ Estructura estándar de un proyecto Maven
Maven espera (y los IDEs también) una estructura fija:
```
mi-proyecto/
  pom.xml
  src/
    main/
      java/          # código de producción
      resources/     # ficheros: .properties, .xml, etc.
    test/
      java/          # tests (JUnit)
      resources/
  target/            # salida del build (se genera, NO se versiona)
```

---

## 🧾 El `pom.xml` (lo importante)
El `pom.xml` es el “contrato” del proyecto: identidad + dependencias + plugins.

### 1) Coordenadas (identidad del proyecto)
- `groupId`: el “grupo” (normalmente tu dominio al revés o tu organización).
- `artifactId`: el nombre del proyecto.
- `version`: versión del artefacto.
- `packaging`: `jar` (por defecto) o `war`, etc.

Ejemplo mínimo (para entenderlo):
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.curso</groupId>
  <artifactId>ut19-maven-demo</artifactId>
  <version>1.0.0</version>
  <packaging>jar</packaging>
</project>
```

### 2) Dependencias
Las dependencias van en `<dependencies>`. Maven las descarga y las pone en el classpath automáticamente.

Ejemplos típicos para UT19:
- JUnit 5 (tests)
- Mockito (mocking en tests)
- SLF4J + Logback (logging)
- Driver SQLite (JDBC)

Concepto clave: cada dependencia tiene un `scope`:
- `compile` (por defecto): para compilar y ejecutar el programa.
- `test`: solo para tests.
- `runtime`: solo al ejecutar (no al compilar).

### 3) Plugins
Los plugins ejecutan tareas del build (compilar, testear, empaquetar…).
Los más comunes:
- `maven-compiler-plugin` (versión de Java).
- `maven-surefire-plugin` (tests unitarios).
- `maven-failsafe-plugin` (tests de integración).
- `maven-shade-plugin` (crear “fat jar” con dependencias).

---

## 🔁 Ciclo de vida de Maven (lo que debes memorizar)
Maven trabaja con “fases” (phases). Las principales:
- `clean`: borra `target/` (salida anterior).
- `compile`: compila código de `src/main/java`.
- `test`: ejecuta tests de `src/test/java`.
- `package`: empaqueta (normalmente `jar`) en `target/`.
- `verify`: valida el build (suele usarse con checks/plugins).
- `install`: instala el artefacto en tu repositorio local (`~/.m2`).
- `deploy`: publica en un repositorio remoto (empresa/CI).

Comandos de uso diario:
```powershell
mvn clean
mvn test
mvn package
mvn clean package
mvn -DskipTests package
mvn install
```

Consejos:
- `mvn -q ...` reduce salida (quiet).
- `mvn -X ...` modo debug si algo falla.
- `mvn -U ...` fuerza actualización de snapshots.

---

## 📦 Dependencias (transitivas y conflictos)
Si añades una dependencia, Maven puede traer **dependencias transitivas** (las que esa librería necesita).

Para inspeccionar qué está entrando:
```powershell
mvn dependency:tree
```

Si hay conflicto de versiones, Maven elige una versión (dependiendo del “nearest-wins”).
Soluciones típicas:
- Fijar versión en `dependencyManagement` o en la propia dependencia.
- Excluir una dependencia transitiva con `<exclusions>`.

---

## 🧪 Tests con Maven (JUnit)
- Los tests van en `src/test/java`.
- Maven ejecuta tests con `mvn test`.
- Por convención, Surefire detecta clases tipo:
  - `*Test.java`, `Test*.java`, `*Tests.java`

Para saltar tests (solo en casos puntuales):
```powershell
mvn -DskipTests package
```

---

## 🗂️ Recursos: `src/main/resources`
Aquí van ficheros que tu aplicación necesita en runtime:
- `logback.xml` (configuración logging)
- `.properties`, `.json`, plantillas, etc.

En UT19 (Arquitectura + Logging), lo típico es:
- `src/main/resources/logback.xml`

---

## 🎛️ Profiles (perfiles)
Los perfiles permiten cambiar configuración según entorno (dev/test/prod), por ejemplo:
- Usar una base de datos distinta
- Cambiar nivel de logging
- Activar/desactivar plugins

Activar un perfil:
```powershell
mvn -Pdev test
```

---

## 🧱 Repositorios y caché local (`~/.m2`)
Maven descarga dependencias y las guarda en tu caché local:
- Windows: `C:\Users\<TU_USUARIO>\.m2\repository`

Archivo de configuración (opcional):
- `~/.m2/settings.xml`

Ahí se configura:
- Proxy corporativo
- Mirrors (repositorios alternativos)
- Credenciales (repos privados)

---

## 🧩 Crear un proyecto Maven desde cero (opcional)
Si quieres generar un proyecto plantilla:
```powershell
mvn archetype:generate -DgroupId=com.curso -DartifactId=demo-maven -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

---

## 🧠 Buenas prácticas recomendadas
- No subas `target/` a Git.
- Centraliza versiones con `<properties>` (por ejemplo `java.version`, `junit.version`).
- Fija versiones de plugins importantes (evita builds “que cambian solos”).
- Usa `dependency:tree` cuando “algo raro” aparece en classpath.

---

## 🧯 Solución de problemas (lo más típico)
- `mvn: command not found` → Maven no está en `PATH`.
- `JAVA_HOME is not defined correctly` → `JAVA_HOME` apunta mal (debe ser el JDK).
- Error descargando dependencias → proxy/firewall o repositorio caído; revisa `settings.xml`.
- `Unsupported major.minor version` → estás ejecutando con un Java más viejo que el que compila.
- Tests no se ejecutan → nombre de clase no coincide con patrones (`*Test`).

---

## 🧩 Ejercicios guiados (para UT19)
1) Crear un proyecto Maven básico
- Genera el proyecto (archetype) o crea estructura + `pom.xml`.
- Añade una clase `App` con `main`.

2) Añadir JUnit 5
- Crea `AppTest` en `src/test/java`.
- Ejecuta `mvn test`.

3) Añadir logging (SLF4J + Logback)
- Crea `logback.xml` en `src/main/resources`.
- Loggea eventos en el `main` y en un servicio.

4) Añadir SQLite JDBC
- Crea una clase que abra conexión y ejecute un `SELECT 1`.
- Ejecuta con `mvn test` (si lo metes como test) o `mvn package` + ejecución desde IDE.

---

## 🏁 Siguiente paso (UT19 del curso)
Cuando domines Maven, aplica todo en el proyecto:
- `cursos/Curso java completo/UT19_ArquitecturaCapas_JDBC`

Ahí verás un ejemplo “real” con capas, tests y logging.
