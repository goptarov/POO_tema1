package pieces.moveStrategies;

import board.Board;
import board.Position;

import java.util.ArrayList;
import java.util.List;

public class KnightMoveStrategy implements MoveStrategy{
    public List<Position> getPossibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        int x = from.getX();
        int y = from.getY();

        Position ul = new Position((char)(x - 1), y + 2);
        Position ur = new Position((char)(x + 1), y + 2);
        Position ru = new Position((char)(x + 2), y + 1);
        Position rd = new Position((char)(x + 2), y - 1);
        Position dr = new Position((char)(x + 1), y - 2);
        Position dl = new Position((char)(x - 1), y - 2);
        Position lu = new Position((char)(x - 2), y + 1);
        Position ld = new Position((char)(x - 2), y - 1);

        if (board.isValidCoordinate(ul)){
            if (board.getPieceAt(ul) == null)
                moves.add(ul);
            else if (board.getPieceAt(ul).getColor() != board.getPieceAt(from).getColor())
                moves.add(ul);
        }
        if (board.isValidCoordinate(ur)){
            if (board.getPieceAt(ur) == null)
                moves.add(ur);
            else if (board.getPieceAt(ur).getColor() != board.getPieceAt(from).getColor())
                moves.add(ur);
        }
        if (board.isValidCoordinate(ru)){
            if (board.getPieceAt(ru) == null)
                moves.add(ru);
            else if (board.getPieceAt(ru).getColor() != board.getPieceAt(from).getColor())
                moves.add(ru);
        }
        if (board.isValidCoordinate(rd)){
            if (board.getPieceAt(rd) == null)
                moves.add(rd);
            else if (board.getPieceAt(rd).getColor() != board.getPieceAt(from).getColor())
                moves.add(rd);
        }
        if (board.isValidCoordinate(dr)){
            if (board.getPieceAt(dr) == null)
                moves.add(dr);
            else if (board.getPieceAt(dr).getColor() != board.getPieceAt(from).getColor())
                moves.add(dr);
        }
        if (board.isValidCoordinate(dl)){
            if (board.getPieceAt(dl) == null)
                moves.add(dl);
            else if (board.getPieceAt(dl).getColor() != board.getPieceAt(from).getColor())
                moves.add(dl);
        }
        if (board.isValidCoordinate(ld)){
            if (board.getPieceAt(ld) == null)
                moves.add(ld);
            else if (board.getPieceAt(ld).getColor() != board.getPieceAt(from).getColor())
                moves.add(ld);
        }
        if (board.isValidCoordinate(lu)){
            if (board.getPieceAt(lu) == null)
                moves.add(lu);
            else if (board.getPieceAt(lu).getColor() != board.getPieceAt(from).getColor())
                moves.add(lu);
        }
        return moves;
    }
}
