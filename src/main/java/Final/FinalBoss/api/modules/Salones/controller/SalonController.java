package Final.FinalBoss.api.modules.Salones.controller;

import Final.FinalBoss.api.modules.Salones.dto.SalonResponseDTO;
import Final.FinalBoss.api.modules.Salones.service.SalonService;
import Final.FinalBoss.api.modules.clientes.model.dto.ClienteResponseDTO;
import Final.FinalBoss.api.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/salones")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SalonResponseDTO>>> obtenerSalones() {
        try {
            List<SalonResponseDTO> data = salonService.obtenerSalones();
            ApiResponse<List<SalonResponseDTO>> response = new ApiResponse<>(true, "Proceso exitoso", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<List<SalonResponseDTO>> fallo = new ApiResponse<>(false, "Proceso fallido", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fallo);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalonResponseDTO>> obtenerSalonPorId(@PathVariable Long id) {
        try {
            SalonResponseDTO data = salonService.obtenerPorID(id);
            ApiResponse<SalonResponseDTO> response = new ApiResponse<>(true, "Proceso exitoso", data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<SalonResponseDTO> fallo = new ApiResponse<>(false, "Proceso fallido", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fallo);
        }
    }
}
