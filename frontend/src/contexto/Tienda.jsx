import { createContext, useContext, useEffect, useReducer, useState } from "react";
import { api } from "../servicios/api";

function carritoReducer(carrito, accion) {
  switch (accion.tipo) {
    case "agregar": {
      const existe = carrito.some((item) => item.id === accion.id);
      return existe
        ? carrito.map((item) =>
            item.id === accion.id ? { ...item, quantity: item.quantity + accion.cantidad } : item
          )
        : [...carrito, { id: accion.id, quantity: accion.cantidad }];
    }
    case "disminuir":
      return carrito.flatMap((item) => {
        if (item.id !== accion.id) return [item];
        return item.quantity > 1 ? [{ ...item, quantity: item.quantity - 1 }] : [];
      });
    case "eliminar":
      return carrito.filter((item) => item.id !== accion.id);
    case "ajustar":
      return carrito.flatMap((item) => {
        const stock = accion.stocks[item.id] || 0;
        return stock > 0 ? [{ ...item, quantity: Math.min(item.quantity, stock) }] : [];
      });
    case "vaciar":
      return [];
    default:
      return carrito;
  }
}

const TiendaContext = createContext(null);

export function TiendaProvider({ children }) {
  const [carrito, dispatch] = useReducer(
    carritoReducer,
    null,
    () => JSON.parse(localStorage.getItem("carrito")) || []
  );
  const [sesion, setSesion] = useState(() => JSON.parse(localStorage.getItem("sesionActual")));
  const [productos, setProductos] = useState([]);
  const [cargandoProductos, setCargandoProductos] = useState(true);

  const recargarProductos = async () => {
    try {
      setCargandoProductos(true);
      const lista = await api.getProductos();
      setProductos(lista);
    } catch (error) {
      console.error("Error al cargar productos desde el backend:", error);
    } finally {
      setCargandoProductos(false);
    }
  };

  useEffect(() => {
    recargarProductos();
  }, []);

  useEffect(() => {
    localStorage.setItem("carrito", JSON.stringify(carrito));
  }, [carrito]);

  const totalItems = carrito.reduce((total, item) => total + item.quantity, 0);

  const cantidadEnCarrito = (id) => {
    const linea = carrito.find((item) => item.id === id);
    return linea ? linea.quantity : 0;
  };

  const agregarAlCarrito = (id, cantidad = 1) => {
    const producto = productos.find((p) => p.id === id);
    const stock = producto ? Number(producto.stock) : 0;
    const agregables = Math.min(cantidad, stock - cantidadEnCarrito(id));
    if (agregables <= 0) return 0;
    dispatch({ tipo: "agregar", id, cantidad: agregables });
    return agregables;
  };

  const iniciarSesion = (datos) => {
    localStorage.setItem("sesionActual", JSON.stringify(datos));
    setSesion(datos);
  };

  const cerrarSesion = () => {
    localStorage.removeItem("sesionActual");
    setSesion(null);
  };

  return (
    <TiendaContext.Provider
      value={{
        carrito,
        dispatch,
        totalItems,
        agregarAlCarrito,
        cantidadEnCarrito,
        sesion,
        iniciarSesion,
        cerrarSesion,
        productos,
        cargandoProductos,
        recargarProductos,
      }}
    >
      {children}
    </TiendaContext.Provider>
  );
}

export const useTienda = () => useContext(TiendaContext);