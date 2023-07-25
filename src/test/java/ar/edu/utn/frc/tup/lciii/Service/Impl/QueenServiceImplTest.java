package ar.edu.utn.frc.tup.lciii.Service.Impl;

import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.impl.QueenServiceImpl;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueenServiceImplTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private QueenServiceImpl queenService;
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
        queenService = new QueenServiceImpl();
        Piece piece = new Queen();
        piece.setPosition(new Position(3,3));
        piece.setPosiblePositions(Arrays.asList(new Position(3,6), new Position(2,5)));

        queenService.setParam(piece);

        Assertions.assertEquals(new Position(3,3),queenService.getPosition());
        Assertions.assertEquals((Arrays.asList(new Position(3, 6), new Position(2, 5))),queenService.getPosiblePositions());
    }
    @Test
    public void fillPositions00() {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(0,0),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        queenService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(0,i));
        }
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(i,0));
        }
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(i,i));
        }

        Assertions.assertEquals(expectedLst,queenService.getPosiblePositions());
    }
    @Test
    public void fillPositions07() {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(0,7),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        queenService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for (int i=6;i>=0;i--){
            expectedLst.add(new Position(0,i));
        }
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(i,7));
        }
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(i,7-i));
        }

        Assertions.assertEquals(expectedLst,queenService.getPosiblePositions());
    }
    @Test
    public void fillPositions70() {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(7,0),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        queenService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(7,i));
        }
        for (int i=6;i>=0;i--){
            expectedLst.add(new Position(i,0));
        }
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(7-i,i));
        }

        Assertions.assertEquals(expectedLst,queenService.getPosiblePositions());
    }
    @Test
    public void fillPositions77() {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(7,7),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        queenService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(7,7-i));
        }
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(7-i,7));
        }
        for (int i=1;i<8;i++){
            expectedLst.add(new Position(7-i,7-i));
        }

        Assertions.assertEquals(expectedLst,queenService.getPosiblePositions());
    }
    @Test
    public void fillPositions33WithEnemies() {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(3,3),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,4),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,2),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,3),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,3),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,4),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,2),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,2),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,4),"P","",null));
        List<Piece> myPieces = new ArrayList<>();

        queenService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for(Piece p: enemyPieces){
            expectedLst.add(p.getPosition());
        }

        Assertions.assertEquals(expectedLst,queenService.getPosiblePositions());
    }
    @Test
    public void fillPositionsB3() {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(3,3),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,4),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,2),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,3),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,3),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,4),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,2),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,2),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,4),"P","",null));


        queenService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();


        Assertions.assertEquals(expectedLst,queenService.getPosiblePositions());    }
    @Test
    public void verifyEatingPiece() {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(0,0),lst);
        position = new Position(0,7);
        List<Piece> enemyListPieces = new ArrayList<>();
        for(int i=0; i<8; i++){
            enemyListPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,7-i),"P","P",null));
        }
        for (int i=1;i<8;i++){
            lst.add(new Position(0,i));
        }
        for (int i=1;i<8;i++){
            lst.add(new Position(i,0));
        }
        for (int i=1;i<8;i++){
            lst.add(new Position(i,i));
        }
        boolean resultado =queenService.verifyEatingPiece(enemyListPieces,position);

        Assertions.assertTrue(resultado);
    }
    @Test
    public void verifyEndMovePositionTrueBreak() throws Exception {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(0,0),lst);
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
        boolean resultado = queenService.verifyEndMovePosition(position);

        Assertions.assertTrue(resultado);
    }
    @Test
    public void verifyEndMovePositionFalse() throws Exception {
        List<Position> lst = new ArrayList<>();
        queenService = new QueenServiceImpl(new Position(0,0),lst);
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
        boolean resultado = queenService.verifyEndMovePosition(position);

        Assertions.assertFalse(resultado);
    }

}