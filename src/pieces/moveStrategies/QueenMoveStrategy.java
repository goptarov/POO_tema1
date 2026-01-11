package pieces.moveStrategies;

import board.Board;
import board.Position;

import java.util.ArrayList;
import java.util.List;

public class QueenMoveStrategy implements MoveStrategy {

    private void addMove(Board board, List<Position> moves, Position from, Position to, int[] metAPiece, int x){
        if (metAPiece[x] == 1)
            return;
        if (board.isValidCoordinate(to)) {
            if (board.getPieceAt(to) == null) //move
                moves.add(to);
            else{
                metAPiece[x] = 1;
                if (board.getPieceAt(to).getColor() != board.getPieceAt(from).getColor()) //capture
                    moves.add(to);
            }
        }
        else metAPiece[x] = 1;
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        int x = from.getX();
        int y = from.getY();

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

            addMove(board, moves, from, u, metAPiece, 0);
            addMove(board, moves, from, d, metAPiece, 1);
            addMove(board, moves, from, l, metAPiece, 2);
            addMove(board, moves, from, r, metAPiece, 3);
            addMove(board, moves, from, ul, metAPiece, 4);
            addMove(board, moves, from, ur, metAPiece, 5);
            addMove(board, moves, from, dl, metAPiece, 6);
            addMove(board, moves, from, dr, metAPiece, 7);
        }
        return moves;
    }
}
