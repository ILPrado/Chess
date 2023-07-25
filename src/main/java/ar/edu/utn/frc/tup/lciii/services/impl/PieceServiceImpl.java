package ar.edu.utn.frc.tup.lciii.services.impl;//clase abstracta que representara una pieza de ajedrez generica

//a partir de ella definiremos las subclases por cada tipo de pieza de ajedrez: pawn/rook/knight/bishop/queen/king


import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.PieceServiceInterface;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public abstract class PieceServiceImpl implements PieceServiceInterface {


    public boolean verifyMove(Piece piece, Position position, List<Piece> enemyList, List<Piece> myList){
        setParam(piece);
        fillPositions(enemyList,myList);

        boolean aux = false;
        if (verifyEatingPiece(enemyList,position)||verifyEndMovePosition(position))
        {
            move(position,piece);
            aux = true;
        }else {
            System.out.println("This piece can't move to the specified position");
        }
        return aux;
    }
    public abstract void setParam(Piece piece);
    public abstract void fillPositions(List<Piece> enemyList, List<Piece> myList);
    public abstract boolean verifyEndMovePosition(Position position);
    public abstract boolean verifyEatingPiece(List<Piece> enemyList, Position p);
    public void move(Position position, Piece piece){
        piece.setPosition(position);
    }
    public boolean checkEnemyPositions(Position position, List<Piece> enemyList){
        boolean aux = false;
        for (Piece piece: enemyList){
            if (piece.getStatus().equals(Status.ALIVE)&&piece.getPosition().equals(position)){
                aux = true;
                break;
            }
        }
        return aux;
    }
    public boolean checkMyPieces(Position position, List<Piece> myList){
        boolean aux = false;
        for (Piece piece: myList){
            if(piece.getStatus().equals(Status.ALIVE)&&piece.getPosition().equals(position)){
                aux = true;
                break;
            }
        }
        return aux;
    }

    public abstract Piece clonePiece(Piece piece);

}
