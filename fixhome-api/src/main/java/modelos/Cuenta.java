package modelos;

// Representa los datos que llegan en el JSON: usuario y contraseña
public class Cuenta {

    private String usuario;
    private String contrasena;

    public Cuenta() { }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}