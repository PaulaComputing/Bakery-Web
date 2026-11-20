import TarjetaProducto from "../componentes/TarjetaProducto";
import { useTienda } from "../contexto/Tienda";
import usarTitulo from "../usarTitulo";

export default function Productos() {
  usarTitulo("Productos - Pastelería Mil Sabores");

  const { productos, cargandoProductos, agregarAlCarrito, cantidadEnCarrito } = useTienda();

  return (
    <main className="container my-5">
      <h1 className="text-center font-heading display-4 mb-5">Productos</h1>

      {cargandoProductos ? (
        <p className="text-center text-muted">Cargando catálogo...</p>
      ) : (
        <div className="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
          {productos.map((p) => (
            <TarjetaProducto key={p.id} producto={p} onAgregar={agregarAlCarrito}
              disponible={Number(p.stock) - cantidadEnCarrito(p.id)} />
          ))}
        </div>
      )}
    </main>
  );
}