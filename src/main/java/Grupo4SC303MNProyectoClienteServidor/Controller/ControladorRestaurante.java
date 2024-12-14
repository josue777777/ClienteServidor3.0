package Grupo4SC303MNProyectoClienteServidor.Controller;

import Grupo4SC303MNProyectoClienteServidor.Utilidades.DataBase;
import Grupo4SC303MNProyectoClienteServidor.Model.Plato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorRestaurante {
    private final DataBase db;

    public ControladorRestaurante() {
        db = DataBase.getInstance();
    }

    // Métodos relacionados con pedidos
    public boolean registrarPedidos(int clienteID, int restauranteID) {
        String query = "INSERT INTO Pedidos (ClienteID, RestauranteID) VALUES (?, ?)";
        try (Connection conexion = db.setConexion()) {
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, clienteID);
            statement.setInt(2, restauranteID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar el pedido: " + e.getMessage());
            return false;
        }
    }

    public String consultarPedido(int pedidoID) {
        String query = "SELECT * FROM Pedidos WHERE ID = ?";
        try (Connection conexion = db.setConexion()) {
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, pedidoID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return "ID: " + resultSet.getInt("ID") +
                        ", ClienteID: " + resultSet.getInt("ClienteID") +
                        ", RestauranteID: " + resultSet.getInt("RestauranteID") +
                        ", Fecha: " + resultSet.getTimestamp("Fecha") +
                        ", Estado: " + resultSet.getString("Estado");
            } else {
                return "No se encontró el pedido con ID: " + pedidoID;
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el pedido: " + e.getMessage());
            return null;
        }
    }

    public List<String> listarPedidos() {
        List<String> pedidos = new ArrayList<>();
        String query = "SELECT * FROM Pedidos";
        try (Connection conexion = db.setConexion()) {
            PreparedStatement statement = conexion.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pedidos.add("ID: " + resultSet.getInt("ID") +
                        ", ClienteID: " + resultSet.getInt("ClienteID") +
                        ", RestauranteID: " + resultSet.getInt("RestauranteID") +
                        ", Fecha: " + resultSet.getTimestamp("Fecha") +
                        ", Estado: " + resultSet.getString("Estado"));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    public boolean eliminarPedido(int pedidoID) {
        String query = "DELETE FROM Pedidos WHERE ID = ?";
        try (Connection conexion = db.setConexion()) {
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, pedidoID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarPedido(int pedidoID, String nuevoEstado) {
        String query = "UPDATE Pedidos SET Estado = ? WHERE ID = ?";
        try (Connection conexion = db.setConexion()) {
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, nuevoEstado);
            statement.setInt(2, pedidoID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el pedido: " + e.getMessage());
            return false;
        }
    }

    // Métodos relacionados con restaurantes
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

    // Métodos relacionados con menús
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

    public boolean registrarPedidoDetalle(int pedidoID, int productoID, int cantidad) {
        String query = "INSERT INTO PedidoDetalles (PedidoID, ProductoID, Cantidad) VALUES (?, ?, ?)";
        try (Connection conexion = db.setConexion()) {
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1, pedidoID);
            statement.setInt(2, productoID);
            statement.setInt(3, cantidad);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar el detalle del pedido: " + e.getMessage());
            return false;
        }
    }


    private boolean registrarDetallePedido(int pedidoID, int menuID, int cantidad) {
        String query = "SELECT Precio FROM Menus WHERE ID = ?";
        try (Connection conexion = db.setConexion();
             PreparedStatement statement = conexion.prepareStatement(query)) {

            statement.setInt(1, menuID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double precioUnitario = resultSet.getDouble("Precio");
                double subtotal = precioUnitario * cantidad;

                // Inserta el detalle del pedido
                String insertQuery = "INSERT INTO DetallePedido (PedidoID, MenuID, Cantidad, Subtotal) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertStatement = conexion.prepareStatement(insertQuery)) {
                    insertStatement.setInt(1, pedidoID);
                    insertStatement.setInt(2, menuID);
                    insertStatement.setInt(3, cantidad);
                    insertStatement.setDouble(4, subtotal);
                    return insertStatement.executeUpdate() > 0;
                }
            } else {
                System.err.println("MenuID " + menuID + " no encontrado.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar el detalle del pedido: " + e.getMessage());
            return false;
        }
    }


    public List<String> listarMenuPorId(int restauranteId) {
        List<String> menu = new ArrayList<>();
        String query = "SELECT m.ID, m.Nombre, m.Precio, m.Tipo AS Categoria, m.Descripcion " +
                "FROM Menus m " +
                "WHERE m.RestauranteID = ?";

        try (Connection conexion = db.setConexion();
             PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, restauranteId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String platillo = String.format("ID: %d - %s - %s - %.2f - %s",
                            resultSet.getInt("ID"),
                            resultSet.getString("Nombre"),
                            resultSet.getString("Categoria"),
                            resultSet.getDouble("Precio"),
                            resultSet.getString("Descripcion"));
                    menu.add(platillo);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar el menú: " + e.getMessage());
        }

        return menu;

    }
}
