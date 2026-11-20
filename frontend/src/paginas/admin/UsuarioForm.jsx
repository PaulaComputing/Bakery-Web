import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Campo from "../../componentes/Campo";
import { regionesComunas } from "../../datos/comunas";
import { validarRun } from "../../utilidades/validarRun";
import { api } from "../../servicios/api";
import usarTitulo from "../../usarTitulo";

const formVacio = {
  run: "", nombre: "", apellidos: "", correo: "", password: "", fechaNacimiento: "",
  tipo: "", region: "", comuna: "", direccion: ""
};

export default function UsuarioForm() {
  usarTitulo("Usuario - Administrador");

  const { run: runEditar } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState(formVacio);
  const [errores, setErrores] = useState({});
  const [cargando, setCargando] = useState(!!runEditar);
  const [guardando, setGuardando] = useState(false);

  useEffect(() => {
    if (!runEditar) return;
    api.getUsuario(runEditar)
      .then((usuarioActual) => {
        setForm({
          run: usuarioActual.run,
          nombre: usuarioActual.nombre,
          apellidos: usuarioActual.apellidos || "",
          correo: usuarioActual.correo,
          password: "",
          fechaNacimiento: usuarioActual.fechaNacimiento || "",
          tipo: usuarioActual.tipo,
          region: usuarioActual.region || "",
          comuna: usuarioActual.comuna || "",
          direccion: usuarioActual.direccion || "",
        });
      })
      .catch(() => navigate("/admin/usuarios", { replace: true }))
      .finally(() => setCargando(false));
  }, [runEditar, navigate]);

  const cambiar = (e) => {
    const { name, value } = e.target;
    setForm(name === "region" ? { ...form, region: value, comuna: "" } : { ...form, [name]: value });
  };

  const guardar = async (event) => {
    event.preventDefault();

    const emailRegex = /@(duoc\.cl|profesor\.duoc\.cl|gmail\.com)$/i;
    const nuevos = {};

    const runLimpio = form.run.trim().toUpperCase();
    if (runLimpio.length < 7 || runLimpio.length > 9 || !validarRun(runLimpio)) {
      nuevos.run = true;
    }

    if (form.nombre.trim().length === 0 || form.nombre.length > 50) nuevos.nombre = true;
    if (form.apellidos.trim().length === 0 || form.apellidos.length > 100) nuevos.apellidos = true;
    if (form.correo.length === 0 || form.correo.length > 100 || !emailRegex.test(form.correo)) nuevos.correo = true;

    // Al crear es obligatoria; al editar solo se valida si el admin escribió una nueva contraseña
    if (!runEditar && (form.password.length < 4 || form.password.length > 10)) {
      nuevos.password = true;
    } else if (runEditar && form.password.length > 0 && (form.password.length < 4 || form.password.length > 10)) {
      nuevos.password = true;
    }

    if (!form.tipo) nuevos.tipo = true;
    if (form.direccion.trim().length === 0 || form.direccion.length > 300) nuevos.direccion = true;
    if (!form.region) nuevos.region = true;
    if (!form.comuna) nuevos.comuna = true;

    setErrores(nuevos);
    if (Object.keys(nuevos).length > 0) return;

    const payload = {
      run: runEditar ? runEditar : runLimpio,
      nombre: form.nombre.trim(),
      apellidos: form.apellidos.trim(),
      correo: form.correo.trim().toLowerCase(),
      password: form.password,
      fechaNacimiento: form.fechaNacimiento,
      tipo: form.tipo,
      region: form.region,
      comuna: form.comuna,
      direccion: form.direccion.trim(),
    };

    try {
      setGuardando(true);
      if (runEditar) {
        await api.actualizarUsuario(runEditar, payload);
      } else {
        await api.crearUsuario(payload);
      }
      alert(runEditar ? "Usuario actualizado correctamente." : "Usuario creado correctamente.");
      navigate("/admin/usuarios");
    } catch (error) {
      alert(error.message || "Error al guardar el usuario.");
    } finally {
      setGuardando(false);
    }
  };

  if (cargando) {
    return (
      <main className="container my-5">
        <p className="text-muted">Cargando datos del usuario...</p>
      </main>
    );
  }

  return (
    <main className="container my-5">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h1 className="font-heading display-5 mb-0">{runEditar ? "Editar usuario" : "Nuevo usuario"}</h1>
        <Link to="/admin/usuarios" className="btn btn-cart"><i className="bi bi-arrow-left"></i> Volver</Link>
      </div>

      <div className="card" style={{ maxWidth: "760px" }}>
        <div className="card-body">
          <form noValidate onSubmit={guardar}>
            <Campo id="run" etiqueta="Run (sin puntos ni guion, ej: 19011022K)" type="text" minLength="7" maxLength="9" required readOnly={!!runEditar}
              value={form.run} onChange={cambiar} error={errores.run}
              mensaje="Ingresa un run válido (7 a 9 caracteres, sin puntos ni guion)." />

            <div className="row">
              <Campo id="nombre" etiqueta="Nombre" type="text" maxLength="50" required clase="col-md-6 mb-3"
                value={form.nombre} onChange={cambiar} error={errores.nombre}
                mensaje="El nombre es requerido (máximo 50 caracteres)." />

              <Campo id="apellidos" etiqueta="Apellidos" type="text" maxLength="100" required clase="col-md-6 mb-3"
                value={form.apellidos} onChange={cambiar} error={errores.apellidos}
                mensaje="Los apellidos son requeridos (máximo 100 caracteres)." />
            </div>

            <Campo id="correo" etiqueta="Correo" type="email" maxLength="100" required
              value={form.correo} onChange={cambiar} error={errores.correo}
              mensaje="Solo se permiten correos @duoc.cl, @profesor.duoc.cl o @gmail.com." />

            <Campo id="password" etiqueta={runEditar ? "Nueva contraseña (dejar vacío para mantener)" : "Contraseña"} type="password" minLength="4" maxLength="10" required={!runEditar}
              value={form.password} onChange={cambiar} error={errores.password}
              mensaje="La contraseña debe tener entre 4 y 10 caracteres." />

            <div className="row">
              <Campo id="fechaNacimiento" etiqueta="Fecha de nacimiento (opcional)" type="date" clase="col-md-6 mb-3"
                value={form.fechaNacimiento} onChange={cambiar} />

              <Campo id="tipo" etiqueta="Tipo de usuario" componente="select" required clase="col-md-6 mb-3"
                value={form.tipo} onChange={cambiar} error={errores.tipo}
                mensaje="Selecciona un tipo de usuario.">
                <option value="" disabled>Seleccione el perfil...</option>
                <option value="Administrador">Administrador</option>
                <option value="Cliente">Cliente</option>
              </Campo>
            </div>

            <div className="row">
              <Campo id="region" etiqueta="Región" componente="select" required clase="col-md-6 mb-3"
                value={form.region} onChange={cambiar} error={errores.region}
                mensaje="Selecciona una región.">
                <option value="" disabled>Seleccione la región...</option>
                {Object.keys(regionesComunas).map((region) => (
                  <option key={region} value={region}>{region}</option>
                ))}
              </Campo>

              <Campo id="comuna" etiqueta="Comuna" componente="select" required clase="col-md-6 mb-3"
                disabled={!form.region} value={form.comuna} onChange={cambiar} error={errores.comuna}
                mensaje="Selecciona una comuna.">
                <option value="" disabled>Seleccione la comuna...</option>
                {(regionesComunas[form.region] || []).map((comuna) => (
                  <option key={comuna} value={comuna}>{comuna}</option>
                ))}
              </Campo>
            </div>

            <Campo id="direccion" etiqueta="Dirección" type="text" maxLength="300" required
              value={form.direccion} onChange={cambiar} error={errores.direccion}
              mensaje="La dirección es requerida (máximo 300 caracteres)." />

            <div className="d-flex gap-2 mt-4">
              <button type="submit" className="btn btn-primary" disabled={guardando}>
                {guardando ? "Guardando..." : "Guardar"}
              </button>
              <Link to="/admin/usuarios" className="btn btn-cart">Cancelar</Link>
            </div>
          </form>
        </div>
      </div>
    </main>
  );
}