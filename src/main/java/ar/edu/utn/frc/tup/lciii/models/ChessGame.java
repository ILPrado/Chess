package ar.edu.utn.frc.tup.lciii.models;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacocoAnnotationGenerated
public class ChessGame {
    private Integer id;
    private Chessboard chessboard;
    private Player playerOne;
    private Player playerTwo;
    private Integer playerInTurn;
    private ChessMove chessMove;
    private String status = "In Progress";
}
