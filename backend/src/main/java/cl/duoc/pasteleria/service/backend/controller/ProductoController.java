// CRUD completo de Productos: GET público, POST/PUT/DELETE solo Administrador
package cl.duoc.pasteleria.service.backend.controller;

import cl.duoc.pasteleria.service.backend.model.Producto;
import cl.duoc.pasteleria.service.backend.repository.ProductoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "2. Productos", description = "Operaciones CRUD del catálogo de la pastelería")
public class ProductoController {

    private final ProductoRepository productoRepository;

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por su código (ej. TC001)")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable String id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto (Solo Administrador)")
    public ResponseEntity<?> crear(@RequestBody Producto producto) {
        if (productoRepository.existsById(producto.getId())) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Ya existe un producto con ese código."));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productoRepository.save(producto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente (Solo Administrador)")
    public ResponseEntity<Producto> actualizar(@PathVariable String id, @RequestBody Producto datos) {
        return productoRepository.findById(id).map(p -> {
            p.setNombre(datos.getNombre());
            p.setCategoria(datos.getCategoria());
            p.setDescripcion(datos.getDescripcion());
            p.setPrecio(datos.getPrecio());
            p.setStock(datos.getStock());
            p.setStockCritico(datos.getStockCritico());
            p.setImagen(datos.getImagen());
            return ResponseEntity.ok(productoRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto (Solo Administrador)")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}