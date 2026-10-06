package Final.FinalBoss.api.modules.eventos.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoRequestDTO {

    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(max = 100)
    private String nombre_evento;

    @NotNull(message = "La fecha del evento es obligatoria")
    private LocalDate fecha_evento;

    @NotNull(message = "La cantidad de personas es obligatoria")
    @Min(value = 1, message = "La cantidad de personas debe ser mayor a cero")
    private Integer cantidad_personas;

    @NotNull(message = "La cantidad de horas es obligatoria")
    @Min(value = 1, message = "La cantidad de horas debe ser mayor a cero")
    private Integer cantidad_horas;

    @NotNull(message = "El cliente es obligatorio")
    private Long id_cliente;

    @NotNull(message = "El salón es obligatorio")
    private Long id_salon;

    private String estado;
}
