package Grupo4SC303MNProyectoClienteServidor.Model;
import java.time.LocalDateTime;
import java.util.List;
public class Pedido {

    private int id; // ID del pedido
    private int clienteId; // ID del cliente
    private int restauranteId; // ID del restaurante
    private LocalDateTime fecha; // Fecha del pedido
    private String estado; // Estado del pedido
    private  String detalles; // Lista de detalles del pedido

    // Constructor
    public Pedido(int id, int clienteId, int restauranteId, LocalDateTime fecha, String estado, String detalles) {
        this.id = id;
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
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