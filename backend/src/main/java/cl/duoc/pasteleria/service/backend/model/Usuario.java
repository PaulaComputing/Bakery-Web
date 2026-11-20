package cl.duoc.pasteleria.service.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @Column(length = 9, nullable = false, unique = true)
    private String run;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 100)
    private String apellidos;

    @Column(length = 100, nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String telefono;

    @Column(length = 20)
    private String fechaNacimiento;

    @Column(length = 20, nullable = false)
    private String tipo; // "Administrador" o "Cliente"

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String comuna;

    @Column(length = 300)
    private String direccion;
}