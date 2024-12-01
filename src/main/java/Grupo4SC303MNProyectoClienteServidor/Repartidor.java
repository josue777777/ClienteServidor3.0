package Grupo4SC303MNProyectoClienteServidor;

public class Repartidor extends Usuario {

    private String codigoEmpleado;


    public Repartidor(String nombre, String apellido, String id, String email,String codigoEmpleado) {
        super(nombre, apellido, id, email);
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }


}
