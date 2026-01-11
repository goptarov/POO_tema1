package pieces;

import board.Colors;
import board.Position;
import pieces.moveStrategies.KingMoveStrategy;

public class King extends Piece {

    public King(Colors color, Position pos){
        super(color, pos, new KingMoveStrategy());
    }

    @Override
    public char type(){
        return 'K';
    }
}
