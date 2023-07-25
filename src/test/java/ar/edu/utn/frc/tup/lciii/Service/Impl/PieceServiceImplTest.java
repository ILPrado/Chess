package ar.edu.utn.frc.tup.lciii.Service.Impl;

import ar.edu.utn.frc.tup.lciii.models.*;
import java.util.List;

public class PieceServiceImplTest {

    public boolean checkMyPieces(Position position, List<Piece> myList) {
        boolean aux = false;
        for (Piece piece : myList) {
            if (piece.getStatus().equals(Status.ALIVE) && piece.getPosition().equals(position)) {
                aux = true;
                break;
            }
        }
        return aux;
    }



    public boolean checkEnemyPositions(Position position, List<Piece> enemyList) {
        boolean aux = false;
        for (Piece piece : enemyList) {
            if (piece.getStatus().equals(Status.ALIVE) && piece.getPosition().equals(position)) {
                aux = true;
                break;
            }
        }
        return aux;
    }
}
