# 📝 EJERCICIOS PRÁCTICOS: Construye el Sistema de Hotel

## 🎯 Objetivo
En estos ejercicios vas a **construir tú mismo** las partes clave del sistema.
Cada ejercicio tiene pistas y la solución al final (¡pero intenta hacerlo primero sin mirar!).

---

## ⏱️ Tiempo estimado: 2 horas

| Ejercicio | Tiempo | Concepto |
|-----------|--------|----------|
| Ejercicio 1 | 15 min | Clase Abstracta |
| Ejercicio 2 | 15 min | Herencia |
| Ejercicio 3 | 15 min | Interfaz |
| Ejercicio 4 | 20 min | Implementación + Excepción |
| Ejercicio 5 | 20 min | Clase Reserva |
| Ejercicio 6 | 15 min | DAO básico |
| Ejercicio 7 | 20 min | Integración |

---

# 🔵 EJERCICIO 1: Clase Abstracta Persona

## 📋 Enunciado
Crea la clase abstracta `Persona` con:
- Atributos: `nombre` (String), `telefono` (String)
- Constructor que inicialice ambos atributos
- Método abstracto `mostrarInfo()`
- Getters y setters

## 💡 Pistas
```java
// Una clase abstracta se declara así:
public abstract class NombreClase {
    
    // Un método abstracto NO tiene cuerpo (no tiene las llaves {})
    public abstract void miMetodo();
}
```

## ✍️ Escribe tu código aquí:
```java
package hotel.modelo;

public _______ class Persona {
    
    // TODO: Declara los atributos (protected para que las hijas accedan)
    
    
    // TODO: Crea el constructor
    
    
    // TODO: Declara el método abstracto mostrarInfo()
    
    
    // TODO: Crea getters y setters
    
}
```

## ✅ Solución
<details>
<summary>👉 Click para ver la solución</summary>

```java
package hotel.modelo;

public abstract class Persona {
    
    protected String nombre;
    protected String telefono;
    
    public Persona(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }
    
    public abstract void mostrarInfo();
    
    // Getters
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
```
</details>

---

# 🟢 EJERCICIO 2: Clase Cliente (Herencia)

## 📋 Enunciado
Crea la clase `Cliente` que:
- **Hereda** de `Persona`
- Añade el atributo `email`
- Tiene un atributo `id` (para la base de datos)
- Implementa `mostrarInfo()` mostrando todos los datos

## 💡 Pistas
```java
// Para heredar usamos "extends"
public class Hija extends Padre {
    
    // Para llamar al constructor del padre usamos "super()"
    public Hija(String param1, String param2) {
        super(param1, param2);  // Llama al constructor de Padre
    }
    
    // Para sobreescribir un método usamos @Override
    @Override
    public void metodoDelPadre() {
        // Nueva implementación
    }
}
```

## ✍️ Escribe tu código aquí:
```java
package hotel.modelo;

public class Cliente _______ Persona {
    
    private int id;
    private String email;
    
    // TODO: Constructor que llame a super() e inicialice email
    public Cliente(String nombre, String telefono, String email) {
        // ¿Cómo llamamos al constructor de Persona?
        
    }
    
    // TODO: Implementa mostrarInfo()
    @Override
    public void mostrarInfo() {
        // Muestra: nombre, telefono, email
        
    }
    
    // TODO: Getters y setters para id y email
    
}
```

## ✅ Solución
<details>
<summary>👉 Click para ver la solución</summary>

```java
package hotel.modelo;

public class Cliente extends Persona {
    
    private int id;
    private String email;
    
    public Cliente(String nombre, String telefono, String email) {
        super(nombre, telefono);  // Llama al constructor de Persona
        this.email = email;
    }
    
    // Constructor con ID (para cuando leemos de la BD)
    public Cliente(int id, String nombre, String telefono, String email) {
        super(nombre, telefono);
        this.id = id;
        this.email = email;
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("=== DATOS DEL CLIENTE ===");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);      // Heredado
        System.out.println("Teléfono: " + telefono);  // Heredado
        System.out.println("Email: " + email);
    }
    
    // Getters
    public int getId() { return id; }
    public String getEmail() { return email; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
}
```
</details>

---

# 🔷 EJERCICIO 3: Interfaz Reservable

## 📋 Enunciado
Crea la interfaz `Reservable` con dos métodos:
- `reservar()` - puede lanzar una excepción
- `cancelarReserva()` - libera el recurso

## 💡 Pistas
```java
// Una interfaz se declara con "interface" (no "class")
public interface NombreInterfaz {
    
    // Los métodos de una interfaz NO tienen cuerpo
    void metodo1();
    void metodo2() throws Exception;  // Puede lanzar excepción
}
```

## ✍️ Escribe tu código aquí:
```java
package hotel.modelo;

public _________ Reservable {
    
    // TODO: Método reservar() que puede lanzar Exception
    
    
    // TODO: Método cancelarReserva()
    
}
```

## ✅ Solución
<details>
<summary>👉 Click para ver la solución</summary>

```java
package hotel.modelo;

public interface Reservable {
    
    void reservar() throws Exception;
    
    void cancelarReserva();
}
```
</details>

---

# 🏠 EJERCICIO 4: Clase Habitacion + Excepción

## 📋 Enunciado

### Parte A: Crea la excepción `ReservaInvalidaException`
Una excepción personalizada que hereda de `Exception`.

### Parte B: Crea la clase `Habitacion` que:
- Implementa la interfaz `Reservable`
- Tiene atributos: `id`, `tipo`, `precioPorNoche`, `disponible`
- En `reservar()`: si ya está ocupada, lanza `ReservaInvalidaException`

## 💡 Pistas
```java
// Excepción personalizada
public class MiExcepcion extends Exception {
    public MiExcepcion(String mensaje) {
        super(mensaje);
    }
}

// Implementar una interfaz
public class MiClase implements MiInterfaz {
    @Override
    public void metodoDeInterfaz() {
        // Implementación obligatoria
    }
}

// Lanzar una excepción
if (condicionDeError) {
    throw new MiExcepcion("Mensaje de error");
}
```

## ✍️ Escribe tu código:

### Parte A: ReservaInvalidaException
```java
package hotel.excepciones;

public class ReservaInvalidaException _______ Exception {
    
    // TODO: Constructor que recibe un mensaje
    
}
```

### Parte B: Habitacion
```java
package hotel.modelo;

import hotel.excepciones.ReservaInvalidaException;

public class Habitacion _________ Reservable {
    
    private int id;
    private String tipo;
    private double precioPorNoche;
    private boolean disponible;
    
    // TODO: Constructor
    
    
    // TODO: Implementar reservar()
    // Si disponible es false, lanzar ReservaInvalidaException
    @Override
    public void reservar() throws ReservaInvalidaException {
        
    }
    
    // TODO: Implementar cancelarReserva()
    @Override
    public void cancelarReserva() {
        
    }
    
    // TODO: toString()
    
    // TODO: Getters y setters
}
```

## ✅ Solución
<details>
<summary>👉 Click para ver la solución</summary>

**ReservaInvalidaException.java:**
```java
package hotel.excepciones;

public class ReservaInvalidaException extends Exception {
    
    public ReservaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
```

**Habitacion.java:**
```java
package hotel.modelo;

import hotel.excepciones.ReservaInvalidaException;

public class Habitacion implements Reservable {
    
    private int id;
    private String tipo;
    private double precioPorNoche;
    private boolean disponible;
    
    public Habitacion(int id, String tipo, double precioPorNoche, boolean disponible) {
        this.id = id;
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.disponible = disponible;
    }
    
    @Override
    public void reservar() throws ReservaInvalidaException {
        if (!disponible) {
            throw new ReservaInvalidaException("La habitación " + id + " no está disponible");
        }
        disponible = false;
        System.out.println("Habitación " + id + " reservada correctamente");
    }
    
    @Override
    public void cancelarReserva() {
        disponible = true;
        System.out.println("Habitación " + id + " liberada");
    }
    
    @Override
    public String toString() {
        return "Habitación " + id + " | " + tipo + " | " + precioPorNoche + "€ | " + 
               (disponible ? "Disponible" : "Ocupada");
    }
    
    // Getters
    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public double getPrecioPorNoche() { return precioPorNoche; }
    public boolean isDisponible() { return disponible; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setPrecioPorNoche(double precio) { this.precioPorNoche = precio; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
```
</details>

---

# 📅 EJERCICIO 5: Clase Reserva

## 📋 Enunciado
Crea la clase `Reserva` con:
- Atributos: `id`, `cliente`, `habitacion`, `fechaEntrada`, `fechaSalida`, `importeTotal`
- El importe se calcula automáticamente en el constructor
- Método `calcularImporteTotal()` = días × precio/noche

## 💡 Pistas
```java
// Para calcular días entre dos fechas:
long diferenciaMilisegundos = fechaSalida.getTime() - fechaEntrada.getTime();
long dias = diferenciaMilisegundos / (1000 * 60 * 60 * 24);

// Imports necesarios:
import java.util.Date;
```

## ✍️ Escribe tu código:
```java
package hotel.modelo;

import java.util.Date;

public class Reserva {
    
    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private Date fechaEntrada;
    private Date fechaSalida;
    private double importeTotal;
    
    // TODO: Constructor que calcule el importe
    public Reserva(Cliente cliente, Habitacion habitacion, 
                   Date fechaEntrada, Date fechaSalida) {
        
        // Asigna los atributos y calcula el importe
        
    }
    
    // TODO: Método calcularImporteTotal()
    public double calcularImporteTotal() {
        // Calcula: número de días × precio por noche
        
    }
    
    // TODO: toString() que muestre toda la info
    
    // TODO: Getters y setters
}
```

## ✅ Solución
<details>
<summary>👉 Click para ver la solución</summary>

```java
package hotel.modelo;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reserva {
    
    private int id;
    private Cliente cliente;
    private Habitacion habitacion;
    private Date fechaEntrada;
    private Date fechaSalida;
    private double importeTotal;
    
    public Reserva(Cliente cliente, Habitacion habitacion, 
                   Date fechaEntrada, Date fechaSalida) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.importeTotal = calcularImporteTotal();
    }
    
    public double calcularImporteTotal() {
        if (fechaEntrada == null || fechaSalida == null) return 0;
        
        long diferencia = fechaSalida.getTime() - fechaEntrada.getTime();
        long dias = TimeUnit.DAYS.convert(diferencia, TimeUnit.MILLISECONDS);
        
        if (dias <= 0) dias = 1;  // Mínimo 1 noche
        
        return dias * habitacion.getPrecioPorNoche();
    }
    
    @Override
    public String toString() {
        return "=== RESERVA #" + id + " ===" +
               "\nCliente: " + cliente.getNombre() +
               "\nHabitación: " + habitacion.getId() + " (" + habitacion.getTipo() + ")" +
               "\nEntrada: " + fechaEntrada +
               "\nSalida: " + fechaSalida +
               "\nTOTAL: " + importeTotal + "€";
    }
    
    // Getters
    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Habitacion getHabitacion() { return habitacion; }
    public Date getFechaEntrada() { return fechaEntrada; }
    public Date getFechaSalida() { return fechaSalida; }
    public double getImporteTotal() { return importeTotal; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setImporteTotal(double total) { this.importeTotal = total; }
}
```
</details>

---

# 🗄️ EJERCICIO 6: HabitacionDAO (JDBC básico)

## 📋 Enunciado
Crea el método `listarDisponibles()` en `HabitacionDAO` que:
1. Conecta a la base de datos
2. Ejecuta: `SELECT * FROM Habitaciones WHERE disponible = 1`
3. Convierte cada fila en un objeto `Habitacion`
4. Devuelve un `ArrayList<Habitacion>`

## 💡 Pistas
```java
// Patrón básico de JDBC:
Connection conn = ConexionBD.getConexion();
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery("SELECT * FROM MiTabla");

while (rs.next()) {
    int id = rs.getInt("columna_id");
    String texto = rs.getString("columna_texto");
    double numero = rs.getDouble("columna_numero");
    boolean flag = rs.getBoolean("columna_boolean");
}
```

## ✍️ Escribe tu código:
```java
package hotel.dao;

import hotel.modelo.Habitacion;
import java.sql.*;
import java.util.ArrayList;

public class HabitacionDAO {
    
    public ArrayList<Habitacion> listarDisponibles() {
        ArrayList<Habitacion> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM Habitaciones WHERE disponible = 1";
        
        try {
            // TODO: Obtener conexión
            Connection conn = _________________;
            
            // TODO: Crear statement y ejecutar query
            Statement stmt = _________________;
            ResultSet rs = _________________;
            
            // TODO: Recorrer resultados y crear objetos Habitacion
            while (rs.next()) {
                Habitacion hab = new Habitacion(
                    // Lee las columnas: id, tipo, precio, disponible
                    
                );
                lista.add(hab);
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        return lista;
    }
}
```

## ✅ Solución
<details>
<summary>👉 Click para ver la solución</summary>

```java
package hotel.dao;

import hotel.modelo.Habitacion;
import java.sql.*;
import java.util.ArrayList;

public class HabitacionDAO {
    
    public ArrayList<Habitacion> listarDisponibles() {
        ArrayList<Habitacion> lista = new ArrayList<>();
        
        String sql = "SELECT * FROM Habitaciones WHERE disponible = 1";
        
        try (
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                Habitacion hab = new Habitacion(
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getDouble("precio"),
                    rs.getBoolean("disponible")
                );
                lista.add(hab);
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        return lista;
    }
}
```
</details>

---

# 🧪 EJERCICIO 7: Prueba de Integración

## 📋 Enunciado
Crea un método `main` de prueba que:
1. Cree un `Cliente`
2. Cree una `Habitacion` disponible
3. Intente reservar la habitación
4. Intente reservarla de nuevo (debe fallar)
5. Cancele la reserva
6. Muestre la info con `mostrarInfo()` y `toString()`

## ✍️ Escribe tu código:
```java
public class PruebaHotel {
    
    public static void main(String[] args) {
        
        // 1. Crear un cliente
        Cliente cliente = new Cliente("Juan García", "666123456", "juan@email.com");
        
        // 2. Crear una habitación disponible
        Habitacion hab = new Habitacion(1, "doble", 70.0, true);
        
        // 3. Mostrar info del cliente
        cliente.mostrarInfo();
        
        // 4. Mostrar habitación
        System.out.println(hab);
        
        // 5. Intentar reservar (debería funcionar)
        try {
            // TODO: Llamar a hab.reservar()
            
            System.out.println("Primera reserva: OK");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // 6. Intentar reservar de nuevo (debería FALLAR)
        try {
            // TODO: Llamar a hab.reservar() otra vez
            
            System.out.println("Segunda reserva: OK");
        } catch (Exception e) {
            System.out.println("Error esperado: " + e.getMessage());
        }
        
        // 7. Cancelar reserva
        // TODO: Llamar a hab.cancelarReserva()
        
        
        // 8. Mostrar estado final
        System.out.println("Estado final: " + hab);
    }
}
```

## ✅ Solución
<details>
<summary>👉 Click para ver la solución</summary>

```java
package hotel;

import hotel.modelo.*;
import hotel.excepciones.ReservaInvalidaException;

public class PruebaHotel {
    
    public static void main(String[] args) {
        
        System.out.println("=== PRUEBA DEL SISTEMA DE HOTEL ===\n");
        
        // 1. Crear un cliente
        Cliente cliente = new Cliente("Juan García", "666123456", "juan@email.com");
        
        // 2. Crear una habitación disponible
        Habitacion hab = new Habitacion(1, "doble", 70.0, true);
        
        // 3. Mostrar info del cliente
        System.out.println("--- Datos del cliente ---");
        cliente.mostrarInfo();
        
        // 4. Mostrar habitación
        System.out.println("\n--- Habitación ---");
        System.out.println(hab);
        
        // 5. Intentar reservar (debería funcionar)
        System.out.println("\n--- Intentando primera reserva ---");
        try {
            hab.reservar();
            System.out.println("✅ Primera reserva: OK");
        } catch (ReservaInvalidaException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        // 6. Intentar reservar de nuevo (debería FALLAR)
        System.out.println("\n--- Intentando segunda reserva (debe fallar) ---");
        try {
            hab.reservar();
            System.out.println("✅ Segunda reserva: OK");
        } catch (ReservaInvalidaException e) {
            System.out.println("✅ Error esperado: " + e.getMessage());
        }
        
        // 7. Cancelar reserva
        System.out.println("\n--- Cancelando reserva ---");
        hab.cancelarReserva();
        
        // 8. Mostrar estado final
        System.out.println("\n--- Estado final ---");
        System.out.println(hab);
        
        System.out.println("\n=== FIN DE LA PRUEBA ===");
    }
}
```
</details>

---

# 🏆 ¡FELICIDADES!

Si has completado todos los ejercicios, has practicado:

✅ **Clases abstractas** - No se pueden instanciar, definen estructura  
✅ **Herencia** - `extends` y `super()`  
✅ **Interfaces** - Contratos que las clases deben cumplir  
✅ **Implementación** - `implements` y `@Override`  
✅ **Excepciones personalizadas** - `extends Exception`  
✅ **Composición** - Una clase contiene otras (`Reserva` tiene `Cliente`)  
✅ **JDBC básico** - Conexión y consultas a base de datos  

---

## 📚 Siguiente Paso

Ahora revisa el código completo en la carpeta `src/hotel/` y compáralo con tus soluciones.
¡Verás que el código del proyecto tiene más comentarios y funcionalidades extra!
