package controlador;

import dao.CuentaDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Cuenta;
import org.mindrot.jbcrypt.BCrypt;
import util.Json;

// Servicio web de inicio de sesión: POST /api/login
@WebServlet(name = "SvLogin", urlPatterns = {"/api/login"})
public class SvLogin extends HttpServlet {

    private final CuentaDAO dao = new CuentaDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Leer el JSON recibido
        Cuenta datos = Json.leerCuenta(request);
        if (datos == null) {
            Json.responder(response, 400, false,
                    "El cuerpo debe ser un JSON con usuario y contrasena");
            return;
        }

        String usuario = datos.getUsuario() == null ? null : datos.getUsuario().trim();
        String contrasena = datos.getContrasena();

        // 2. Los dos campos son obligatorios
        if (usuario == null || usuario.isEmpty()
                || contrasena == null || contrasena.isEmpty()) {
            Json.responder(response, 400, false,
                    "El usuario y la contraseña son obligatorios");
            return;
        }

        // 3. Comparar la contraseña con la guardada (cifrada)
        try {
            String cifrada = dao.obtenerContrasenaCifrada(usuario);

            if (cifrada != null && BCrypt.checkpw(contrasena, cifrada)) {
                Json.responder(response, 200, true, "Autenticación satisfactoria");
            } else {
                // Mismo mensaje si el usuario no existe o la clave es incorrecta
                Json.responder(response, 401, false, "Error en la autenticación");
            }
        } catch (SQLException e) {
            getServletContext().log("Error al iniciar sesión", e);
            Json.responder(response, 500, false,
                    "Error del servidor. Intenta de nuevo más tarde");
        }
    }
}