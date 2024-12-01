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





