package Final.FinalBoss.api.modules.eventos.model.entity;

import Final.FinalBoss.api.modules.clientes.model.entity.ClienteEntity;
import Final.FinalBoss.api.modules.Salones.entity.SalonEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "eventos")
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EVENTO")
    private Long id;

    @Column(name = "NOMBRE_EVENTO", nullable = false, length = 100)
    private String nombre_evento;

    @Column(name = "FECHA_EVENTO", nullable = false)
    private LocalDate fecha_evento;

    @Column(name = "CANTIDAD_PERSONAS", nullable = false)
    private Integer cantidad_personas;

    @Column(name = "CANTIDAD_HORAS", nullable = false)
    private Integer cantidad_horas;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;

    @Column(name = "TOTAL_PAGO", nullable = false)
    private Double total_pago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_SALON", nullable = false)
    private SalonEntity salon;
}
