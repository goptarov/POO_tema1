package pieces;

import board.Board;
import board.Colors;
import board.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Colors color, Position pos){
        super(color, pos);
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();
        Position currentPosition = this.getPosition();
        int x = currentPosition.getX();
        int y = currentPosition.getY();

        Position ul = new Position((char)(x - 1), y + 1);
        Position u = new Position((char)x, y + 1);
        Position ur = new Position((char)(x + 1), y + 1);
        Position r = new Position((char)(x + 1), y);
        Position dr = new Position((char)(x + 1), y - 1);
        Position d = new Position((char)x, y - 1);
        Position dl = new Position((char)(x - 1), y - 1);
        Position l = new Position((char)(x - 1), y);

        addKingMove(board, moves, ul);
        addKingMove(board, moves, u);
        addKingMove(board, moves, ur);
        addKingMove(board, moves, r);
        addKingMove(board, moves, dr);
        addKingMove(board, moves, d);
        addKingMove(board, moves, dl);
        addKingMove(board, moves, l);

        return moves;
    }

    @Override
    public char type(){
        return 'K';
    }
}
