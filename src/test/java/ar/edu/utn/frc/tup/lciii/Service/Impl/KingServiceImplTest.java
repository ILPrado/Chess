package ar.edu.utn.frc.tup.lciii.Service.Impl;

import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.impl.KingServiceImpl;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KingServiceImplTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private KingServiceImpl kingService;
    private Position position;
    private ByteArrayOutputStream testOut;

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

    @DisplayName("Test set Parameters to Piece")
    @Test
    public void SetearParametros() {
        kingService = new KingServiceImpl();
        Piece piece = new King();
        piece.setPosition(new Position(3,3));
        piece.setPosiblePositions(Arrays.asList(new Position(3,6), new Position(2,5)));

        kingService.setParam(piece);

        Assertions.assertEquals(new Position(3,3), kingService.getPosition());
        Assertions.assertEquals((Arrays.asList(new Position(3, 6), new Position(2, 5))), kingService.getPosiblePositions());
    }

    @Test
    public void fillPositionsWithEnemies() {
        List<Position> lst = new ArrayList<>();
        kingService = new KingServiceImpl(new Position(3,3),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,4),"P","P",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,4),"P","P",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,3),"P","P",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,2),"P","P",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,2),"P","P",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,2),"P","P",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,3),"P","P",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,4),"P","P",null));
        List<Piece> myPieces = new ArrayList<>();

        kingService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for(Piece p: enemyPieces){
            expectedLst.add(p.getPosition());
        }
        Assertions.assertEquals(expectedLst, kingService.getPosiblePositions());
    }
    @Test
    public void fillPositionsWithMyPices() {
        List<Position> lst = new ArrayList<>();
        kingService = new KingServiceImpl(new Position(3,3),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,4),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,2),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,3),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,3),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,4),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,2),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,2),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,4),"P","P",null));

        kingService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();

        Assertions.assertEquals(expectedLst, kingService.getPosiblePositions());    }
    @Test
    public void verifyEatingPiece() {
        List<Position> lst = new ArrayList<>();
        kingService = new KingServiceImpl(new Position(3,3),lst);
        position = new Position(3,4);
        List<Piece> enemyListPieces = new ArrayList<>();

            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,4),"P","P",null));
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,4),"P","P",null));
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,3),"P","P",null));
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,2),"P","P",null));
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,2),"P","P",null));
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,3),"P","P",null));
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,4),"P","P",null));
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,2),"P","P",null));
            lst.add(new Position(3,4));
            lst.add(new Position(2,4));
            lst.add(new Position(2,3));
            lst.add(new Position(2,2));
            lst.add(new Position(3,2));
            lst.add(new Position(4,3));
            lst.add(new Position(4,4));

        boolean resultado = kingService.verifyEatingPiece(enemyListPieces,position);

        Assertions.assertTrue(resultado);
    }
    @Test
    public void verifyEatingPieceFalse() {
        List<Position> lst = new ArrayList<>();
        kingService = new KingServiceImpl(new Position(1,3),lst);
        position = new Position(3,4);
        List<Piece> enemyListPieces = new ArrayList<>();
        for(int i=0; i<8; i++){
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,7-i),"P","P",null));
        }
        lst.add(new Position(1,4));
        lst.add(new Position(0,4));
        lst.add(new Position(0,3));
        lst.add(new Position(0,2));
        lst.add(new Position(1,2));
        lst.add(new Position(2,2));
        lst.add(new Position(3,2));
        lst.add(new Position(2,4));

        boolean resultado = kingService.verifyEatingPiece(enemyListPieces,position);

        Assertions.assertFalse(resultado);
    }
    @Test
    public void verifyEndMovePositionTrueBreak() throws Exception {
        List<Position> lst = new ArrayList<>();
        kingService = new KingServiceImpl(new Position(0,0),lst);
        position = new Position(0,7);
        for (int i=1;i<8;i++){
            lst.add(new Position(0,i));
        }
        for (int i=1;i<8;i++){
            lst.add(new Position(i,0));
        }
        for (int i=1;i<8;i++){
            lst.add(new Position(i,i));
        }
        boolean resultado = kingService.verifyEndMovePosition(position);

        Assertions.assertTrue(resultado);
    }
    @Test
    public void verifyEndMovePositionFalse() throws Exception {
        List<Position> lst = new ArrayList<>();
        kingService = new KingServiceImpl(new Position(0,0),lst);
        position = new Position(1,7);
        for (int i=1;i<8;i++){
            lst.add(new Position(0,i));
        }
        for (int i=1;i<8;i++){
            lst.add(new Position(i,0));
        }
        for (int i=1;i<8;i++){
            lst.add(new Position(i,i));
        }
        boolean resultado = kingService.verifyEndMovePosition(position);

        Assertions.assertFalse(resultado);
    }

    @Test
    public void removeIfOutBoard() throws Exception {

            List<Position> lst = new ArrayList<>();
            kingService = new KingServiceImpl(new Position(0, 0), lst);
            List<Piece> enemyPieces = new ArrayList<>();
            List<Piece> myPieces = new ArrayList<>();

            kingService.fillPositions(enemyPieces, myPieces);
            List<Position> expectedLst = new ArrayList<>();

            expectedLst.add(new Position(-1, -1));
            Assertions.assertNotEquals(expectedLst, kingService.getPosiblePositions());
        }

    @Test
    public void removeIfOutBoard2() throws Exception {

        List<Position> lst = new ArrayList<>();
        kingService = new KingServiceImpl(new Position(0, 0), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        kingService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();


        expectedLst.add(new Position(8, -1));
        expectedLst.add(new Position(-1, 8));

        Assertions.assertNotEquals(expectedLst, kingService.getPosiblePositions());
    }
    }

