/*
 * ******************************************************************************************
 * (Programación Orientada a Objetos - POO)
 *                  📚 **TEORÍA Y CONCEPTOS: CLASES Y OBJETOS EN JAVA**
 *                     🦷 SISTEMA DE GESTIÓN DE CLÍNICA DENTAL 🦷
 * ──────────────────────────────────────────────────
 * En esta práctica aprenderás a:
 *
 * ✅ **Comprender la programación orientada a objetos en Java.**
 * ✅ **Crear y utilizar clases en Java.**
 * ✅ **Definir atributos y métodos en una clase.**
 * ✅ **Crear objetos a partir de una clase.**
 * ✅ **Usar constructores para inicializar objetos.**
 * ✅ **Comprender la encapsulación y el uso de getters y setters.**
 * ✅ **Implementar métodos con lógica de negocio.**
 * ✅ **Sobrecarga de constructores.**
 * ✅ **Gestionar relaciones entre objetos.**
 * ✅ **Manejo de fechas y estados.**
 *
 * 🚀 **¡Explora, experimenta y mejora el código!**
 ******************************************************************************************/

/*
 * 🧠 **TEORÍA GLOBAL: CLASES Y OBJETOS EN JAVA**
 * ────────────────────────────────────────────
 *
 * 🟢 **¿Qué es una Clase?**
 *  - Una **clase** es una plantilla o modelo que define las características (**atributos**) y
 *    comportamientos (**métodos**) de un objeto.
 *  - Es como un "plano" para construir objetos del mundo real.
 *
 * 🟠 **¿Qué es un Objeto?**
 *  - Un **objeto** es una instancia concreta de una clase, que tiene valores específicos.
 *  - En Java, un objeto se crea con la palabra clave `new`.
 *
 * 🔵 **Encapsulación:**
 *  - Consiste en ocultar los datos internos de un objeto y proporcionar acceso controlado
 *    mediante métodos públicos (getters/setters).
 *  - Los atributos se declaran como `private` para protegerlos.
 *
 * 🟣 **Constructor:**
 *  - Método especial que se ejecuta automáticamente al crear un objeto.
 *  - Tiene el mismo nombre que la clase y no devuelve ningún valor.
 *  - Se puede sobrecargar (tener varios constructores con diferentes parámetros).
 *
 * 🟡 **Relaciones entre Objetos:**
 *  - Los objetos pueden interactuar y relacionarse entre sí.
 *  - Por ejemplo: un Paciente puede tener una Cita con un Dentista.
 *
 * 🔹 **Ejemplo básico de una Clase y su Objeto:**
 * ```java
 * class Paciente {
 *     private String nombre;
 *     private String telefono;
 *
 *     public Paciente(String nombre, String telefono) {
 *         this.nombre = nombre;
 *         this.telefono = telefono;
 *     }
 *
 *     public void agendarCita() {
 *         System.out.println("Cita agendada para: " + nombre);
 *     }
 * }
 *
 * public class Main {
 *     public static void main(String[] args) {
 *         Paciente paciente = new Paciente("Ana García", "666555444");
 *         paciente.agendarCita();
 *     }
 * }
 * ```
 */

// (Programación Orientada a Objetos - POO)
// Definimos la clase principal
public class UT5_ClasesObjetos {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║   🦷 SISTEMA DE GESTIÓN DE CLÍNICA DENTAL 🦷        ║");
        System.out.println("║        Clínica Dental Prudent - Móstoles            ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * CREACIÓN DE DENTISTAS CON DIFERENTES ESPECIALIDADES
        // * ─────────────────────────────────────────────────────

        // ! ✅ Crear dentistas usando diferentes constructores
        Dentista dentista1 = new Dentista("Dr. Carlos Martínez", "Implantes Dentales", 15);
        Dentista dentista2 = new Dentista("Dra. Laura Sánchez", "Ortodoncia Invisible", 12);
        Dentista dentista3 = new Dentista("Dr. Miguel Ángel López", "Odontopediatría");

        // * Configurar atributos adicionales
        dentista3.setAñosExperiencia(8);

        System.out.println("👨‍⚕️ EQUIPO MÉDICO REGISTRADO:\n");
        dentista1.mostrarPerfil();
        System.out.println();
        dentista2.mostrarPerfil();
        System.out.println();
        dentista3.mostrarPerfil();
        System.out.println();

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * CREACIÓN DE PACIENTES
        // * ─────────────────────────────────────────────────────

        Paciente paciente1 = new Paciente("Ana García López", 35, "666555444", "ana.garcia@email.com");
        Paciente paciente2 = new Paciente("Roberto Fernández", 42, "677888999");
        Paciente paciente3 = new Paciente("María Rodríguez", 28);

        // * Configurar datos adicionales
        paciente2.setEmail("roberto.f@email.com");
        paciente3.setTelefono("688777666");
        paciente3.setEmail("maria.r@email.com");

        System.out.println("\n👥 PACIENTES REGISTRADOS:\n");
        paciente1.mostrarFicha();
        System.out.println();
        paciente2.mostrarFicha();
        System.out.println();
        paciente3.mostrarFicha();
        System.out.println();

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * CREACIÓN DE TRATAMIENTOS DISPONIBLES
        // * ─────────────────────────────────────────────────────

        System.out.println("\n💊 CATÁLOGO DE TRATAMIENTOS:\n");

        Tratamiento trat1 = new Tratamiento("Implante Dental Completo", "Implantes Dentales", 90, 1200.00);
        Tratamiento trat2 = new Tratamiento("Ortodoncia Invisible", "Ortodoncia Invisible", 720, 3500.00);
        Tratamiento trat3 = new Tratamiento("Limpieza Dental Profunda", "Odontología Conservadora", 45, 80.00);
        Tratamiento trat4 = new Tratamiento("Endodoncia Molar", "Endodoncia", 60, 350.00);
        Tratamiento trat5 = new Tratamiento("Blanqueamiento Dental", "Estética Dental");

        // * Configurar tratamiento sin precio definido
        trat5.setDuracionMinutos(60);
        trat5.setPrecio(250.00);

        trat1.mostrarDetalles();
        System.out.println();
        trat2.mostrarDetalles();
        System.out.println();
        trat3.mostrarDetalles();
        System.out.println();
        trat4.mostrarDetalles();
        System.out.println();
        trat5.mostrarDetalles();
        System.out.println();

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * AGENDAR CITAS MÉDICAS
        // * ─────────────────────────────────────────────────────

        System.out.println("\n📅 GESTIÓN DE CITAS:\n");

        Cita cita1 = new Cita(paciente1, dentista1, "15/12/2025", "10:00", trat1);
        Cita cita2 = new Cita(paciente2, dentista2, "16/12/2025", "11:30", trat2);
        Cita cita3 = new Cita(paciente3, dentista3, "17/12/2025", "09:00", trat3);

        cita1.mostrarInformacion();
        System.out.println();
        cita2.mostrarInformacion();
        System.out.println();
        cita3.mostrarInformacion();
        System.out.println();

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * CONFIRMAR Y COMPLETAR CITAS
        // * ─────────────────────────────────────────────────────

        System.out.println("✅ CONFIRMANDO CITAS:\n");
        cita1.confirmarCita();
        cita2.confirmarCita();
        cita3.confirmarCita();

        System.out.println("\n🏥 REALIZANDO TRATAMIENTOS:\n");
        cita1.completarCita();
        cita3.completarCita();

        System.out.println("\n❌ CANCELANDO CITA:\n");
        cita2.cancelarCita("Paciente no disponible");

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * ACTUALIZAR ESTADÍSTICAS DE DENTISTAS Y PACIENTES
        // * ─────────────────────────────────────────────────────

        System.out.println("\n📊 ACTUALIZANDO ESTADÍSTICAS:\n");

        dentista1.atenderPaciente();
        dentista1.atenderPaciente();
        dentista1.atenderPaciente();

        dentista3.atenderPaciente();

        paciente1.agregarVisita();
        paciente1.agregarVisita();
        paciente3.agregarVisita();

        System.out.println("\n👨‍⚕️ ESTADO ACTUAL DEL EQUIPO MÉDICO:\n");
        dentista1.mostrarPerfil();
        System.out.println();
        dentista2.mostrarPerfil();
        System.out.println();
        dentista3.mostrarPerfil();
        System.out.println();

        System.out.println("\n👥 HISTORIAL DE PACIENTES:\n");
        paciente1.mostrarFicha();
        System.out.println();
        paciente2.mostrarFicha();
        System.out.println();
        paciente3.mostrarFicha();
        System.out.println();

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * COMPARACIONES Y ANÁLISIS
        // * ─────────────────────────────────────────────────────

        System.out.println("⚖️  COMPARACIONES:\n");
        dentista1.compararExperiencia(dentista2);
        System.out.println();
        paciente1.compararVisitas(paciente3);
        System.out.println();
        trat1.compararPrecio(trat3);

        // (Programación Orientada a Objetos - POO)
        // * ─────────────────────────────────────────────────────
        // * CALCULAR INGRESOS Y ESTADÍSTICAS
        // * ─────────────────────────────────────────────────────

        System.out.println("\n💰 ANÁLISIS FINANCIERO:\n");
        double ingresosCita1 = cita1.calcularCostoTotal();
        double ingresosCita3 = cita3.calcularCostoTotal();
        double ingresosTotal = ingresosCita1 + ingresosCita3;

        System.out.println("Ingresos totales del día: " + String.format("%.2f", ingresosTotal) + "€");
        System.out.println("Tratamientos completados: 2");
        System.out.println("Citas canceladas: 1");

        // (Programación Orientada a Objetos - POO)
        // ! ✅ TAREA PARA EL ALUMNO:
        // * 1. Crea una clase `HistorialMedico` con atributos: paciente, alergias, enfermedades, medicamentos
        // * 2. Implementa una clase `Receta` con tratamiento, medicamentos y dosis
        // * 3. Añade un método en Paciente para agregar historial médico
        // * 4. Crea métodos para generar presupuestos de tratamientos
        // * 5. Implementa un sistema de recordatorios de citas
        // * 6. BONUS: Crea una clase `Factura` que genere facturas automáticas
    }
}

// (Programación Orientada a Objetos - POO)
// Definimos la clase Dentista
class Dentista {
    // (Programación Orientada a Objetos - POO)
    // * ⚠️ Atributos (variables de instancia) - PRIVATE para encapsulación
    private String nombre;
    private String especialidad;
    private int añosExperiencia;
    private int pacientesAtendidos;
    private double valoracionPromedio;
    private boolean disponible;

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor completo (3 parámetros)
    // * Inicializa todos los atributos principales al crear el objeto
    public Dentista(String nombre, String especialidad, int añosExperiencia) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
        this.pacientesAtendidos = 0;
        this.valoracionPromedio = 0.0;
        this.disponible = true;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor sobrecargado (2 parámetros)
    // * Permite crear un dentista sin especificar años de experiencia
    public Dentista(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.añosExperiencia = 0;
        this.pacientesAtendidos = 0;
        this.valoracionPromedio = 0.0;
        this.disponible = true;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Getters - Permiten acceder a los atributos privados
    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public int getAñosExperiencia() {
        return añosExperiencia;
    }

    public int getPacientesAtendidos() {
        return pacientesAtendidos;
    }

    public double getValoracionPromedio() {
        return valoracionPromedio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Setters - Permiten modificar los atributos privados con validación
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("⚠️ El nombre no puede estar vacío.");
        }
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad != null && !especialidad.trim().isEmpty()) {
            this.especialidad = especialidad;
        } else {
            System.out.println("⚠️ La especialidad no puede estar vacía.");
        }
    }

    public void setAñosExperiencia(int añosExperiencia) {
        if (añosExperiencia >= 0) {
            this.añosExperiencia = añosExperiencia;
        } else {
            System.out.println("⚠️ Los años de experiencia no pueden ser negativos.");
        }
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: atenderPaciente
    // * Incrementa el contador de pacientes atendidos
    public void atenderPaciente() {
        pacientesAtendidos++;
        System.out.println("✅ " + nombre + " atendió un paciente (Total: " + pacientesAtendidos + ")");
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: agregarValoracion
    // * Actualiza la valoración promedio del dentista
    public void agregarValoracion(double valoracion) {
        if (valoracion >= 0 && valoracion <= 5) {
            if (valoracionPromedio == 0) {
                valoracionPromedio = valoracion;
            } else {
                valoracionPromedio = (valoracionPromedio + valoracion) / 2;
            }
            System.out.println("⭐ Nueva valoración recibida: " + valoracion + "/5");
        } else {
            System.out.println("⚠️ La valoración debe estar entre 0 y 5.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: esExperimentado
    // * Determina si el dentista es experimentado (más de 10 años)
    public boolean esExperimentado() {
        return añosExperiencia >= 10;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: obtenerCategoria
    // * Devuelve la categoría del dentista según su experiencia
    public String obtenerCategoria() {
        if (añosExperiencia >= 20) {
            return "👑 ESPECIALISTA SENIOR";
        } else if (añosExperiencia >= 10) {
            return "⭐ ESPECIALISTA";
        } else if (añosExperiencia >= 5) {
            return "🎓 PROFESIONAL";
        } else {
            return "🌱 JUNIOR";
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: compararExperiencia
    // * Compara la experiencia con otro dentista
    public void compararExperiencia(Dentista otro) {
        System.out.println("Comparando experiencia:");
        System.out.println("  " + this.nombre + ": " + this.añosExperiencia + " años");
        System.out.println("  " + otro.nombre + ": " + otro.añosExperiencia + " años");

        if (this.añosExperiencia > otro.añosExperiencia) {
            System.out.println("  ➡️  " + this.nombre + " tiene más experiencia");
        } else if (this.añosExperiencia < otro.añosExperiencia) {
            System.out.println("  ➡️  " + otro.nombre + " tiene más experiencia");
        } else {
            System.out.println("  ➡️  Ambos tienen la misma experiencia");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: mostrarPerfil
    // * Muestra el perfil completo del dentista
    public void mostrarPerfil() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("│ 👨‍⚕️ PERFIL PROFESIONAL                         │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.println("│ Nombre:          " + nombre);
        System.out.println("│ Especialidad:    " + especialidad);
        System.out.println("│ Experiencia:     " + añosExperiencia + " años");
        System.out.println("│ Categoría:       " + obtenerCategoria());
        System.out.println("│ Pacientes:       " + pacientesAtendidos);
        if (valoracionPromedio > 0) {
            System.out.println("│ Valoración:      " + String.format("%.1f", valoracionPromedio) + "/5.0 ⭐");
        }
        System.out.println("│ Disponibilidad:  " + (disponible ? "✅ Disponible" : "❌ No disponible"));
        System.out.println("└──────────────────────────────────────────────────┘");
    }
}

// (Programación Orientada a Objetos - POO)
// Definimos la clase Paciente
class Paciente {
    // (Programación Orientada a Objetos - POO)
    // * ⚠️ Atributos (variables de instancia)
    private String nombreCompleto;
    private int edad;
    private String telefono;
    private String email;
    private int numeroVisitas;
    private String ultimaVisita;
    private boolean tieneSeguros;

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor completo (4 parámetros)
    public Paciente(String nombreCompleto, int edad, String telefono, String email) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.telefono = telefono;
        this.email = email;
        this.numeroVisitas = 0;
        this.ultimaVisita = "Nunca";
        this.tieneSeguros = false;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor sobrecargado (3 parámetros)
    public Paciente(String nombreCompleto, int edad, String telefono) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.telefono = telefono;
        this.email = "No especificado";
        this.numeroVisitas = 0;
        this.ultimaVisita = "Nunca";
        this.tieneSeguros = false;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor sobrecargado (2 parámetros)
    public Paciente(String nombreCompleto, int edad) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.telefono = "No especificado";
        this.email = "No especificado";
        this.numeroVisitas = 0;
        this.ultimaVisita = "Nunca";
        this.tieneSeguros = false;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Getters
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public int getEdad() {
        return edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public int getNumeroVisitas() {
        return numeroVisitas;
    }

    public String getUltimaVisita() {
        return ultimaVisita;
    }

    public boolean isTieneSeguros() {
        return tieneSeguros;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Setters con validación
    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto != null && !nombreCompleto.trim().isEmpty()) {
            this.nombreCompleto = nombreCompleto;
        } else {
            System.out.println("⚠️ El nombre no puede estar vacío.");
        }
    }

    public void setEdad(int edad) {
        if (edad > 0 && edad < 120) {
            this.edad = edad;
        } else {
            System.out.println("⚠️ La edad debe estar entre 0 y 120.");
        }
    }

    public void setTelefono(String telefono) {
        if (telefono != null && telefono.length() >= 9) {
            this.telefono = telefono;
        } else {
            System.out.println("⚠️ El teléfono debe tener al menos 9 dígitos.");
        }
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            System.out.println("⚠️ El email debe contener '@'.");
        }
    }

    public void setTieneSeguros(boolean tieneSeguros) {
        this.tieneSeguros = tieneSeguros;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: agregarVisita
    // * Incrementa el contador de visitas y actualiza la fecha
    public void agregarVisita() {
        numeroVisitas++;
        ultimaVisita = "Hoy";
        System.out.println("✅ Visita registrada para " + nombreCompleto + " (Total: " + numeroVisitas + ")");
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: esPacienteFrecuente
    // * Determina si es un paciente frecuente (más de 5 visitas)
    public boolean esPacienteFrecuente() {
        return numeroVisitas >= 5;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: obtenerCategoriaPaciente
    // * Devuelve la categoría del paciente según sus visitas
    public String obtenerCategoriaPaciente() {
        if (numeroVisitas >= 10) {
            return "💎 VIP";
        } else if (numeroVisitas >= 5) {
            return "⭐ FRECUENTE";
        } else if (numeroVisitas >= 1) {
            return "👤 REGULAR";
        } else {
            return "🆕 NUEVO";
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: compararVisitas
    // * Compara el número de visitas con otro paciente
    public void compararVisitas(Paciente otro) {
        System.out.println("Comparando historial de visitas:");
        System.out.println("  " + this.nombreCompleto + ": " + this.numeroVisitas + " visitas");
        System.out.println("  " + otro.nombreCompleto + ": " + otro.numeroVisitas + " visitas");

        if (this.numeroVisitas > otro.numeroVisitas) {
            System.out.println("  ➡️  " + this.nombreCompleto + " ha visitado más veces");
        } else if (this.numeroVisitas < otro.numeroVisitas) {
            System.out.println("  ➡️  " + otro.nombreCompleto + " ha visitado más veces");
        } else {
            System.out.println("  ➡️  Ambos tienen el mismo número de visitas");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: mostrarFicha
    // * Muestra la ficha completa del paciente
    public void mostrarFicha() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("│ 👤 FICHA DE PACIENTE                            │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.println("│ Nombre:          " + nombreCompleto);
        System.out.println("│ Edad:            " + edad + " años");
        System.out.println("│ Teléfono:        " + telefono);
        System.out.println("│ Email:           " + email);
        System.out.println("│ Visitas:         " + numeroVisitas);
        System.out.println("│ Última visita:   " + ultimaVisita);
        System.out.println("│ Categoría:       " + obtenerCategoriaPaciente());
        System.out.println("│ Seguro médico:   " + (tieneSeguros ? "✅ Sí" : "❌ No"));
        System.out.println("└──────────────────────────────────────────────────┘");
    }
}

// (Programación Orientada a Objetos - POO)
// Definimos la clase Tratamiento
class Tratamiento {
    // (Programación Orientada a Objetos - POO)
    // * ⚠️ Atributos (variables de instancia)
    private String nombre;
    private String categoria;
    private int duracionMinutos;
    private double precio;
    private String descripcion;
    private boolean requiereAnestesia;

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor completo (4 parámetros)
    public Tratamiento(String nombre, String categoria, int duracionMinutos, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
        this.descripcion = "Tratamiento dental profesional";
        this.requiereAnestesia = false;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor sobrecargado (2 parámetros)
    public Tratamiento(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.duracionMinutos = 30;
        this.precio = 0.0;
        this.descripcion = "Tratamiento dental profesional";
        this.requiereAnestesia = false;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Getters
    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isRequiereAnestesia() {
        return requiereAnestesia;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Setters con validación
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("⚠️ El nombre no puede estar vacío.");
        }
    }

    public void setCategoria(String categoria) {
        if (categoria != null && !categoria.trim().isEmpty()) {
            this.categoria = categoria;
        } else {
            System.out.println("⚠️ La categoría no puede estar vacía.");
        }
    }

    public void setDuracionMinutos(int duracionMinutos) {
        if (duracionMinutos > 0) {
            this.duracionMinutos = duracionMinutos;
        } else {
            System.out.println("⚠️ La duración debe ser mayor a 0.");
        }
    }

    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
        } else {
            System.out.println("⚠️ El precio no puede ser negativo.");
        }
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRequiereAnestesia(boolean requiereAnestesia) {
        this.requiereAnestesia = requiereAnestesia;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: formatearDuracion
    // * Convierte minutos a formato de horas y minutos
    public String formatearDuracion() {
        if (duracionMinutos >= 60) {
            int horas = duracionMinutos / 60;
            int minutos = duracionMinutos % 60;
            return horas + "h " + minutos + "m";
        }
        return duracionMinutos + " min";
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: aplicarDescuento
    // * Aplica un descuento al precio del tratamiento
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje > 0 && porcentaje <= 100) {
            double precioAnterior = precio;
            double descuento = precio * (porcentaje / 100);
            precio = precio - descuento;
            System.out.println("✅ Descuento del " + porcentaje + "% aplicado a " + nombre);
            System.out.println("   Precio: " + String.format("%.2f", precioAnterior) + "€ → " + String.format("%.2f", precio) + "€");
        } else {
            System.out.println("⚠️ El porcentaje debe estar entre 0 y 100.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: esComplejo
    // * Determina si el tratamiento es complejo (más de 60 minutos)
    public boolean esComplejo() {
        return duracionMinutos > 60;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: compararPrecio
    // * Compara el precio con otro tratamiento
    public void compararPrecio(Tratamiento otro) {
        System.out.println("Comparando precios:");
        System.out.println("  " + this.nombre + ": " + String.format("%.2f", this.precio) + "€");
        System.out.println("  " + otro.nombre + ": " + String.format("%.2f", otro.precio) + "€");

        if (this.precio > otro.precio) {
            System.out.println("  ➡️  " + this.nombre + " es más caro");
        } else if (this.precio < otro.precio) {
            System.out.println("  ➡️  " + otro.nombre + " es más caro");
        } else {
            System.out.println("  ➡️  Ambos tienen el mismo precio");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: mostrarDetalles
    // * Muestra los detalles completos del tratamiento
    public void mostrarDetalles() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("│ 💊 DETALLES DEL TRATAMIENTO                     │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.println("│ Nombre:          " + nombre);
        System.out.println("│ Categoría:       " + categoria);
        System.out.println("│ Duración:        " + formatearDuracion());
        System.out.println("│ Precio:          " + String.format("%.2f", precio) + "€");
        System.out.println("│ Descripción:     " + descripcion);
        System.out.println("│ Anestesia:       " + (requiereAnestesia ? "✅ Requerida" : "❌ No requerida"));
        System.out.println("│ Complejidad:     " + (esComplejo() ? "🔴 Alta" : "🟢 Baja/Media"));
        System.out.println("└──────────────────────────────────────────────────┘");
    }
}

// (Programación Orientada a Objetos - POO)
// Definimos la clase Cita
class Cita {
    // (Programación Orientada a Objetos - POO)
    // * ⚠️ Atributos (variables de instancia)
    private Paciente paciente;
    private Dentista dentista;
    private String fecha;
    private String hora;
    private Tratamiento tratamiento;
    private String estado; // * "Pendiente", "Confirmada", "Completada", "Cancelada"
    private String motivoCancelacion;

    // (Programación Orientada a Objetos - POO)
    // * ✅ Constructor completo (5 parámetros)
    public Cita(Paciente paciente, Dentista dentista, String fecha, String hora, Tratamiento tratamiento) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.fecha = fecha;
        this.hora = hora;
        this.tratamiento = tratamiento;
        this.estado = "Pendiente";
        this.motivoCancelacion = "";
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Getters
    public Paciente getPaciente() {
        return paciente;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public String getEstado() {
        return estado;
    }

    // (Programación Orientada a Objetos - POO)
    // * 🛠️ Setters con validación
    public void setFecha(String fecha) {
        if (fecha != null && !fecha.trim().isEmpty()) {
            this.fecha = fecha;
        } else {
            System.out.println("⚠️ La fecha no puede estar vacía.");
        }
    }

    public void setHora(String hora) {
        if (hora != null && !hora.trim().isEmpty()) {
            this.hora = hora;
        } else {
            System.out.println("⚠️ La hora no puede estar vacía.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: confirmarCita
    // * Cambia el estado de la cita a "Confirmada"
    public void confirmarCita() {
        if (estado.equals("Pendiente")) {
            estado = "Confirmada";
            System.out.println("✅ Cita confirmada para " + paciente.getNombreCompleto());
            System.out.println("   Fecha: " + fecha + " | Hora: " + hora);
        } else {
            System.out.println("⚠️ La cita no está pendiente.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: completarCita
    // * Marca la cita como completada
    public void completarCita() {
        if (estado.equals("Confirmada")) {
            estado = "Completada";
            System.out.println("✅ Cita completada exitosamente");
            System.out.println("   Paciente: " + paciente.getNombreCompleto());
            System.out.println("   Tratamiento: " + tratamiento.getNombre());
        } else {
            System.out.println("⚠️ La cita debe estar confirmada primero.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: cancelarCita
    // * Cancela la cita con un motivo
    public void cancelarCita(String motivo) {
        if (!estado.equals("Completada")) {
            estado = "Cancelada";
            motivoCancelacion = motivo;
            System.out.println("❌ Cita cancelada");
            System.out.println("   Paciente: " + paciente.getNombreCompleto());
            System.out.println("   Motivo: " + motivo);
        } else {
            System.out.println("⚠️ No se puede cancelar una cita completada.");
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: calcularCostoTotal
    // * Calcula el costo total de la cita
    public double calcularCostoTotal() {
        if (estado.equals("Completada")) {
            return tratamiento.getPrecio();
        }
        return 0.0;
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: obtenerIconoEstado
    // * Devuelve un icono según el estado de la cita
    private String obtenerIconoEstado() {
        switch (estado) {
            case "Pendiente":
                return "⏳";
            case "Confirmada":
                return "✅";
            case "Completada":
                return "🏁";
            case "Cancelada":
                return "❌";
            default:
                return "❓";
        }
    }

    // (Programación Orientada a Objetos - POO)
    // * ✅ Método: mostrarInformacion
    // * Muestra toda la información de la cita
    public void mostrarInformacion() {
        System.out.println("┌──────────────────────────────────────────────────┐");
        System.out.println("│ 📅 INFORMACIÓN DE LA CITA                       │");
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.println("│ Paciente:        " + paciente.getNombreCompleto());
        System.out.println("│ Dentista:        " + dentista.getNombre());
        System.out.println("│ Especialidad:    " + dentista.getEspecialidad());
        System.out.println("│ Fecha:           " + fecha);
        System.out.println("│ Hora:            " + hora);
        System.out.println("│ Tratamiento:     " + tratamiento.getNombre());
        System.out.println("│ Duración:        " + tratamiento.formatearDuracion());
        System.out.println("│ Precio:          " + String.format("%.2f", tratamiento.getPrecio()) + "€");
        System.out.println("│ Estado:          " + obtenerIconoEstado() + " " + estado);
        if (estado.equals("Cancelada")) {
            System.out.println("│ Motivo:          " + motivoCancelacion);
        }
        System.out.println("└──────────────────────────────────────────────────┘");
    }
}

/*
 * ⚡ **TAREAS PARA EL ALUMNO:**
 * ════════════════════════════════════════════════════════════════
 *
 * 🎯 TAREA 1: Clase HistorialMedico
 * ──────────────────────────────────────────────────────────────
 * 1. Crea una clase `HistorialMedico` con los siguientes atributos:
 *    - paciente (Paciente)
 *    - alergias (String)
 *    - enfermedadesCronicas (String)
 *    - medicamentosActuales (String)
 *    - grupoSanguineo (String)
 *
 * 2. Implementa al menos 2 constructores sobrecargados.
 *
 * 3. Crea los siguientes métodos:
 *    - agregarAlergia(String alergia)
 *    - tieneAlergias(): boolean
 *    - mostrarHistorial()
 *
 * 4. Añade getters y setters con validaciones apropiadas.
 *
 * 5. En main(), crea 2 objetos HistorialMedico y prueba todos los métodos.
 *
 * 🎯 TAREA 2: Clase Factura
 * ──────────────────────────────────────────────────────────────
 * 1. Crea una clase `Factura` con atributos:
 *    - numeroFactura (int)
 *    - paciente (Paciente)
 *    - tratamiento (Tratamiento)
 *    - fecha (String)
 *    - iva (double)
 *    - descuento (double)
 *
 * 2. Implementa métodos:
 *    - calcularSubtotal(): double
 *    - calcularIVA(): double
 *    - calcularTotal(): double
 *    - aplicarDescuento(double porcentaje)
 *    - generarFactura(): muestra la factura completa
 *
 * 3. En main(), crea facturas y calcula totales.
 *
 * 🎯 TAREA 3: Clase Receta y Sistema de Presupuestos (AVANZADO)
 * ──────────────────────────────────────────────────────────────
 * 1. Crea una clase `Receta` con atributos:
 *    - dentista (Dentista)
 *    - paciente (Paciente)
 *    - medicamento (String)
 *    - dosis (String)
 *    - duracionTratamiento (int)
 *
 * 2. Crea una clase `Presupuesto` que agrupe varios tratamientos.
 *
 * 3. Implementa métodos para calcular costos totales con descuentos.
 *
 * 4. BONUS: Crea un sistema de recordatorios de citas por SMS/Email.
 *
 * 🎯 TAREA 4: Mejoras a las clases existentes
 * ──────────────────────────────────────────────────────────────
 * 1. En la clase Dentista, añade un método `cambiarDisponibilidad()`
 *    que alterne entre disponible/no disponible.
 *
 * 2. En la clase Paciente, añade un método `calcularEdadEnMeses()`
 *    y `esPacienteInfantil()` (menor de 12 años).
 *
 * 3. En la clase Tratamiento, añade un método `calcularTiempoTotal(int sesiones)`
 *    que calcule el tiempo total si requiere múltiples sesiones.
 *
 * 🚀 **¡Explora, experimenta y mejora el código!**
 *
 * 💡 CONCEPTOS CLAVE APRENDIDOS:
 * ══════════════════════════════════════════════════════════════
 * ✅ Clases y Objetos
 * ✅ Atributos (variables de instancia)
 * ✅ Métodos (comportamientos)
 * ✅ Constructores y sobrecarga de constructores
 * ✅ Encapsulación (private + getters/setters)
 * ✅ Validación de datos
 * ✅ Métodos con lógica de negocio
 * ✅ Relaciones entre objetos (Composición)
 * ✅ Métodos de comparación entre objetos
 * ✅ Manejo de estados y flujos de trabajo
 * ✅ Formateo de datos (tiempo, precios)
 * ✅ Formateo de salida profesional
 *
 */
