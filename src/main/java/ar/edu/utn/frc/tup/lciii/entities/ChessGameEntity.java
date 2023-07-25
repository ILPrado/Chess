package ar.edu.utn.frc.tup.lciii.entities;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "ChessGames")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacocoAnnotationGenerated
public class ChessGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(mappedBy = "chessGame", cascade = CascadeType.ALL)
    private PlayerEntity playerOne;
    @OneToOne(mappedBy = "chessGame", cascade = CascadeType.ALL)
    private PlayerEntity playerTwo;
    @Column
    private Integer PlayerInTurn;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "chessGame", cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private List<ChessMoveEntity> lstChessMove;
    @Column
    private String status;
}
