package pieces;

import board.Colors;
import board.Position;
import pieces.moveStrategies.RookMoveStrategy;

public class Rook extends Piece {

    public Rook(Colors color, Position pos){
        super(color, pos, new RookMoveStrategy());
    }

    @Override
    public char type(){
        return '\u265C';
    }
}
