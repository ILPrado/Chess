package ar.edu.utn.frc.tup.lciii.models;

import java.util.List;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacocoAnnotationGenerated

public class Player {
    private String namePlayer;
    private List<Piece> lstPieces;
}
