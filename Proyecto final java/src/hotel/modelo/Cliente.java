package hotel.modelo;

/**
 * Clase Cliente que hereda de Persona
 * Representa un cliente del hotel
 */
public class Cliente extends Persona {
    
    private int id;
    private String email;
    
    // Constructor sin id (para clientes nuevos)
    public Cliente(String nombre, String telefono, String email) {
        super(nombre, telefono); // Llama al constructor de Persona
        this.email = email;
    }
    
    // Constructor con id (cuando se lee de la BD)
    public Cliente(int id, String nombre, String telefono, String email) {
        super(nombre, telefono);
        this.id = id;
        this.email = email;
    }
    
    // Implementacion del metodo abstracto
    @Override
    public void mostrarInfo() {
        System.out.println("Cliente: " + nombre);
        System.out.println("Telefono: " + telefono);
        System.out.println("Email: " + email);
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", email=" + email + "]";
    }
}
