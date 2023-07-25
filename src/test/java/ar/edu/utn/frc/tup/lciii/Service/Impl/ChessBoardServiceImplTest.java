package ar.edu.utn.frc.tup.lciii.Service.Impl;
import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.impl.ChessBoardServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.*;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ChessBoardServiceImplTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ChessBoardServiceImpl chessBoardService;
    private String[][] boardTester;
    private Chessboard chessboard;
    private ByteArrayOutputStream testOut;
    private ByteArrayInputStream testIn;

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

    @DisplayName("Test set Inizialize board")
    @Test
    public void initializeBoardTest() {
        chessBoardService = new ChessBoardServiceImpl();
        String s1 = "\u001B[32m-";
        boardTester = new String[][] {{s1,s1,s1,s1,s1,s1,s1,s1}, {s1,s1,s1,s1,s1,s1,s1,s1},{s1,s1,s1,s1,s1,s1,s1,s1},
            {s1,s1,s1,s1,s1,s1,s1,s1},{s1,s1,s1,s1,s1,s1,s1,s1},{s1,s1,s1,s1,s1,s1,s1,s1},
            {s1,s1,s1,s1,s1,s1,s1,s1},{s1,s1,s1,s1,s1,s1,s1,s1}};
        chessBoardService.initializeBoard();
        Assert.assertEquals(boardTester,chessBoardService.getBoard());
    }

    @DisplayName("Test draw board with pieces")
    @Test
    public void drawBoardTest() {
        chessBoardService = new ChessBoardServiceImpl();
        List<Piece> lstP1 = new ArrayList<>();
        lstP1.add(new Tower(Color.WHITE,Status.ALIVE,new Position(0,0),"R","R",null));
        lstP1.add(new Horse(Color.WHITE,Status.ALIVE,new Position(1,0),"N","N",null));
        lstP1.add(new Bishop(Color.WHITE,Status.ALIVE,new Position(2,0),"B","B",null));
        lstP1.add(new Queen(Color.WHITE,Status.ALIVE,new Position(3,0),"Q","Q",null));
        lstP1.add(new King(Color.WHITE,Status.ALIVE,new Position(4,0),"K","K",null));
        lstP1.add(new Bishop(Color.WHITE,Status.ALIVE,new Position(5,0),"B","B",null));
        lstP1.add(new Horse(Color.WHITE,Status.ALIVE,new Position(6,0),"N","N",null));
        lstP1.add(new Tower(Color.WHITE,Status.ALIVE,new Position(7,0),"R","R",null));
        lstP1.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(2,2),"T","T",null));
        List<Piece> lstP2 = new ArrayList<>();
        lstP2.add(new Tower(Color.BLACK,Status.ALIVE,new Position(0,7),"R","R",null));
        lstP2.add(new Horse(Color.BLACK,Status.ALIVE,new Position(1,7),"N","N",null));
        lstP2.add(new Bishop(Color.BLACK,Status.ALIVE,new Position(2,7),"B","B",null));
        lstP2.add(new Queen(Color.BLACK,Status.ALIVE,new Position(3,7),"Q","Q",null));
        lstP2.add(new King(Color.BLACK,Status.ALIVE,new Position(4,7),"K","K",null));
        lstP2.add(new Bishop(Color.BLACK,Status.ALIVE,new Position(5,7),"B","B",null));
        lstP2.add(new Horse(Color.BLACK,Status.ALIVE,new Position(6,7),"N","N",null));
        lstP2.add(new Tower(Color.BLACK,Status.ALIVE,new Position(7,7),"R","R",null));
        lstP2.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,3),"T","T",null));
        Player p1 = new Player("p1",lstP1);
        Player p2 = new Player("p2",lstP2);
        String stringTester = "      A    B     C     D     E     F     G     H\n" +
                "  -------------------------------------------------\n" +
                "7 |  R\u001B[0m  |  N\u001B[0m  |  B\u001B[0m  |  Q\u001B[0m  |  K\u001B[0m  |  B\u001B[0m  |  N\u001B[0m  |  R\u001B[0m  |  7\n" +
                "  -------------------------------------------------\n" +
                "6 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  6\n" +
                "  -------------------------------------------------\n" +
                "5 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  5\n" +
                "  -------------------------------------------------\n" +
                "4 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  4\n" +
                "  -------------------------------------------------\n" +
                "3 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  T\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  3\n" +
                "  -------------------------------------------------\n" +
                "2 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  T\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  2\n" +
                "  -------------------------------------------------\n" +
                "1 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  1\n" +
                "  -------------------------------------------------\n" +
                "0 |  R\u001B[0m  |  N\u001B[0m  |  B\u001B[0m  |  Q\u001B[0m  |  K\u001B[0m  |  B\u001B[0m  |  N\u001B[0m  |  R\u001B[0m  |  0\n" +
                "  -------------------------------------------------\n" +
                "    A     B      C     D     E     F     G     H\n";
        provideInput(stringTester);
        chessBoardService.drawBoard(p1,p2);
        Assert.assertEquals(stringTester+System.lineSeparator(),getOutput());
    }
    @DisplayName("Test draw board with pieces")
    @Test
    public void drawBoardPiecesDeadTest() {
        chessBoardService = new ChessBoardServiceImpl();
        List<Piece> lstP1 = new ArrayList<>();
        lstP1.add(new Tower(Color.WHITE,Status.DEAD,new Position(0,0),"R","R",null));
        lstP1.add(new Horse(Color.WHITE,Status.DEAD,new Position(1,0),"N","N",null));
        lstP1.add(new Bishop(Color.WHITE,Status.DEAD,new Position(2,0),"B","B",null));
        lstP1.add(new Queen(Color.WHITE,Status.DEAD,new Position(3,0),"Q","Q",null));
        lstP1.add(new King(Color.WHITE,Status.DEAD,new Position(4,0),"K","K",null));
        lstP1.add(new Bishop(Color.WHITE,Status.DEAD,new Position(6,0),"N","N",null));
        lstP1.add(new Tower(Color.WHITE,Status.DEAD,new Position(7,0),"R","R",null));
        lstP1.add(new Pawn(Color.WHITE,Status.DEAD,new Position(2,2),"T","T",null));
        List<Piece> lstP2 = new ArrayList<>();
        lstP2.add(new Tower(Color.BLACK,Status.DEAD,new Position(0,7),"R","R",null));
        lstP2.add(new Horse(Color.BLACK,Status.DEAD,new Position(1,7),"N","N",null));
        lstP2.add(new Bishop(Color.BLACK,Status.DEAD,new Position(2,7),"B","B",null));
        lstP2.add(new Queen(Color.BLACK,Status.DEAD,new Position(3,7),"Q","Q",null));
        lstP2.add(new King(Color.BLACK,Status.DEAD,new Position(4,7),"K","K",null));
        lstP2.add(new Bishop(Color.BLACK,Status.DEAD,new Position(5,7),"B","B",null));
        lstP2.add(new Horse(Color.BLACK,Status.DEAD,new Position(6,7),"N","N",null));
        lstP2.add(new Tower(Color.BLACK,Status.DEAD,new Position(7,7),"R","R",null));
        lstP2.add(new Pawn(Color.BLACK,Status.DEAD,new Position(3,3),"T","T",null));
        Player p1 = new Player("p1",lstP1);
        Player p2 = new Player("p2",lstP2);
        String stringTester = "      A    B     C     D     E     F     G     H\n" +
                "  -------------------------------------------------\n" +
                "7 |  R\u001B[0m  |  N\u001B[0m  |  B\u001B[0m  |  Q\u001B[0m  |  K\u001B[0m  |  B\u001B[0m  |  N\u001B[0m  |  R\u001B[0m  |  7\n" +
                "  -------------------------------------------------\n" +
                "6 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  6\n" +
                "  -------------------------------------------------\n" +
                "5 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  5\n" +
                "  -------------------------------------------------\n" +
                "4 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  4\n" +
                "  -------------------------------------------------\n" +
                "3 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  T\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  3\n" +
                "  -------------------------------------------------\n" +
                "2 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  T\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  2\n" +
                "  -------------------------------------------------\n" +
                "1 |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  \u001B[32m-\u001B[0m  |  1\n" +
                "  -------------------------------------------------\n" +
                "0 |  R\u001B[0m  |  N\u001B[0m  |  B\u001B[0m  |  Q\u001B[0m  |  K\u001B[0m  |  B\u001B[0m  |  N\u001B[0m  |  R\u001B[0m  |  0\n" +
                "  -------------------------------------------------\n" +
                "    A     B      C     D     E     F     G     H\n";
        provideInput(stringTester);
        chessBoardService.drawBoard(p1,p2);
        Assert.assertNotEquals(stringTester+System.lineSeparator(),getOutput());
    }


    @Test
    public void getBoardTest() {
        chessBoardService = new ChessBoardServiceImpl();

        boardTester = new String[8][8];
        chessBoardService.getChessBoard();
        Assert.assertEquals(boardTester,chessBoardService.getBoard());
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput(){
        return testOut.toString();
    }
}
