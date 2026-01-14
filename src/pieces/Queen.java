package pieces;

import board.Colors;
import board.Position;
import pieces.moveStrategies.QueenMoveStrategy;

public class Queen extends Piece {

    public Queen(Colors color, Position pos){
        super(color, pos, new QueenMoveStrategy());
    }

    @Override
    public char type(){
        return '\u265B';
    }
}
