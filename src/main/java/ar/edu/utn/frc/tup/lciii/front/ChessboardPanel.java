package ar.edu.utn.frc.tup.lciii.front;

import ar.edu.utn.frc.tup.lciii.anotations.JacocoAnnotationGenerated;
import ar.edu.utn.frc.tup.lciii.models.Chessboard;
import ar.edu.utn.frc.tup.lciii.models.Piece;
import ar.edu.utn.frc.tup.lciii.models.Player;
import ar.edu.utn.frc.tup.lciii.models.Status;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ar.edu.utn.frc.tup.lciii.models.Color.BLACK;
@JacocoAnnotationGenerated
public class ChessboardPanel extends JPanel {

    private Chessboard chessboard;
    List<Piece> lstPiecesInGame2 = new ArrayList<>();
    Image imgs[];

    public ChessboardPanel(Player p1, Player p2) throws IOException {
        setPreferredSize(new Dimension(512, 512));
        chessboard = new Chessboard();
        drawChessboard(p1,p2);
    }
    public void drawChessboard(Player p1, Player p2)throws IOException {

        lstPiecesInGame2.clear();

        for (Piece p : p1.getLstPieces()) {
            if (p.getStatus().equals(Status.ALIVE)) {
                lstPiecesInGame2.add(p);
            }
        }
        for (Piece p : p2.getLstPieces()) {
            if (p.getStatus().equals(Status.ALIVE)) {
                lstPiecesInGame2.add(p);
            }
        }
        //URL url = getClass().getResource("/images/pieces.png");
        BufferedImage all = ImageIO.read(new File("images\\pieces.png")); //"D:\\chess.png"
        imgs = new Image[12];
        int ind = 0;
        for (int y = 0; y < 400; y += 200) {
            for (int x = 0; x < 1200; x += 200) {
                imgs[ind] = all.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }
    }

    @Override
    public void paintComponent (Graphics g){
        boolean white = true;
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                if (white) {
                    g.setColor(new Color(119, 148, 85));
                } else {
                    g.setColor(new Color(235, 235, 208));

                }
                g.fillRect(x * 64, y * 64, 64, 64);
                white = !white;
            }
            white = !white;
        }

        g.setColor(Color.BLACK);
        for (int x = 0; x < 8; x++) {
            char letter = (char) ('A' + x);
            g.drawString(Character.toString(letter), x * 64 + 64/2-5, 8 * 64 + 20);
        }

        g.setColor(Color.BLACK);
        FontMetrics fontMetrics = g.getFontMetrics();
        int numbersMargin = getWidth() - fontMetrics.stringWidth("8") - 10; // Margen derecho para los números
        for (int y = 0; y < 8; y++) {
            int numberX = numbersMargin + (fontMetrics.stringWidth("8") - fontMetrics.stringWidth(Integer.toString(y + 1))) / 2;
            int numberY = (7 - y) * 64 + 64 / 2 + fontMetrics.getAscent() / 2;
            g.drawString(Integer.toString(y), numberX, numberY);
        }

        for (Piece p : lstPiecesInGame2) {
            if (p.getStatus().equals(Status.ALIVE)) {
                int ind = 0;
                if (p.getType().equalsIgnoreCase("K")) {
                    ind = 0;
                }
                if (p.getType().equalsIgnoreCase("Q")) {
                    ind = 1;
                }
                if (p.getType().equalsIgnoreCase("B")) {
                    ind = 2;
                }
                if (p.getType().equalsIgnoreCase("N")) {
                    ind = 3;
                }
                if (p.getType().equalsIgnoreCase("R")) {
                    ind = 4;
                }
                if (p.getType().equalsIgnoreCase("P")) {
                    ind = 5;
                }
                if (p.getColor().equals(BLACK)) {
                    ind += 6;
                }
                g.drawImage(imgs[ind], p.getPosition().getColumn() * 64, (7 - p.getPosition().getRow()) * 64, this);
            }
        }
    }
}
