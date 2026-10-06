package Final.FinalBoss.api.modules.eventos.controller;

import Final.FinalBoss.api.modules.eventos.model.dto.EventoRequestDTO;
import Final.FinalBoss.api.modules.eventos.model.dto.EventoResponseDTO;
import Final.FinalBoss.api.modules.eventos.service.EventoService;
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
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService service;

    @PostMapping
    public ResponseEntity<ApiResponse<EventoResponseDTO>> crearEvento(@Valid @RequestBody EventoRequestDTO json) {
        try {
            EventoResponseDTO data = service.crearEvento(json);
            ApiResponse<EventoResponseDTO> response = new ApiResponse<>(true, "Evento registrado con éxito", data);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Error de validación al crear evento: {}", e.getMessage());
            ApiResponse<EventoResponseDTO> fallo = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fallo);
        } catch (Exception e) {
            log.error("Error al crear evento: {}", e.getMessage());
            ApiResponse<EventoResponseDTO> fallo = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fallo);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EventoResponseDTO>>> obtenerEventos() {
        try {
            List<EventoResponseDTO> data = service.obtenerEventos();
            ApiResponse<List<EventoResponseDTO>> response = new ApiResponse<>(true, "Proceso exitoso", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al obtener eventos: {}", e.getMessage());
            ApiResponse<List<EventoResponseDTO>> fallo = new ApiResponse<>(false, "Error al obtener eventos", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fallo);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventoResponseDTO>> obtenerEventoPorId(@PathVariable Long id) {
        try {
            EventoResponseDTO data = service.obtenerPorID(id);
            ApiResponse<EventoResponseDTO> response = new ApiResponse<>(true, "Proceso exitoso", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al buscar evento con ID {}: {}", id, e.getMessage());
            ApiResponse<EventoResponseDTO> fallo = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fallo);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarEvento(@PathVariable Long id) {
        try {
            Boolean data = service.eliminar(id);
            if (data) {
                ApiResponse<Void> response = new ApiResponse<>(true, "Evento eliminado con éxito");
                return ResponseEntity.ok(response);
            }
            ApiResponse<Void> error = new ApiResponse<>(false, "No se ha encontrado el evento");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad referencial al eliminar evento ID {}: {}", id, e.getMessage());
            ApiResponse<Void> fallo = new ApiResponse<>(false, "No se puede eliminar: conflicto de integridad de datos");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(fallo);
        } catch (Exception e) {
            log.error("Error al eliminar evento ID {}: {}", id, e.getMessage());
            ApiResponse<Void> fallo = new ApiResponse<>(false, "Error al eliminar evento", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fallo);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventoResponseDTO>> actualizarEvento(@PathVariable Long id, @Valid @RequestBody EventoRequestDTO dto) {
        try {
            EventoResponseDTO data = service.actualizar(dto, id);
            if (data != null) {
                ApiResponse<EventoResponseDTO> response = new ApiResponse<>(true, "Evento actualizado con éxito", data);
                return ResponseEntity.ok(response);
            }
            ApiResponse<EventoResponseDTO> error = new ApiResponse<>(false, "No se ha encontrado el evento", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (IllegalArgumentException e) {
            log.error("Error de validación al actualizar evento: {}", e.getMessage());
            ApiResponse<EventoResponseDTO> fallo = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fallo);
        } catch (Exception e) {
            log.error("Error al actualizar evento ID {}: {}", id, e.getMessage());
            ApiResponse<EventoResponseDTO> fallo = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fallo);
        }
    }
}
