// Este servicio valida el stock en MySQL antes de procesar una compra, descuenta las unidades 
// y guarda el pedido con su detalle (@Transactional asegura que si falla un producto, 
// no se descuente nada).

package cl.duoc.pasteleria.service.backend.service;

import cl.duoc.pasteleria.service.backend.dto.CompraRequest;
import cl.duoc.pasteleria.service.backend.model.*;
import cl.duoc.pasteleria.service.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    private static final double COSTO_ENVIO = 3500.0;

    @Transactional
    public Pedido procesarCompra(String correoUsuario, CompraRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Tu carrito está vacío.");
        }

        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correoUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Pedido pedido = Pedido.builder()
                .fecha(LocalDateTime.now())
                .usuario(usuario)
                .detalles(new ArrayList<>())
                .build();

        double subtotal = 0.0;

        for (CompraRequest.ItemCarritoDTO item : request.getItems()) {
            Producto producto = productoRepository.findById(item.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + item.getId()));

            if (producto.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para: " + producto.getNombre());
            }

            // Descontar stock en la base de datos
            producto.setStock(producto.getStock() - item.getQuantity());
            productoRepository.save(producto);

            DetallePedido detalle = DetallePedido.builder()
                    .pedido(pedido)
                    .producto(producto)
                    .cantidad(item.getQuantity())
                    .precioUnitario(producto.getPrecio())
                    .build();

            pedido.getDetalles().add(detalle);
            subtotal += producto.getPrecio() * item.getQuantity();
        }

        pedido.setSubtotal(subtotal);
        pedido.setEnvio(COSTO_ENVIO);
        pedido.setTotal(subtotal + COSTO_ENVIO);

        return pedidoRepository.save(pedido);
    }
}
