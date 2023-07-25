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
public class KingServiceImpl extends  PieceServiceImpl{

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
        posiblePositions.add(new Position(position.getColumn(),position.getRow()+1)); // arriba
        posiblePositions.add(new Position(position.getColumn()+1,position.getRow()+1)); // arriba-derecha
        posiblePositions.add(new Position(position.getColumn()+1,position.getRow())); // derecha
        posiblePositions.add(new Position(position.getColumn()+1,position.getRow()-1)); // abajo-derecha
        posiblePositions.add(new Position(position.getColumn(),position.getRow()-1)); //abajo
        posiblePositions.add(new Position(position.getColumn()-1,position.getRow()-1)); //abajo-izquierda
        posiblePositions.add(new Position(position.getColumn()-1,position.getRow())); // izquierda
        posiblePositions.add(new Position(position.getColumn()-1,position.getRow()+1)); // arrba-izquierda

        for (int i = posiblePositions.size()-1; i >=0; i-- ) {
            Position p = posiblePositions.get(i);
            if (p.getRow() < 0 || p.getRow() > 7 || p.getColumn() > 7 || p.getColumn() < 0)
            {
                posiblePositions.remove(p);
            }
            else
            {
                for (Piece piece: myList) {
                    if (piece.getPosition().equals(p) && piece.getStatus().equals(Status.ALIVE))
                    {
                        posiblePositions.remove(p);
                        break;
                    }
                }
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
            if (aux){
                break;}
        }
        return aux;
    }

    @Override
    public Piece clonePiece(Piece piece)
    {
        return new King(piece.getColor(),piece.getStatus(),piece.getPosition(), piece.getSymbol(), piece.getType(),
                piece.getPosiblePositions());
    }
}