package Final.FinalBoss.api.modules.clientes.controller;

import Final.FinalBoss.api.modules.clientes.model.dto.ClienteRequestDTO;
import Final.FinalBoss.api.modules.clientes.model.dto.ClienteResponseDTO;
import Final.FinalBoss.api.modules.clientes.service.ClienteService;
import Final.FinalBoss.api.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> crearCliente(@Valid @RequestBody ClienteRequestDTO json) {
        try {
            ClienteResponseDTO data = service.CrearCliente(json);
            ApiResponse<ClienteResponseDTO> response = new ApiResponse<>(true, "Cliente creado con éxito", data);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al crear cliente: {}", e.getMessage());
            ApiResponse<ClienteResponseDTO> fallo = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fallo);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponseDTO>>> obtenerClientes() {
        try {
            List<ClienteResponseDTO> data = service.ObtenerClientes();
            ApiResponse<List<ClienteResponseDTO>> response = new ApiResponse<>(true, "Proceso exitoso", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener clientes: {}", e.getMessage());
            ApiResponse<List<ClienteResponseDTO>> fallo = new ApiResponse<>(false, "Error al obtener clientes", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fallo);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> obtenerClienteId(@PathVariable Long id) {
        try {
            ClienteResponseDTO data = service.ObtenerporID(id);
            ApiResponse<ClienteResponseDTO> response = new ApiResponse<>(true, "Proceso exitoso", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al buscar cliente con ID {}: {}", id, e.getMessage());
            ApiResponse<ClienteResponseDTO> fallo = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fallo);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarCliente(@PathVariable Long id) {
        try {
            Boolean data = service.Eliminar(id);
            if (data) {
                ApiResponse<Void> response = new ApiResponse<>(true, "Cliente eliminado con éxito");
                return ResponseEntity.ok(response);
            }
            ApiResponse<Void> error = new ApiResponse<>(false, "No se ha encontrado el cliente");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (DataIntegrityViolationException e) {
            // Criterio 5: Validación de FK activa (no eliminar cliente con eventos)
            log.error("No se puede eliminar el cliente ID {} por tener eventos asociados", id);
            ApiResponse<Void> fallo = new ApiResponse<>(false, "No se puede eliminar: el cliente tiene eventos asociados");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(fallo);
        } catch (Exception e) {
            log.error("Error al eliminar cliente ID {}: {}", id, e.getMessage());
            ApiResponse<Void> fallo = new ApiResponse<>(false, "Error al eliminar cliente", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fallo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponseDTO>> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) {
        try {
            ClienteResponseDTO data = service.Actualizar(dto, id);
            if (data != null) {
                ApiResponse<ClienteResponseDTO> response = new ApiResponse<>(true, "Cliente actualizado con éxito", data);
                return ResponseEntity.ok(response);
            }
            ApiResponse<ClienteResponseDTO> error = new ApiResponse<>(false, "No se ha encontrado el cliente", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            log.error("Error al actualizar cliente ID {}: {}", id, e.getMessage());
            ApiResponse<ClienteResponseDTO> fallo = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fallo);
        }
    }
}
