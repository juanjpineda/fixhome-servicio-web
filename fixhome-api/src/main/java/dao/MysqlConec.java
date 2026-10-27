package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Abre la conexión con la base de datos MySQL
public class MysqlConec {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/fixhome_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "admin2026";

    // Devuelve una conexión abierta o lanza un error si no puede conectar
    public Connection establecerConexion() throws SQLException {
        try {
            // Carga el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}