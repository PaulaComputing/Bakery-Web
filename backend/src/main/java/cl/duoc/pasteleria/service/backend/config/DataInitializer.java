//insertar 'BD'
package cl.duoc.pasteleria.service.backend.config;

import cl.duoc.pasteleria.service.backend.model.Producto;
import cl.duoc.pasteleria.service.backend.model.Usuario;
import cl.duoc.pasteleria.service.backend.repository.ProductoRepository;
import cl.duoc.pasteleria.service.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.saveAll(List.of(
                Usuario.builder()
                    .run("111111111")
                    .nombre("Admin")
                    .apellidos("Sistema")
                    .correo("admin@duoc.cl")
                    .password(passwordEncoder.encode("admin123"))
                    .tipo("Administrador")
                    .region("Región Metropolitana de Santiago")
                    .comuna("Santiago")
                    .direccion("Av. España 8")
                    .build(),
                Usuario.builder()
                    .run("222222222")
                    .nombre("Cliente")
                    .apellidos("Demo")
                    .correo("cliente@gmail.com")
                    .password(passwordEncoder.encode("cliente123"))
                    .tipo("Cliente")
                    .region("Región Metropolitana de Santiago")
                    .comuna("Maipú")
                    .direccion("Av. Pajaritos 1234")
                    .build()
            ));
        }

        if (productoRepository.count() == 0) {
            productoRepository.saveAll(List.of(
                new Producto("TC001", "Torta Cuadrada de Chocolate", "Tortas Cuadradas", "Deliciosa torta cuadrada de chocolate artesanal.", 45000.0, 20, 5, "/Assets/torta cuadrada de chocolate.jpg"),
                new Producto("TC002", "Torta Cuadrada de Frutas", "Tortas Cuadradas", "Bizcocho esponjoso con crema y frutas de estación.", 50000.0, 20, 5, "/Assets/torta cuadrada de frutas.jpg"),
                new Producto("TT001", "Torta Circular de Vainilla", "Tortas Circulares", "Clásica torta circular con suave crema de vainilla.", 40000.0, 20, 5, "/Assets/Torta Circular de Vainilla.jpg"),
                new Producto("TT002", "Torta Circular de Manjar", "Tortas Circulares", "Rellena con abundante manjar casero y nueces.", 42000.0, 20, 5, "/Assets/Torta Circular de Manjar.jpg"),
                new Producto("PI001", "Mousse de Chocolate", "Postres Individuales", "Suave mousse individual de chocolate semiamargo.", 5000.0, 20, 5, "/Assets/Mousse de Chocolate.jpg"),
                new Producto("PI002", "Tiramisú Clásico", "Postres Individuales", "Postre italiano con café, mascarpone y cacao.", 5500.0, 20, 5, "/Assets/Tiramisu Clasico.jpg"),
                new Producto("PSA001", "Torta Sin Azúcar de Naranja", "Productos Sin Azúcar", "Endulzada naturalmente, ideal para cuidar el azúcar.", 48000.0, 20, 5, "/Assets/Torta Sin Azucar de Naranja.jpg"),
                new Producto("PSA002", "Cheesecake Sin Azúcar", "Productos Sin Azúcar", "Cremoso cheesecake sin azúcar añadida.", 47000.0, 20, 5, "/Assets/Cheesecake Sin Azucar.jpg"),
                new Producto("PT001", "Empanada de Manzana", "Pastelería Tradicional", "Masa horneada rellena de manzana caramelizada y canela.", 3000.0, 20, 5, "/Assets/Empanada de Manzana.jpg"),
                new Producto("PT002", "Tarta de Santiago", "Pastelería Tradicional", "Tarta tradicional de almendras.", 6000.0, 20, 5, "/Assets/Tarta de Santiago.jpg"),
                new Producto("PG001", "Brownie Sin Gluten", "Productos Sin Gluten", "Brownie húmedo de chocolate libre de gluten.", 4000.0, 20, 5, "/Assets/Brownie Sin Gluten.jpg"),
                new Producto("PG002", "Pan Sin Gluten", "Productos Sin Gluten", "Pan artesanal libre de gluten.", 3500.0, 20, 5, "/Assets/Pan Sin Gluten.jpg"),
                new Producto("PV001", "Torta Vegana de Chocolate", "Productos Vegana", "100% libre de ingredientes de origen animal.", 50000.0, 20, 5, "/Assets/Torta Vegana de Chocolate.jpg"),
                new Producto("PV002", "Galletas Veganas de Avena", "Productos Vegana", "Crujientes galletas de avena y pasas.", 4500.0, 20, 5, "/Assets/Galletas Veganas de Avena.jpg"),
                new Producto("TE001", "Torta Especial de Cumpleaños", "Tortas Especiales", "Decoración personalizada para celebrar tu cumpleaños.", 55000.0, 20, 5, "/Assets/Torta Especial de Cumpleanos.jpg"),
                new Producto("TE002", "Torta Especial de Boda", "Tortas Especiales", "Elegante torta de varios pisos para bodas.", 60000.0, 20, 5, "/Assets/Torta Especial de Boda.jpg")
            ));
        }
    }
}