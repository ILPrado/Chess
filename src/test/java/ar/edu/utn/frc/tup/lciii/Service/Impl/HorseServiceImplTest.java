package ar.edu.utn.frc.tup.lciii.Service.Impl;

import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.impl.HorseServiceImpl;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HorseServiceImplTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private HorseServiceImpl horseService;
    private Position position;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        position = new Position();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @DisplayName("Test set Parameters to Piece")
    @Test
    public void SetearParametrosTest() {
        horseService = new HorseServiceImpl();
        Piece piece = new Horse();
        piece.setPosition(new Position(0, 2));
        piece.setPosiblePositions(Arrays.asList(new Position(0, 2), new Position(1, 0)));

        horseService.setParam(piece);

        Assertions.assertEquals(new Position(0, 2), horseService.getPosition());
        Assertions.assertEquals((Arrays.asList(new Position(0, 2), new Position(1, 0))), horseService.getPosiblePositions());
    }

    @Test
    public void verifyEatingPieceTest() {
        position.setColumn(2);
        position.setRow(5);
        List<Position> lstPosiblePositions = new ArrayList<>();
        lstPosiblePositions.add(position);
        // posicion de mi pieza inicial
        horseService = new HorseServiceImpl(new Position(3, 3), lstPosiblePositions);
        List<Piece> lst = new ArrayList<>();

        lst.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(2, 5 ), "P", "P", lstPosiblePositions));
        lst.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(4, 5 ), "P", "P", lstPosiblePositions));
        lst.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(4, 1 ), "P", "P", lstPosiblePositions));
        lst.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(1, 2 ), "P", "P", lstPosiblePositions));
        lst.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(2, 1 ), "P", "P", lstPosiblePositions));

        lstPosiblePositions.add(new Position(2, 5));
        lstPosiblePositions.add(new Position(4, 5));
        lstPosiblePositions.add(new Position(4, 1));
        lstPosiblePositions.add(new Position(1, 2));
        lstPosiblePositions.add(new Position(2, 1));

        // posicion final
        boolean resultado = horseService.verifyEatingPiece(lst, position);

        Assertions.assertTrue(resultado);

    }
    @Test
    public void verifyEatingPieceFalseTest() {


        List<Position> lstPosiblePositions = new ArrayList<>();
   
        horseService = new HorseServiceImpl(new Position(0, 7), lstPosiblePositions);
        List<Piece> lst = new ArrayList<>();

        lst.add(new Pawn(Color.BLACK, Status.DEAD, new Position(1, 5 ), "P", "P", null));
        lst.add(new Pawn(Color.BLACK, Status.DEAD, new Position(2, 6 ), "P", "P", null));

        position= new Position(1,5);
        Position position1= new Position(2,6);
        lstPosiblePositions.add(position);
        lstPosiblePositions.add(position1);

        // posicion final
        boolean resultado = horseService.verifyEatingPiece(lst,position);

        Assertions.assertFalse(resultado);

    }


    // OK
    @Test
    public void verifyEndMovePositionTest() throws Exception {
        //
        horseService = new HorseServiceImpl();
        horseService.setPosiblePositions(Arrays.asList(new Position(7, 0), new Position(5, 1), new Position(6, 2)));
        position.setColumn(7);
        position.setRow(0);
        Position position1= new Position(5,1);
        Position position2= new Position(6,2);

        boolean resultado = horseService.verifyEndMovePosition(position)&&horseService.verifyEndMovePosition(position1)&&
                horseService.verifyEndMovePosition(position2);

        Assertions.assertTrue(resultado);

    }
    @Test
    public void verifyEndMovePositionFalseTest() throws Exception {
        //
        horseService = new HorseServiceImpl();
        horseService.setPosiblePositions(Arrays.asList(new Position(0, 7), new Position(1, 5), new Position(2, 6)));
        position.setColumn(0);
        position.setRow(7);
        Position position1= new Position(0,8);
        Position position2= new Position(-1,7);

        boolean resultado = horseService.verifyEndMovePosition(position)&&horseService.verifyEndMovePosition(position1)&&
                horseService.verifyEndMovePosition(position2);

        Assertions.assertFalse(resultado);

    }



    @Test
    public void fillPositionTest00() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(0, 0), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        horseService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();


            expectedLst.add(new Position(2, 1));
            expectedLst.add(new Position(1, 2));

        Assertions.assertEquals(expectedLst, horseService.getPosiblePositions());
    }
    @Test
    public void fillPositionTest01() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(0, 5), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        horseService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();

        expectedLst.add(new Position(2, 6));
        expectedLst.add(new Position(2, 4));
        expectedLst.add(new Position(1, 7));
        expectedLst.add(new Position(1, 3));

        Assertions.assertEquals(expectedLst, horseService.getPosiblePositions());
    }



    @Test
    public void fillPositionTest03() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(7, 7), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        horseService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();


        expectedLst.add(new Position(5, 6));
        expectedLst.add(new Position(6, 5));

        Assertions.assertEquals(expectedLst, horseService.getPosiblePositions());
    }

    @Test
    public void fillPositionTest04() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(7, 0), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        horseService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();


        expectedLst.add(new Position(5, 1));
        expectedLst.add(new Position(6, 2));

        Assertions.assertEquals(expectedLst, horseService.getPosiblePositions());
    }
    @Test
    public void fillPositionTest05() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(0, 7), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        horseService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();

        expectedLst.add(new Position(2, 6));
        expectedLst.add(new Position(1, 5));


        Assertions.assertEquals(expectedLst, horseService.getPosiblePositions());
    }

    @Test
    public void fillPositionsConFichasPropiasMuertas() {
            List<Position> lst = new ArrayList<>();
            horseService = new HorseServiceImpl(new Position(3,3),lst);
            List<Piece> enemyPieces = new ArrayList<>();
            List<Piece> myPieces = new ArrayList<>();
            myPieces.add(new Pawn(Color.WHITE, Status.DEAD, new Position(5, 4), "P", "P", null));
            myPieces.add(new Pawn(Color.WHITE, Status.DEAD, new Position(5, 2), "P", "P", null));
            myPieces.add(new Pawn(Color.WHITE, Status.DEAD, new Position(1, 4), "P", "P", null));
            myPieces.add(new Pawn(Color.WHITE, Status.DEAD, new Position(1, 2), "P", "P", null));
            myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(4, 5), "P", "P", null));
            myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(2, 5), "P", "P", null));
            myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(4, 1), "P", "P", null));
            myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(2, 1), "P", "P", null));


            horseService.fillPositions(enemyPieces,myPieces);
            List<Position> expectedLst = new ArrayList<>();

            Assertions.assertNotEquals(expectedLst,horseService.getPosiblePositions());

    }

    @Test
    public void fillPositionSinPiezasAdelanteTest() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(3, 3), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        horseService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();
        expectedLst.add(new Position(5, 4));
        expectedLst.add(new Position(5, 2));
        expectedLst.add(new Position(1, 4));
        expectedLst.add(new Position(1, 2));
        expectedLst.add(new Position(4, 5));
        expectedLst.add(new Position(2, 5));
        expectedLst.add(new Position(4, 1));
        expectedLst.add(new Position(2, 1));

        Assertions.assertEquals(expectedLst,horseService.getPosiblePositions());
    }


    @Test
    public void fillPositionConPiezasEnemigasTest() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(3, 3), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(5, 4), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(5, 2), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(1, 4), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(1, 2), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(4, 5), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(2, 5), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(4, 1), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(2, 1), "P", "P", null));


        List<Piece> myPieces = new ArrayList<>();

        horseService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for (Piece p : enemyPieces) {
            expectedLst.add(p.getPosition());
        }
        Assertions.assertEquals(expectedLst, horseService.getPosiblePositions());

    }

    @Test
    public void fillPositionsConFichasPropias() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(3,3),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(5, 4), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(5, 2), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(1, 4), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(1, 2), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(4, 5), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(2, 5), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(4, 1), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(2, 1), "P", "P", null));


        horseService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();

        Assertions.assertEquals(expectedLst,horseService.getPosiblePositions());
    }


    @Test
    public void fillPositionInMatchTest() {
        List<Position> lst = new ArrayList<>();
        horseService = new HorseServiceImpl(new Position(3, 3), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(5, 4), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(5, 2), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(1, 4), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(1, 2), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(4, 5), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(2, 5), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(4, 1), "P", "P", null));
        myPieces.add(new Pawn(Color.WHITE, Status.ALIVE, new Position(2, 1), "P", "P", null));

        horseService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();
        expectedLst.add(new Position(5, 4));
        expectedLst.add(new Position(5, 2));
        expectedLst.add(new Position(1, 4));
        expectedLst.add(new Position(1, 2));

        Assertions.assertEquals(expectedLst, horseService.getPosiblePositions());
    }

}
