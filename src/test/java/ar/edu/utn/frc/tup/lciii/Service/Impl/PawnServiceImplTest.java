package ar.edu.utn.frc.tup.lciii.Service.Impl;
import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.impl.PawnServiceImpl;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PawnServiceImplTest {

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private Player player;
    private Position position;

    private PawnServiceImpl pawnService;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        pawnService = new PawnServiceImpl();
        pawnService.setColor(Color.WHITE);
        position =  new Position(2,3);
        pawnService.setPosition(position);
        List<Position>lstPositions= new ArrayList<>();
        lstPositions.add(new Position(2,4));
        pawnService.setPosiblePositions(lstPositions);


        player = new Player();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @DisplayName("Test set Parameters to Piece")
    @Test
    public void SetearParametros() {
        Piece piece = new Pawn();
        piece.setPosition(new Position(3,3));
        piece.setColor(Color.BLACK);
        piece.setPosiblePositions(Arrays.asList(new Position(3,6), new Position(2,5)));

        pawnService.setParam(piece);

        Assertions.assertEquals(new Position(3,3),pawnService.getPosition());
        Assertions.assertEquals(Color.BLACK,pawnService.getColor());
        Assertions.assertEquals((Arrays.asList(new Position(3, 6), new Position(2, 5))),pawnService.getPosiblePositions());
    }

    @Test
    public void fillPositionsW1() {
        List<Position> lst = new ArrayList<>();
        pawnService = new PawnServiceImpl(new Position(1,1), Color.WHITE,lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        for (int i = 0; i<8;i++){
            enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,6),"P","P",null));
            myPieces.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(i,1),"P","P",null));
        }

        pawnService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        expectedLst.add(new Position(1,2));
        expectedLst.add(new Position(1,3));

        Assertions.assertEquals(expectedLst,pawnService.getPosiblePositions());
    }
    @Test
    public void fillPositionsW2() {
        List<Position> lst = new ArrayList<>();
        pawnService = new PawnServiceImpl(new Position(1,1), Color.WHITE,lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        for (int i = 0; i<8;i++){
            enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,2),"P","P",null));
            myPieces.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(i,1),"P","P",null));
        }

        pawnService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();


        Assertions.assertEquals(expectedLst,pawnService.getPosiblePositions());
    }
    @Test
    public void fillPositionsW3() {
        List<Position> lst = new ArrayList<>();
        pawnService = new PawnServiceImpl(new Position(1,2), Color.WHITE,lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        for (int i = 0; i<8;i++){
            enemyPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,6),"P","P",null));
            myPieces.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(i,1),"P","P",null));
        }

        pawnService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        expectedLst.add(new Position(1,3));

        Assertions.assertEquals(expectedLst,pawnService.getPosiblePositions());
    }
    @Test
    public void fillPositionsB1() {
        List<Position> lst = new ArrayList<>();
        pawnService = new PawnServiceImpl(new Position(1,6), Color.BLACK,lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        for (int i = 0; i<8;i++){
            enemyPieces.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(i,1),"P","P",null));
            myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,6),"P","P",null));
        }

        pawnService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        expectedLst.add(new Position(1,5));
        expectedLst.add(new Position(1,4));

        Assertions.assertEquals(expectedLst,pawnService.getPosiblePositions());
    }
    @Test
    public void fillPositionsB2() {
        List<Position> lst = new ArrayList<>();
        pawnService = new PawnServiceImpl(new Position(1,6), Color.BLACK,lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        for (int i = 0; i<8;i++){
            enemyPieces.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(i,5),"P","P",null));
            myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,6),"P","P",null));
        }

        pawnService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();


        Assertions.assertEquals(expectedLst,pawnService.getPosiblePositions());
    }
    @Test
    public void fillPositionsB3() {
        List<Position> lst = new ArrayList<>();
        pawnService = new PawnServiceImpl(new Position(1,5), Color.BLACK,lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();
        for (int i = 0; i<8;i++){
            enemyPieces.add(new Pawn(Color.WHITE,Status.ALIVE,new Position(i,1),"P","P",null));
            myPieces.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,6),"P","P",null));
        }

        pawnService.fillPositions(enemyPieces,myPieces);
        List<Position> expectedLst = new ArrayList<>();
        expectedLst.add(new Position(1,4));


        Assertions.assertEquals(expectedLst,pawnService.getPosiblePositions());
    }

    @Test
    public void verifyEatingPieceW() {
        pawnService.setColor(Color.WHITE);
        position.setColumn(0);
        position.setRow(5);
        pawnService.setPosition(position);
        List<Piece> lst = new ArrayList<>();
        for(int i=0; i<8; i++){
            lst.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,7-i),"P","P",null));
        }
        player.setLstPieces(lst);
        boolean resultado =pawnService.verifyEatingPiece(player.getLstPieces(), position);

        Assertions.assertTrue(resultado);

    }
    @Test
    public void verifyEatingPieceB() {
        pawnService.setColor(Color.BLACK);
        position.setColumn(2);
        position.setRow(7);
        pawnService.setPosition(position);
        List<Piece> lst = new ArrayList<>();
        for(int i=0; i<8; i++){
            lst.add(new Pawn(Color.BLACK,Status.ALIVE,new Position(i,7-i),"P","P",null));
        }
        player.setLstPieces(lst);

        boolean resultado =pawnService.verifyEatingPiece(player.getLstPieces(), position);

        Assertions.assertTrue(resultado);

    }

    @Test
    public void verifyEndMovePosition() throws Exception {
        // when
        pawnService.setPosiblePositions(Arrays.asList(new Position(3,6), new Position(2,5)));
        position.setColumn(2);
        position.setRow(5);
        boolean resultado = pawnService.verifyEndMovePosition(position);

        Assertions.assertTrue(resultado);

    }

    @Test
    public void removeIfOutBoard() throws Exception {

        List<Position> lst = new ArrayList<>();
        pawnService = new PawnServiceImpl(new Position(0, 0),Color.WHITE, lst);
        List<Piece> enemyPieces = new ArrayList<>();
        List<Piece> myPieces = new ArrayList<>();

        pawnService.fillPositions(enemyPieces, myPieces);
        List<Position> expectedLst = new ArrayList<>();

        expectedLst.add(new Position(0, -1));
        Assertions.assertNotEquals(expectedLst, pawnService.getPosiblePositions());
    }


    @Test
    public void setPosition(){
        Assertions.assertNotNull(pawnService);
        pawnService.setPosition(position);
        Assertions.assertEquals(position, pawnService.getPosition());


    }
    @Test
    public void getPosition(){
    Assertions.assertNotNull(pawnService);
    Assertions.assertEquals(position, pawnService.getPosition());


    }

    @Test
    public void setColor(){
        Assertions.assertNotNull(pawnService);
        pawnService.setColor(Color.WHITE);
        Assertions.assertEquals(Color.WHITE, pawnService.getColor());

    }
    @Test
    public void getColor(){
        Assertions.assertNotNull(pawnService);
        Assertions.assertEquals(Color.WHITE, pawnService.getColor());

}
    @Test
    public void getListPositions() {
        List<Position> posiblePositions = new ArrayList<>();
        posiblePositions.add(new Position(2,4));
        Assertions.assertNotNull(pawnService);
        Assertions.assertEquals(posiblePositions, pawnService.getPosiblePositions());
    }

    @Test
    public void setLiatPositions(){
        Assertions.assertNotNull(pawnService);
        List<Position> lst = new ArrayList<>();
        lst.add(new Position(2,4));
        pawnService.setPosiblePositions(lst);
        Assertions.assertEquals(lst, pawnService.getPosiblePositions());

    }
}