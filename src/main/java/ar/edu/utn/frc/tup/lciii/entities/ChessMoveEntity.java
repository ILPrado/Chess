package ar.edu.utn.frc.tup.lciii.entities;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ChessMoves")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacocoAnnotationGenerated
public class ChessMoveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String move;
    @ManyToOne
    @JoinColumn(name = "ChessGame_id", nullable = false)
    private ChessGameEntity chessGame;
}
