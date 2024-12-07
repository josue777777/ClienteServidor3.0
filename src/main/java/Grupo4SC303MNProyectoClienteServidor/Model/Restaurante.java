package Grupo4SC303MNProyectoClienteServidor.Model;

import java.util.Arrays;

public class Restaurante {

    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String descripcion; // Información adicional sobre el restaurante
    private Menu[] menu; // Relación con un menú

    public Restaurante(int id, String nombre, String direccion, String telefono, String descripcion, Menu[] menu) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.menu = menu;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Restaurante: " +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", menu=" + Arrays.toString(menu) +
                '}';
    }
}
