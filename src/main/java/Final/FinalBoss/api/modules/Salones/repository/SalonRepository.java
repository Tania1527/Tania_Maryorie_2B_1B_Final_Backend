package Final.FinalBoss.api.modules.Salones.repository;

import Final.FinalBoss.api.modules.Salones.entity.SalonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepository extends JpaRepository<SalonEntity, Long> {
}
