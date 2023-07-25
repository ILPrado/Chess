package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.models.Piece;
import ar.edu.utn.frc.tup.lciii.models.Position;
import ar.edu.utn.frc.tup.lciii.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class BishopServiceImpl extends PieceServiceImpl {

    private Position position;
    private  List<Position> posiblePositions;

    @Override
    public void setParam(Piece piece){
        position = piece.getPosition();
        posiblePositions = piece.getPosiblePositions();
    }
    @Override
    public void fillPositions(List<Piece> enemyList, List<Piece> myList){
        // TODO: Ver si se puede crear una funcion "general"
        posiblePositions.clear();
        for (int i=position.getRow()+1, j=position.getColumn()+1; i<8 && j<8; i++, j++) // Recorro para arriba - derecha
        {
            Position p = new Position(j,i);
            if(checkMyPieces(p,myList))
            {
                break;
            } else if (checkEnemyPositions(p,enemyList))
            {
                posiblePositions.add(p);
                break;
            } else {
                posiblePositions.add(p);
            }

        }
        for (int i=position.getRow()-1, j=position.getColumn()+1; i>=0 && j<8; i--, j++) // Recorro para abajo - derecha
        {
            Position p = new Position(j,i);
            if(checkMyPieces(p,myList))
            {
               break;
            } else if (checkEnemyPositions(p,enemyList))
            {
                posiblePositions.add(p);
                break;
            }else {
                posiblePositions.add(p);
            }
        }
        for (int i=position.getRow()-1, j=position.getColumn()-1; i>=0 && j>=0; i--, j--) // Recorro para la abajo - izquierda
        {
            Position p = new Position(j,i);
            if(checkMyPieces(p,myList))
            {
                break;
            } else if (checkEnemyPositions(p,enemyList))
            {
                posiblePositions.add(p);
                break;
            } else {
                posiblePositions.add(p);
            }
        }
        for (int i=position.getRow()+1, j=position.getColumn()-1; i<8 && j>=0; i++, j--) // Recorro para la arriba - izquierda
        {
            Position p = new Position(j,i);
            if(checkMyPieces(p,myList))
            {
                break;
            } else if (checkEnemyPositions(p,enemyList))
            {
                posiblePositions.add(p);
                break;
            } else {
                posiblePositions.add(p);
            }
        }
    }
    @Override
    public boolean verifyEatingPiece(List<Piece> enemyList, Position p){
        boolean aux = false;
        for (Piece piece: enemyList) {
            if (piece.getStatus().equals(Status.ALIVE)){
                for (Position position: posiblePositions) {
                    if(piece.getPosition().equals(p) && position.equals(p))
                    {
                        aux = true;
                        piece.setStatus(Status.DEAD);
                        break;
                    }
                }
            }
            if (aux) { break; }
        }
        return aux;
    }
    @Override
    public boolean verifyEndMovePosition(Position position){
        boolean aux = false;
        for (Position p: posiblePositions) {
            if(p.equals(position))
            {
                aux = true;
                break;
            }
        }
        return aux;
    }

    @Override
    public Piece clonePiece(Piece piece)
    {
        return new Bishop(piece.getColor(),piece.getStatus(),piece.getPosition(), piece.getSymbol(), piece.getType(),
                piece.getPosiblePositions());
    }
}