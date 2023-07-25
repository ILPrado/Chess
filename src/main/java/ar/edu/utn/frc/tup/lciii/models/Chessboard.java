package ar.edu.utn.frc.tup.lciii.models;
//sera matriz bidimensional de objetos "piece" que representara las distintas piezas de ajedrez en sus posiciones correspondientes


import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacocoAnnotationGenerated
public class Chessboard {
    private String[][] board; 
}
