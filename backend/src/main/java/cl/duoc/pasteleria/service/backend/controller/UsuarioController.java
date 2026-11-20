// CRUD completo de Usuarios para el panel Administrador

package cl.duoc.pasteleria.service.backend.controller;

import cl.duoc.pasteleria.service.backend.model.Usuario;
import cl.duoc.pasteleria.service.backend.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "3. Usuarios", description = "Gestión CRUD de usuarios (Solo Administrador)")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios")
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{run}")
    @Operation(summary = "Buscar usuario por RUN")
    public ResponseEntity<Usuario> obtenerPorRun(@PathVariable String run) {
        return usuarioRepository.findById(run.toUpperCase())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un usuario (Admin o Cliente)")
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        String runLimpio = usuario.getRun().trim().toUpperCase();
        if (usuarioRepository.existsById(runLimpio)) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Ya existe un usuario con ese RUN."));
        }
        usuario.setRun(runLimpio);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

    @PutMapping("/{run}")
    @Operation(summary = "Actualizar datos de un usuario")
    public ResponseEntity<Usuario> actualizar(@PathVariable String run, @RequestBody Usuario datos) {
        return usuarioRepository.findById(run.toUpperCase()).map(u -> {
            u.setNombre(datos.getNombre());
            u.setApellidos(datos.getApellidos());
            u.setCorreo(datos.getCorreo());
            u.setTipo(datos.getTipo());
            u.setFechaNacimiento(datos.getFechaNacimiento());
            u.setRegion(datos.getRegion());
            u.setComuna(datos.getComuna());
            u.setDireccion(datos.getDireccion());
            if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
                u.setPassword(passwordEncoder.encode(datos.getPassword()));
            }
            return ResponseEntity.ok(usuarioRepository.save(u));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{run}")
    @Operation(summary = "Eliminar un usuario")
    public ResponseEntity<Void> eliminar(@PathVariable String run) {
        if (!usuarioRepository.existsById(run.toUpperCase())) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(run.toUpperCase());
        return ResponseEntity.noContent().build();
    }
}
