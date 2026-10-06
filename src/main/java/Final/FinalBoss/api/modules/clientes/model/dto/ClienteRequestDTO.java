package Final.FinalBoss.api.modules.clientes.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100)
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Agrege un correo valido")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 15)
    private String telefono;

    @NotBlank(message = "El direccion es obligatorio")
    @Size(max = 200)
    private String direccion;

}
