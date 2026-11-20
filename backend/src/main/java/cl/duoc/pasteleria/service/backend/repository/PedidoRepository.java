package cl.duoc.pasteleria.service.backend.repository;

import cl.duoc.pasteleria.service.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioRun(String run);
}
