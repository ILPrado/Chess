package ar.edu.utn.frc.tup.lciii.Service.Impl;
import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.impl.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChessGameServiceImplTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private static final String MOVE_INPUT_REGEX = "[A-H]{1} [0-7]{1};[A-H]{1} [0-7]{1}";
    private PieceServiceImpl pieceService;

    private ChessGameServiceImpl chessGameService;

    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        chessGameService = new ChessGameServiceImpl();

    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void initializePiecesBlackTest() {

        List<Piece> lstPieces = new ArrayList<Piece>();

        String paint = "";
        int a = 7;
        int b = 6;
        lstPieces.add(new Tower(Color.BLACK, Status.ALIVE, new Position(0, a), paint + "R", "R", new ArrayList<>()));
        lstPieces.add(new Horse(Color.BLACK, Status.ALIVE, new Position(1, a), paint + "N", "N", new ArrayList<>()));
        lstPieces.add(new Bishop(Color.BLACK, Status.ALIVE, new Position(2, a), paint + "B", "B", new ArrayList<>()));
        lstPieces.add(new Queen(Color.BLACK, Status.ALIVE, new Position(3, a), paint + "Q", "Q", new ArrayList<>()));
        lstPieces.add(new King(Color.BLACK, Status.ALIVE, new Position(4, a), paint + "K", "K", new ArrayList<>()));
        lstPieces.add(new Bishop(Color.BLACK, Status.ALIVE, new Position(5, a), paint + "B", "B", new ArrayList<>()));
        lstPieces.add(new Horse(Color.BLACK, Status.ALIVE, new Position(6, a), paint + "N", "N", new ArrayList<>()));
        lstPieces.add(new Tower(Color.BLACK, Status.ALIVE, new Position(7, a), paint + "R", "R", new ArrayList<>()));

        for (int i = 0; i < 8; i++) {
            lstPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(i, b), paint + "P", "P", new ArrayList<>()));
        }

        Assertions.assertEquals(lstPieces, chessGameService.initializePieces(Color.BLACK));

    }

    @Test
    public void initializePiecesWhiteTest() {

        List<Piece> lstPieces = new ArrayList<Piece>();

        String paint = "";
        int a = 0;
        int b = 1;
        lstPieces.add(new Tower(Color.WHITE, Status.ALIVE, new Position(0, a), paint + "R", "R", new ArrayList<>()));
        lstPieces.add(new Horse(Color.WHITE, Status.ALIVE, new Position(1, a), paint + "N", "N", new ArrayList<>()));
        lstPieces.add(new Bishop(Color.WHITE, Status.ALIVE, new Position(2, a), paint + "B", "B", new ArrayList<>()));
        lstPieces.add(new Queen(Color.WHITE, Status.ALIVE, new Position(3, a), paint + "Q", "Q", new ArrayList<>()));
        lstPieces.add(new King(Color.WHITE, Status.ALIVE, new Position(4, a), paint + "K", "K", new ArrayList<>()));
        lstPieces.add(new Bishop(Color.WHITE, Status.ALIVE, new Position(5, a), paint + "B", "B", new ArrayList<>()));
        lstPieces.add(new Horse(Color.WHITE, Status.ALIVE, new Position(6, a), paint + "N", "N", new ArrayList<>()));
        lstPieces.add(new Tower(Color.WHITE, Status.ALIVE, new Position(7, a), paint + "R", "R", new ArrayList<>()));

        for (int i = 0; i < 8; i++) {
            lstPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(i, b), paint + "P", "P", new ArrayList<>()));
        }

        Assertions.assertEquals(lstPieces, chessGameService.initializePieces(Color.WHITE));

    }

    @Test
    public void charToNumber() {
        String answer = "A 1;A 2";
        String answer2 = "B 1;B 2";
        String answer3 = "C 1;C 2";
        String answer4 = "D 1;D 3";
        String answer5 = "E 1;E 4";
        String answer6 = "F 1;F 5";
        String answer7 = "G 1;G 6";
        String answer8 = "H 1;H 7";


        StringBuilder sb = new StringBuilder(answer);

        Assertions.assertEquals("0102", chessGameService.charToNumber(answer));
        Assertions.assertEquals("1112", chessGameService.charToNumber(answer2));
        Assertions.assertEquals("2122", chessGameService.charToNumber(answer3));
        Assertions.assertEquals("3133", chessGameService.charToNumber(answer4));
        Assertions.assertEquals("4144", chessGameService.charToNumber(answer5));
        Assertions.assertEquals("5155", chessGameService.charToNumber(answer6));
        Assertions.assertEquals("6166", chessGameService.charToNumber(answer7));
        Assertions.assertEquals("7177", chessGameService.charToNumber(answer8));

    }

    
    @Test
    public void movePawnTest() {
        List<Position> lstPosiblePositions = new ArrayList<>();
        Position p1 = new Position(0, 4);
        lstPosiblePositions.add(p1);
        List<Piece> lstMyPieces = new ArrayList<>();
        List<Piece> lstEnemyPieces = new ArrayList<>();
        pieceService = new PawnServiceImpl(new Position(0, 3), Color.WHITE, lstPosiblePositions);

        Pawn pawn = new Pawn(Color.WHITE, Status.ALIVE, new Position(0, 3), "P", "P", lstPosiblePositions);
        lstMyPieces.add(pawn);
        Player player1 = new Player("Nacho", lstMyPieces);
        Player enemyPlayer = new Player("JUAN", lstEnemyPieces);
        Position positionIni = new Position(0, 3);
        Position positionEnd = new Position(0, 4);

        boolean result = chessGameService.move(player1, enemyPlayer, positionIni, positionEnd);

        Assertions.assertTrue(result);


    }

    @Test
    public void moveQueenTest() {
        List<Position> lstPosiblePositions = new ArrayList<>();
        Position p1 = new Position(0, 4);
        lstPosiblePositions.add(p1);
        List<Piece> lstMyPieces = new ArrayList<>();
        List<Piece> lstEnemyPieces = new ArrayList<>();
        pieceService = new PawnServiceImpl(new Position(0, 7), Color.BLACK, lstPosiblePositions);

        Queen queen= new Queen(Color.BLACK, Status.ALIVE, new Position(0, 7), "Q", "Q", lstPosiblePositions);
        lstMyPieces.add(queen);
        Player player1 = new Player("Nacho", lstMyPieces);
        Player enemyPlayer = new Player("JUAN", lstEnemyPieces);
        Position positionIniq = new Position(0, 7);
        Position positionEndq = new Position(0, 5);

        boolean result = chessGameService.move(player1, enemyPlayer, positionIniq, positionEndq);

        Assertions.assertTrue(result);

    }
    @Test
    public void moveKingTest() {
        List<Position> lstPosiblePositions = new ArrayList<>();
        Position p1 = new Position(0, 4);
        lstPosiblePositions.add(p1);
        List<Piece> lstMyPieces = new ArrayList<>();
        List<Piece> lstEnemyPieces = new ArrayList<>();
        pieceService = new KingServiceImpl(new Position(0, 3), lstPosiblePositions);

        King king = new King(Color.WHITE, Status.ALIVE, new Position(0, 3), "K", "K", lstPosiblePositions);
        lstMyPieces.add(king);
        Player player1 = new Player("Nacho", lstMyPieces);
        Player enemyPlayer = new Player("JUAN", lstEnemyPieces);
        Position positionIni = new Position(0, 3);
        Position positionEnd = new Position(0, 4);

        boolean result = chessGameService.move(player1, enemyPlayer, positionIni, positionEnd);

        Assertions.assertTrue(result);


    }
    @Test
    public void moveRockTest() {
        List<Position> lstPosiblePositions = new ArrayList<>();
        Position p1 = new Position(0, 4);
        lstPosiblePositions.add(p1);
        List<Piece> lstMyPieces = new ArrayList<>();
        List<Piece> lstEnemyPieces = new ArrayList<>();
        pieceService = new KingServiceImpl(new Position(0, 3), lstPosiblePositions);

        Tower tower = new Tower(Color.WHITE, Status.ALIVE, new Position(0, 3), "R", "R", lstPosiblePositions);
        lstMyPieces.add(tower);
        Player player1 = new Player("Nacho", lstMyPieces);
        Player enemyPlayer = new Player("JUAN", lstEnemyPieces);
        Position positionIni = new Position(0, 3);
        Position positionEnd = new Position(0, 4);

        boolean result = chessGameService.move(player1, enemyPlayer, positionIni, positionEnd);

        Assertions.assertTrue(result);


    }
    @Test
    public void moveKnigtTest() {
        List<Position> lstPosiblePositions = new ArrayList<>();
        Position p1 = new Position(0, 4);
        lstPosiblePositions.add(p1);
        List<Piece> lstMyPieces = new ArrayList<>();
        List<Piece> lstEnemyPieces = new ArrayList<>();
        pieceService = new KingServiceImpl(new Position(0, 3), lstPosiblePositions);

        Horse horse = new Horse(Color.WHITE, Status.ALIVE, new Position(0, 3), "N", "N", lstPosiblePositions);
        lstMyPieces.add(horse);
        Player player1 = new Player("Nacho", lstMyPieces);
        Player enemyPlayer = new Player("JUAN", lstEnemyPieces);
        Position positionIni = new Position(0, 3);
        Position positionEnd = new Position(2, 2);

        boolean result = chessGameService.move(player1, enemyPlayer, positionIni, positionEnd);

        Assertions.assertTrue(result);


    }
    @Test
    public void moveBishopTest() {
        List<Position> lstPosiblePositions = new ArrayList<>();
        Position p1 = new Position(0, 4);
        lstPosiblePositions.add(p1);
        List<Piece> lstMyPieces = new ArrayList<>();
        List<Piece> lstEnemyPieces = new ArrayList<>();
        pieceService = new KingServiceImpl(new Position(0, 3), lstPosiblePositions);

        Bishop bishop= new Bishop(Color.WHITE, Status.ALIVE, new Position(0, 3), "B", "B", lstPosiblePositions);
        lstMyPieces.add(bishop);
        Player player1 = new Player("Nacho", lstMyPieces);
        Player enemyPlayer = new Player("JUAN", lstEnemyPieces);
        Position positionIni = new Position(0, 3);
        Position positionEnd = new Position(1, 4);

        boolean result = chessGameService.move(player1, enemyPlayer, positionIni, positionEnd);

        Assertions.assertTrue(result);


    }
    @Test
    public void moveDefaultFalseTest() {
        List<Position> lstPosiblePositions = new ArrayList<>();
        Position p1 = new Position(0, 4);
        lstPosiblePositions.add(p1);
        List<Piece> lstMyPieces = new ArrayList<>();
        List<Piece> lstEnemyPieces = new ArrayList<>();
        pieceService = new KingServiceImpl(new Position(0, 3), lstPosiblePositions);
        Pawn pawn = new Pawn(Color.WHITE, Status.ALIVE, new Position(0, 3), "K", " ", lstPosiblePositions);
        lstMyPieces.add(pawn);
        Player player1 = new Player("Nacho", lstMyPieces);
        Player enemyPlayer = new Player("JUAN", lstEnemyPieces);
        Position positionIni = new Position(0, 3);
        Position positionEnd = new Position(0, 6);

        boolean result = chessGameService.move(player1, enemyPlayer, positionIni, positionEnd);

        Assertions.assertFalse(result,"Invalid option selected, please try again");
    }

    @Test
    public void moveKingFalseTest() {
        List<Position> lstPosiblePositions = new ArrayList<>();
        Position p1 = new Position(0, 4);
        lstPosiblePositions.add(p1);
        List<Piece> lstMyPieces = new ArrayList<>();
        List<Piece> lstEnemyPieces = new ArrayList<>();
        pieceService = new KingServiceImpl(new Position(0, 3), lstPosiblePositions);

        Pawn pawn = new Pawn(Color.WHITE, Status.DEAD, new Position(0, 3), "K", "K", lstPosiblePositions);
        lstMyPieces.add(pawn);
        Player player1 = new Player("Nacho", lstMyPieces);
        Player enemyPlayer = new Player("JUAN", lstEnemyPieces);
        Position positionIni = new Position(0, 3);
        Position positionEnd = new Position(0, 6);

        boolean result = chessGameService.move(player1, enemyPlayer, positionIni, positionEnd);

        Assertions.assertFalse(result);
    }

    @Test
    public void validateGuardarTest() {
        Pattern pattern = Pattern.compile(MOVE_INPUT_REGEX);
            String input = "00";
            Player player = new Player();

            Boolean answer = chessGameService.Validate(input,player);

          Assertions.assertTrue(answer);

        }

    @Test
    public void validateTest2() {
        Pattern pattern = Pattern.compile(MOVE_INPUT_REGEX);
        String input = "A 1;A 2";
        Player player = new Player();
        List<Piece> pieceList = new ArrayList<>();
        Pawn pawn = new Pawn(Color.BLACK, Status.ALIVE, new Position(0, 1), "P", "P", null);

        pieceList.add(pawn);
        player.setLstPieces(pieceList);


        Boolean answer = chessGameService.Validate(input,player);

        Assertions.assertTrue(answer);

    }


    @Test
    public void validateTest3() {

        String input = "A 3;A 6";
        Boolean aux = false;
        Player player = new Player();
        List<Piece> pieceList = new ArrayList<>();
        Pawn pawn = new Pawn(Color.WHITE, Status.ALIVE, new Position(0, 1), "P", "P", null);
        pieceList.add(pawn);
        player.setLstPieces(pieceList);
        Boolean comparacion = aux;


        Boolean answer = comparacion.equals(chessGameService.Validate(input,player));

        Assertions.assertTrue(answer,"You don't have pieces in this position.");

    }
    @Test
    public void validateTest4() {

        String input = "A 9;A 6";
        Boolean aux = false;
        Player player = new Player();
        List<Piece> pieceList = new ArrayList<>();
        Pawn pawn = new Pawn(Color.WHITE, Status.ALIVE, new Position(0, 1), "P", "P", null);
        pieceList.add(pawn);
        player.setLstPieces(pieceList);
        Boolean comparacion = aux;


        Boolean answer = comparacion.equals(chessGameService.Validate(input,player));

        Assertions.assertTrue(answer,"Invalid position.");

    }


    @Test
    public void testCheckMate1() {

        // Test 'sacrificando' la Torre podes salvar el jaque
        chessGameService = new ChessGameServiceImpl();

        List<Piece> myList = new ArrayList<>();
        List<Piece> enemyList = new ArrayList<>();

        List<Position> posiblePositionsKing = new ArrayList<>();
        posiblePositionsKing.add(new Position(6,0));
        posiblePositionsKing.add(new Position(7,1));
        myList.add(new King(Color.WHITE, Status.ALIVE,new Position(7,0),"K","K",posiblePositionsKing));

        List<Position> posiblePositionsPawn = new ArrayList<>();
        posiblePositionsPawn.add(new Position(6,3));
        myList.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(6,2),"P","P",posiblePositionsPawn));

        List<Position> posiblePositionsPawn2 = new ArrayList<>();
        posiblePositionsPawn2.add(new Position(7,4));
        myList.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(7,3),"P","P",posiblePositionsPawn2));

        List<Position> posiblePositionsTower = new ArrayList<>();
        posiblePositionsTower.add(new Position(6,0));
        posiblePositionsTower.add(new Position(7,1));
        posiblePositionsTower.add(new Position(5,1));
        myList.add(new Tower(Color.WHITE,Status.ALIVE,new Position(6,1),"R","R",posiblePositionsTower));

        List<Position> posiblePositionsPawn3 = new ArrayList<>();
        posiblePositionsPawn3.add(new Position(4,3));
        myList.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(4,2),"P","P",posiblePositionsPawn3));

        enemyList.add(new Tower(Color.BLACK,Status.ALIVE,new Position(7,2),"R","R", new ArrayList<>()));
        enemyList.add(new Bishop(Color.BLACK,Status.ALIVE,new Position(5,1),"B","B",new ArrayList<>()));

        boolean aux = chessGameService.checkMate(myList,enemyList);
        Assertions.assertFalse(aux);
    }

    @Test
    public void testCheckMate2() {

        // Test 'comiendo' el Caballo podes salvar el jaque
        chessGameService = new ChessGameServiceImpl();

        List<Piece> myList = new ArrayList<>();
        List<Piece> enemyList = new ArrayList<>();

        List<Position> posiblePositionsTower = new ArrayList<>();
        posiblePositionsTower.add(new Position(4,3));
        posiblePositionsTower.add(new Position(4,1));
        posiblePositionsTower.add(new Position(5,2));
        posiblePositionsTower.add(new Position(6,2));
        posiblePositionsTower.add(new Position(7,2));
        myList.add(new Tower(Color.WHITE,Status.ALIVE,new Position(4,2),"R","R",posiblePositionsTower));

        List<Position> posiblePositionsKing = new ArrayList<>();
        posiblePositionsKing.add(new Position(5,1));
        posiblePositionsKing.add(new Position(6,1));
        posiblePositionsKing.add(new Position(7,1));
        posiblePositionsKing.add(new Position(7,0));
        myList.add(new King(Color.WHITE, Status.ALIVE,new Position(6,0),"K","K",posiblePositionsKing));

        List<Position> posiblePositionsPawn1 = new ArrayList<>();
        posiblePositionsPawn1.add(new Position(5,1));
        myList.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(5,0),"P","P",posiblePositionsPawn1));

        List<Position> posiblePositionsPawn2 = new ArrayList<>();
        posiblePositionsPawn2.add(new Position(3,3));
        myList.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(3,2),"P","P",posiblePositionsPawn2));

        List<Position> posiblePositionsPawn3 = new ArrayList<>();
        posiblePositionsPawn3.add(new Position(5,5));
        myList.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(5,4),"P","P",posiblePositionsPawn3));

        enemyList.add(new Tower(Color.BLACK,Status.ALIVE,new Position(4,1),"R","R", new ArrayList<>()));
        enemyList.add(new Bishop(Color.BLACK,Status.ALIVE,new Position(4,3),"B","B",new ArrayList<>()));
        enemyList.add(new Horse(Color.BLACK,Status.ALIVE,new Position(7,2),"N","N",new ArrayList<>()));
        enemyList.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,1),"P","P",new ArrayList<>()));
        enemyList.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(5,3),"P","P",new ArrayList<>()));
        enemyList.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(6,4),"P","P",new ArrayList<>()));
        enemyList.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,4),"P","P",new ArrayList<>()));

        boolean aux = chessGameService.checkMate(myList,enemyList);
        Assertions.assertFalse(aux);
    }

}