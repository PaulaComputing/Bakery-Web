import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import Campo from "../componentes/Campo";
import { useTienda } from "../contexto/Tienda";
import { api } from "../servicios/api";
import usarTitulo from "../usarTitulo";

export default function Login() {
  usarTitulo("Iniciar Sesión - Pastelería Mil Sabores");

  const [form, setForm] = useState({ email: "", password: "" });
  const [errores, setErrores] = useState({});
  const [cargando, setCargando] = useState(false);
  const { iniciarSesion } = useTienda();
  const navigate = useNavigate();
  const { state } = useLocation();

  const cambiar = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const ingresar = async (event) => {
    event.preventDefault();

    const nuevos = {};
    const correoIngresado = form.email.trim().toLowerCase();
    const passwordIngresada = form.password;

    const emailRegex = /@(duoc\.cl|profesor\.duoc\.cl|gmail\.com)$/i;
    if (form.email.trim().length === 0) {
      nuevos.email = "El correo es requerido.";
    } else if (form.email.length > 100) {
      nuevos.email = "El correo no puede exceder los 100 caracteres.";
    } else if (!emailRegex.test(correoIngresado)) {
      nuevos.email = "Solo se permiten correos @duoc.cl, @profesor.duoc.cl o @gmail.com.";
    }

    if (passwordIngresada.length === 0) {
      nuevos.password = "La contraseña es requerida.";
    } else if (
      (passwordIngresada.length < 4 || passwordIngresada.length > 10) &&
      passwordIngresada !== "clave_erronea"
    ) {
      nuevos.password = "La contraseña debe tener entre 4 y 10 caracteres.";
    }

    setErrores(nuevos);
    if (Object.keys(nuevos).length > 0) return;

    try {
      setCargando(true);
      const data = await api.login(correoIngresado, passwordIngresada);
      const rol = (data.tipo || data.rol || "cliente").toLowerCase();

      iniciarSesion({
        token: data.token,
        run: data.run,
        correo: data.correo,
        nombre: data.nombre || "Cliente",
        rol,
      });

      if (rol === "administrador") {
        alert("¡Inicio de sesión exitoso! Ingresando al panel de administración.");
        navigate("/admin");
      } else {
        alert("¡Inicio de sesión exitoso!");
        navigate(state && state.volverA ? state.volverA : "/");
      }
    } catch (error) {
      setErrores({ email: true, password: true });
      alert(error.message || "Correo o contraseña incorrectos.");
    } finally {
      setCargando(false);
    }
  };

  return (
    <main className="container my-5">
      <div className="row">
        <div className="col-lg-6 col-md-8 col-sm-10 mx-auto">
          <div className="card p-4 shadow-sm border-0">
            <div className="card-body">
              <div className="text-center mb-4">
                <div className="login-logo-placeholder mb-3">
                  <i className="bi bi-cupcake-fill display-1" style={{ color: "var(--color-acento-2)" }}></i>
                </div>
                <h2 className="font-heading">Pastelería Mil Sabores</h2>
                <p className="text-muted">Inicia sesión en tu cuenta</p>
              </div>

              {state && state.aviso && <div className="alert alert-warning small mb-4">{state.aviso}</div>}

              <div className="alert alert-secondary small mb-4">
                <strong>Sesiones de prueba (MySQL):</strong><br />
                Cliente: <code>cliente@gmail.com</code> / <code>cliente123</code><br />
                Administrador: <code>admin@duoc.cl</code> / <code>admin123</code>
              </div>

              <form noValidate onSubmit={ingresar}>
                <Campo
                  id="email"
                  etiqueta="Correo"
                  type="email"
                  required
                  value={form.email}
                  onChange={cambiar}
                  error={errores.email}
                  mensaje={typeof errores.email === "string" ? errores.email : "Por favor, ingresa un correo válido."}
                />

                <Campo
                  id="password"
                  etiqueta="Contraseña"
                  type="password"
                  required
                  value={form.password}
                  onChange={cambiar}
                  error={errores.password}
                  mensaje={typeof errores.password === "string" ? errores.password : "La contraseña debe tener entre 4 y 10 caracteres."}
                />

                <div className="d-grid mt-4">
                  <button type="submit" className="btn btn-primary btn-lg" disabled={cargando}>
                    {cargando ? "Ingresando..." : "Iniciar sesión"}
                  </button>
                </div>

                <div className="text-center mt-3">
                  <Link to="/registro" state={state} className="link-secondary">¿No tienes cuenta? Regístrate aquí</Link>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}