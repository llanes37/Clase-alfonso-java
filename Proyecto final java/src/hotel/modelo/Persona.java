package hotel.modelo;

/**
 * Clase abstracta Persona
 * Sirve como clase padre para Cliente
 */
public abstract class Persona {
    
    // Atributos protected para que los herede Cliente
    protected String nombre;
    protected String telefono;
    
    // Constructor
    public Persona(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }
    
    // Metodo abstracto que deben implementar las clases hijas
    public abstract void mostrarInfo();
    
    // Getters y setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
