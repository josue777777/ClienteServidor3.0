package Grupo4SC303MNProyectoClienteServidor.Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Clase para gestionar la conexión a la base de datos
public class DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/UniversidadClienteServidor";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static DataBase instance;

    // Constructor privado
    private DataBase() { }

    // Singleton para obtener una única instancia de la clase
    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    // Metodo para establecer la conexión a la base de datos
    public Connection setConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Metodo para obtener el nombre del cliente por su numero de ID
    public String obtenerNombreCliente(int clienteID) {
        String query = "SELECT Nombre FROM Clientes WHERE ID = ?";
        try (Connection conexion = setConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, clienteID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Nombre");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el nombre del cliente: " + e.getMessage());
        }
        return null;
    }

    // Metodo para obtener el nombre de un Restaurante por su ID
    public String obtenerNombreRestaurante(int restauranteID) {
        String query = "SELECT Nombre FROM Restaurantes WHERE ID = ?";
        try (Connection conexion = setConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, restauranteID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("Nombre");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el nombre del restaurante: " + e.getMessage());
        }
        return null;
    }

    // Metodo para registrar un detalle de un pedido
    public boolean registrarDetallePedido(int pedidoID, int menuID, int cantidad, double subtotal) {
        String query = "INSERT INTO DetallePedido (PedidoID, MenuID, Cantidad, Subtotal) VALUES (?, ?, ?, ?)";
        try (Connection conexion = setConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, pedidoID);
            stmt.setInt(2, menuID);
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, subtotal);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar el detalle del pedido: " + e.getMessage());
        }
        return false;
    }

    public boolean registrarMenu(int restauranteID, String nombre, double precio, String categoria, String descripcion, String urlImagen, int tiempoPreparacion) {
        // Consulta para insertar el menú
        String registrarMenuQuery = "INSERT INTO Menus (RestauranteID, Nombre, Precio, Categoria, Descripcion, Disponible, URLImagen, TiempoPreparacion) VALUES (?, ?, ?, ?, ?, TRUE, ?, ?)";

        try (Connection conexion = setConexion();
             PreparedStatement registrarMenuStmt = conexion.prepareStatement(registrarMenuQuery)) {

            // Establecer los valores para el menú
            registrarMenuStmt.setInt(1, restauranteID);
            registrarMenuStmt.setString(2, nombre);
            registrarMenuStmt.setDouble(3, precio);
            registrarMenuStmt.setString(4, categoria);
            registrarMenuStmt.setString(5, descripcion);
            registrarMenuStmt.setString(6, urlImagen);
            registrarMenuStmt.setInt(7, tiempoPreparacion);

            return registrarMenuStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar el menú: " + e.getMessage());
            return false;
        }
    }



}
