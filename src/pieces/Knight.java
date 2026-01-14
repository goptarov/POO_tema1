package pieces;

import board.Colors;
import board.Position;
import pieces.moveStrategies.KnightMoveStrategy;

public class Knight extends Piece {

    public Knight(Colors color, Position pos){
        super(color, pos, new KnightMoveStrategy());
    }

    @Override
    public char type() {
        return '\u265E';
    }
}
