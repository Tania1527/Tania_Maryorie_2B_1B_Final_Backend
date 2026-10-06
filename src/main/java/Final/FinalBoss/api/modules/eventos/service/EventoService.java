package Final.FinalBoss.api.modules.eventos.service;

import Final.FinalBoss.api.Exceptions.DataNoFoundException;
import Final.FinalBoss.api.Exceptions.DuplicateDataException;
import Final.FinalBoss.api.modules.clientes.model.entity.ClienteEntity;
import Final.FinalBoss.api.modules.clientes.repository.ClientesRepository;
import Final.FinalBoss.api.modules.Salones.entity.SalonEntity;
import Final.FinalBoss.api.modules.Salones.repository.SalonRepository;
import Final.FinalBoss.api.modules.eventos.model.dto.EventoRequestDTO;
import Final.FinalBoss.api.modules.eventos.model.dto.EventoResponseDTO;
import Final.FinalBoss.api.modules.eventos.model.entity.EventoEntity;
import Final.FinalBoss.api.modules.eventos.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final ClientesRepository clienteRepository;
    private final SalonRepository salonRepository;

    private EventoResponseDTO convertirADTO(EventoEntity entity) {
        EventoResponseDTO dto = new EventoResponseDTO();
        dto.setId_evento(entity.getId());
        dto.setNombre_evento(entity.getNombre_evento());
        dto.setFecha_evento(entity.getFecha_evento());
        dto.setCantidad_personas(entity.getCantidad_personas());
        dto.setCantidad_horas(entity.getCantidad_horas());
        dto.setEstado(entity.getEstado());
        dto.setTotal_pago(entity.getTotal_pago());

        if (entity.getCliente() != null) {
            dto.setId_cliente(entity.getCliente().getId());
            dto.setNombre_cliente(entity.getCliente().getNombre() + " " + entity.getCliente().getApellido());
        }

        if (entity.getSalon() != null) {
            dto.setId_salon(entity.getSalon().getId());
            dto.setNombre_salon(entity.getSalon().getNombre_salon());
        }

        return dto;
    }

    public EventoResponseDTO crearEvento(EventoRequestDTO dto) {
        ClienteEntity cliente = clienteRepository.findById(dto.getId_cliente())
                .orElseThrow(() -> new DataNoFoundException("Cliente con ID " + dto.getId_cliente() + " no encontrado"));

        SalonEntity salon = salonRepository.findById(dto.getId_salon())
                .orElseThrow(() -> new DataNoFoundException("Salón con ID " + dto.getId_salon() + " no encontrado"));

        if (dto.getCantidad_personas() > salon.getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de personas (" + dto.getCantidad_personas() +
                    ") supera la capacidad máxima del salón (" + salon.getCapacidad() + ")");
        }

        List<EventoEntity> eventosEnMismaFecha = eventoRepository.buscarPorSalonYFecha(salon, dto.getFecha_evento());
        for (EventoEntity e : eventosEnMismaFecha) {
            if (!"CANCELADO".equalsIgnoreCase(e.getEstado())) {
                throw new DuplicateDataException("El salón '" + salon.getNombre_salon() +
                        "' ya tiene un evento programado para la fecha " + dto.getFecha_evento());
            }
        }

        EventoEntity entity = new EventoEntity();
        entity.setNombre_evento(dto.getNombre_evento());
        entity.setFecha_evento(dto.getFecha_evento());
        entity.setCantidad_personas(dto.getCantidad_personas());
        entity.setCantidad_horas(dto.getCantidad_horas());

        if (dto.getEstado() != null && !dto.getEstado().isBlank()) {
            entity.setEstado(dto.getEstado().toUpperCase());
        } else {
            entity.setEstado("CONFIRMADA");
        }

        double totalCalculado = dto.getCantidad_horas() * salon.getPrecio_renta();
        entity.setTotal_pago(totalCalculado);

        entity.setCliente(cliente);
        entity.setSalon(salon);

        EventoEntity guardado = eventoRepository.save(entity);
        return convertirADTO(guardado);
    }

    public List<EventoResponseDTO> obtenerEventos() {
        List<EventoEntity> lista = eventoRepository.findAll();
        List<EventoResponseDTO> dtos = new ArrayList<>();
        for (EventoEntity e : lista) {
            dtos.add(convertirADTO(e));
        }
        return dtos;
    }

    public EventoResponseDTO obtenerPorID(Long id) {
        Optional<EventoEntity> opt = eventoRepository.findById(id);
        if (opt.isPresent()) {
            return convertirADTO(opt.get());
        }
        throw new DataNoFoundException("No se ha encontrado el evento con ID: " + id);
    }

    public Boolean eliminar(Long id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public EventoResponseDTO actualizar(EventoRequestDTO dto, Long id) {
        EventoEntity entity = eventoRepository.findById(id)
                .orElseThrow(() -> new DataNoFoundException("No se ha encontrado el evento con ID: " + id));

        ClienteEntity cliente = clienteRepository.findById(dto.getId_cliente())
                .orElseThrow(() -> new DataNoFoundException("Cliente con ID " + dto.getId_cliente() + " no encontrado"));

        SalonEntity salon = salonRepository.findById(dto.getId_salon())
                .orElseThrow(() -> new DataNoFoundException("Salón con ID " + dto.getId_salon() + " no encontrado"));

        if (dto.getCantidad_personas() > salon.getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de personas (" + dto.getCantidad_personas() +
                    ") supera la capacidad máxima del salón (" + salon.getCapacidad() + ")");
        }

        entity.setNombre_evento(dto.getNombre_evento());
        entity.setFecha_evento(dto.getFecha_evento());
        entity.setCantidad_personas(dto.getCantidad_personas());
        entity.setCantidad_horas(dto.getCantidad_horas());

        if (dto.getEstado() != null && !dto.getEstado().isBlank()) {
            entity.setEstado(dto.getEstado().toUpperCase());
        }

        double totalCalculado = dto.getCantidad_horas() * salon.getPrecio_renta();
        entity.setTotal_pago(totalCalculado);

        entity.setCliente(cliente);
        entity.setSalon(salon);

        EventoEntity actualizado = eventoRepository.save(entity);
        return convertirADTO(actualizado);
    }
}
