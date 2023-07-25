package ar.edu.utn.frc.tup.lciii.models;//clase abstracta que representara una pieza de ajedrez generica

//a partir de ella definiremos las subclases por cada tipo de pieza de ajedrez: pawn/rook/knight/bishop/queen/king


import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacocoAnnotationGenerated

public abstract class Piece {
    private Color color;
    private Status status;
    private Position position;
    private String symbol;
    private String type;
    private List<Position> posiblePositions;
}
