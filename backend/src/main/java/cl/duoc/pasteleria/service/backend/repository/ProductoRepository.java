package cl.duoc.pasteleria.service.backend.repository;

import cl.duoc.pasteleria.service.backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, String> {
    List<Producto> findByCategoriaIgnoreCase(String categoria);
}
