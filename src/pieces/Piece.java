package pieces;

import board.Board;
import board.ChessPair;
import board.Colors;
import board.Position;

import java.util.List;

public abstract class Piece implements ChessPiece{
    private final Colors color;
    private Position pos;

    public Piece(Colors color, Position pos) {
        this.color = color;
        this.pos = pos;
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

    void addMove(Board board, List<Position> moves, Position pos, int[] metAPiece, int x){
        if (metAPiece[x] == 1)
            return;
        if (board.isValidCoordinate(pos)) {
            if (board.getPieceAt(pos) == null) //move
                moves.add(pos);
            else{
                metAPiece[x] = 1;
                if (board.getPieceAt(pos).getColor() != this.getColor()) //capture
                    moves.add(pos);
            }
        }
        else metAPiece[x] = 1;
    }

    void addKingMove(Board board, List<Position> moves, Position pos){
        if (board.isValidCoordinate(pos)){
            if (board.getPieceAt(pos) == null)
                moves.add(pos);
            else if (board.getPieceAt(pos).getColor() != this.getColor())
                moves.add(pos);
        }
    }

    @Override
    public boolean checkForCheck(Board board, Position kingPosition) {
        if (!(board.getPieceAt(kingPosition) instanceof King))
            return false;
        if (this.getColor() != board.getPieceAt(kingPosition).getColor()){
            for (Position move : this.getPossibleMoves(board)) {
                if (board.isValidMove(this.pos, move) && move.equals(kingPosition))
                    return true;
            }
        }
        return false;
    }
}
