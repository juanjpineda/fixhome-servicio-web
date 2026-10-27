// Pantalla principal: permite alternar entre registro e inicio de sesión
import { useState } from 'react'
import FormularioAuth from './components/FormularioAuth'

function App() {
  // Estado: cuál de los dos formularios se muestra
  const [modo, setModo] = useState('login')

  return (
    <div className="container py-5" style={{ maxWidth: 440 }}>
      <h1 className="text-center mb-4" style={{ color: '#0f2a47' }}>FixHome</h1>

      <div className="card shadow-sm">
        <div className="card-body p-4">
          {/* Pestañas para cambiar de formulario */}
          <div className="btn-group w-100 mb-4">
            <button
              type="button"
              className={`btn ${modo === 'login' ? 'btn-primary' : 'btn-outline-primary'}`}
              onClick={() => setModo('login')}
            >
              Iniciar sesión
            </button>
            <button
              type="button"
              className={`btn ${modo === 'registro' ? 'btn-primary' : 'btn-outline-primary'}`}
              onClick={() => setModo('registro')}
            >
              Registrarme
            </button>
          </div>

          {/* key hace que el formulario se reinicie al cambiar de pestaña */}
          <FormularioAuth key={modo} modo={modo} />
        </div>
      </div>
    </div>
  )
}

export default App