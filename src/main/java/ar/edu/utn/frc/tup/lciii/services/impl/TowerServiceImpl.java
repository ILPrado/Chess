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
//ROOK= TORRE
public class TowerServiceImpl extends PieceServiceImpl{
    private Position position;
    private List<Position> posiblePositions;

    @Override
    public void setParam(Piece piece){
        position = piece.getPosition();
        posiblePositions = piece.getPosiblePositions();}
    @Override
    public void fillPositions(List<Piece> enemyList, List<Piece> myList){
        this.posiblePositions.clear();
        for (int i=position.getRow()+1; i<8; i++) // Recorro para arriba
        {

            Position p = new Position(position.getColumn(),i);
            if(checkMyPieces(p,myList)){ // Si hay una ficha mia no agrego el movimiento
                break;
            } else if (checkEnemyPositions(p,enemyList)){ // Si hay una ficha enemiga, agrego el mov (puedo comer)
                posiblePositions.add(p);
                break;
            }
            else { // Si no hay ninguna pieza, agrego el movimiento
                posiblePositions.add(p);
            }
        }
        for (int i=position.getRow()-1; i>=0; i--){ // Recorro para abajo

            Position p = new Position(position.getColumn(),i);
            if(checkMyPieces(p,myList)) {
                break;
            } else if (checkEnemyPositions(p,enemyList)) {
                posiblePositions.add(p);
                break;
            } else
            {
                posiblePositions.add(p);
            }
        }
        for (int i=position.getColumn()+1; i<8; i++){ // Recorro para la derecha

            Position p = new Position(i,position.getRow());
            if(checkMyPieces(p,myList)){
                break;
            } else if (checkEnemyPositions(p,enemyList)){
                posiblePositions.add(p);
                break;}
            else {
                posiblePositions.add(p);}
        }
        for (int i=position.getColumn()-1; i>=0; i--){ // Recorro para la izquierda

            Position p = new Position(i,position.getRow());
            if(checkMyPieces(p,myList)){
                break;
            } else if (checkEnemyPositions(p,enemyList)){
                posiblePositions.add(p);
                break;
            }else {
                posiblePositions.add(p);}
        }
    }
    @Override // Posicion final del  movimiento de usuario, corroborar si hay una pieza enemiga en esa posicion
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
            if (aux) {
                break;
            }

        }
        return aux;
    }

    // Verifica que la posicion que paso por parametros
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
        return new Tower(piece.getColor(),piece.getStatus(),piece.getPosition(), piece.getSymbol(), piece.getType(),
                piece.getPosiblePositions());
    }
}




