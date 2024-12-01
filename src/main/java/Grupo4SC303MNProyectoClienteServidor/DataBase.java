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
}


// Clase para operaciones relacionadas con usuarios
class UsuariosOperaciones {
    private final DataBase db;

    public UsuariosOperaciones() {
        db = DataBase.getInstance();
    }

    public boolean registrarUsuario(String nombreUsuario, String contrasena) {
        String query = "INSERT INTO Usuarios (NombreUsuario, Contrasena) VALUES (?, ?)";
        try (Connection conexion = db.setConexion();
             PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombreUsuario);
            statement.setString(2, contrasena);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar el usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean autenticarUsuario(String nombreUsuario, String contrasena) {
        String query = "SELECT * FROM Usuarios WHERE NombreUsuario = ? AND Contrasena = ?";
        try (Connection conexion = db.setConexion();
             PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, nombreUsuario);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.err.println("Error al autenticar el usuario: " + e.getMessage());
            return false;
        }
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
