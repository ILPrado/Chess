package ar.edu.utn.frc.tup.lciii.entities;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Table(name = "Players")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JacocoAnnotationGenerated
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "ChessGames_id", nullable = false)
    private ChessGameEntity chessGame;
    @Column
    private String namePlayer;
    @OneToMany(mappedBy = "player")
    private List<PieceEntity> lstPieces;
}
