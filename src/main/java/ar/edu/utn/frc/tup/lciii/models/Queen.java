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

public class Queen extends ar.edu.utn.frc.tup.lciii.models.Piece {
    private Color color;
    private Status status;
    private Position position;
    private String symbol;
    private String type = "Q";
    private List<Position> posiblePositions;

}
