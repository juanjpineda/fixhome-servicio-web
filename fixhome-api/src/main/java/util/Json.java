package util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Cuenta;

// Funciones para leer el JSON que llega y responder en JSON
public class Json {

    private static final Gson GSON = new Gson();

    // Lee el cuerpo de la petición y lo convierte en un objeto Cuenta.
    // Devuelve null si el JSON está vacío o mal formado
    public static Cuenta leerCuenta(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        StringBuilder cuerpo = new StringBuilder();
        try (BufferedReader lector = request.getReader()) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                cuerpo.append(linea);
            }
        }
        try {
            return GSON.fromJson(cuerpo.toString(), Cuenta.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    // Responde con un código HTTP y un JSON: {"exito": true/false, "mensaje": "..."}
    public static void responder(HttpServletResponse response, int estado,
                                 boolean exito, String mensaje) throws IOException {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("exito", exito);
        cuerpo.put("mensaje", mensaje);

        response.setStatus(estado);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(GSON.toJson(cuerpo));
    }
}