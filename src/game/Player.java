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
    private TreeSet<ChessPair<Position, Piece>> pieces = new TreeSet<>();

    public Player(String name, Colors color) {
        this.name = name;
        this.color = color;
        this.points = 0;
    }

    public void recordCapture(Piece target) {
        /*
        if (!board.isValidCoordinate(from) || !board.isValidCoordinate(to)) {
            throw new InvalidCommandException("Invalid coordinates");
        }
        if (board.getPieceAt(from) == null) {
            throw new InvalidMoveException("No piece at starting position!");
        }
        if (board.getPieceAt(from).getColor() != this.color) {
            throw new InvalidMoveException("This is not your piece!");
        }
        */
        if (target == null) return;
        captured.add(target);

        if (target instanceof Queen) setPoints(this.points + 90);
        if (target instanceof Rook) setPoints(this.points + 50);
        if (target instanceof Bishop || target instanceof Knight) setPoints(this.points + 30);
        if (target instanceof Pawn) setPoints(this.points + 10);
    }

    public List<ChessPair<Position, Piece>> getOwnedPieces(Board board) {return board.getPiecesByColor(this.color);
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
