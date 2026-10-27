// Formulario que sirve para registrarse o para iniciar sesión
import { useState } from 'react'
import { enviar } from '../api'

function FormularioAuth({ modo }) {
  const esRegistro = modo === 'registro'

  // Estado: lo que se escribe, el resultado y si está enviando
  const [usuario, setUsuario] = useState('')
  const [contrasena, setContrasena] = useState('')
  const [resultado, setResultado] = useState(null)
  const [enviando, setEnviando] = useState(false)

  // Evento onSubmit: llama al servicio y muestra la respuesta
  const manejarEnvio = async (evento) => {
    evento.preventDefault()
    setEnviando(true)
    setResultado(null)

    const respuesta = await enviar(esRegistro ? 'registro' : 'login', usuario, contrasena)

    setResultado(respuesta)
    setEnviando(false)
    // Si el registro fue exitoso, se limpia el formulario
    if (esRegistro && respuesta.exito) {
      setUsuario('')
      setContrasena('')
    }
  }

  return (
    <form onSubmit={manejarEnvio}>
      <div className="mb-3">
        <label className="form-label" htmlFor="usuario">Usuario</label>
        <input
          id="usuario"
          type="text"
          className="form-control"
          value={usuario}
          onChange={(e) => setUsuario(e.target.value)}
          placeholder="Tu nombre de usuario"
        />
      </div>

      <div className="mb-3">
        <label className="form-label" htmlFor="contrasena">Contraseña</label>
        <input
          id="contrasena"
          type="password"
          className="form-control"
          value={contrasena}
          onChange={(e) => setContrasena(e.target.value)}
          placeholder="Tu contraseña"
        />
      </div>

      <button type="submit" className="btn btn-primary w-100" disabled={enviando}>
        {enviando ? 'Enviando...' : esRegistro ? 'Registrarme' : 'Iniciar sesión'}
      </button>

      {/* Mensaje que devuelve el servicio: verde si salió bien, rojo si falló */}
      {resultado && (
        <div className={`alert mt-3 ${resultado.exito ? 'alert-success' : 'alert-danger'}`} role="alert">
          {resultado.mensaje}
        </div>
      )}
    </form>
  )
}

export default FormularioAuth