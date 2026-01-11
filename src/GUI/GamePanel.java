package GUI;

import board.Board;
import board.Position;
import core.Main;
import exceptions.InvalidCommandException;
import exceptions.InvalidMoveException;
import game.Game;
import game.Player;
import board.Colors;
import pieces.Piece;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private AppWindow parent;
    private Game game;
    private Player white, black;
    private Position selectedPos = null;
    private List<Position> possibleMoves = new ArrayList<>();
    private Colors humanPlayerColor = Colors.WHITE;

    private final int TILE_SIZE = 40;

    public GamePanel(AppWindow parent) {
        this.parent = parent;
        if (Main.getInstance().getCurrentGame() == null) return;
        if (Main.getInstance().getCurrentGame().getPlayers().getFirst().getName().equals("Computer"))
            humanPlayerColor = Colors.BLACK;

        this.setPreferredSize(new Dimension(TILE_SIZE * 8, TILE_SIZE * 8));

        this.addMouseListener(new  MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int row;
                char col;
                if (humanPlayerColor == Colors.WHITE) {
                    col = (char) ('H' - (e.getX() / TILE_SIZE));
                    row = e.getY() / TILE_SIZE;
                }
                else{
                     col = (char) ('A' + (e.getX() / TILE_SIZE));
                     row = 8 - (e.getY() / TILE_SIZE);
                }

                handleClick(new Position(col, row));
            }
        });
    }

    private void handleClick(Position clickPos){
        if (selectedPos != null && possibleMoves.contains(clickPos)){
            try{
                Piece captured = game.getBoard().getPieceAt(clickPos);
                game.executeMove(selectedPos, clickPos);

            } catch (InvalidCommandException | InvalidMoveException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
