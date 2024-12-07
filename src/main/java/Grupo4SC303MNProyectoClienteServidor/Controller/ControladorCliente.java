package Grupo4SC303MNProyectoClienteServidor.Controller;

import Grupo4SC303MNProyectoClienteServidor.Utilidades.DataBase;

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
    public boolean esAdministrador(String nombreUsuario) {
        String query = "SELECT COUNT(*) FROM Administradores a JOIN Usuarios u ON a.UsuarioID = u.ID WHERE u.NombreUsuario = ?";
        try (Connection conexion = db.setConexion();
             PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nombreUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar si el usuario es administrador: " + e.getMessage());
        }
        return false;
    }

    public boolean registrarAdministrador(String nombreUsuario, String contrasena, String nombre, String apellido, String nombreRestaurante, String direccionRestaurante, String tipoComida) {
        // Consulta para insertar el usuario
        String registrarUsuarioQuery = "INSERT INTO Usuarios (NombreUsuario, Contrasena) VALUES (?, ?)";
        // Consulta para obtener el ID del usuario recién creado
        String obtenerUsuarioIDQuery = "SELECT ID FROM Usuarios WHERE NombreUsuario = ?";
        // Consulta para insertar el administrador
        String registrarAdministradorQuery = "INSERT INTO Administradores (UsuarioID, Nombre, Apellido, NombreRestauranteAcargo, DireccionRestaurante, TipoComida) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = db.setConexion();
             PreparedStatement registrarUsuarioStmt = conexion.prepareStatement(registrarUsuarioQuery);
             PreparedStatement obtenerUsuarioIDStmt = conexion.prepareStatement(obtenerUsuarioIDQuery);
             PreparedStatement registrarAdministradorStmt = conexion.prepareStatement(registrarAdministradorQuery)) {

            // Registrar el usuario
            registrarUsuarioStmt.setString(1, nombreUsuario);
            registrarUsuarioStmt.setString(2, contrasena);
            if (registrarUsuarioStmt.executeUpdate() > 0) {
                // Obtener el ID del usuario
                obtenerUsuarioIDStmt.setString(1, nombreUsuario);
                ResultSet rs = obtenerUsuarioIDStmt.executeQuery();
                if (rs.next()) {
                    int usuarioID = rs.getInt("ID");

                    // Registrar el administrador con la nueva estructura de la tabla
                    registrarAdministradorStmt.setInt(1, usuarioID);
                    registrarAdministradorStmt.setString(2, nombre);
                    registrarAdministradorStmt.setString(3, apellido);
                    registrarAdministradorStmt.setString(4, nombreRestaurante);
                    registrarAdministradorStmt.setString(5, direccionRestaurante);
                    registrarAdministradorStmt.setString(6, tipoComida);

                    return registrarAdministradorStmt.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar el administrador: " + e.getMessage());
        }
        return false;
    }


}


