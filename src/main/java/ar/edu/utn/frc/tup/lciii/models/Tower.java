package ar.edu.utn.frc.tup.lciii.models;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacocoAnnotationGenerated
//ROOK= TORRE
public class Tower extends ar.edu.utn.frc.tup.lciii.models.Piece {
    private Color color;
    private Status status;
    private Position position;
    private String symbol;
    private String type = "R";
    private List<Position> posiblePositions;

}
