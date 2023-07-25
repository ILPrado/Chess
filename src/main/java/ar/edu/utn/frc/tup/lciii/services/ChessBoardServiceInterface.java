package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.models.Chessboard;
import ar.edu.utn.frc.tup.lciii.models.Player;
import org.springframework.stereotype.Service;


@Service
public interface ChessBoardServiceInterface {
  void drawBoard(Player player1, Player player2);
  void initializeBoard();
  Chessboard getChessBoard();

}
