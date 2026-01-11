package pieces;

import board.Position;
import board.Board;

import java.util.List;

public interface ChessPiece {
    boolean checkForCheck(Board board, Position kingPosition);
    List<Position> getPossibleMoves(Board board);
    char type();
}
