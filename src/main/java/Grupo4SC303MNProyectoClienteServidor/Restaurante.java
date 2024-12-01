package Grupo4SC303MNProyectoClienteServidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

// Clase para operaciones relacionadas con restaurantes
class RestauranteBD {
    private final DataBase db;

    public RestauranteBD() {
        db = DataBase.getInstance();
    }

    public boolean agregarRestauranteBD(String nombre, String direccion, String telefono) {
        String query = "INSERT INTO Restaurantes (Nombre, Direccion, Telefono) VALUES (?, ?, ?)";
        try (Connection conexion = db.setConexion();
             PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar el restaurante: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarRestaurantesBD() {
        List<String> restaurantes = new ArrayList<>();
        String query = "SELECT * FROM Restaurantes";
        try (Connection conexion = db.setConexion();
             PreparedStatement statement = conexion.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                restaurantes.add("ID: " + resultSet.getInt("ID") + ", Nombre: " + resultSet.getString("Nombre") +
                        ", Dirección: " + resultSet.getString("Direccion") + ", Teléfono: " + resultSet.getString("Telefono"));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar restaurantes: " + e.getMessage());
        }
        return restaurantes;
    }
}

// Clase para operaciones relacionadas con menús
class MenuOperaciones {
    private final DataBase db;

    public MenuOperaciones() {
        db = DataBase.getInstance();
    }

    public boolean agregarPlato(String nombre, String descripcion, double precio) {
        String query = "INSERT INTO Menus (Nombre, Descripcion, Precio) VALUES (?, ?, ?)";
        try (Connection conexion = db.setConexion();
             PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar el plato: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarMenu() {
        List<String> menus = new ArrayList<>();
        String query = "SELECT * FROM Menus";
        try (Connection conexion = db.setConexion();
             PreparedStatement statement = conexion.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                menus.add("ID: " + resultSet.getInt("ID") + ", Nombre: " + resultSet.getString("Nombre") +
                        ", Descripción: " + resultSet.getString("Descripcion") + ", Precio: $" + resultSet.getDouble("Precio"));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar el menú: " + e.getMessage());
        }
        return menus;
    }
}
