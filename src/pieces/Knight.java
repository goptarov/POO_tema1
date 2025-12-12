package pieces;

import board.Board;
import board.Colors;
import board.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(Colors color, Position pos){
        super(color, pos);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        Position currentPosition = this.getPosition();
        int x = currentPosition.getX();
        int y = currentPosition.getY();

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
            else if (board.getPieceAt(ul).getColor() != this.getColor())
                moves.add(ul);
        }
        if (board.isValidCoordinate(ur)){
            if (board.getPieceAt(ur) == null)
                moves.add(ur);
            else if (board.getPieceAt(ur).getColor() != this.getColor())
                moves.add(ur);
        }
        if (board.isValidCoordinate(ru)){
            if (board.getPieceAt(ru) == null)
                moves.add(ru);
            else if (board.getPieceAt(ru).getColor() != this.getColor())
                moves.add(ru);
        }
        if (board.isValidCoordinate(rd)){
            if (board.getPieceAt(rd) == null)
                moves.add(rd);
            else if (board.getPieceAt(rd).getColor() != this.getColor())
                moves.add(rd);
        }
        if (board.isValidCoordinate(dr)){
            if (board.getPieceAt(dr) == null)
                moves.add(dr);
            else if (board.getPieceAt(dr).getColor() != this.getColor())
                moves.add(dr);
        }
        if (board.isValidCoordinate(dl)){
            if (board.getPieceAt(dl) == null)
                moves.add(dl);
            else if (board.getPieceAt(dl).getColor() != this.getColor())
                moves.add(dl);
        }
        if (board.isValidCoordinate(ld)){
            if (board.getPieceAt(ld) == null)
                moves.add(ld);
            else if (board.getPieceAt(ld).getColor() != this.getColor())
                moves.add(ld);
        }
        if (board.isValidCoordinate(lu)){
            if (board.getPieceAt(lu) == null)
                moves.add(lu);
            else if (board.getPieceAt(lu).getColor() != this.getColor())
                moves.add(lu);
        }
        return moves;
    }

    @Override
    public char type() {
        return 'N';
    }
}
