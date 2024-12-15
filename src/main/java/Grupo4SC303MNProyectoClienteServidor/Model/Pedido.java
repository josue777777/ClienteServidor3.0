package Grupo4SC303MNProyectoClienteServidor.Model;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Pedido {

    private int id; // ID del pedido
    private int clienteId; // ID del cliente
    private int restauranteId; // ID del restaurante
    private LocalDateTime fecha; // Fecha del pedido
    private String estado; // Estado del pedido
    private  String detalles; // Lista de detalles del pedido

    // contador pedido id
    private static int nextId = 1;
    // Constructor
    public Pedido( int clienteId, int restauranteId, List<String> detalles) {
        this.id = nextId++;
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;

        this.fecha = LocalDateTime.now(); // Fecha actual en el momento de la creación del pedido
        this.estado = "Pendiente"; // Estado inicial del pedido
    }
    public  Pedido(int productoId, LocalDateTime fecha, String estado , String detalles){
        this.clienteId = productoId;
        this.fecha = fecha;
        this.estado = estado;
        this.detalles = detalles;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(int restauranteId) {
        this.restauranteId = restauranteId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}