const API_URL = "http://localhost:8080/api";

function getToken() {
  const sesion = JSON.parse(localStorage.getItem("sesionActual"));
  return sesion?.token || null;
}

async function request(endpoint, options = {}) {
  const token = getToken();
  const headers = {
    "Content-Type": "application/json",
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...options.headers,
  };

  const response = await fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers,
  });

  const texto = await response.text();

  if (!response.ok) {
    let mensaje = `Error ${response.status}`;
    if (texto) {
      try {
        const data = JSON.parse(texto);
        mensaje = data.mensaje || data.error || data.message || texto;
      } catch {
        mensaje = texto;
      }
    }
    throw new Error(mensaje);
  }

  if (!texto) return null;
  try {
    return JSON.parse(texto);
  } catch {
    return texto;
  }
}
export const api = {
  // 1. Autenticación
  login: (correo, password) =>
    request("/auth/login", {
      method: "POST",
      body: JSON.stringify({ correo, password }),
    }),

  registro: (usuario) =>
    request("/auth/registro", {
      method: "POST",
      body: JSON.stringify(usuario),
    }),

  // 2. Productos
  getProductos: () => request("/productos"),
  getProducto: (id) => request(`/productos/${id}`),
  crearProducto: (producto) =>
    request("/productos", {
      method: "POST",
      body: JSON.stringify(producto),
    }),
  actualizarProducto: (id, producto) =>
    request(`/productos/${id}`, {
      method: "PUT",
      body: JSON.stringify(producto),
    }),
  eliminarProducto: (id) =>
    request(`/productos/${id}`, {
      method: "DELETE",
    }),

  // 3. Usuarios (Admin)
  getUsuarios: () => request("/usuarios"),
  getUsuario: (run) => request(`/usuarios/${run}`),
  crearUsuario: (usuario) =>
    request("/usuarios", {
      method: "POST",
      body: JSON.stringify(usuario),
    }),
  actualizarUsuario: (run, usuario) =>
    request(`/usuarios/${run}`, {
      method: "PUT",
      body: JSON.stringify(usuario),
    }),
  eliminarUsuario: (run) =>
    request(`/usuarios/${run}`, {
      method: "DELETE",
    }),

  // 4. Pedidos
  crearPedido: (items) =>
    request("/pedidos", {
      method: "POST",
      body: JSON.stringify({ items }),
    }),
  getMisPedidos: () => request("/pedidos/mis-pedidos"),
  getTodosLosPedidos: () => request("/pedidos"),
};