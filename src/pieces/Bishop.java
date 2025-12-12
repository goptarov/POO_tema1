package pieces;

import board.Board;
import board.Colors;
import board.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(Colors color, Position pos){
        super(color, pos);
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();
        Position currentPos = this.getPosition();
        int x = currentPos.getX();
        int y = currentPos.getY();

        int[] metAPiece = {0, 0, 0, 0}; //Array that lets the function know if a piece has been met on this axis (4 axis - bishop)

        for(int i = 1; i < 8; i++){
            Position ul = new Position((char)(x - i), y + i);
            Position ur = new Position((char)(x + i), y + i);
            Position dl = new Position((char)(x - i), y - i);
            Position dr = new Position((char)(x + i), y - i);

            addMove(board, moves, ul, metAPiece, 0);
            addMove(board, moves, ur, metAPiece, 1);
            addMove(board, moves, dl, metAPiece, 2);
            addMove(board, moves, dr, metAPiece, 3);
        }
        return moves;
    }

    @Override
    public char type(){
        return 'B';
    }
}