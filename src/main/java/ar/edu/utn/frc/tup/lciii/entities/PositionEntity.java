package ar.edu.utn.frc.tup.lciii.entities;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Positions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JacocoAnnotationGenerated
public class PositionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "Pieces_id", nullable = false)
    private PieceEntity piece;
    @Column
    private Integer columnNumber;
    @Column
    private Integer rowNumber;
    @OneToOne
    @JoinColumn(name = "ChessMoves_id")
    private ChessMoveEntity chessMove;
}
