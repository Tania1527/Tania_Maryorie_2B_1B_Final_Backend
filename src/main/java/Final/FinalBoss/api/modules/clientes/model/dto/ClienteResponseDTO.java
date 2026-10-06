package Final.FinalBoss.api.modules.clientes.model.dto;

import lombok.Data;

@Data
public class ClienteResponseDTO {

    private Long id_cliente;

    private String nombre;

    private String apellido;

    private String email;

    private String telefono;

    private String direccion;
}
