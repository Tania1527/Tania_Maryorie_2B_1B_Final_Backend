package Final.FinalBoss.api.modules.clientes.service;

import Final.FinalBoss.api.Exceptions.DataNoFoundException;
import Final.FinalBoss.api.Exceptions.DuplicateDataException;
import Final.FinalBoss.api.modules.clientes.model.dto.ClienteRequestDTO;
import Final.FinalBoss.api.modules.clientes.model.dto.ClienteResponseDTO;
import Final.FinalBoss.api.modules.clientes.model.entity.ClienteEntity;
import Final.FinalBoss.api.modules.clientes.repository.ClientesRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service @RequiredArgsConstructor
public class ClienteService {

    private final ClientesRepository repo;

    private ClienteResponseDTO ConvertirADTO(ClienteEntity entity) {
        ClienteResponseDTO dto = new ClienteResponseDTO();

        dto.setId_cliente(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        dto.setDireccion(entity.getDireccion());

        return dto;
    }

    private ClienteEntity ConvertirAEntity(ClienteRequestDTO dto) {
        ClienteEntity entity = new ClienteEntity();

        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setDireccion(dto.getDireccion());

        return entity;
    }

    public ClienteResponseDTO CrearCliente (ClienteRequestDTO dto) {
        if (repo.existsByEmail(dto.getEmail())){
            throw new DuplicateDataException("Ya hay un cliente con este correo");
        }

        ClienteEntity entity = ConvertirAEntity(dto);

        ClienteEntity entitysave = repo.save(entity);

        return ConvertirADTO(entitysave);
    }

    public List<ClienteResponseDTO> ObtenerClientes() {
        List<ClienteEntity> entities = repo.findAll();
        List<ClienteResponseDTO> dtos = new ArrayList<>();

        for (ClienteEntity entity: entities) {
            dtos.add(ConvertirADTO(entity));
        }
        return dtos;
    }

    public ClienteResponseDTO ObtenerporID(Long id) {
        Optional<ClienteEntity> entityOption = repo.findById(id);
        if (entityOption.isPresent()) {
            return ConvertirADTO(entityOption.get());
        }
        throw new DataNoFoundException("No se a encontrado el id: " + id);
    }

    public Boolean Eliminar (Long id) {
        if (repo.existsById(id)){
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public ClienteResponseDTO Actualizar(@Valid ClienteRequestDTO dto, Long id) {
        try {
            Optional<ClienteEntity> entityOptional = repo.findById(id);
            if (entityOptional.isPresent()){
                ClienteEntity entity = entityOptional.get();
                if (!entity.getEmail().equals(dto.getEmail()) && repo.existsByEmail(dto.getEmail())){
                    throw new DuplicateDataException("Ya hay un cliente con este correo");
                }

                entity.setNombre(dto.getNombre());
                entity.setApellido(dto.getApellido());
                entity.setTelefono(dto.getTelefono());
                entity.setEmail(dto.getEmail());
                entity.setDireccion(dto.getDireccion());

                ClienteEntity entitysave = repo.save(entity);

                return ConvertirADTO(entitysave);
            }
            return null;
        }
        catch (Exception e) {
            log.error("Error en actualizar el cliente: " + id);
            return null;
        }
    }

}
