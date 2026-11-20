package cl.duoc.pasteleria.service.backend.security;

import cl.duoc.pasteleria.service.backend.model.Usuario;
import cl.duoc.pasteleria.service.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        String roleName = "ROLE_" + usuario.getTipo().toUpperCase();

        return new User(
                usuario.getCorreo(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority(roleName))
        );
    }
}