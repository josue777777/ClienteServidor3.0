package Grupo4SC303MNProyectoClienteServidor;

public class Restaurante {

    private String nombre;
    private String direccion;
    private String estiloComida;
    private String horario;


    public Restaurante(String nombre, String direccion, String estiloComida, String horario) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.estiloComida = estiloComida;
        this.horario = horario;

    }

    public String mostrarMenu() {
        // Implementación del metodo mostrar menu
        return "";
    }


    public static void registrarPedidos() {
        // Implementación del metodo de registrar pedidos
    }

    public static void consultarPedido() {
        // Implementación del metododo de consultar pedidos
    }

    public static void listarPedidos() {
        // Implementación del metodo de listar pedidos ( nuevo metodo aparte de los que tenemos en el uml)
    }

    public static void eliminarPedido() {
        // Implementación del metodo de eliminar pedidos ( nuevo metodo aparte de los que tenemos en el uml)
    }

    public static void actualizarPedido() {
        // Implementación del metodo actualizar pedido (se cambia el nombre del metodo con respecto al que tenemos en el uml ,  es una palabra mas acertada)
    }
}
