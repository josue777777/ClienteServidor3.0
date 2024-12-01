package Grupo4SC303MNProyectoClienteServidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String id;


    public Usuario(String nombre, String apellido, String id, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.email = email;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
