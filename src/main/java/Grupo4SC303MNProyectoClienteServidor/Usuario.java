package Grupo4SC303MNProyectoClienteServidor;

public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String id;


    public Usuario(String nombre, String apellido, String id, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
