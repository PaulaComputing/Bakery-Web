// Endpoint protegido para procesar pagos desde el Carrito de React
package cl.duoc.pasteleria.service.backend.controller;

import cl.duoc.pasteleria.service.backend.dto.CompraRequest;
import cl.duoc.pasteleria.service.backend.model.Pedido;
import cl.duoc.pasteleria.service.backend.repository.PedidoRepository;
import cl.duoc.pasteleria.service.backend.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "4. Pedidos", description = "Procesamiento de compras y descuento de stock")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    @PostMapping
    @Operation(summary = "Registrar compra del carrito (Requiere token JWT)")
    public ResponseEntity<?> crearPedido(@RequestBody CompraRequest request, Authentication auth) {
        try {
            Pedido pedido = pedidoService.procesarCompra(auth.getName(), request);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    @GetMapping
    @Operation(summary = "Listar historial de pedidos")
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
}