package pieces.moveStrategies;

import board.Board;
import board.Position;

import java.util.List;

public interface MoveStrategy{
    List<Position> getPossibleMoves(Board board, Position from);
}
