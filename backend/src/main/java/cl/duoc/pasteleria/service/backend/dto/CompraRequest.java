package cl.duoc.pasteleria.service.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class CompraRequest {
    private List<ItemCarritoDTO> items;

    @Data
    public static class ItemCarritoDTO {
        private String id;
        private Integer quantity;
    }
}
