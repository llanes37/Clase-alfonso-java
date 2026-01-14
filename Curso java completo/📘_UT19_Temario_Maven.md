# 📘 UT19 — Temario: Maven (instalación y uso real)

> Temario resumido de la unidad UT19 sobre Maven. Para la explicación completa, usa: `cursos/Curso java completo/📘_UT19_Maven_Guia_Completa.md`

## 🎯 Resultados de aprendizaje
- Instalar Maven y validar la configuración (`JAVA_HOME`, `PATH`, `mvn -v`).
- Comprender la estructura estándar de un proyecto Maven.
- Leer y mantener un `pom.xml` (coordenadas, dependencias, plugins).
- Ejecutar el ciclo de vida: `clean`, `test`, `package`, `install`.
- Diagnosticar problemas típicos de dependencias y repositorios (`.m2`, `settings.xml`).

## 🧭 Contenidos (temario)
1. ¿Qué es Maven? (build, dependencias, estándar)
2. Instalación en Windows (manual / Chocolatey / Scoop)
3. Estructura del proyecto (`src/main/java`, `src/test/java`, `resources`, `target`)
4. `pom.xml`:
   - `groupId`, `artifactId`, `version`, `packaging`
   - Dependencias y `scope` (compile/test/runtime)
   - Plugins (Compiler, Surefire)
5. Ciclo de vida y comandos esenciales
6. Dependencias transitivas, conflictos, `dependency:tree` y exclusiones
7. Tests con JUnit 5 (convenciones de nombres)
8. Recursos (`logback.xml`, `.properties`) y classpath
9. Perfiles (`-P`) y properties
10. Repositorios: Maven Central, caché local (`.m2`) y `settings.xml`
11. Integración con IDE y flujo de trabajo diario
12. Troubleshooting: `JAVA_HOME`, proxies, versiones de Java, tests no detectados

## 🧪 Actividades propuestas
- Crear proyecto Maven mínimo y compilarlo.
- Añadir JUnit 5 + primer test y ejecutar `mvn test`.
- Añadir logging (SLF4J + Logback) y cargar `logback.xml` desde `resources`.
- Añadir SQLite JDBC y hacer una conexión simple.

## ✅ Criterios de evaluación (rúbrica rápida)
- Maven instalado y verificado correctamente (2).
- Proyecto estructura Maven + `pom.xml` coherente (3).
- Tests ejecutan con `mvn test` (3).
- Uso de `dependency:tree` y resolución de un conflicto (2).

