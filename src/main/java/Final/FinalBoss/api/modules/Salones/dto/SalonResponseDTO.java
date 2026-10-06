package Final.FinalBoss.api.modules.Salones.dto;

import lombok.Data;

@Data
public class SalonResponseDTO {
    private Long id_salon;
    private String nombre_salon;
    private Integer capacidad;
    private Double precio_renta;
    private String ubicacion;
}
