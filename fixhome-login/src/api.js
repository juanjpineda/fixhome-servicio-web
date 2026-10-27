// Funciones para llamar al servicio web de Java (back-end en Tomcat)

// Dirección base del servicio
const URL_BASE = 'http://localhost:8080/fixhome-api/api'

// Envía usuario y contraseña a la ruta indicada ('registro' o 'login').
// Devuelve { exito, mensaje } para que la pantalla lo muestre.
export async function enviar(ruta, usuario, contrasena) {
  try {
    const respuesta = await fetch(`${URL_BASE}/${ruta}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ usuario, contrasena }),
    })
    // El servicio siempre responde un JSON: { exito, mensaje }
    return await respuesta.json()
  } catch {
    // Pasa si Tomcat está apagado o no hay conexión
    return { exito: false, mensaje: 'No se pudo conectar con el servidor' }
  }
}