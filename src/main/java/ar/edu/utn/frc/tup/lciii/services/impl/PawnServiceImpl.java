package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.models.*;

import java.util.List;
import lombok.*;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class PawnServiceImpl extends PieceServiceImpl {
    private Position position;
    private Color color;
    private List<Position> posiblePositions;
    public void setParam(Piece piece) {
        position = piece.getPosition();
        color = piece.getColor();
        posiblePositions = piece.getPosiblePositions();
    }
    @Override
    public void fillPositions(List<Piece> enemyList, List<Piece> myList) {
        posiblePositions.clear();
        if (this.color.equals(Color.WHITE)){
            if (position.getRow() == 0 || position.getRow() == 8){// Verifico que no este afuera de su lugar
                // TODO: Devolver error.
            } else if (position.getRow() == 1){//fila 2

                for (int i = 1; i < 3; i++) { //le sumo 1 o le sumo 2
                    Position p = new Position(position.getColumn(),position.getRow() + i);
                    if(p.getRow() <= 7 && !checkEnemyPositions(p, enemyList) && !checkMyPieces(p, myList)){ // valido q no se haya salido del tablero
                        posiblePositions.add(p);
                    }else {
                        break;
                    }
                }
            } else{
                Position p = new Position(position.getColumn(),position.getRow() + 1);
                if(p.getRow() <= 7 && !checkEnemyPositions(p, enemyList) && !checkMyPieces(p, myList)){ // valido q no se haya salido del tablero
                    posiblePositions.add(p);
                }
            }
        }else{
            if(position.getRow() == 7 || position.getRow() == -1){// Verifico que no este afuera de su lugar
                // TODO: Devolver error.
            } else if (position.getRow() == 6) //fila 7
            {
                for(int i = 1; i < 3; i++){ //le resto 1 o le resto 2
                    Position p = new Position(position.getColumn(),position.getRow() - i);
                    if (p.getRow() >= 0 && !checkEnemyPositions(p, enemyList) && !checkMyPieces(p, myList)){ // valido q no se haya salido del tablero
                        posiblePositions.add(p);
                    }else {
                        break;
                    }
                }
            } else {
                Position p = new Position(position.getColumn(),position.getRow() - 1);
                if (p.getRow() >= 0 && !checkEnemyPositions(p, enemyList) && !checkMyPieces(p, myList)){ // valido q no se haya salido del tablero
                    posiblePositions.add(p);
                }
            }
        }
    }
    @Override
    public boolean verifyEatingPiece(List<Piece> enemyList, Position p) {
        boolean aux = false;
        for (Piece piece : enemyList){
            if(piece.getStatus().equals(Status.ALIVE)){
                if (color.equals(Color.WHITE)){
                    if(piece.getPosition().getRow().equals(position.getRow() + 1) && (piece.getPosition().getColumn() ==
                            position.getColumn() + 1 || piece.getPosition().getColumn() == position.getColumn() - 1)){
                        piece.setStatus(Status.DEAD);
                        aux = true;
                        break;
                    }
                }else{
                    if (piece.getPosition().getRow().equals(position.getRow() - 1) && (piece.getPosition().getColumn() ==
                            position.getColumn() + 1 || piece.getPosition().getColumn() == position.getColumn() - 1)){
                        piece.setStatus(Status.DEAD);
                        aux = true;
                        break;
                    }
                }
            }
        }
        return aux;
    }
    @Override
    public boolean verifyEndMovePosition(Position position){
        boolean aux = false;
        for (Position p: posiblePositions){
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
        return new Pawn(piece.getColor(),piece.getStatus(),piece.getPosition(), piece.getSymbol(), piece.getType(),
                piece.getPosiblePositions());
    }
}