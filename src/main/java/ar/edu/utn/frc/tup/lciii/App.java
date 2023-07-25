package ar.edu.utn.frc.tup.lciii;


import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;

import ar.edu.utn.frc.tup.lciii.controllers.ChessGameController;

import javax.swing.*;
import java.io.IOException;

/**
 * Hello to TPI Chess
 *
 */
@JacocoAnnotationGenerated
public class App {

    /**
     * This is the main program
     */
    public static void main(String[] args) throws IOException {
        ChessGameController chessGameController = new ChessGameController();
        chessGameController.ChessGame();
    }
}