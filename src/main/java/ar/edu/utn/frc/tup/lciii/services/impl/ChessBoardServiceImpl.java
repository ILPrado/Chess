package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.models.Chessboard;
import ar.edu.utn.frc.tup.lciii.models.Piece;
import ar.edu.utn.frc.tup.lciii.models.Status;
import ar.edu.utn.frc.tup.lciii.services.ChessBoardServiceInterface;



import ar.edu.utn.frc.tup.lciii.models.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Data
@AllArgsConstructor
//implementar metodos para inicializar el tablero, mostrarlo en consola y vaidar movimientos de piezas
public class ChessBoardServiceImpl implements ChessBoardServiceInterface {
    private Chessboard chessboard;
    private String[][] board;
    private static final Integer ROWS = 8;
    private static final Integer COLUMNS = 8;
    private static final String Espacio = "\u001B[32m-";

    public ChessBoardServiceImpl() {
        board = new String[ROWS][COLUMNS];
    }
    @Override
    public void initializeBoard() {
        for (int f = 0; f < ROWS; f++) {
            for (int c = 0; c < COLUMNS; c++) {
                this.board[f][c] = Espacio;
            }
        }
    }
    @Override
    public Chessboard getChessBoard() {
        chessboard = new Chessboard(board);
        return chessboard;
    }
    @Override
    public void drawBoard(Player p1 , Player p2) {
        List<Piece> lstPiecesInGame = new ArrayList<>();

        for (Piece p : p1.getLstPieces()) {
            if (p.getStatus().equals(Status.ALIVE)) {
                lstPiecesInGame.add(p);
            }
        }
        for (Piece p : p2.getLstPieces()) {
            if (p.getStatus().equals(Status.ALIVE)) {
                lstPiecesInGame.add(p);
            }
        }

        boolean aux = false;
        StringBuilder sb = new StringBuilder();

        sb.append("      A    B     C     D     E     F     G     H\n");
        sb.append("  -------------------------------------------------\n");
        for (int f = ROWS-1; f >= 0; f--) {
            sb.append(f + " |");
            for (int c = 0; c < COLUMNS; c++) {
                aux=false;
                for (Piece p:lstPiecesInGame ) {
                    if(p.getPosition().getRow().equals(f)&&p.getPosition().getColumn().equals(c)){
                        sb.append("  " + p.getSymbol() + "\u001B[0m" + "  ");
                        sb.append("|");
                        aux=true;
                        break;
                    }

                }
                if (!aux){
                    sb.append("  " + Espacio + "\u001B[0m" + "  ");
                    sb.append("|");
                }
            }
            sb.append("  " + f + "\n");
            sb.append("  -------------------------------------------------\n");
        }
        sb.append("    A     B      C     D     E     F     G     H\n");
        System.out.println(sb);

    }

}