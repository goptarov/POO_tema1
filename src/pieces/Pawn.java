package pieces;

import board.Board;
import board.Colors;
import board.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Colors color, Position pos){
        super(color, pos);
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();
        Position currentPos = this.getPosition();
        int x = currentPos.getX();
        int y = currentPos.getY();

        int direction = 0;
        if (this.getColor() == Colors.White)
            direction = 1;
        if (this.getColor() == Colors.Black){
            direction = -1;
        }

        //Deplasare
        Position forwardOne = new Position((char)x, y + direction);

        if (board.isValidCoordinate(forwardOne) && board.getPieceAt(forwardOne) == null){
            moves.add(forwardOne);

            boolean isFirstMove = false;
            if ((this.getColor() == Colors.White && y == 2) || (this.getColor() == Colors.Black && y == 7)) {
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
                if (board.isValidCoordinate(diagonal) && board.getPieceAt(diagonal).getColor() != this.getColor()) {
                    moves.add(diagonal);
                }
            }
        }

        return moves;
    }

    @Override
    public char type() {
        return 'P';
    }
}
