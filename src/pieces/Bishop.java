package pieces;

import board.Colors;
import board.Position;
import pieces.moveStrategies.BishopMoveStrategy;

public class Bishop extends Piece {

    public Bishop(Colors color, Position pos) {
        super(color, pos, new BishopMoveStrategy());
    }

    @Override
    public char type() {
        return '\u265D';
    }
}