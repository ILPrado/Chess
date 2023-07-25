package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import ar.edu.utn.frc.tup.lciii.entities.*;
import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.Data.HibernateUtil;
import ar.edu.utn.frc.tup.lciii.services.ChessGameServiceInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class ChessGameServiceImpl implements ChessGameServiceInterface {
    private PieceServiceImpl pieceService;
    private ChessBoardServiceImpl boardService;
    private ModelMapper modelMapper = new ModelMapper();

    // Regex para chequear el input del movimiento
    private static final String MOVE_INPUT_REGEX = "[A-H]{1} [0-7]{1};[A-H]{1} [0-7]{1}";
    public List<Piece> initializePieces(Color color) {
        List<Piece> lstPieces = new ArrayList<Piece>();
        String paint = "";
        int a=0;
        int b=1;
        if(color.equals(Color.BLACK)){
            a=7;
            b=6;
            paint ="";
        }
        lstPieces.add(new Tower(color, Status.ALIVE,new Position(0,a),paint+"R","R",new ArrayList<>()));
        lstPieces.add(new Horse(color, Status.ALIVE,new Position(1,a),paint+"N","N",new ArrayList<>()));
        lstPieces.add(new Bishop(color, Status.ALIVE,new Position(2,a),paint+"B","B",new ArrayList<>()));
        lstPieces.add(new Queen(color, Status.ALIVE,new Position(3,a),paint+"Q","Q",new ArrayList<>()));
        lstPieces.add(new King(color, Status.ALIVE,new Position(4,a),paint+"K","K",new ArrayList<>()));
        lstPieces.add(new Bishop(color, Status.ALIVE,new Position(5,a),paint+"B","B",new ArrayList<>()));
        lstPieces.add(new Horse(color, Status.ALIVE,new Position(6,a),paint+"N","N",new ArrayList<>()));
        lstPieces.add(new Tower(color, Status.ALIVE,new Position(7,a),paint+"R","R",new ArrayList<>()));

        for (int i=0; i<8; i++){
            lstPieces.add(new Pawn(color, Status.ALIVE,new Position(i,b),paint+"P","P",new ArrayList<>()));
        }
        return lstPieces;
    }
    public boolean Validate(String input, Player player) {
        Pattern pattern = Pattern.compile(MOVE_INPUT_REGEX);
        Boolean answer = null;
        if(input.equals("00")){
            System.out.println("Saving and exiting the match...");
            return true;
        }
        if (pattern.matcher(input).matches()) {
            String newInput = charToNumber(input);
            for (Piece p:player.getLstPieces()){
                if (p.getPosition().getRow().equals(Integer.parseInt(String.valueOf(newInput.charAt(1))))&&
                        p.getPosition().getColumn().equals(Integer.parseInt(String.valueOf(newInput.charAt(0))))){
                    answer=true;
                    break;
                }
            }
            if (answer==null){
                answer=false;
                System.out.println("You don't have pieces in this position.");
            }
        } else {
            answer = false;
            System.out.println("Invalid position.");
        }
        return answer;
    }
    public String charToNumber(String answer) {
        StringBuilder sb = new StringBuilder(answer);
        for(int i =0;i<answer.length();++i){
            switch (answer.charAt(i)){
                case 'A':
                    sb.setCharAt(i,'0');
                    break;
                case 'B':
                    sb.setCharAt(i,'1');
                    break;
                case 'C':
                    sb.setCharAt(i,'2');
                    break;
                case 'D':
                    sb.setCharAt(i,'3');
                    break;
                case 'E':
                    sb.setCharAt(i,'4');
                    break;
                case 'F':
                    sb.setCharAt(i,'5');
                    break;
                case 'G':
                    sb.setCharAt(i,'6');
                    break;
                case 'H':
                    sb.setCharAt(i,'7');
                    break;
                default:
                    break;
            }
        }
        sb.deleteCharAt(1);
        sb.deleteCharAt(2);
        sb.deleteCharAt(3);
        return sb.toString();
    }
    public boolean move(Player player, Player enemyPlayer, Position pIni, Position pEnd) {
        boolean answer = false;
        for (Piece p: player.getLstPieces()){
            if(p.getStatus().equals(Status.ALIVE)){
                if (pIni.getRow().equals(p.getPosition().getRow())&&pIni.getColumn().equals(p.getPosition().getColumn())){
                    switch (p.getType()) {
                        case "P" -> {
                            pieceService = new PawnServiceImpl();
                            answer = pieceService.verifyMove(p,pEnd,enemyPlayer.getLstPieces(),player.getLstPieces());
                            break;
                        }
                        case "R" -> {
                            pieceService = new TowerServiceImpl();
                            answer = pieceService.verifyMove(p,pEnd,enemyPlayer.getLstPieces(),player.getLstPieces());
                            break;
                        }
                        case "N" -> {
                            pieceService = new HorseServiceImpl();
                            answer = pieceService.verifyMove(p,pEnd,enemyPlayer.getLstPieces(),player.getLstPieces());
                            break;
                        }
                        case "B" -> {
                            pieceService = new BishopServiceImpl();
                            answer = pieceService.verifyMove(p,pEnd,enemyPlayer.getLstPieces(),player.getLstPieces());
                            break;
                        }
                        case "Q" -> {
                            pieceService = new QueenServiceImpl();
                            answer = pieceService.verifyMove(p,pEnd,enemyPlayer.getLstPieces(),player.getLstPieces());
                            break;
                        }
                        case "K" -> {
                            pieceService = new KingServiceImpl();
                            answer = pieceService.verifyMove(p,pEnd,enemyPlayer.getLstPieces(),player.getLstPieces());
                            break;
                        }
                        default -> System.out.println("Invalid option selected, please try again");
                    }
                }
            }
        }

        return answer;
    }
    @JacocoAnnotationGenerated

    // hacer dos veces la funcion, una para ver si el jugador se deja en jaque a si mismo, y la otra
    // para ver si el jugador no dejo en jaque al otro.
    public boolean check(List<Piece> kingLst, List<Piece> enemyLst){
        // kingLst --> lista al cual le hicieron jaque (revisar su rey)
        // enemyLst --> lista del cual hizo jaque
        Position kingPosition = null;
        boolean aux = false;
        for (Piece p: kingLst) {
            if(p.getType().equals("K"))
            {
                kingPosition = p.getPosition();
                break;
            }
        }
        for (Piece piece: enemyLst) {
            if (piece.getStatus().equals(Status.ALIVE)) {
                switch (piece.getType()) {
                    case "P" -> {
                        pieceService = new PawnServiceImpl();
                        pieceService.setParam(piece);
                        pieceService.fillPositions(kingLst, enemyLst);
                        break;
                    }
                    case "R" -> {
                        pieceService = new TowerServiceImpl();
                        pieceService.setParam(piece);
                        pieceService.fillPositions(kingLst, enemyLst);
                        break;
                    }
                    case "N" -> {
                        pieceService = new HorseServiceImpl();
                        pieceService.setParam(piece);
                        pieceService.fillPositions(kingLst, enemyLst);
                        break;
                    }
                    case "B" -> {
                        pieceService = new BishopServiceImpl();
                        pieceService.setParam(piece);
                        pieceService.fillPositions(kingLst, enemyLst);
                        break;
                    }
                    case "Q" -> {
                        pieceService = new QueenServiceImpl();
                        pieceService.setParam(piece);
                        pieceService.fillPositions(kingLst, enemyLst);
                        break;
                    }
                    case "K" -> {
                        pieceService = new KingServiceImpl();
                        pieceService.setParam(piece);
                        pieceService.fillPositions(kingLst, enemyLst);
                        break;
                    }
                    default -> System.out.println("Invalid option selected, please try again");
                }

                for (Position position : piece.getPosiblePositions()) {
                    if (kingPosition == null) {
                        System.out.println("Che, no tiene REY!!!");
                    } else {
                        if (position.equals(kingPosition)) {
                            aux = true;
                            break;
                        }
                    }
                }
            }
            if(aux){break;}
        }
        return aux;
    }
    public boolean checkMate(List<Piece> player1, List<Piece> player2){
        boolean aux = false;

        List<Piece> myPieces = cloneList(player1);
        List<Piece> enemyPieces = cloneList(player2);

        for (Piece piece: myPieces) {
            if(piece.getStatus().equals(Status.ALIVE)) {
                for (Position pos : piece.getPosiblePositions()) {
                    Position originalPosition = piece.getPosition();
                    piece.setPosition(pos);

                    Piece killedPiece = null;
                    boolean kill = false;
                    for (Piece checkPiece : enemyPieces) {
                        if (checkPiece.getStatus().equals(Status.ALIVE) && checkPiece.getPosition().equals(piece.getPosition())) {
                            checkPiece.setStatus(Status.DEAD);
                            kill = true;
                            killedPiece = checkPiece;
                            break;
                        }
                    }
                    if(!check(myPieces,enemyPieces))
                    {
                        aux = true;
                        break;
                    }
                    if(kill)
                    {
                        for (Piece p: enemyPieces) {
                            if (p.getStatus().equals(Status.DEAD) && p.getType().equals(killedPiece.getType()) &&
                                    p.getPosition().equals(killedPiece.getPosition())){

                                p.setStatus(Status.ALIVE);
                                break;
                            }
                        }
                    }
                    piece.setPosition(originalPosition);
                }
            }if(aux){break;}
        }
        return !aux;
    }

    @JacocoAnnotationGenerated
    @Override
    public Long getLastId() {
        return 0L;
    }
    @JacocoAnnotationGenerated
    @Override
    public void createChessGame(ChessGame chessGame) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            ChessGameEntity chessGameEntity = new ChessGameEntity();
            if (chessGame.getPlayerInTurn()==1){
                chessGameEntity.setPlayerInTurn(1);
            }else {
                chessGameEntity.setPlayerInTurn(2);
            }
            chessGameEntity.setStatus(chessGame.getStatus());
            session.save(chessGameEntity);
            chessGame.setId(chessGameEntity.getId().intValue());

            PlayerEntity p1Entity = new PlayerEntity();
            List<PieceEntity> p1Lst = new ArrayList<>();
            p1Entity.setLstPieces(p1Lst);
            p1Entity.setChessGame(chessGameEntity);
            p1Entity.setNamePlayer(chessGame.getPlayerOne().getNamePlayer());
            session.save(p1Entity);

            PlayerEntity p2Entity = new PlayerEntity();
            List<PieceEntity> p2Lst = new ArrayList<>();
            p2Entity.setLstPieces(p2Lst);
            p2Entity.setChessGame(chessGameEntity);
            p2Entity.setNamePlayer(chessGame.getPlayerTwo().getNamePlayer());
            session.save(p2Entity);

            chessGameEntity.setPlayerOne(p1Entity);
            chessGameEntity.setPlayerTwo(p2Entity);

            for(Piece p : chessGame.getPlayerOne().getLstPieces() ){
                PieceEntity piece = new PieceEntity();
                piece.setColor(p.getColor().toString());
                piece.setStatus(p.getStatus().toString());
                piece.setSymbol(p.getSymbol());
                piece.setType(p.getType());
                piece.setPlayer(p1Entity);
                p1Lst.add(piece);
                session.save(piece);

                PositionEntity pos1 = new PositionEntity();
                pos1.setColumnNumber(p.getPosition().getColumn());
                pos1.setRowNumber(p.getPosition().getRow());
                pos1.setPiece(piece);
                session.save(pos1);

                piece.setPosition(pos1);
            }
            for(Piece p : chessGame.getPlayerTwo().getLstPieces() ){
                PieceEntity piece = new PieceEntity();
                piece.setColor(p.getColor().toString());
                piece.setStatus(p.getStatus().toString());
                piece.setSymbol(p.getSymbol());
                piece.setType(p.getType());
                piece.setPlayer(p2Entity);
                p2Lst.add(piece);
                session.save(piece);

                PositionEntity pos2 = new PositionEntity();
                pos2.setColumnNumber(p.getPosition().getColumn());
                pos2.setRowNumber(p.getPosition().getRow());
                pos2.setPiece(piece);
                session.save(pos2);

                piece.setPosition(pos2);
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
    }
    @JacocoAnnotationGenerated
    @Override
    public void setChessMove(ChessGame chessGame) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            ChessMoveEntity chessMoveEntity = new ChessMoveEntity();
            chessMoveEntity.setChessGame(session.get(ChessGameEntity.class,chessGame.getId()));
            chessMoveEntity.setMove(chessGame.getChessMove().getMove());
            session.save(chessMoveEntity);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
    }
    @JacocoAnnotationGenerated
    @Override
    public void setChessGame(ChessGame chessGame, Position pIni,Position pEnd) {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            ChessGameEntity chessGameEntity = session.get(ChessGameEntity.class,chessGame.getId());
            chessGameEntity.setPlayerInTurn(chessGame.getPlayerInTurn());
            chessGameEntity.setStatus(chessGame.getStatus());
            session.update(chessGameEntity);

            List<PlayerEntity> lstPlayer = session.createQuery("from PlayerEntity where chessGame.id = :ChessGames_id", PlayerEntity.class)
                    .setParameter("ChessGames_id", chessGame.getId())
                    .list();
            List<PieceEntity> myList = new ArrayList<>();
            List<PieceEntity> enemyList = new ArrayList<>();
            if(chessGame.getPlayerInTurn()==1){
                myList = session.createQuery("from PieceEntity where player.id = :players_id", PieceEntity.class)
                        .setParameter("players_id", lstPlayer.get(0).getId())
                        .list();
                enemyList = session.createQuery("from PieceEntity where player.id = :players_id", PieceEntity.class)
                        .setParameter("players_id", lstPlayer.get(1).getId())
                        .list();
            }else {
                myList = session.createQuery("from PieceEntity where player.id = :players_id", PieceEntity.class)
                        .setParameter("players_id", lstPlayer.get(1).getId())
                        .list();
                enemyList = session.createQuery("from PieceEntity where player.id = :players_id", PieceEntity.class)
                        .setParameter("players_id", lstPlayer.get(0).getId())
                        .list();
            }
            for (PieceEntity p:myList){
                if(p.getStatus().equals(Status.ALIVE.name())&&p.getPosition().getColumnNumber().equals(pIni.getColumn())&&p.getPosition().getRowNumber().equals(pIni.getRow())){
                    PositionEntity positionEntity = session.createQuery("from PositionEntity where piece.id = :pieces_id",PositionEntity.class)
                            .setParameter("pieces_id",p.getId()).uniqueResult();
                    positionEntity.setColumnNumber(pEnd.getColumn());
                    positionEntity.setRowNumber(pEnd.getRow());
                    session.update(positionEntity);
                }
            }
            for (PieceEntity p:enemyList){
                if(p.getStatus().equals(Status.ALIVE.name())&&p.getPosition().getColumnNumber().equals(pEnd.getColumn())&&p.getPosition().getRowNumber().equals(pEnd.getRow())){
                    PieceEntity pieceEntity = session.get(PieceEntity.class,p.getId());
                    pieceEntity.setStatus(Status.DEAD.name());
                    session.update(pieceEntity);
                }
            }

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
    }
    @JacocoAnnotationGenerated
    @Override
    public String viewChessGamesSaved() {
        // listar los chess games que se pueden continuar
        Transaction transaction = null;
        String result = "N°-player1-player2";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Long> idLst = session.createQuery("select id from ChessGameEntity where status='In Progress'").list();

            for (Long idGame : idLst){
                String playerOneName = session.createQuery("select namePlayer from PlayerEntity where chessGame.id= :chessGames_id order by id asc",String.class)
                        .setParameter("chessGames_id",idGame)
                        .setMaxResults(1)
                        .uniqueResult();
                String playerTwoName = session.createQuery("select namePlayer from PlayerEntity where chessGame.id= :chessGames_id order by id desc ",String.class)
                        .setParameter("chessGames_id",idGame)
                        .setMaxResults(1)
                        .uniqueResult();
                result = result+System.lineSeparator()+idGame + " - " + playerOneName+ " vs "+playerTwoName;
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }
    @JacocoAnnotationGenerated

    @Override
    public boolean chessGameExist(Integer aux) {
        //verificar que es id existe
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           boolean exist = false;
            ChessGameEntity chessGameEntity = session.get(ChessGameEntity.class,aux);
            if (chessGameEntity != null&&chessGameEntity.getStatus().equals("In Progress")){
                exist = true;
            }
            return exist;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @JacocoAnnotationGenerated
    @Override
    public ChessGame getChessGame(Integer aux) {
        //crear chess game
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            ChessGameEntity chessGameEntity = session.createQuery("SELECT cg FROM ChessGameEntity cg " +
                            "JOIN fetch cg.playerOne p1 " +
                            "join fetch cg.playerTwo p2 " +
                            "where cg.id = :id and p2.id>p1.id",ChessGameEntity.class)
                    .setParameter("id", aux)
                    .setMaxResults(1)
                    .uniqueResult();
            ChessGame chessGame = new ChessGame();
            Player playerOne = new Player();
            Player playerTwo = new Player();
            playerOne.setLstPieces(setLstPieces(chessGameEntity.getPlayerOne().getLstPieces()));
            playerOne.setNamePlayer(chessGameEntity.getPlayerOne().getNamePlayer());
            playerTwo.setLstPieces(setLstPieces(chessGameEntity.getPlayerTwo().getLstPieces()));
            playerTwo.setNamePlayer(chessGameEntity.getPlayerTwo().getNamePlayer());
            chessGame.setId(chessGameEntity.getId().intValue());
            chessGame.setStatus(chessGameEntity.getStatus());
            chessGame.setPlayerOne(playerOne);
            chessGame.setPlayerTwo(playerTwo);
            chessGame.setPlayerInTurn(chessGameEntity.getPlayerInTurn());
            boardService = new ChessBoardServiceImpl();
            boardService.initializeBoard();
            chessGame.setChessboard(boardService.getChessBoard());
            return chessGame;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @JacocoAnnotationGenerated
    private List<Piece> setLstPieces(List<PieceEntity> lstPiecesEntity) {
        List<Piece> lstPieces = new ArrayList<>();
        String paint ="";
        for (PieceEntity p: lstPiecesEntity){
            if(p.getColor().equals(Color.BLACK.name())){
                paint ="";
            }
            switch (p.getType()){
                case "P" -> {
                    Pawn pawn = new Pawn(Color.valueOf(p.getColor()), Status.valueOf(p.getStatus()), new Position(p.getPosition().getColumnNumber(), p.getPosition().getRowNumber()), p.getSymbol(), p.getType(), new ArrayList<>());
                    lstPieces.add(pawn);
                }
                case "R" -> {
                    Tower tower = new Tower(Color.valueOf(p.getColor()), Status.valueOf(p.getStatus()), new Position(p.getPosition().getColumnNumber(), p.getPosition().getRowNumber()), p.getSymbol(), p.getType(), new ArrayList<>());
                    lstPieces.add(tower);
                }
                case "N" -> {
                    Horse horse = new Horse(Color.valueOf(p.getColor()), Status.valueOf(p.getStatus()), new Position(p.getPosition().getColumnNumber(), p.getPosition().getRowNumber()), p.getSymbol(), p.getType(), new ArrayList<>());
                    lstPieces.add(horse);
                }
                case "B" -> {
                    Bishop bishop = new Bishop(Color.valueOf(p.getColor()), Status.valueOf(p.getStatus()), new Position(p.getPosition().getColumnNumber(), p.getPosition().getRowNumber()), p.getSymbol(), p.getType(), new ArrayList<>());
                    lstPieces.add(bishop);
                }
                case "Q" -> {
                    Queen queen = new Queen(Color.valueOf(p.getColor()), Status.valueOf(p.getStatus()), new Position(p.getPosition().getColumnNumber(), p.getPosition().getRowNumber()), p.getSymbol(), p.getType(), new ArrayList<>());
                    lstPieces.add(queen);
                }
                case "K" -> {
                    King king = new King(Color.valueOf(p.getColor()), Status.valueOf(p.getStatus()), new Position(p.getPosition().getColumnNumber(), p.getPosition().getRowNumber()), p.getSymbol(), p.getType(), new ArrayList<>());
                    lstPieces.add(king);
                }
            }
        }
        return lstPieces;
    }

    @Override
    public List<Piece> cloneList(List<Piece> originalList){
        List<Piece> clonedList = new ArrayList<>();
        Piece clonedPiece = null;

        for (Piece piece: originalList) {
            switch (piece.getType()) {
                case "P" -> {
                    pieceService = new PawnServiceImpl();
                    clonedPiece = pieceService.clonePiece(piece);
                    break;
                }
                case "R" -> {
                    pieceService = new TowerServiceImpl();
                    clonedPiece = pieceService.clonePiece(piece);
                    break;
                }
                case "N" -> {
                    pieceService = new HorseServiceImpl();
                    clonedPiece = pieceService.clonePiece(piece);
                    break;
                }
                case "B" -> {
                    pieceService = new BishopServiceImpl();
                    clonedPiece = pieceService.clonePiece(piece);
                    break;
                }
                case "Q" -> {
                    pieceService = new QueenServiceImpl();
                    clonedPiece = pieceService.clonePiece(piece);
                    break;
                }
                case "K" -> {
                    pieceService = new KingServiceImpl();
                    clonedPiece = pieceService.clonePiece(piece);
                    break;
                }
                default -> System.out.println("Invalid option selected, please try again");
            }

            clonedList.add(clonedPiece);
        }
        return clonedList;
    }
}
