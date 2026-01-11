package pieces.moveStrategies;

import board.Board;
import board.Position;

import java.util.ArrayList;
import java.util.List;

public class BishopMoveStrategy implements MoveStrategy {
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
    public List<Position> getPossibleMoves(Board board, Position from){
        List<Position> moves = new ArrayList<>();
        int x = from.getX();
        int y = from.getY();

        int[] metAPiece = {0, 0, 0, 0}; //Array that lets the function know if a piece has been met on this axis (4 axis - bishop)

        for(int i = 1; i < 8; i++){
            Position ul = new Position((char)(x - i), y + i);
            Position ur = new Position((char)(x + i), y + i);
            Position dl = new Position((char)(x - i), y - i);
            Position dr = new Position((char)(x + i), y - i);

            addMove(board, moves, from, ul, metAPiece, 0);
            addMove(board, moves, from, ur, metAPiece, 1);
            addMove(board, moves, from, dl, metAPiece, 2);
            addMove(board, moves, from, dr, metAPiece, 3);
        }
        return moves;
    }
}
