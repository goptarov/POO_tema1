package pieces;

import board.Colors;
import board.Position;
import pieces.moveStrategies.PawnMoveStrategy;

public class Pawn extends Piece {

    public Pawn(Colors color, Position pos){
        super(color, pos, new PawnMoveStrategy());
    }

    @Override
    public char type() {
        return '\u265F';
    }
}
