package pieces;

import board.Board;
import board.Colors;
import board.Position;
import pieces.moveStrategies.MoveStrategy;

import java.util.List;

public abstract class Piece implements ChessPiece {
    private final Colors color;
    private Position pos;
    private MoveStrategy moveStrategy;

    public Piece(Colors color, Position pos, MoveStrategy moveStrategy) {
        this.color = color;
        this.pos = pos;
        this.moveStrategy = moveStrategy;
    }

    public Colors getColor() {
        return color;
    }

    public Position getPosition() {
        return pos;
    }

    public void setPosition(Position pos) {
        this.pos = pos;
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        return moveStrategy.getPossibleMoves(board, this.getPosition());
    }

    @Override
    public boolean checkForCheck(Board board, Position kingPosition) {
        if (!(board.getPieceAt(kingPosition) instanceof King))
            return false;
        if (this.getColor() != board.getPieceAt(kingPosition).getColor()){
            for (Position move : getPossibleMoves(board)) {
                if (board.isValidMove(this.pos, move) && move.equals(kingPosition))
                    return true;
            }
        }
        return false;
    }
}
