package ar.edu.utn.frc.tup.lciii.controllers;


import ar.edu.utn.frc.tup.lciii.front.ChessboardGUI;
import ar.edu.utn.frc.tup.lciii.front.ConsolePanel;
import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import ar.edu.utn.frc.tup.lciii.models.*;
import ar.edu.utn.frc.tup.lciii.services.ChessBoardServiceInterface;
import ar.edu.utn.frc.tup.lciii.services.ChessGameServiceInterface;
import ar.edu.utn.frc.tup.lciii.services.impl.*;
import lombok.Data;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


@Data
public class ChessGameController {

    private final Scanner scanner = new Scanner(System.in);

    private ChessGameServiceInterface gameService = new ChessGameServiceImpl();

    private ChessBoardServiceInterface chessBoardService = new ChessBoardServiceImpl();
    private final Player playerOne = new Player();
    private final Player playerTwo = new Player();
    private ChessMove chessMove;
    private ChessGame chessGame;
    private ChessboardGUI frame;

    public ChessGameController() {
    }
    /*El siguiente metodo inicializa el menu, donde los usuarios deben decidir
        entre comenzar una partida nueva o continuar una partida.*/
    public void ChessGame() throws IOException {
        System.out.println("Hello, TPI Chess.");
        System.out.println("Welcome to the ChessGame");
        String option = "-1";
        int aux = -1;
        do {
            System.out.println("You need to choose about three options: ");
            System.out.println("1: Init New Game");
            System.out.println("2: Continue with an older game");
            System.out.println("3: Close the application");
            option = scanner.next();
            try{
                aux = Integer.parseInt(option);
            }catch (Exception ex){
                System.out.println("bad Format");
            }
            scanner.nextLine();
        }while (aux>3 || aux<1);
        switch (aux) {
            case 1 ->
                    chessGameDecision("NEW GAME");
            case 2 ->
                    chessGameDecision("CONTINUE GAME");
            case 3 -> {
                System.out.println("Good Bye, and see you in the next game!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option selected, please choose either 1, 2 or 3");
        }
    }


    /*El siguiente metodo toma la decision de los jugadores
     e inicializa la partida ya sea nueva o continuada.*/
    public void chessGameDecision(String initialState) throws IOException {

        if(initialState.equals("NEW GAME")){
            chessBoardService.initializeBoard();
            chessMove = new ChessMove();
            chessGame = new ChessGame(0,chessBoardService.getChessBoard(),playerOne,playerTwo,0,chessMove,"In Progress");
            createPlayers();
            gameService.createChessGame(chessGame);
            playGame();
        } else if (initialState.equals("CONTINUE GAME")) {
            CreateSampleGames();
            boolean result= false;
            String gameSelected = "0";
            Integer aux = 0;
            do {
                String chessGamesSaved = gameService.viewChessGamesSaved();
                System.out.println("Enter the number of the game you want to continue");
                System.out.println(chessGamesSaved);
                gameSelected = scanner.next();
                try{
                    aux = Integer.parseInt(gameSelected);
                }catch (Exception ex){
                    System.out.println("bad Format");
                }
                scanner.nextLine();
                if(aux !=0){
                    result = gameService.chessGameExist(aux);
                    if(result){
                        chessGame = gameService.getChessGame(aux);
                    }else {
                        System.out.println("Game number dont exist!!!");
                    }
                }else {
                    System.out.println("bad Request");
                }
            }while (!result);
            this.playerOne.setNamePlayer(chessGame.getPlayerOne().getNamePlayer());
            this.playerOne.setLstPieces(chessGame.getPlayerOne().getLstPieces());
            this.playerTwo.setNamePlayer(chessGame.getPlayerTwo().getNamePlayer());
            this.playerTwo.setLstPieces(chessGame.getPlayerTwo().getLstPieces());
            chessMove = new ChessMove();
            chessGame.setChessMove(chessMove);
            playGame();
        }
    }

// El siguiente metodo crea un ChessGame de prueba para poder ser continuado
    private void CreateSampleGames() {
        chessBoardService.initializeBoard();
        chessMove = new ChessMove();
        chessGame = new ChessGame(
                0,
                chessBoardService.getChessBoard(),
                playerOne,
                playerTwo,
                0,
                chessMove,
                "In Progress");
        createSamplePlayers();
        gameService.createChessGame(chessGame);
    }
    /*El siguiente metodo Inicializa los jugadores de ejemplo para la prueba de continuar un juego en una nase de datos H2.*/
    private void createSamplePlayers() {
        playerOne.setNamePlayer("Hernan Morais");
        playerTwo.setNamePlayer("Exequiel Santoro");
        String answer = "0";
            playerOne.setLstPieces(gameService.initializePieces(Color.WHITE));
            playerTwo.setLstPieces(gameService.initializePieces(Color.BLACK));
            chessGame.setPlayerInTurn(1);
            playerOne.getLstPieces().get(12).setPosition(new Position(4,3));
            playerTwo.getLstPieces().get(12).setPosition(new Position(4,4));
    }


    /*El siguiente metodo Inicializa los jugadores en caso de una partida nueva
    dando la opcion de elegir quien utilizara las piezas blancas.*/
    public void createPlayers() {
        System.out.println("Enter player's one name.");
        playerOne.setNamePlayer(scanner.nextLine());
        System.out.println("Enter player's two name.");
        playerTwo.setNamePlayer(scanner.nextLine());

        String answer = "0";

        while (!answer.equals("1") && !answer.equals("2")) {
            System.out.println("Wich player is going to use white pieces?");
            System.out.println("1. Player One");
            System.out.println("2. Player Two");
            answer = scanner.nextLine();
        }
        if (answer.equals("1")) {
            playerOne.setLstPieces(gameService.initializePieces(Color.WHITE));
            playerTwo.setLstPieces(gameService.initializePieces(Color.BLACK));
            chessGame.setPlayerInTurn(1);
        } else {
            playerTwo.setLstPieces(gameService.initializePieces(Color.WHITE));
            playerOne.setLstPieces(gameService.initializePieces(Color.BLACK));
            chessGame.setPlayerInTurn(2);
        }
    }



    /*El siguiente metodo contiene la logica del las jugadas, validando mediante los servicios los movimientos elegidos
     por los jugadores, dibujando el tablero y realizando los cambios de turno, Tambien se da la opcion en cada turno de abandonar
     la partida ingresando 00.*/
    public void playGame() throws IOException {
        // COMIENZA EL JUEGO Y AVISO DE QUIEN COMIENZA
        frame = new ChessboardGUI(playerOne, playerTwo);
        frame.setVisible(true);
        System.out.println("Welcome to the Chess Game!");
        // LOGICA REPETITIVA DE JUEGO
        boolean finished;
        do {
            //chessBoardService.drawBoard(playerOne,playerTwo);
            frame.getChessboardPanel().drawChessboard(playerOne,playerTwo);
            frame.getChessboardPanel().repaint();
            if (chessGame.getPlayerInTurn()==1){
                System.out.println(playerOne.getNamePlayer()+"'s turn. ("+playerOne.getLstPieces().get(0).getColor()+" PIECES)");
            }else {
                System.out.println(playerTwo.getNamePlayer()+"'s turn. ("+playerTwo.getLstPieces().get(0).getColor()+" PIECES)");
            }
            Position pIni = new Position();
            Position pEnd = new Position();
            boolean aux;
            do {
                finished = false;
                // PIDO MOVIMIENTO Y VALIDO QUE EXISTA UNA PIEZA EN LA UBICACION
                chessMove.setMove(getMove());
                pIni = new Position(Integer.parseInt(String.valueOf(chessMove.getMove().charAt(0))),
                        Integer.parseInt(String.valueOf(chessMove.getMove().charAt(1))));
                pEnd = new Position(Integer.parseInt(String.valueOf(chessMove.getMove().charAt(2))),
                        Integer.parseInt(String.valueOf(chessMove.getMove().charAt(3))));
                List<Piece> backP1Pieces = gameService.cloneList(playerOne.getLstPieces());
                List<Piece> backP2Pieces = gameService.cloneList(playerTwo.getLstPieces());

                if(chessGame.getPlayerInTurn()==1){
                    aux = (gameService.move(playerOne,playerTwo,pIni,pEnd));
                    if(aux){
                        if(gameService.check(playerOne.getLstPieces(),playerTwo.getLstPieces())){
                            aux = false;
                            playerOne.setLstPieces(backP1Pieces);
                            playerTwo.setLstPieces(backP2Pieces);

                            System.out.println("Invalid move, you'll leave your king in check.");
                        }
                        if(gameService.check(playerTwo.getLstPieces(),playerOne.getLstPieces())) {
                            System.out.println("Player '"+playerOne.getNamePlayer()+"' checked player '"+playerTwo.getNamePlayer()+"'.");
                        }
                        if(gameService.checkMate(playerTwo.getLstPieces(),playerOne.getLstPieces())){
                                setWinner(playerOne);
                                finished = true;
                        }

                    }
                }else {
                    aux = (gameService.move(playerTwo,playerOne,pIni,pEnd));
                    if(aux){
                        if(gameService.check(playerTwo.getLstPieces(),playerOne.getLstPieces())){
                            aux = false;
                            playerOne.setLstPieces(backP1Pieces);
                            playerTwo.setLstPieces(backP2Pieces);
                            System.out.println("Invalid move, you'll leave your king in check.");
                        }
                        if(gameService.check(playerOne.getLstPieces(),playerTwo.getLstPieces())) {
                            System.out.println("Player '"+playerTwo.getNamePlayer()+"' checked player '"+playerOne.getNamePlayer()+"'.");
                        }
                        if(gameService.checkMate(playerOne.getLstPieces(),playerTwo.getLstPieces())){
                                setWinner(playerTwo);
                                finished = true;
                        }
                    }
                }

            }while (!aux);

            changePlayerInTurn();
            gameService.setChessMove(chessGame);
            gameService.setChessGame(chessGame,pIni,pEnd);


        }while(!finished);
    }

    private void setWinner(Player player) {
        System.out.println("CHECKMATE!!!");
        System.out.println("'"+player.getNamePlayer()+"' wins.");
    }

/*El siguiente metodo pide la jugador en turno que ingrese la jugada (posicion de inicio y final de una pieza) mediante
 notacion algebraica, y por medio de la capa service valida que la notacion sea correcta y que el movimiento sea posible.
 Luego por medio del service convierto la notacion algebraica a un string de numeros para poder luego ingresarla como posicion.*/
@JacocoAnnotationGenerated
    private String getMove() {
        ConsolePanel consolePanel = frame.getConsolePanel();
        String answer;
        boolean validator;
        do {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Please, write your next move´s position or '00' to Quit");
            System.out.println("Eg. 'A 1;B 1'");
            frame.getConsolePanel().getConsoleTextArea().setCaretPosition(frame.getConsolePanel().getConsoleTextArea().getDocument().getLength());
            // Pausar el flujo del juego hasta que se libere el semáforo (se ingrese un comando en el JPanel de la consola)
            try {
                consolePanel.getCommandSemaphore().acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Obtener el comando ingresado desde el JPanel de la consola
            answer = consolePanel.getLastCommand().toUpperCase();
            if (chessGame.getPlayerInTurn() == 1) {
                validator = gameService.Validate(answer, playerOne);
            } else {
                validator = gameService.Validate(answer, playerTwo);
            }
        }while (!validator);
        if(answer.equals("00")){
            System.out.println("Thank you for playing Chess '4 de Copas', I hope you enjoyed it.");
            System.exit(0);
        }
        answer = gameService.charToNumber(answer);
        return answer;
    }


    /*El siguiente metodo se encarga de setear el jugador en turno.*/
    private void changePlayerInTurn() {
        if(chessGame.getPlayerInTurn()==1){
            chessGame.setPlayerInTurn(2);
        }else {
            chessGame.setPlayerInTurn(1);
        }
    }

}
