package Grupo4SC303MNProyectoClienteServidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorCliente {


    private final DataBase db;

    public ControladorCliente() {
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

