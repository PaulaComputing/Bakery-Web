// Login con JWT + Registro público de clientes
package cl.duoc.pasteleria.service.backend.controller;

import cl.duoc.pasteleria.service.backend.dto.AuthResponse;
import cl.duoc.pasteleria.service.backend.dto.LoginRequest;
import cl.duoc.pasteleria.service.backend.model.Usuario;
import cl.duoc.pasteleria.service.backend.repository.UsuarioRepository;
import cl.duoc.pasteleria.service.backend.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "1. Autenticación", description = "Endpoints para login con JWT y registro de clientes")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo().trim(), request.getPassword())
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "Correo o contraseña incorrectos."));
        }

        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(request.getCorreo().trim()).orElseThrow();
        String rolNormalizado = usuario.getTipo().toLowerCase(); // "administrador" o "cliente"
        String token = jwtService.generarToken(usuario.getCorreo(), rolNormalizado);

        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .run(usuario.getRun())
                .correo(usuario.getCorreo())
                .nombre(usuario.getNombre())
                .rol(rolNormalizado)
                .build());
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar un nuevo cliente")
    public ResponseEntity<?> registro(@RequestBody Usuario nuevo) {
        String runLimpio = nuevo.getRun().trim().toUpperCase();
        String correoLimpio = nuevo.getCorreo().trim().toLowerCase();

        if (usuarioRepository.existsByRunIgnoreCase(runLimpio)) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Ya existe una cuenta registrada con ese run."));
        }
        if (usuarioRepository.existsByCorreoIgnoreCase(correoLimpio)) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Ya existe una cuenta registrada con este correo."));
        }

        nuevo.setRun(runLimpio);
        nuevo.setCorreo(correoLimpio);
        nuevo.setTipo("Cliente");
        nuevo.setPassword(passwordEncoder.encode(nuevo.getPassword()));

        usuarioRepository.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "¡Registro exitoso! Ahora puedes iniciar sesión."));
    }
}