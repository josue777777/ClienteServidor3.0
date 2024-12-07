package Grupo4SC303MNProyectoClienteServidor.Controller;

import Grupo4SC303MNProyectoClienteServidor.Utilidades.DataBase;

import java.sql.*;

public class ControladorAdministrador {

    private static final String URL = "jdbc:mysql://localhost:3306/UniversidadClienteServidor";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static DataBase instance;



    // Metodo para establecer la conexión a la base de datos
    public Connection setConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
