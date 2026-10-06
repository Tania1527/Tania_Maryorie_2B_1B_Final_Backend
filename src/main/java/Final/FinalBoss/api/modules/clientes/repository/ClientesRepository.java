package Final.FinalBoss.api.modules.clientes.repository;

import Final.FinalBoss.api.modules.clientes.model.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends JpaRepository <ClienteEntity, Long>{
    Boolean existsByEmail(String email);
}
