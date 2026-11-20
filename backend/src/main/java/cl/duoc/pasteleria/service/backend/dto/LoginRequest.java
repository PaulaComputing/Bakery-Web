package cl.duoc.pasteleria.service.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String password;
}