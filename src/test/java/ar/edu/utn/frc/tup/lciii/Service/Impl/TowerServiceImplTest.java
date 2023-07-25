package ar.edu.utn.frc.tup.lciii.Service.Impl;

import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.impl.TowerServiceImpl;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TowerServiceImplTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;


    private Player player;

    private Position position;

    private Color color;
    private TowerServiceImpl towerService;

    private ByteArrayOutputStream testOut;


    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        position = new Position();
        towerService = new TowerServiceImpl();

    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    // Ok
    @DisplayName("Test set Parameters to Piece")
    @Test
    public void SetearParametrosTest() {
        Piece piece = new Tower();
        piece.setPosition(new Position(0, 2));
        piece.setPosiblePositions(Arrays.asList(new Position(0, 2), new Position(2, 5)));

        towerService.setParam(piece);

        Assertions.assertEquals(new Position(0, 2), towerService.getPosition());
        Assertions.assertEquals((Arrays.asList(new Position(0, 2), new Position(2, 5))), towerService.getPosiblePositions());
    }

    @Test
    public void verifyEatingPieceTest() {
        position.setColumn(2);
        position.setRow(5);
        List<Position> lstPosiblePositions = new ArrayList<>();
        lstPosiblePositions.add(position);
        // posicion de mi pieza inicial
        towerService = new TowerServiceImpl(new Position(2, 7), lstPosiblePositions);
        List<Piece> lst = new ArrayList<>();
        //lista de piezas enemigas
        for (int i = 0; i < 8; i++) {
            lst.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(i, 7 - i), "P", "P", lstPosiblePositions));
        }
        //Verifico que en columna 2 fija.. decrezca a la fila 7
        for (int i = 0; i < 8; i++) {
            lstPosiblePositions.add(new Position(2, 7 - i));
        }
        //Verifico que las vaya  en la fila 7 se mueva hacia un lado izquierda(por las columnas)
        for (int i = 1; i <= 2; i++) {
            lstPosiblePositions.add(new Position(2 - i, 7));
        }
        // verifico que en la fila 7 se mueva hacia la derecha(por las columnas)
        for (int i = 3; i < 8; i++) {
            lstPosiblePositions.add(new Position(i, 7));
        }
        // posicion final
        boolean resultado = towerService.verifyEatingPiece(lst, position);

        Assertions.assertTrue(resultado);

    }

    // OK
    @Test
    public void verifyEndMovePositionTest() throws Exception {
        //
        towerService = new TowerServiceImpl();
        towerService.setPosiblePositions(Arrays.asList(new Position(3, 6), new Position(0, 4)));
        position.setColumn(0);
        position.setRow(4);
        boolean resultado = towerService.verifyEndMovePosition(position);

        Assertions.assertTrue(resultado);

    }

    @Test
    public void fillPositionTest00() {
        List<Position> lst = new ArrayList<>();
        towerService = new TowerServiceImpl(new Position(7, 0), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        towerService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            expectedLst.add(new Position(7, i));
        }
        for (int i = 6; i >= 0; i--) {
            expectedLst.add(new Position(i, 0));
        }
        Assertions.assertEquals(expectedLst, towerService.getPosiblePositions());
    }


    @Test
    public void fillPositionSinPiezasAdelanteTest() {
        List<Position> lst = new ArrayList<>();
        towerService = new TowerServiceImpl(new Position(3, 3), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        towerService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();

        for (int i = 4; i < 8; i++) {
            expectedLst.add(new Position(3, i));
        }
        for (int i = 2; i >= 0; i--) {
            expectedLst.add(new Position(3, i));
        }
        for (int i = 4; i < 8; i++) { //
            expectedLst.add(new Position(i, 3));
        }
        for (int i = 2; i >= 0; i--) {
            expectedLst.add(new Position(i, 3));
        }

        Assertions.assertEquals(expectedLst, towerService.getPosiblePositions());
    }


    @Test
    public void fillPositionConPiezasEnemigasTest() {
        List<Position> lst = new ArrayList<>();
        towerService = new TowerServiceImpl(new Position(3, 3), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(3, 4), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(3, 2), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(4, 3), "P", "P", null));
        enemyPieces.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(2, 3), "P", "P", null));

        List<Piece> myPieces = new ArrayList<>();

        towerService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for (Piece p : enemyPieces) {
            expectedLst.add(p.getPosition());
        }
        Assertions.assertEquals(expectedLst, towerService.getPosiblePositions());

    }

    @Test
    public void fillPositionsConFichasPropias() {
        List<Position> lst = new ArrayList<>();
        towerService = new TowerServiceImpl(new Position(3,3),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,4),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,2),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,3),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,3),"P","P",null));


        towerService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();

        Assertions.assertEquals(expectedLst,towerService.getPosiblePositions());
    }


    @Test
    public void fillPositionInMatchTest() {
        List<Position> lst = new ArrayList<>();
        towerService = new TowerServiceImpl(new Position(3, 3), lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(3,1),"P","P",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,3),"P","P",null));
        enemyPieces.add(new Tower(Color.WHITE, Status.ALIVE, new Position(1, 3), "T", "T", null));
        enemyPieces.add(new Bishop(Color.WHITE, Status.ALIVE, new Position(3, 5), "B", "B", null));
        towerService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();

            expectedLst.add(new Position(3, 4));

            expectedLst.add(new Position(3, 5));

            expectedLst.add(new Position(3, 2));

            expectedLst.add(new Position(2, 3));

            expectedLst.add(new Position(1, 3));

        Assertions.assertEquals(expectedLst, towerService.getPosiblePositions());
    }

}


