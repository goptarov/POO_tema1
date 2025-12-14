package Game;

import board.Colors;
import board.Position;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

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

}
