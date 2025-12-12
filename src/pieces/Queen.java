package pieces;

import board.Board;
import board.Colors;
import board.Position;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Colors color, Position pos){
        super(color, pos);
    }

    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        Position currentPosition = this.getPosition();
        int x = currentPosition.getX();
        int y = currentPosition.getY();

        int[] metAPiece = {0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 1; i < 8; i++) {
            Position u = new Position((char) x, y + i);
            Position d = new Position((char) x, y - i);
            Position l = new Position((char) (x - i), y);
            Position r = new Position((char) (x + i), y);
            Position ul = new Position((char) (x - i), y + i);
            Position ur = new Position((char) (x + i), y + i);
            Position dl = new Position((char) (x - i), y - i);
            Position dr = new Position((char) (x + i), y - i);

            addMove(board, moves, u, metAPiece, 0);
            addMove(board, moves, d, metAPiece, 1);
            addMove(board, moves, l, metAPiece, 2);
            addMove(board, moves, r, metAPiece, 3);
            addMove(board, moves, ul, metAPiece, 4);
            addMove(board, moves, ur, metAPiece, 5);
            addMove(board, moves, dl, metAPiece, 6);
            addMove(board, moves, dr, metAPiece, 7);
        }
        return moves;
    }

    @Override
    public char type(){
        return 'Q';
    }
}
