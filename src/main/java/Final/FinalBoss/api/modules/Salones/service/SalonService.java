package Final.FinalBoss.api.modules.Salones.service;

import Final.FinalBoss.api.Exceptions.DataNoFoundException;
import Final.FinalBoss.api.modules.Salones.dto.SalonResponseDTO;
import Final.FinalBoss.api.modules.Salones.entity.SalonEntity;
import Final.FinalBoss.api.modules.Salones.repository.SalonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalonService {

    private final SalonRepository salonRepository;

    private SalonResponseDTO convertirADTO(SalonEntity entity) {
        SalonResponseDTO dto = new SalonResponseDTO();
        dto.setId_salon(entity.getId());
        dto.setNombre_salon(entity.getNombre_salon());
        dto.setCapacidad(entity.getCapacidad());
        dto.setPrecio_renta(entity.getPrecio_renta());
        dto.setUbicacion(entity.getUbicacion());
        return dto;
    }

    public List<SalonResponseDTO> obtenerSalones() {
        List<SalonEntity> entities = salonRepository.findAll();
        List<SalonResponseDTO> dtos = new ArrayList<>();
        for (SalonEntity entity : entities) {
            dtos.add(convertirADTO(entity));
        }
        return dtos;
    }

    public SalonResponseDTO obtenerPorID(Long id) {
        Optional<SalonEntity> opt = salonRepository.findById(id);
        if (opt.isPresent()) {
            return convertirADTO(opt.get());
        }
        throw new DataNoFoundException("No se ha encontrado el salón con ID: " + id);
    }
}
