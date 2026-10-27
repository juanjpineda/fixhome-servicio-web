package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Consultas a la tabla cuenta
public class CuentaDAO {

    // Código de error de MySQL cuando se repite un valor UNIQUE
    private static final int ERROR_DUPLICADO = 1062;

    // Guarda una cuenta. Devuelve true si se guardó y false si el usuario ya existe
    public boolean registrar(String usuario, String contrasenaCifrada) throws SQLException {
        String sql = "INSERT INTO cuenta (usuario, contrasena) VALUES (?, ?)";
        try (Connection cn = new MysqlConec().establecerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasenaCifrada);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == ERROR_DUPLICADO) {
                return false;
            }
            throw e;
        }
    }

    // Busca la contraseña cifrada de un usuario. Devuelve null si no existe
    public String obtenerContrasenaCifrada(String usuario) throws SQLException {
        String sql = "SELECT contrasena FROM cuenta WHERE usuario = ?";
        try (Connection cn = new MysqlConec().establecerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("contrasena") : null;
            }
        }
    }
}