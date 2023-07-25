package ar.edu.utn.frc.tup.lciii.Service.Impl;
import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.impl.BishopServiceImpl;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BishopServiceImplTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private BishopServiceImpl bishopService;
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
    public void SetearParametros() {
        bishopService = new BishopServiceImpl();
        Piece piece = new Bishop();
        piece.setPosition(new Position(3,3));
        piece.setPosiblePositions(Arrays.asList(new Position(4,4), new Position(5,5)));

        bishopService.setParam(piece);

        Assertions.assertEquals(new Position(3,3),bishopService.getPosition());
        Assertions.assertEquals((Arrays.asList(new Position(4, 4), new Position(5, 5))),bishopService.getPosiblePositions());
    }
     @Test
     public void fillPositionSinPiezasAdelanteTest() {
         List<Position> lst = new ArrayList<>();
         bishopService = new BishopServiceImpl(new Position(3, 3), lst);
         List<Piece> enemyPieces = new ArrayList<>();
         List<Piece> myPieces = new ArrayList<>();

         bishopService.fillPositions(enemyPieces, myPieces);
         List<Position> expectedLst = new ArrayList<>();

         for (int i = 4; i < 8; i++) {
             expectedLst.add(new Position(i, i));
         }
         for (int i = 2; i >= 0; i--) {
             expectedLst.add(new Position(6-i,i));
         }

         for (int i = 2; i >= 0; i--) {
             expectedLst.add(new Position(i, i));
         }

        for (int i = 4; i < 7; i++) {
             expectedLst.add(new Position(6-i, i));
         }
         Assertions.assertEquals(expectedLst, bishopService.getPosiblePositions());
      }

    @Test
    public void fillPositionsWithEnemies() {
        List<Position> lst = new ArrayList<>();
        bishopService = new BishopServiceImpl(new Position(3,3),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,4),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,2),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,2),"P","",null));
        enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,4),"P","",null));
        List<Piece> myPieces = new ArrayList<>();

        bishopService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        for(Piece p: enemyPieces){
            expectedLst.add(p.getPosition());
        }

        Assertions.assertEquals(expectedLst,bishopService.getPosiblePositions());
    }

    @Test
    public void fillPositionsWithMyPices() {
        List<Position> lst = new ArrayList<>();
        bishopService = new BishopServiceImpl(new Position(3,3),lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,4),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(4,2),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,2),"P","",null));
        myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(2,4),"P","",null));

        bishopService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();


        Assertions.assertEquals(expectedLst,bishopService.getPosiblePositions());    }

    @Test
     public void verifyEatingPiece() {
        position.setColumn(3);
        position.setRow(4);
        List<Position> lstPosiblePositions = new ArrayList<>();
        lstPosiblePositions.add(position);
        // posicion de mi pieza inicial
        bishopService = new BishopServiceImpl(new Position(0, 1), lstPosiblePositions);
        List<Piece> lst = new ArrayList<>();
        //lista de piezas enemigas
        for (int i = 0; i < 8; i++) {
            lst.add(new Pawn(Color.BLACK, Status.ALIVE, new Position(i, 7 - i), "P", "P", lstPosiblePositions));
        }

        for (int i=1;i<7;i++){
            lstPosiblePositions.add(new Position(i,i+1));
        }

        // posicion final
        boolean resultado = bishopService.verifyEatingPiece(lst, position);

        Assertions.assertTrue(resultado);
     }

    @Test
    public void verifyEndMovePositionTest() throws Exception {
        //
        bishopService = new BishopServiceImpl();
        bishopService.setPosiblePositions(Arrays.asList(new Position(3, 6), new Position(2, 7)));
        position.setColumn(2);
        position.setRow(7);
        boolean resultado = bishopService.verifyEndMovePosition(position);

        Assertions.assertTrue(resultado);

    }
}
