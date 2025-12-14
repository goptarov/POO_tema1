package Game;

import board.Board;
import board.ChessPair;
import board.Colors;
import board.Position;
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

    public void makeMove(Position from, Position to, Board board) throws InvalidMoveException {

        if (board.getPieceAt(from) == null) {
            throw new InvalidMoveException("No piece at starting position!");
        }
        if (board.getPieceAt(from).getColor() != this.color) {
            throw new InvalidMoveException("This is not your piece!");
        }

        Piece target = board.getPieceAt(to);
        board.movePiece(from, to);

        if (target != null) {
            captured.add(target);
            if (target instanceof Queen) setPoints(this.points + 9);
            if (target instanceof Rook) setPoints(this.points + 5);
            if (target instanceof Bishop || target instanceof Knight) setPoints(this.points + 3);
            if (target instanceof Pawn) setPoints(this.points + 1);
        }
    }

    public List<ChessPair<Position, Piece>> getOwnedPieces(Board board) {
        return board.getPiecesByColor(this.color);
    }

    public Colors getColor(){
        return color;
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
