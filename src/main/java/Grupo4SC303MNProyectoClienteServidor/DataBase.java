package Grupo4SC303MNProyectoClienteServidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Clase para gestionar la conexión a la base de datos
public class DataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/UniversidadClienteServidor";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static DataBase instance;

    private DataBase() { }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public Connection setConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

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

//    public boolean registrarDetallePedido(int pedidoID, int menuID, int cantidad, double subtotal) {
//        String query = "INSERT INTO DetallePedido (PedidoID, MenuID, Cantidad, Subtotal) VALUES (?, ?, ?, ?)";
//        try (Connection conexion = db.setConexion();
//             PreparedStatement stmt = conexion.prepareStatement(query)) {
//            stmt.setInt(1, pedidoID);
//            stmt.setInt(2, menuID);
//            stmt.setInt(3, cantidad);
//            stmt.setDouble(4, subtotal);
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.err.println("Error al registrar el detalle del pedido: " + e.getMessage());
//            return false;
//        }
//    }

}





