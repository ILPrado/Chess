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
public class HorseServiceImpl extends PieceServiceImpl{

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

        if(position.getColumn() < 6) // puedo recorrer derecha
        {
            if(position.getRow()>0&&position.getRow()<7) // puedo recorer +1 y -1
            {
                posiblePositions.add(new Position(position.getColumn()+2,position.getRow()+1));
                posiblePositions.add(new Position(position.getColumn()+2,position.getRow()-1));
            }
            else
            {
                if(position.getRow().equals(0)) // si estoy en los limites tengo disponible un solo movimiento
                {
                    posiblePositions.add(new Position(position.getColumn()+2,position.getRow()+1));
                }else {
                    posiblePositions.add(new Position(position.getColumn()+2,position.getRow()-1)); // estoy del otro lado del tablero
                }
            }
        }
        if(position.getColumn() > 1) // puedo recorrer izquierda
        {
            if(position.getRow()>0&&position.getRow()<7) // puedo recorer +1 y -1
            {
                posiblePositions.add(new Position(position.getColumn()-2,position.getRow()+1));
                posiblePositions.add(new Position(position.getColumn()-2,position.getRow()-1));
            }
            else
            {
                if(position.getRow().equals(0))
                {
                    posiblePositions.add(new Position(position.getColumn()-2,position.getRow()+1));
                }else {
                    posiblePositions.add(new Position(position.getColumn()-2,position.getRow()-1));
                }
            }
        }
        if(position.getRow() < 6) // puedo recorrer arriba
        {
            if(position.getColumn()>0&&position.getColumn()<7) // puedo recorer +1 y -1
            {
                posiblePositions.add(new Position(position.getColumn()+1,position.getRow()+2));
                posiblePositions.add(new Position(position.getColumn()-1,position.getRow()+2));
            }
            else
            {
                if(position.getColumn().equals(0))
                {
                    posiblePositions.add(new Position(position.getColumn()+1,position.getRow()+2));
                }else {
                    posiblePositions.add(new Position(position.getColumn()-1,position.getRow()+2));
                }
            }
        }
        if(position.getRow() > 1) // puedo recorrer abajo
        {
            if(position.getColumn()>0&&position.getColumn()<7) // puedo recorer +1 y -1
            {
                posiblePositions.add(new Position(position.getColumn()+1,position.getRow()-2));
                posiblePositions.add(new Position(position.getColumn()-1,position.getRow()-2));
            }
            else
            {
                if(position.getColumn().equals(0))
                {
                    posiblePositions.add(new Position(position.getColumn()+1,position.getRow()-2));
                }else {
                    posiblePositions.add(new Position(position.getColumn()-1,position.getRow()-2));
                }
            }
        }
        // busco en mis piezas y en mis posibles movimientos y borro los que coincidan
        for (Piece p: myList) {
            if(p.getStatus().equals(Status.ALIVE)) {
                for (Position pos : posiblePositions) {
                    if (p.getPosition().equals(pos)) {
                        posiblePositions.remove(pos);
                        break;
                    }
                }
            }
        }
    }
    @Override
    public boolean verifyEndMovePosition(Position position) {
        boolean aux = false;
        for (Position p : posiblePositions) {
            if (p.equals(position)) {
                aux = true;
                break;
            }
        }
        return aux;
    }
    @Override
    public boolean verifyEatingPiece(List<Piece> enemyList, Position p){
        boolean aux = false;
        for (Piece piece : enemyList){
            if(piece.getStatus().equals(Status.ALIVE)){
                for (Position position : posiblePositions){
                    if (piece.getPosition().equals(p) && position.equals(p)){
                        aux = true;
                        piece.setStatus(Status.DEAD);
                        break;
                    }
                }
            }
            if(aux){ break;
            }
        }
        return aux;
    }
    @Override
    public Piece clonePiece(Piece piece)
    {
        return new Horse(piece.getColor(),piece.getStatus(),piece.getPosition(), piece.getSymbol(), piece.getType(),
                piece.getPosiblePositions());
    }
}