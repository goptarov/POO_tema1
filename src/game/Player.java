package game;

import board.Board;
import board.ChessPair;
import board.Colors;
import board.Position;
import exceptions.InvalidCommandException;
import exceptions.InvalidMoveException;
import pieces.*;


import java.util.*;

public class Player {
    String name;
    Colors color;
    private int points;
    private List<Piece> captured = new ArrayList<>();

    public Player(String name, Colors color) {
        this.name = name;
        this.color = color;
        this.points = 0;
    }

    public void recordCapture(Piece target) {
        if (target == null) return;

        captured.add(target);

        if (target instanceof Queen) setPoints(this.points + 90);
        if (target instanceof Rook) setPoints(this.points + 50);
        if (target instanceof Bishop || target instanceof Knight) setPoints(this.points + 30);
        if (target instanceof Pawn) setPoints(this.points + 10);
    }

    public List<ChessPair<Position, Piece>> getOwnedPieces(Board board) {
        return board.getPiecesByColor(this.color);
    }

    public Colors getColor(){
        return color;
    }

    public String getName(){
        return name;
    }

    public List<Piece> getCaptured() {
        return captured;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
