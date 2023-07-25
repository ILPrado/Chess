package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.models.Piece;
import ar.edu.utn.frc.tup.lciii.models.Position;
import ar.edu.utn.frc.tup.lciii.models.Status;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class QueenServiceImpl extends PieceServiceImpl{

    Position position;
    List<Position> posiblePositions;

    @Override
    public void setParam(Piece piece){
        position = piece.getPosition();
        posiblePositions = piece.getPosiblePositions();
    }
    @Override
    public void fillPositions(List<Piece> enemyList, List<Piece> myList){
        posiblePositions.clear();
        for (int i=position.getRow()+1; i<8; i++) // Recorro para arriba
        {
            Position p = new Position(position.getColumn(),i);
            if(checkMyPieces(p,myList)) // Si hay una ficha mia no agrego el movimiento
            {
                break;
            } else if (checkEnemyPositions(p,enemyList)) // Si hay una ficha enemiga, agrego el mov (puedo comer)
            {
                posiblePositions.add(p);
                break;
            } else { // Si no hay ninguna pieza, agrego el movimiento
                posiblePositions.add(p);
            }

        }
        for (int i=position.getRow()-1; i>=0; i--) // Recorro para abajo
        {
            Position p = new Position(position.getColumn(),i);
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
        for (int i=position.getColumn()+1; i<8; i++) // Recorro para la derecha
        {
            Position p = new Position(i,position.getRow());
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
        for (int i=position.getColumn()-1; i>=0; i--) // Recorro para la izquierda
        {
            Position p = new Position(i,position.getRow());
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
            } else {
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
    public boolean verifyEatingPiece(List<Piece> enemyList, Position p){
        boolean aux = false;
        for (Piece piece: enemyList) {
            if(piece.getStatus().equals(Status.ALIVE))
            {
                for (Position position : posiblePositions) {
                    if (piece.getPosition().equals(p) && position.equals(p)) {
                        aux = true;
                        piece.setStatus(Status.DEAD);
                        break;
                    }
                }
            }
            if (aux) {
                break;
            }
        }
        return aux;
    }

    @Override
    public Piece clonePiece(Piece piece)
    {
        return new Queen(piece.getColor(),piece.getStatus(),piece.getPosition(), piece.getSymbol(), piece.getType(),
                piece.getPosiblePositions());
    }
}