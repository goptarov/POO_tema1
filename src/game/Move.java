package game;

import board.Colors;
import board.Position;
import pieces.Piece;

public class Move {
    private Colors color;
    private Position from;
    private Position to;
    private Piece captured;

    public Move(Colors color, Position from, Position to,  Piece captured) {
        this.color = color;
        this.from = from;
        this.to = to;
        this.captured = captured;
    }

    public Colors getColor() {
        return color;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public Piece getCaptured() {
        return captured;
    }

}
