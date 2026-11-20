package cl.duoc.pasteleria.service.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    @JsonIgnore
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private Integer cantidad;
    private Double precioUnitario;
}