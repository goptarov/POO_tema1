package GUI;

import board.Colors;
import core.Main;
import game.Game;
import game.Move;

import javax.swing.*;
import java.awt.*;

public class GameInfoBar extends JPanel {

    JLabel status = new JLabel("Playing...");
    JLabel turn = new JLabel("White's turn");
    JLabel whiteCapturedPieces = new JLabel("White captured: ");
    JLabel blackCapturedPieces = new JLabel("Black captured: ");
    JLabel whitePoints = new JLabel("White has 0 points");
    JLabel blackPoints = new JLabel("Black has 0 points");

    public GameInfoBar() {

        this.setLayout(new GridLayout(2, 3, 10, 10));
        this.setBackground(new Color(40, 58, 55));

        turn.setForeground(Color.WHITE);
        turn.setFont(new Font("Verdana", Font.PLAIN, 12));
        whiteCapturedPieces.setForeground(Color.WHITE);
        whiteCapturedPieces.setFont(new Font("Verdana", Font.PLAIN, 12));
        blackCapturedPieces.setForeground(Color.WHITE);
        blackCapturedPieces.setFont(new Font("Verdana", Font.PLAIN, 12));
        whitePoints.setForeground(Color.WHITE);
        whitePoints.setFont(new Font("Verdana", Font.PLAIN, 12));
        blackPoints.setForeground(Color.WHITE);
        blackPoints.setFont(new Font("Verdana", Font.PLAIN, 12));
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Verdana", Font.PLAIN, 16));

        this.add(status);
        this.add(whiteCapturedPieces);
        this.add(whitePoints);
        this.add(turn);
        this.add(blackCapturedPieces);
        this.add(blackPoints);
    }

    public void update(Move move){
        Game game = Main.getInstance().getCurrentGame();

        turn.setText(game.getCurrentPlayer().getColor() + "'s turn");
        whitePoints.setText("White has " + game.getPlayers().getFirst().getPoints() + " points.");
        blackPoints.setText("Black has " + game.getPlayers().getLast().getPoints() + " points.");
        if (game.checkForCheckMate())
            status.setText("Checkmate!");

        if (move.getCaptured() != null){
            if (move.getColor() == Colors.WHITE)
                whiteCapturedPieces.setText(whiteCapturedPieces.getText().concat(move.getCaptured().type() + " "));
            if (move.getColor() == Colors.BLACK)
                blackCapturedPieces.setText(blackCapturedPieces.getText().concat(move.getCaptured().type() + " "));
        }
    }
}
