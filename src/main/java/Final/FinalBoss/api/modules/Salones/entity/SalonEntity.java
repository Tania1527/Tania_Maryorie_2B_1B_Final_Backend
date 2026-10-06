package Final.FinalBoss.api.modules.Salones.entity;

import Final.FinalBoss.api.modules.eventos.model.entity.EventoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@ToString(exclude = "eventos")
@Table(name = "salones")
public class SalonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SALON")
    private Long id;

    @Column(name = "NOMBRE_SALON", nullable = false, length = 100)
    private String nombre_salon;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    @Column(name = "PRECIO_RENTA", nullable = false)
    private Double precio_renta;

    @Column(name = "UBICACION", length = 100)
    private String ubicacion;

    @OneToMany(mappedBy = "salon", cascade = CascadeType.ALL)
    private List<EventoEntity> eventos = new ArrayList<>();
}
