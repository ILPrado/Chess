package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.models.Piece;
import ar.edu.utn.frc.tup.lciii.models.Position;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PieceServiceInterface {

    boolean verifyMove(Piece piece, Position position, List<Piece> enemyList, List<Piece> myList);
    void setParam(Piece piece);
    // Lleno las posibles posiciones de mi peon
    // Validando: que no este "out of bounds" y que no haya ninguna pieza enemiga a donde quiero mover.

    void fillPositions(List<Piece> enemyList, List<Piece> myList);
    // Verifico si llego hasta la casilla indicada por param

    boolean verifyEndMovePosition(Position position);
    // Verifico si puedo comer a alguna pieza de la lista por parametro

    boolean verifyEatingPiece(List<Piece> enemyList, Position p);
    void move(Position position, Piece piece);
    // Busco en la lista de piezas enemiga si alguna coincide con mi posicion enviada por parametro
    // Devuelvo true si alguna coincide
    boolean checkEnemyPositions(Position position, List<Piece> enemyList);
    boolean checkMyPieces(Position position, List<Piece> myList);

    Piece clonePiece(Piece piece);

}
