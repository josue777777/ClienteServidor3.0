package Grupo4SC303MNProyectoClienteServidor.Model;

public class Administrador {

    private int id;
    private String nombre;
    private String apellido;
    private String contraseña;
    private Restaurante restaurante; // Relación con un restaurante

    public Administrador(int id, String nombre, String apellido, String correo, String telefono, String contraseña, Restaurante restaurante) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.restaurante = restaurante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public String toString() {
        return "Administrador: " +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", restaurante=" + restaurante;
    }
}