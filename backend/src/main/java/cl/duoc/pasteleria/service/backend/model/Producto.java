package cl.duoc.pasteleria.service.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @Column(length = 20, nullable = false, unique = true)
    private String id; // Ej: "TC001", "PI001"

    @Column(length = 100, nullable = false)
    private String nombre;

    @Column(length = 100, nullable = false)
    private String categoria;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer stock;

    private Integer stockCritico;

    @Column(length = 255)
    private String imagen;
}