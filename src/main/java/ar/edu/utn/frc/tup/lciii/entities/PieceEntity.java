package ar.edu.utn.frc.tup.lciii.entities;//clase abstracta que representara una pieza de ajedrez generica

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "Pieces")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacocoAnnotationGenerated
public class PieceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String color;
    @Column
    private String status;
    @OneToOne(mappedBy = "piece")
    private PositionEntity position;
    @Column
    private String symbol;
    @Column
    private String type;
    @ManyToOne
    @JoinColumn(name = "Player_id", nullable = false)
    private PlayerEntity player;
}
