package pieces.moveStrategies;

import board.Board;
import board.Colors;
import board.Position;

import java.util.ArrayList;
import java.util.List;

public class PawnMoveStrategy implements MoveStrategy{
    @Override
    public List<Position> getPossibleMoves(Board board, Position from){
        List<Position> moves = new ArrayList<>();
        int x = from.getX();
        int y = from.getY();

        int direction = 0;
        if (board.getPieceAt(from).getColor() == Colors.WHITE)
            direction = 1;
        if (board.getPieceAt(from).getColor() == Colors.BLACK){
            direction = -1;
        }

        //Deplasare
        Position forwardOne = new Position((char)x, y + direction);

        if (board.isValidCoordinate(forwardOne) && board.getPieceAt(forwardOne) == null){
            moves.add(forwardOne);

            boolean isFirstMove = false;
            if ((board.getPieceAt(from).getColor() == Colors.WHITE && y == 2) || (board.getPieceAt(from).getColor() == Colors.BLACK && y == 7)) {
                isFirstMove = true;
            }
            if (isFirstMove) {
                Position forwardTwo = new Position((char)x, y + 2 * direction);
                if (board.isValidCoordinate(forwardTwo) && board.getPieceAt(forwardTwo) == null){
                    moves.add(forwardTwo);
                }
            }
        }

        //Capturare
        int[] captureOffsets = {-1, 1};
        for (int offset : captureOffsets) {
            Position diagonal = new Position((char)(x + offset), y + direction);

            if (board.getPieceAt(diagonal) != null) {
                if (board.isValidCoordinate(diagonal) && board.getPieceAt(diagonal).getColor() != board.getPieceAt(from).getColor()) {
                    moves.add(diagonal);
                }
            }
        }

        return moves;
    }
}
