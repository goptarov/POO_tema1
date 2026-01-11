package pieces.moveStrategies;

import board.Board;
import board.Position;

import java.util.ArrayList;
import java.util.List;

public class KingMoveStrategy implements MoveStrategy {
    private void addMove(Board board, List<Position> moves, Position from, Position to){
        if (board.isValidCoordinate(to)){
            if (board.getPieceAt(to) == null)
                moves.add(to);
            else if (board.getPieceAt(to).getColor() != board.getPieceAt(from).getColor())
                moves.add(to);
        }
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position from){
        List<Position> moves = new ArrayList<>();

        int x = from.getX();
        int y = from.getY();

        Position ul = new Position((char)(x - 1), y + 1);
        Position u = new Position((char)x, y + 1);
        Position ur = new Position((char)(x + 1), y + 1);
        Position r = new Position((char)(x + 1), y);
        Position dr = new Position((char)(x + 1), y - 1);
        Position d = new Position((char)x, y - 1);
        Position dl = new Position((char)(x - 1), y - 1);
        Position l = new Position((char)(x - 1), y);

        addMove(board, moves, from, ul);
        addMove(board, moves, from, u);
        addMove(board, moves, from, ur);
        addMove(board, moves, from, r);
        addMove(board, moves, from, dr);
        addMove(board, moves, from, d);
        addMove(board, moves, from, dl);
        addMove(board, moves, from, l);

        return moves;
    }
}
