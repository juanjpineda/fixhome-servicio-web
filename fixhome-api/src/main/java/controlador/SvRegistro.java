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
import util.Validaciones;

// Servicio web de registro: POST /api/registro
@WebServlet(name = "SvRegistro", urlPatterns = {"/api/registro"})
public class SvRegistro extends HttpServlet {

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

        // 2. Validar los datos
        String error = Validaciones.validar(usuario, contrasena);
        if (error != null) {
            Json.responder(response, 400, false, error);
            return;
        }

        // 3. Cifrar la contraseña y guardar
        try {
            String cifrada = BCrypt.hashpw(contrasena, BCrypt.gensalt());
            boolean registrado = dao.registrar(usuario, cifrada);

            if (registrado) {
                Json.responder(response, 201, true, "Usuario registrado correctamente");
            } else {
                Json.responder(response, 409, false, "El usuario ya existe");
            }
        } catch (SQLException e) {
            getServletContext().log("Error al registrar", e);
            Json.responder(response, 500, false,
                    "Error del servidor. Intenta de nuevo más tarde");
        }
    }
}