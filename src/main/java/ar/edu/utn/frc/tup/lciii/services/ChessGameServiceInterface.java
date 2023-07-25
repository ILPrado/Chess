package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.models.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChessGameServiceInterface {

    List<Piece> initializePieces(Color color);
    boolean Validate(String input, Player player);
    String charToNumber(String answer);

    boolean move(Player player, Player enemyPlayer, Position pIni, Position pEnd);

    boolean checkMate(List<Piece> player1, List<Piece> player2);

    //boolean checkMate(List<Piece> player1,List<Piece> player2);

    boolean check(List<Piece> player1, List<Piece> player2);

    List<Piece> cloneList(List<Piece> originalList);

    Long getLastId();

    void createChessGame(ChessGame chessGame);

    void setChessMove(ChessGame chessGame);

    void setChessGame(ChessGame chessGame,Position pIni,Position pEnd);

    String viewChessGamesSaved();

    boolean chessGameExist(Integer aux);

    ChessGame getChessGame(Integer aux);
}
