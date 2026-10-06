package Final.FinalBoss.api.modules.eventos.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoResponseDTO {

    private Long id_evento;
    private String nombre_evento;
    private LocalDate fecha_evento;
    private Integer cantidad_personas;
    private Integer cantidad_horas;
    private String estado;
    private Double total_pago;

    private Long id_cliente;
    private String nombre_cliente;

    private Long id_salon;
    private String nombre_salon;
}
