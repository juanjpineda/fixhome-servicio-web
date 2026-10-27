package util;

// Reglas de validación para el registro
public class Validaciones {

    // Devuelve un mensaje de error, o null si los datos son válidos
    public static String validar(String usuario, String contrasena) {
        // Campos obligatorios
        if (usuario == null || usuario.trim().isEmpty()
                || contrasena == null || contrasena.isEmpty()) {
            return "El usuario y la contraseña son obligatorios";
        }
        // Usuario: 4 a 30 caracteres, solo letras, números, punto o guion bajo
        if (!usuario.trim().matches("[A-Za-z0-9_.]{4,30}")) {
            return "El usuario debe tener entre 4 y 30 caracteres (letras, números, punto o guion bajo)";
        }
        // Contraseña: 6 a 50 caracteres
        if (contrasena.length() < 6 || contrasena.length() > 50) {
            return "La contraseña debe tener entre 6 y 50 caracteres";
        }
        return null;
    }
}