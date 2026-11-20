package cl.duoc.pasteleria.service.backend.repository;

import cl.duoc.pasteleria.service.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByCorreoIgnoreCase(String correo);
    boolean existsByCorreoIgnoreCase(String correo);
    boolean existsByRunIgnoreCase(String run);
}
