package Final.FinalBoss.api.modules.eventos.repository;

import Final.FinalBoss.api.modules.eventos.model.entity.EventoEntity;
import Final.FinalBoss.api.modules.Salones.entity.SalonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long> {

    @Query("SELECT e FROM EventoEntity e WHERE e.salon = :salon AND e.fecha_evento = :fecha")
    List<EventoEntity> buscarPorSalonYFecha(@Param("salon") SalonEntity salon, @Param("fecha") LocalDate fecha);
}
