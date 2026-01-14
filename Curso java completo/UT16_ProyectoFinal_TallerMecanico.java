/*******************************************************************************************
 *  CURSO DE PROGRAMACION EN JAVA - (uso educativo)
 *  UNIDAD 16: PROYECTO FINAL (CONSOLA)
 *
 *  PROYECTO: GESTION DE TALLER MECANICO (CRUD + COLECCIONES + CSV)
 *
 *  Recomendacion para clase:
 *  - Compila y ejecuta ESTE archivo por separado (es un mini-proyecto completo en 1 .java).
 *  - Guarda el archivo en UTF-8 para ver bien las tildes.
 *******************************************************************************************/

/*
 *******************************************************************************************
 * * OBJETIVO DIDACTICO (formato "Better Comments")
 * ? Que vas a practicar con este proyecto?
 *   - POO realista: clases con responsabilidades claras (modelo + servicio + app).
 *   - Encapsulacion: atributos privados + validaciones en setters/constructores.
 *   - Colecciones: Map para busquedas rapidas por ID (clientes, vehiculos, ordenes).
 *   - Enums: estados y tipos controlados (evitan "strings magicos").
 *   - Persistencia simple: lectura/escritura CSV sin librerias externas.
 *   - Menu de consola: entradas validadas para evitar errores del usuario.
 *
 * ! Nota importante
 *   En proyectos reales separarias en paquetes y archivos. Aqui lo dejamos en un solo archivo
 *   para que sea facil de estudiar y entregar en clase.
 *
 * TODO Retos para el alumnado (al final del archivo) con ideas de ampliacion.
 *******************************************************************************************
 */

/*
 *******************************************************************************************
 * * MAPA RAPIDO DEL PROYECTO (para que el alumno se ubique)
 * ? Como esta organizado este archivo?
 *   1) Enums              -> Tipos cerrados (TipoCliente, TipoVehiculo, EstadoOrden)
 *   2) Modelos (POO)      -> Cliente, Vehiculo, LineaTrabajo, OrdenReparacion
 *   3) Servicio (logica)  -> TallerManager (reglas + colecciones + CSV)
 *   4) Utilidad consola   -> ConsoleIO (leer seguro: int/double/fecha/texto)
 *   5) App (menus)        -> UT16_ProyectoFinal_TallerMecanico (main)
 *
 * ? Que ficheros CSV se crean al ejecutar?
 *   - taller_clientes.csv   (clientes)
 *   - taller_vehiculos.csv  (vehiculos)
 *   - taller_ordenes.csv    (ordenes)
 *   - taller_lineas.csv     (lineas de trabajo por orden)
 *
 * ! Importante sobre los IDs
 *   - Cliente: usamos un ID textual (DNI/NIE/CIF) -> clave natural.
 *   - Vehiculo: usamos MATRICULA -> clave natural.
 *   - Orden: usamos UUID (generado) -> clave tecnica, siempre unica.
 *
 * ? Como se relacionan las clases?
 *   Cliente 1 ---- * Vehiculo  (por idCliente)
 *   Vehiculo 1 ---- * Orden     (por matricula)
 *   Orden   1 ---- * LineaTrabajo (lista dentro de la orden)
 *******************************************************************************************
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

// =========================================================================================
// * TEORIA: ENUMS (valores cerrados, seguros y autocompletables)
// -----------------------------------------------------------------------------------------
// En vez de escribir "EN_PROCESO" como String (y equivocarnos con "ENPROCESO", "proceso"...),
// lo representamos como un enum. Asi Java nos ayuda a no cometer errores.
//
// ? Tipico en proyectos reales:
//   - EstadoOrden define el "ciclo de vida" de una orden.
//   - TipoVehiculo ayuda a filtrar o aplicar reglas (ej: tiempos distintos por tipo).
//
// ! Ventaja clave
//   Si escribes EstadoOrden.ENTREGADA, el IDE autocompleta y no hay typos.
// =========================================================================================
enum TipoCliente { PARTICULAR, EMPRESA }
enum TipoVehiculo { COCHE, MOTO, FURGONETA }
enum EstadoOrden { ABIERTA, EN_PROCESO, FINALIZADA, ENTREGADA, CANCELADA }

// =========================================================================================
// * MODELO: Cliente
// -----------------------------------------------------------------------------------------
// Buenas practicas:
// - Atributos privados
// - Validacion en setters
// - toString() para mostrarlo en consola
// =========================================================================================
class Cliente {
    private String id;        // Ej: DNI, NIE, CIF, email... (lo tratamos como String)
    private String nombre;    // Ej: "Ana Lopez"
    private String telefono;  // Ej: "600123123"
    private TipoCliente tipo; // PARTICULAR o EMPRESA

    public Cliente(String id, String nombre, String telefono, TipoCliente tipo) {
        // * Patron didactico:
        //   En vez de asignar directamente (this.id = id), llamamos a setters para reutilizar
        //   las validaciones. Asi mantenemos el objeto SIEMPRE valido.
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipo(tipo);
    }

    public String getId() { return id; }
    public void setId(String id) {
        // ! Validacion minima (se puede mejorar con regex si el profe lo pide)
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID de cliente vacio");
        this.id = id.trim().toUpperCase(Locale.ROOT);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre vacio");
        this.nombre = nombre.trim();
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) {
        // ? Por que no validamos solo numeros?
        //   Para no complicar el ejercicio: hay telefonos con prefijos, espacios, etc.
        //   En un reto final puedes aplicar una expresion regular.
        if (telefono == null || telefono.isBlank()) throw new IllegalArgumentException("Telefono vacio");
        this.telefono = telefono.trim();
    }

    public TipoCliente getTipo() { return tipo; }
    public void setTipo(TipoCliente tipo) {
        this.tipo = (tipo == null) ? TipoCliente.PARTICULAR : tipo;
    }

    // * CSV (persistencia simple)
    // ! Para simplificar, cambiamos ';' por ',' en textos.
    public String toCsv() {
        // * CSV (Separado por ';')
        // ? Por que ';' y no ','?
        //   Porque en España el decimal suele usar ',' y evita confusiones si exportas precios.
        return String.format(Locale.ROOT, "%s;%s;%s;%s",
                id,
                nombre.replace(';', ','),
                telefono.replace(';', ','),
                tipo.name()
        );
    }

    public static Cliente fromCsv(String line) {
        String[] p = line.split(";");
        if (p.length < 4) throw new IllegalArgumentException("Linea CSV invalida (Cliente): " + line);
        return new Cliente(p[0], p[1], p[2], TipoCliente.valueOf(p[3]));
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Cliente[%s] %-18s | %-9s | %-10s", id, nombre, telefono, tipo);
    }
}

// =========================================================================================
// * MODELO: Vehiculo
// -----------------------------------------------------------------------------------------
// Relacion:
// - Un vehiculo pertenece a un cliente (idCliente).
// - Usamos la matricula como identificador (clave natural).
// =========================================================================================
class Vehiculo {
    private String matricula;   // Ej: "1234ABC" (depende de pais; aqui validamos solo "no vacio")
    private String marca;       // Ej: "Seat"
    private String modelo;      // Ej: "Ibiza"
    private TipoVehiculo tipo;  // COCHE, MOTO, FURGONETA
    private String idCliente;   // Referencia a Cliente.id

    public Vehiculo(String matricula, String marca, String modelo, TipoVehiculo tipo, String idCliente) {
        // * Igual que en Cliente: centralizamos validaciones en setters
        setMatricula(matricula);
        setMarca(marca);
        setModelo(modelo);
        setTipo(tipo);
        setIdCliente(idCliente);
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) {
        // ! La matricula se usa como "clave" en el Map, por eso la normalizamos (trim + MAYUS).
        if (matricula == null || matricula.isBlank()) throw new IllegalArgumentException("Matricula vacia");
        this.matricula = matricula.trim().toUpperCase(Locale.ROOT);
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) {
        if (marca == null || marca.isBlank()) throw new IllegalArgumentException("Marca vacia");
        this.marca = marca.trim();
    }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) throw new IllegalArgumentException("Modelo vacio");
        this.modelo = modelo.trim();
    }

    public TipoVehiculo getTipo() { return tipo; }
    public void setTipo(TipoVehiculo tipo) {
        this.tipo = (tipo == null) ? TipoVehiculo.COCHE : tipo;
    }

    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) {
        // ? Por que guardamos idCliente y no un objeto Cliente?
        //   - Para simplificar persistencia (CSV): guardamos IDs, no objetos completos.
        //   - En proyectos grandes podrias guardar referencias/objetos (o usar una BD).
        if (idCliente == null || idCliente.isBlank()) throw new IllegalArgumentException("ID cliente vacio");
        this.idCliente = idCliente.trim().toUpperCase(Locale.ROOT);
    }

    public String toCsv() {
        return String.format(Locale.ROOT, "%s;%s;%s;%s;%s",
                matricula,
                marca.replace(';', ','),
                modelo.replace(';', ','),
                tipo.name(),
                idCliente
        );
    }

    public static Vehiculo fromCsv(String line) {
        String[] p = line.split(";");
        if (p.length < 5) throw new IllegalArgumentException("Linea CSV invalida (Vehiculo): " + line);
        return new Vehiculo(p[0], p[1], p[2], TipoVehiculo.valueOf(p[3]), p[4]);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Vehiculo[%s] %-10s %-12s | %-10s | Cliente: %s",
                matricula, marca, modelo, tipo, idCliente);
    }
}

// =========================================================================================
// * MODELO: LineaTrabajo (una linea de factura / mano de obra / pieza)
// -----------------------------------------------------------------------------------------
// Ejemplos:
// - "Cambio de aceite" -> 49.99
// - "Filtro de aire"   -> 12.50
// =========================================================================================
class LineaTrabajo {
    private String concepto;
    private double precio;

    public LineaTrabajo(String concepto, double precio) {
        setConcepto(concepto);
        setPrecio(precio);
    }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) {
        if (concepto == null || concepto.isBlank()) throw new IllegalArgumentException("Concepto vacio");
        this.concepto = concepto.trim();
    }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) {
        // ! Nunca permitimos precios negativos (regla simple de negocio)
        if (precio < 0) throw new IllegalArgumentException("Precio negativo");
        this.precio = precio;
    }

    public String toCsv(String idOrden) {
        return String.format(Locale.ROOT, "%s;%s;%.2f",
                idOrden,
                concepto.replace(';', ','),
                precio
        );
    }

    public static LineaTrabajo fromCsv(String line) {
        // ! Ojo: este fromCsv NO conoce el idOrden (se gestiona fuera).
        //   El formato real de la linea es: idOrden;concepto;precio
        //   Pero aqui devolvemos SOLO la LineaTrabajo (concepto+precio).
        String[] p = line.split(";");
        if (p.length < 3) throw new IllegalArgumentException("Linea CSV invalida (LineaTrabajo): " + line);
        double precio = Double.parseDouble(p[2]);
        return new LineaTrabajo(p[1], precio);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, " - %-35s %8.2f EUR", concepto, precio);
    }
}

// =========================================================================================
// * MODELO: OrdenReparacion (la "pieza central" del taller)
// -----------------------------------------------------------------------------------------
// Una orden relaciona:
// - un vehiculo (matricula)
// - una fecha de entrada
// - un estado
// - un diagnostico inicial
// - una lista de trabajos/lineas con precio
//
// * Nota: para ser didacticos, usamos LocalDate (sin horas).
// =========================================================================================
class OrdenReparacion {
    private String id;              // UUID
    private String matricula;       // Vehiculo.matricula
    private LocalDate fechaEntrada; // yyyy-MM-dd
    private LocalDate fechaCierre;  // puede ser null
    private EstadoOrden estado;
    private String diagnostico;
    private final List<LineaTrabajo> lineas = new ArrayList<>();

    public OrdenReparacion(String id, String matricula, LocalDate fechaEntrada, String diagnostico) {
        // * Constructor: crea una orden en estado ABIERTA por defecto
        setId(id);
        setMatricula(matricula);
        setFechaEntrada(fechaEntrada);
        setDiagnostico(diagnostico);
        this.estado = EstadoOrden.ABIERTA;
    }

    public String getId() { return id; }
    public void setId(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID orden vacio");
        this.id = id.trim();
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()) throw new IllegalArgumentException("Matricula vacia");
        this.matricula = matricula.trim().toUpperCase(Locale.ROOT);
    }

    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(LocalDate fechaEntrada) {
        // ! LocalDate evita errores de formato y permite validaciones mas adelante
        this.fechaEntrada = Objects.requireNonNull(fechaEntrada, "Fecha entrada null");
    }

    public LocalDate getFechaCierre() { return fechaCierre; }
    public void setFechaCierre(LocalDate fechaCierre) { this.fechaCierre = fechaCierre; }

    public EstadoOrden getEstado() { return estado; }
    public void setEstado(EstadoOrden estado) {
        this.estado = (estado == null) ? EstadoOrden.ABIERTA : estado;
    }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) {
        if (diagnostico == null || diagnostico.isBlank()) throw new IllegalArgumentException("Diagnostico vacio");
        this.diagnostico = diagnostico.trim();
    }

    public List<LineaTrabajo> getLineas() { return lineas; }

    // * Comportamiento del modelo: agregar una linea
    public void agregarLinea(LineaTrabajo linea) {
        // ? Por que una lista dentro de la orden?
        //   Porque una orden tiene muchas lineas (mano de obra + piezas).
        lineas.add(Objects.requireNonNull(linea, "Linea null"));
    }

    // * Calculo del total (suma de lineas)
    public double total() {
        // * Recorremos la lista y sumamos precios (bucle clasico para alumnos)
        double sum = 0;
        for (LineaTrabajo l : lineas) sum += l.getPrecio();
        return sum;
    }

    public String toCsv(DateTimeFormatter fmt) {
        // fechaCierre puede ir vacia
        String cierre = (fechaCierre == null) ? "" : fechaCierre.format(fmt);
        return String.format(Locale.ROOT, "%s;%s;%s;%s;%s;%s",
                id,
                matricula,
                fechaEntrada.format(fmt),
                cierre,
                estado.name(),
                diagnostico.replace(';', ',')
        );
    }

    public static OrdenReparacion fromCsv(String line, DateTimeFormatter fmt) {
        String[] p = line.split(";");
        if (p.length < 6) throw new IllegalArgumentException("Linea CSV invalida (Orden): " + line);

        String id = p[0];
        String matricula = p[1];
        LocalDate entrada = LocalDate.parse(p[2], fmt);
        String cierreTxt = p[3];
        EstadoOrden estado = EstadoOrden.valueOf(p[4]);
        String diagnostico = p[5];

        OrdenReparacion o = new OrdenReparacion(id, matricula, entrada, diagnostico);
        o.setEstado(estado);
        if (!cierreTxt.isBlank()) o.setFechaCierre(LocalDate.parse(cierreTxt, fmt));
        return o;
    }

    @Override
    public String toString() {
        String cierre = (fechaCierre == null) ? "-" : fechaCierre.toString();
        return String.format(Locale.ROOT,
                "Orden[%s] Matricula: %s | Entrada: %s | Cierre: %s | %-11s | Lineas: %d | Total: %.2f EUR",
                id.substring(0, 8), matricula, fechaEntrada, cierre, estado, lineas.size(), total());
    }
}

// =========================================================================================
// * SERVICIO: TallerManager (logica de negocio + colecciones)
// -----------------------------------------------------------------------------------------
// Este "manager" es el corazon del proyecto:
// - Mantiene las colecciones en memoria
// - Aplica reglas (validaciones, relaciones, estados)
// - Se encarga de cargar/guardar CSV
// =========================================================================================
class TallerManager {
    // * Usamos LinkedHashMap para mantener el orden de insercion al listar (mas "bonito").
    // ? Por que Map y no List?
    //   - Con Map buscamos por clave en tiempo muy rapido (aprox O(1)).
    //   - Ejemplo: clientes.get("12345678A") es directo, sin recorrer toda una lista.
    // ! Claves que usamos:
    //   - clientes:  idCliente (DNI/NIE/CIF)
    //   - vehiculos: matricula
    //   - ordenes:   idOrden (UUID)
    private final Map<String, Cliente> clientes = new LinkedHashMap<>();
    private final Map<String, Vehiculo> vehiculos = new LinkedHashMap<>();
    private final Map<String, OrdenReparacion> ordenes = new LinkedHashMap<>();

    // * Persistencia (4 CSV para mantenerlo simple)
    private final Path baseDir;
    private final Path clientesCsv;
    private final Path vehiculosCsv;
    private final Path ordenesCsv;
    private final Path lineasCsv;

    private final DateTimeFormatter dateFmt = DateTimeFormatter.ISO_LOCAL_DATE;

    public TallerManager(Path baseDir) {
        this.baseDir = Objects.requireNonNull(baseDir, "baseDir null");
        this.clientesCsv = baseDir.resolve("taller_clientes.csv");
        this.vehiculosCsv = baseDir.resolve("taller_vehiculos.csv");
        this.ordenesCsv = baseDir.resolve("taller_ordenes.csv");
        this.lineasCsv = baseDir.resolve("taller_lineas.csv");
    }

    // -------------------------------------------------------------------------------------
    // * CRUD Clientes
    // -------------------------------------------------------------------------------------
    public void agregarCliente(Cliente c) {
        Objects.requireNonNull(c, "cliente null");
        if (clientes.containsKey(c.getId())) throw new IllegalArgumentException("Ya existe un cliente con ID: " + c.getId());
        clientes.put(c.getId(), c);
    }

    public boolean eliminarCliente(String idCliente) {
        idCliente = normalizarId(idCliente);
        if (!clientes.containsKey(idCliente)) return false;

        // ! Regla de negocio: no se borra un cliente si tiene vehiculos asociados.
        for (Vehiculo v : vehiculos.values()) {
            if (v.getIdCliente().equals(idCliente)) {
                throw new IllegalStateException("No se puede eliminar: el cliente tiene vehiculos registrados");
            }
        }

        clientes.remove(idCliente);
        return true;
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes.values());
    }

    public Cliente buscarCliente(String idCliente) {
        return clientes.get(normalizarId(idCliente));
    }

    // -------------------------------------------------------------------------------------
    // * CRUD Vehiculos
    // -------------------------------------------------------------------------------------
    public void agregarVehiculo(Vehiculo v) {
        Objects.requireNonNull(v, "vehiculo null");

        // ? Por que comprobamos la existencia del cliente?
        //   Para evitar datos "huerfanos" (vehiculos apuntando a clientes que no existen).
        if (!clientes.containsKey(v.getIdCliente())) throw new IllegalArgumentException("No existe el cliente: " + v.getIdCliente());
        if (vehiculos.containsKey(v.getMatricula())) throw new IllegalArgumentException("Ya existe el vehiculo: " + v.getMatricula());
        vehiculos.put(v.getMatricula(), v);
    }

    public boolean eliminarVehiculo(String matricula) {
        matricula = normalizarId(matricula);
        if (!vehiculos.containsKey(matricula)) return false;

        // ! Regla de negocio: no se borra un vehiculo si tiene ordenes no entregadas/canceladas.
        for (OrdenReparacion o : ordenes.values()) {
            boolean mismaMatricula = o.getMatricula().equals(matricula);
            boolean bloquea = o.getEstado() != EstadoOrden.ENTREGADA && o.getEstado() != EstadoOrden.CANCELADA;
            if (mismaMatricula && bloquea) {
                throw new IllegalStateException("No se puede eliminar: hay ordenes activas para este vehiculo");
            }
        }

        vehiculos.remove(matricula);
        return true;
    }

    public List<Vehiculo> listarVehiculos() {
        return new ArrayList<>(vehiculos.values());
    }

    public Vehiculo buscarVehiculo(String matricula) {
        return vehiculos.get(normalizarId(matricula));
    }

    // -------------------------------------------------------------------------------------
    // * Operaciones Ordenes
    // -------------------------------------------------------------------------------------
    public OrdenReparacion crearOrden(String matricula, LocalDate fechaEntrada, String diagnostico) {
        matricula = normalizarId(matricula);
        if (!vehiculos.containsKey(matricula)) throw new IllegalArgumentException("No existe el vehiculo: " + matricula);

        // * UUID: identificador unico sin depender de una BD
        String id = UUID.randomUUID().toString();
        OrdenReparacion o = new OrdenReparacion(id, matricula, fechaEntrada, diagnostico);
        ordenes.put(o.getId(), o);
        return o;
    }

    public OrdenReparacion buscarOrden(String idOrden) {
        if (idOrden == null) return null;
        return ordenes.get(idOrden.trim());
    }

    public List<OrdenReparacion> listarOrdenes() {
        return new ArrayList<>(ordenes.values());
    }

    public void cambiarEstado(String idOrden, EstadoOrden nuevoEstado) {
        OrdenReparacion o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("No existe la orden: " + idOrden);

        // ! Regla simple de transiciones (didactica)
        //   ABIERTA -> EN_PROCESO -> FINALIZADA -> ENTREGADA
        //   ABIERTA/EN_PROCESO/FINALIZADA -> CANCELADA
        //   ENTREGADA y CANCELADA son estados finales
        EstadoOrden actual = o.getEstado();
        if (actual == EstadoOrden.ENTREGADA || actual == EstadoOrden.CANCELADA) {
            throw new IllegalStateException("La orden ya esta cerrada en estado final: " + actual);
        }

        boolean transicionValida =
                (actual == EstadoOrden.ABIERTA && (nuevoEstado == EstadoOrden.EN_PROCESO || nuevoEstado == EstadoOrden.CANCELADA)) ||
                (actual == EstadoOrden.EN_PROCESO && (nuevoEstado == EstadoOrden.FINALIZADA || nuevoEstado == EstadoOrden.CANCELADA)) ||
                (actual == EstadoOrden.FINALIZADA && (nuevoEstado == EstadoOrden.ENTREGADA || nuevoEstado == EstadoOrden.CANCELADA));

        if (!transicionValida) throw new IllegalStateException("Transicion no valida: " + actual + " -> " + nuevoEstado);

        o.setEstado(nuevoEstado);
        if (nuevoEstado == EstadoOrden.ENTREGADA || nuevoEstado == EstadoOrden.CANCELADA) {
            o.setFechaCierre(LocalDate.now());
        }
    }

    public void agregarLineaAOrden(String idOrden, LineaTrabajo linea) {
        OrdenReparacion o = buscarOrden(idOrden);
        if (o == null) throw new IllegalArgumentException("No existe la orden: " + idOrden);

        // ! Solo dejamos agregar lineas si la orden no esta cerrada
        if (o.getEstado() == EstadoOrden.ENTREGADA || o.getEstado() == EstadoOrden.CANCELADA) {
            throw new IllegalStateException("No se pueden agregar lineas a una orden cerrada");
        }

        o.agregarLinea(linea);
    }

    // -------------------------------------------------------------------------------------
    // * Persistencia CSV (cargar/guardar todo)
    // -------------------------------------------------------------------------------------
    public void cargar() throws IOException {
        Files.createDirectories(baseDir);
        // * Orden importante:
        //   1) Clientes  -> los vehiculos dependen del cliente
        //   2) Vehiculos -> las ordenes dependen del vehiculo
        //   3) Ordenes   -> las lineas dependen de la orden
        //   4) Lineas
        cargarClientes();
        cargarVehiculos();
        cargarOrdenes();
        cargarLineas();
    }

    public void guardar() throws IOException {
        Files.createDirectories(baseDir);
        guardarClientes();
        guardarVehiculos();
        guardarOrdenes();
        guardarLineas();
    }

    private void cargarClientes() throws IOException {
        clientes.clear();
        if (!Files.exists(clientesCsv)) return;
        try (BufferedReader br = Files.newBufferedReader(clientesCsv, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                Cliente c = Cliente.fromCsv(line);
                clientes.put(c.getId(), c);
            }
        }
    }

    private void cargarVehiculos() throws IOException {
        vehiculos.clear();
        if (!Files.exists(vehiculosCsv)) return;
        try (BufferedReader br = Files.newBufferedReader(vehiculosCsv, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                Vehiculo v = Vehiculo.fromCsv(line);

                // ! Si falta el cliente (datos inconsistentes), NO reventamos la carga:
                //   simplemente ignoramos el vehiculo para que el programa pueda arrancar.
                if (clientes.containsKey(v.getIdCliente())) vehiculos.put(v.getMatricula(), v);
            }
        }
    }

    private void cargarOrdenes() throws IOException {
        ordenes.clear();
        if (!Files.exists(ordenesCsv)) return;
        try (BufferedReader br = Files.newBufferedReader(ordenesCsv, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                try {
                    OrdenReparacion o = OrdenReparacion.fromCsv(line, dateFmt);

                    // Igual que con vehiculos: si no existe el vehiculo, ignoramos la orden.
                    if (vehiculos.containsKey(o.getMatricula())) ordenes.put(o.getId(), o);
                } catch (RuntimeException ex) {
                    // ? Por que capturamos aqui?
                    //   Porque un CSV mal formado no deberia impedir que el programa arranque.
                    //   En un proyecto real registrariamos el error con un logger.
                }
            }
        }
    }

    private void cargarLineas() throws IOException {
        if (!Files.exists(lineasCsv)) return;
        try (BufferedReader br = Files.newBufferedReader(lineasCsv, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] p = line.split(";");
                if (p.length < 3) continue;
                String idOrden = p[0];
                OrdenReparacion o = ordenes.get(idOrden);
                if (o == null) continue;

                try {
                    // * Reutilizamos el parser de LineaTrabajo (concepto + precio)
                    LineaTrabajo lt = LineaTrabajo.fromCsv(line);
                    o.agregarLinea(lt);
                } catch (RuntimeException ex) {
                    // Ignoramos lineas invalidas para mantener el arranque del programa.
                }
            }
        }
    }

    private void guardarClientes() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(clientesCsv, StandardCharsets.UTF_8)) {
            bw.write("# id;nombre;telefono;tipo\n");
            for (Cliente c : clientes.values()) {
                bw.write(c.toCsv());
                bw.write("\n");
            }
        }
    }

    private void guardarVehiculos() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(vehiculosCsv, StandardCharsets.UTF_8)) {
            bw.write("# matricula;marca;modelo;tipo;idCliente\n");
            for (Vehiculo v : vehiculos.values()) {
                bw.write(v.toCsv());
                bw.write("\n");
            }
        }
    }

    private void guardarOrdenes() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(ordenesCsv, StandardCharsets.UTF_8)) {
            bw.write("# id;matricula;fechaEntrada;fechaCierre;estado;diagnostico\n");
            for (OrdenReparacion o : ordenes.values()) {
                bw.write(o.toCsv(dateFmt));
                bw.write("\n");
            }
        }
    }

    private void guardarLineas() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(lineasCsv, StandardCharsets.UTF_8)) {
            bw.write("# idOrden;concepto;precio\n");
            for (OrdenReparacion o : ordenes.values()) {
                for (LineaTrabajo lt : o.getLineas()) {
                    bw.write(lt.toCsv(o.getId()));
                    bw.write("\n");
                }
            }
        }
    }

    private static String normalizarId(String s) {
        if (s == null) return null;
        return s.trim().toUpperCase(Locale.ROOT);
    }
}

// =========================================================================================
// * UTIL: ConsoleIO (lectura segura por consola)
// -----------------------------------------------------------------------------------------
// ! Problema tipico con Scanner:
//   mezclar nextInt()/nextDouble() con nextLine() suele "comerse" saltos de linea.
// Solucion didactica:
//   leer siempre lineas completas (nextLine) y parsear nosotros.
// =========================================================================================
class ConsoleIO {
    private ConsoleIO() {}

    public static String readNonEmpty(java.util.Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (s != null && !s.isBlank()) return s.trim();
            System.out.println("  ! Entrada vacia. Intenta de nuevo.");
        }
    }

    public static int readInt(java.util.Scanner sc, String prompt, int min, int max) {
        while (true) {
            String s = readNonEmpty(sc, prompt);
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.println("  ! Fuera de rango (" + min + "..." + max + ").");
                    continue;
                }
                return v;
            } catch (NumberFormatException ex) {
                System.out.println("  ! No es un numero entero.");
            }
        }
    }

    public static double readDouble(java.util.Scanner sc, String prompt, double min, double max) {
        while (true) {
            String s = readNonEmpty(sc, prompt).replace(',', '.'); // soporte basico para coma decimal
            try {
                double v = Double.parseDouble(s);
                if (v < min || v > max) {
                    System.out.println("  ! Fuera de rango (" + min + "..." + max + ").");
                    continue;
                }
                return v;
            } catch (NumberFormatException ex) {
                System.out.println("  ! No es un numero valido.");
            }
        }
    }

    public static LocalDate readDate(java.util.Scanner sc, String prompt) {
        while (true) {
            String s = readNonEmpty(sc, prompt);
            try {
                return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException ex) {
                System.out.println("  ! Formato invalido. Usa YYYY-MM-DD (ej: 2025-12-13).");
            }
        }
    }
}

// =========================================================================================
// * APP: menu principal (punto de entrada)
// -----------------------------------------------------------------------------------------
// Aqui juntamos todo:
// - Cargar CSV al arrancar
// - Mostrar menus
// - Llamar al manager
// - Guardar CSV al salir
// =========================================================================================
public class UT16_ProyectoFinal_TallerMecanico {
    // * Donde se guardan los CSV:
    //   - Si ejecutas desde consola, es el directorio actual (".").
    //   Puedes cambiarlo a una ruta fija si lo prefieres.
    private static final Path DATA_DIR = Path.of(".");

    public static void main(String[] args) {
        Locale.setDefault(Locale.ROOT);
        TallerManager manager = new TallerManager(DATA_DIR);

        // * ARRANQUE: cargamos CSV (si existen)
        // ? Si es la primera vez que ejecutas, no existiran y no pasa nada.
        try {
            manager.cargar();
        } catch (IOException ex) {
            System.out.println("! No se pudieron cargar los datos: " + ex.getMessage());
        }

        try (java.util.Scanner sc = new java.util.Scanner(System.in)) {
            // * Bucle principal: mientras no elijas "Salir", seguiremos mostrando menus
            menuPrincipal(sc, manager);
        }

        // * SALIDA: guardamos el estado actual a CSV
        // ! Importante: si cierras la consola a la fuerza, puede que no se guarde.
        try {
            manager.guardar();
            System.out.println("* Datos guardados en CSV dentro de: " + DATA_DIR.toAbsolutePath());
        } catch (IOException ex) {
            System.out.println("! No se pudieron guardar los datos: " + ex.getMessage());
        }
    }

    private static void menuPrincipal(java.util.Scanner sc, TallerManager manager) {
        int op;
        do {
            // * UI en consola: simple y clara (para practicar logica, no diseño grafico)
            System.out.println("\n==================== TALLER MECANICO (CONSOLA) ====================");
            System.out.println("1) Clientes");
            System.out.println("2) Vehiculos");
            System.out.println("3) Ordenes de reparacion");
            System.out.println("0) Salir");
            op = ConsoleIO.readInt(sc, "Opcion: ", 0, 3);

            switch (op) {
                case 1 -> menuClientes(sc, manager);
                case 2 -> menuVehiculos(sc, manager);
                case 3 -> menuOrdenes(sc, manager);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida.");
            }
        } while (op != 0);
    }

    // -------------------------------------------------------------------------------------
    // * SUBMENU: Clientes
    // -------------------------------------------------------------------------------------
    private static void menuClientes(java.util.Scanner sc, TallerManager manager) {
        int op;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1) Alta cliente");
            System.out.println("2) Baja cliente");
            System.out.println("3) Listar clientes");
            System.out.println("0) Volver");
            op = ConsoleIO.readInt(sc, "Opcion: ", 0, 3);

            try {
                // * Capturamos RuntimeException para no "romper" el programa si el alumno mete mal datos.
                // ? Ejemplos de errores controlados:
                //   - IllegalArgumentException -> validaciones (campos vacios, duplicados...)
                //   - IllegalStateException    -> reglas de negocio (no se puede borrar con dependencias)
                switch (op) {
                    case 1 -> altaCliente(sc, manager);
                    case 2 -> bajaCliente(sc, manager);
                    case 3 -> listarClientes(manager);
                    case 0 -> {}
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (RuntimeException ex) {
                System.out.println("! Error: " + ex.getMessage());
            }
        } while (op != 0);
    }

    private static void altaCliente(java.util.Scanner sc, TallerManager manager) {
        // * Flujo tipico de un "alta" (CRUD):
        //   1) pedir datos
        //   2) construir objeto validado
        //   3) enviarlo al servicio (manager)
        String id = ConsoleIO.readNonEmpty(sc, "ID (DNI/NIE/CIF): ").toUpperCase(Locale.ROOT);
        String nombre = ConsoleIO.readNonEmpty(sc, "Nombre: ");
        String telefono = ConsoleIO.readNonEmpty(sc, "Telefono: ");

        System.out.println("Tipo: 1) PARTICULAR  2) EMPRESA");
        int t = ConsoleIO.readInt(sc, "Elige: ", 1, 2);
        TipoCliente tipo = (t == 1) ? TipoCliente.PARTICULAR : TipoCliente.EMPRESA;

        manager.agregarCliente(new Cliente(id, nombre, telefono, tipo));
        System.out.println("* Cliente creado.");
    }

    private static void bajaCliente(java.util.Scanner sc, TallerManager manager) {
        String id = ConsoleIO.readNonEmpty(sc, "ID cliente a eliminar: ");
        boolean ok = manager.eliminarCliente(id);
        System.out.println(ok ? "* Cliente eliminado." : "! No existe ese cliente.");
    }

    private static void listarClientes(TallerManager manager) {
        List<Cliente> list = manager.listarClientes();
        if (list.isEmpty()) {
            System.out.println("(sin clientes)");
            return;
        }
        for (Cliente c : list) System.out.println(c);
    }

    // -------------------------------------------------------------------------------------
    // * SUBMENU: Vehiculos
    // -------------------------------------------------------------------------------------
    private static void menuVehiculos(java.util.Scanner sc, TallerManager manager) {
        int op;
        do {
            System.out.println("\n--- VEHICULOS ---");
            System.out.println("1) Alta vehiculo");
            System.out.println("2) Baja vehiculo");
            System.out.println("3) Listar vehiculos");
            System.out.println("0) Volver");
            op = ConsoleIO.readInt(sc, "Opcion: ", 0, 3);

            try {
                switch (op) {
                    case 1 -> altaVehiculo(sc, manager);
                    case 2 -> bajaVehiculo(sc, manager);
                    case 3 -> listarVehiculos(manager);
                    case 0 -> {}
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (RuntimeException ex) {
                System.out.println("! Error: " + ex.getMessage());
            }
        } while (op != 0);
    }

    private static void altaVehiculo(java.util.Scanner sc, TallerManager manager) {
        String matricula = ConsoleIO.readNonEmpty(sc, "Matricula: ");
        String marca = ConsoleIO.readNonEmpty(sc, "Marca: ");
        String modelo = ConsoleIO.readNonEmpty(sc, "Modelo: ");
        String idCliente = ConsoleIO.readNonEmpty(sc, "ID cliente propietario: ");

        // ? Por que pedimos el idCliente?
        //   Porque un vehiculo SIEMPRE debe tener un propietario existente.
        // ! Si el cliente no existe, TallerManager.agregarVehiculo() lanzara un error.
        System.out.println("Tipo: 1) COCHE  2) MOTO  3) FURGONETA");
        int t = ConsoleIO.readInt(sc, "Elige: ", 1, 3);
        TipoVehiculo tipo = switch (t) {
            case 1 -> TipoVehiculo.COCHE;
            case 2 -> TipoVehiculo.MOTO;
            default -> TipoVehiculo.FURGONETA;
        };

        manager.agregarVehiculo(new Vehiculo(matricula, marca, modelo, tipo, idCliente));
        System.out.println("* Vehiculo creado.");
    }

    private static void bajaVehiculo(java.util.Scanner sc, TallerManager manager) {
        String matricula = ConsoleIO.readNonEmpty(sc, "Matricula a eliminar: ");
        boolean ok = manager.eliminarVehiculo(matricula);
        System.out.println(ok ? "* Vehiculo eliminado." : "! No existe ese vehiculo.");
    }

    private static void listarVehiculos(TallerManager manager) {
        List<Vehiculo> list = manager.listarVehiculos();
        if (list.isEmpty()) {
            System.out.println("(sin vehiculos)");
            return;
        }
        for (Vehiculo v : list) System.out.println(v);
    }

    // -------------------------------------------------------------------------------------
    // * SUBMENU: Ordenes (se implementa completo mas abajo)
    // -------------------------------------------------------------------------------------
    private static void menuOrdenes(java.util.Scanner sc, TallerManager manager) {
        int op;
        do {
            System.out.println("\n--- ORDENES ---");
            System.out.println("1) Crear orden");
            System.out.println("2) Cambiar estado de orden");
            System.out.println("3) Agregar linea a orden");
            System.out.println("4) Ver detalle de orden");
            System.out.println("5) Listar ordenes");
            System.out.println("0) Volver");
            op = ConsoleIO.readInt(sc, "Opcion: ", 0, 5);

            try {
                switch (op) {
                    case 1 -> crearOrden(sc, manager);
                    case 2 -> cambiarEstado(sc, manager);
                    case 3 -> agregarLinea(sc, manager);
                    case 4 -> verDetalleOrden(sc, manager);
                    case 5 -> listarOrdenes(manager);
                    case 0 -> {}
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (RuntimeException ex) {
                System.out.println("! Error: " + ex.getMessage());
            }
        } while (op != 0);
    }

    private static void crearOrden(java.util.Scanner sc, TallerManager manager) {
        // * Crear orden: une vehiculo + fecha + diagnostico
        // ? Por que pedimos matricula y no un idOrden?
        //   Porque el idOrden lo genera el sistema (UUID) para asegurar unicidad.
        String matricula = ConsoleIO.readNonEmpty(sc, "Matricula: ");
        LocalDate fechaEntrada = ConsoleIO.readDate(sc, "Fecha entrada (YYYY-MM-DD): ");
        String diagnostico = ConsoleIO.readNonEmpty(sc, "Diagnostico inicial: ");

        OrdenReparacion o = manager.crearOrden(matricula, fechaEntrada, diagnostico);
        System.out.println("* Orden creada con ID: " + o.getId());
        System.out.println("  " + o);
    }

    private static void cambiarEstado(java.util.Scanner sc, TallerManager manager) {
        String idOrden = ConsoleIO.readNonEmpty(sc, "ID orden (UUID): ");

        // * El estado simula el "flujo" del taller:
        //   ABIERTA -> EN_PROCESO -> FINALIZADA -> ENTREGADA
        //   (y en cualquier momento podemos CANCELAR)
        System.out.println("Estado nuevo:");
        System.out.println("1) EN_PROCESO");
        System.out.println("2) FINALIZADA");
        System.out.println("3) ENTREGADA");
        System.out.println("4) CANCELADA");
        int op = ConsoleIO.readInt(sc, "Elige: ", 1, 4);

        EstadoOrden estado = switch (op) {
            case 1 -> EstadoOrden.EN_PROCESO;
            case 2 -> EstadoOrden.FINALIZADA;
            case 3 -> EstadoOrden.ENTREGADA;
            default -> EstadoOrden.CANCELADA;
        };

        manager.cambiarEstado(idOrden, estado);
        System.out.println("* Estado actualizado.");
    }

    private static void agregarLinea(java.util.Scanner sc, TallerManager manager) {
        // * Una linea representa mano de obra o una pieza con coste.
        String idOrden = ConsoleIO.readNonEmpty(sc, "ID orden (UUID): ");
        String concepto = ConsoleIO.readNonEmpty(sc, "Concepto: ");
        double precio = ConsoleIO.readDouble(sc, "Precio (EUR): ", 0, 1_000_000);

        manager.agregarLineaAOrden(idOrden, new LineaTrabajo(concepto, precio));
        System.out.println("* Linea agregada.");
    }

    private static void verDetalleOrden(java.util.Scanner sc, TallerManager manager) {
        String idOrden = ConsoleIO.readNonEmpty(sc, "ID orden (UUID): ");
        OrdenReparacion o = manager.buscarOrden(idOrden);
        if (o == null) {
            System.out.println("! No existe esa orden.");
            return;
        }

        System.out.println(o);
        if (o.getLineas().isEmpty()) {
            System.out.println("(sin lineas)");
            return;
        }
        for (LineaTrabajo lt : o.getLineas()) System.out.println(lt);
    }

    private static void listarOrdenes(TallerManager manager) {
        List<OrdenReparacion> list = manager.listarOrdenes();
        if (list.isEmpty()) {
            System.out.println("(sin ordenes)");
            return;
        }
        for (OrdenReparacion o : list) System.out.println(o);
    }
}

/*
 *******************************************************************************************
 * * TAREAS / RETOS PARA EL ALUMNO (para subir nota)
 * 1) Implementa busqueda:
 *    - buscarVehiculosPorCliente(idCliente)
 *    - buscarOrdenesPorMatricula(matricula)
 *
 * 2) Implementa una factura:
 *    - genera un TXT bien formateado por cada orden entregada
 *    - aplica IVA (21%) y descuento para EMPRESA
 *
 * 3) Mejora validaciones:
 *    - matricula con expresion regular
 *    - telefono con longitud minima
 *
 * 4) Persistencia avanzada:
 *    - backup automatico al salir (copiar CSV a una carpeta /backup con fecha)
 *    - exportar/importar JSON si se permite en clase
 *
 * 5) Arquitectura:
 *    - separa en paquetes: model, service, util, app
 *    - crea pruebas unitarias basicas para fromCsv() y reglas de estado
 *******************************************************************************************
 */
