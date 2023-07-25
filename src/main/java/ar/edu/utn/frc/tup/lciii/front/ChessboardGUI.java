package ar.edu.utn.frc.tup.lciii.front;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import ar.edu.utn.frc.tup.lciii.models.Player;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
@JacocoAnnotationGenerated
public class ChessboardGUI extends JFrame {
    private ChessboardPanel chessboardPanel;
    private ConsolePanel consolePanel;

    public ChessboardGUI(Player p1, Player p2) throws IOException {
        setTitle("Chessboard Console");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el panel del tablero
        chessboardPanel = new ChessboardPanel(p1, p2);
        chessboardPanel.setPreferredSize(new Dimension(540, 540));
        add(chessboardPanel, BorderLayout.WEST);

        // Crear el panel de la consola
        consolePanel = new ConsolePanel();
        consolePanel.setPreferredSize(new Dimension(300, 540));
        add(consolePanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
    }

    public ChessboardPanel getChessboardPanel() {
        return chessboardPanel;
    }

    public ConsolePanel getConsolePanel() {
        return consolePanel;
    }
}
