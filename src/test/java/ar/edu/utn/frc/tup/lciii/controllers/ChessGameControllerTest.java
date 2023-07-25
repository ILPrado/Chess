package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.ChessBoardServiceInterface;
import ar.edu.utn.frc.tup.lciii.services.ChessGameServiceInterface;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChessGameControllerTest {

    @Mock
    private ChessBoardServiceInterface boardService;
    @Mock
    private ChessGame chessGame;
    private static final Integer id=1;
    private static final  Integer playerInTurn=1;
    private static final  String status = "In Progress";
    @Mock
    private Player playerOne;
    private static final String playerOneName = "pepe";
    private static final List<Piece> lstPiecesOne = new ArrayList<>(Arrays.asList(
            new Tower(Color.WHITE, Status.ALIVE, new Position(0, 0), "R", "R",new ArrayList<>()),
            new Horse(Color.WHITE, Status.ALIVE, new Position(1, 0), "N", "N",new ArrayList<>()),
            new Bishop(Color.WHITE, Status.ALIVE, new Position(2, 0), "B", "B",new ArrayList<>()),
            new Queen(Color.WHITE, Status.ALIVE, new Position(3, 0), "Q", "Q",new ArrayList<>()),
            new King(Color.WHITE, Status.ALIVE, new Position(4, 0), "K", "K",new ArrayList<>()),
            new Bishop(Color.WHITE, Status.ALIVE, new Position(5, 0), "B", "B",new ArrayList<>()),
            new Horse(Color.WHITE, Status.ALIVE, new Position(6, 0), "N", "N",new ArrayList<>()),
            new Tower(Color.WHITE, Status.ALIVE, new Position(7, 0), "R", "R",new ArrayList<>()),
            new Pawn(Color.WHITE, Status.ALIVE, new Position(0, 1), "P", "P",new ArrayList<>()),
            new Pawn(Color.WHITE, Status.ALIVE, new Position(1, 1), "P", "P",new ArrayList<>()),
            new Pawn(Color.WHITE, Status.ALIVE, new Position(2, 1), "P", "P",new ArrayList<>()),
            new Pawn(Color.WHITE, Status.ALIVE, new Position(3, 1), "P", "P",new ArrayList<>()),
            new Pawn(Color.WHITE, Status.ALIVE, new Position(4, 1), "P", "P",new ArrayList<>()),
            new Pawn(Color.WHITE, Status.ALIVE, new Position(5, 1), "P", "P",new ArrayList<>()),
            new Pawn(Color.WHITE, Status.ALIVE, new Position(6, 1), "P", "P",new ArrayList<>()),
            new Pawn(Color.WHITE, Status.ALIVE, new Position(7, 1), "P", "P",new ArrayList<>())));
    @Mock
    private Player playerTwo;
    private static final String playerTwoName = "tete";
    private static final List<Piece> lstPiecesTwo = new ArrayList<>(Arrays.asList(
            new Tower(Color.BLACK, Status.ALIVE, new Position(0, 7), "R", "R",new ArrayList<>()),
            new Horse(Color.BLACK, Status.ALIVE, new Position(1, 7), "N", "N",new ArrayList<>()),
            new Bishop(Color.BLACK, Status.ALIVE, new Position(2, 7), "B", "B",new ArrayList<>()),
            new Queen(Color.BLACK, Status.ALIVE, new Position(3, 7), "Q", "Q",new ArrayList<>()),
            new King(Color.BLACK, Status.ALIVE, new Position(4, 7), "K", "K",new ArrayList<>()),
            new Bishop(Color.BLACK, Status.ALIVE, new Position(5, 7), "B", "B",new ArrayList<>()),
            new Horse(Color.BLACK, Status.ALIVE, new Position(6, 7), "N", "N",new ArrayList<>()),
            new Tower(Color.BLACK, Status.ALIVE, new Position(7, 7), "R", "R",new ArrayList<>()),
            new Pawn(Color.BLACK, Status.ALIVE, new Position(0, 6), "P", "P",new ArrayList<>()),
            new Pawn(Color.BLACK, Status.ALIVE, new Position(1, 6), "P", "P",new ArrayList<>()),
            new Pawn(Color.BLACK, Status.ALIVE, new Position(2, 6), "P", "P",new ArrayList<>()),
            new Pawn(Color.BLACK, Status.ALIVE, new Position(3, 6), "P", "P",new ArrayList<>()),
            new Pawn(Color.BLACK, Status.ALIVE, new Position(4, 6), "P", "P",new ArrayList<>()),
            new Pawn(Color.BLACK, Status.ALIVE, new Position(5, 6), "P", "P",new ArrayList<>()),
            new Pawn(Color.BLACK, Status.ALIVE, new Position(6, 6), "P", "P",new ArrayList<>()),
            new Pawn(Color.BLACK, Status.ALIVE, new Position(7, 6), "P", "P",new ArrayList<>())));

    @Mock
    private ChessMove chessMove;
    private static final String move = "0102";
    @Mock
    private ChessGameServiceInterface gameService;
    @Spy
    @InjectMocks
    private ChessGameController chessGameController;
    private static final String MOVE_INPUT_REGEX = "[A-H]{1} [0-7]{1};[A-H]{1} [0-7]{1}";
    private ByteArrayOutputStream testOut;
    private ByteArrayInputStream testIn;
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    @DisplayName("Test change Player In Turn")
    public void changePlayerInTurnTest() throws Exception {
        chessGameController = new ChessGameController();
        chessGameController.setChessGame(new ChessGame());
        chessGameController.getChessGame().setPlayerInTurn(1);

        Method changePlayerInTurnToPublic = chessGameController.getClass().getDeclaredMethod("changePlayerInTurn");
        changePlayerInTurnToPublic.setAccessible(true);
        changePlayerInTurnToPublic.invoke(chessGameController);

        assertEquals(2,chessGameController.getChessGame().getPlayerInTurn().intValue());
        changePlayerInTurnToPublic.invoke(chessGameController);
        assertEquals(1,chessGameController.getChessGame().getPlayerInTurn().intValue());
    }

}

