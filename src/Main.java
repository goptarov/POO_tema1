import board.Board;
import board.Position;
import exceptions.InvalidMoveException;
import pieces.Bishop;
import pieces.Pawn;
import pieces.Piece;

import java.util.List;

public class Main{
    public static void main(String[] args){
        Board board = new Board();
        Position startPosition = new Position('C', 1);
        Position endPosition = new Position('F', 4);

        board.initialize();

        Piece whiteBishop = board.getPieceAt(startPosition);
        List<Position> moves = whiteBishop.getPossibleMoves(board);

        for (Position move : moves) {
            System.out.println(move);
        }

        board.pieces.removeIf(pair -> pair.getKey().equals(new Position('D', 2)));
        board.pieces.removeIf(pair -> pair.getKey().equals(new Position('C', 2)));
        board.pieces.removeIf(pair -> pair.getKey().equals(new Position('B', 2)));
        System.out.println("Deleted pawns");

        List<Position> movesAfter = whiteBishop.getPossibleMoves(board);

        if (movesAfter == null){
            System.out.println("No moves available");
        }
        else{
            for (Position move : movesAfter) {
                System.out.println(move);
            }
        }



    }
}