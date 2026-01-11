package board;

import exceptions.InvalidMoveException;
import pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Board {
    public TreeSet<ChessPair<Position, Piece>> pieces = new TreeSet<>();

    public void initialize(){

        for (char col = 'A'; col <= 'H'; col++){
            Position whitePawn = new Position(col,2);
            Position blackPawn = new Position(col,7);
            Position whitePiece = new Position(col,1);
            Position blackPiece = new Position(col,8);

            pieces.add(new ChessPair<>(whitePawn, new Pawn(Colors.WHITE, whitePawn)));
            pieces.add(new ChessPair<>(blackPawn, new Pawn(Colors.BLACK, blackPawn)));

            if (col == 'A' || col == 'H'){
                pieces.add(new ChessPair<>(whitePiece, new Rook(Colors.WHITE, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new Rook(Colors.BLACK, blackPiece)));
            }
            if (col == 'B' || col == 'G'){
                pieces.add(new ChessPair<>(whitePiece, new Knight(Colors.WHITE, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new Knight(Colors.BLACK, blackPiece)));
            }
            if (col == 'C' || col == 'F'){
                pieces.add(new ChessPair<>(whitePiece, new Bishop(Colors.WHITE, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new Bishop(Colors.BLACK, blackPiece)));
            }
            if (col == 'D'){
                pieces.add(new ChessPair<>(whitePiece, new Queen(Colors.WHITE, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new Queen(Colors.BLACK, blackPiece)));
            }
            if (col == 'E'){
                pieces.add(new ChessPair<>(whitePiece, new King(Colors.WHITE, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new King(Colors.BLACK, blackPiece)));
            }
        }

        Position whiteRook1 = new Position('A', 1);
        Position blackRook1 = new Position('A', 8);
        Position whiteRook2 = new Position('H', 1);
        Position blackRook2 = new Position('H', 8);
        Position whiteBishop1 = new Position('C', 1);
        Position blackBishop1 = new Position('C', 8);
        Position whiteBishop2 = new Position('F', 1);
        Position blackBishop2 = new Position('F', 8);
    }

    public void movePiece(Position from, Position to) {
        //if (!isValidCoordinate(from) || !isValidCoordinate(to))
        //    throw new InvalidMoveException("Move is out of bounds.");

        Piece piece = getPieceAt(from);
        /*if (piece == null)
            throw new InvalidMoveException("No piece at starting position.");

        if (!isValidMove(from, to))
            throw new InvalidMoveException("Illegal move for this piece.");

        if (getPieceAt(to) != null)
            pieces.removeIf(pair -> pair.getKey().equals(to));
        */
        pieces.removeIf(pair -> pair.getKey().equals(to));
        pieces.removeIf(pair -> pair.getKey().equals(from));
        piece.setPosition(to);
        pieces.add(new ChessPair<>(to, piece));

    }

    public Piece getPieceAt(Position position){
        for (ChessPair<Position, Piece> pair : pieces){ //go through all the pieces' positions
            if(pair.getKey().equals(position)){
                return pair.getValue();
            }
        }
        return null; //no piece at this position
    }

    public boolean isValidMove(Position from, Position to){
        Piece piece = getPieceAt(from);
        Piece taken = getPieceAt(to);

        if (piece == null)
            return false;

        if (piece.getPossibleMoves(this) == null || !piece.getPossibleMoves(this).contains(to))
            return false;

        //Simulating the move to check if through it the king is left in check.
        boolean isValid = true;

        pieces.removeIf(pair -> pair.getKey().equals(from)); //remove piece that we move

        if (taken != null) //
            pieces.removeIf(pair -> pair.getKey().equals(to)); //if we are taking a piece, remove it aswell

        piece.setPosition(to);
        pieces.add(new ChessPair<>(to, piece)); //add the initial piece to the moved position

        for (ChessPair<Position, Piece> pair : pieces) {

            if (pair.getValue().getColor() == piece.getColor()) continue;

            for (Position move : pair.getValue().getPossibleMoves(this)) {
                //if same color then this move would leave the piece that is being moved's king in check(illegal), if different color then through this move the opponent gets checked.
                if (this.getPieceAt(move) instanceof King) {
                    isValid = false;
                    break;
                }
            }
            if (!isValid) break;
        }
        pieces.removeIf(pair -> pair.getKey().equals(to)); // remove our piece from the moved position

        if (taken != null)
            pieces.add(new ChessPair<>(to, taken)); //if we took a piece, put it back

        piece.setPosition(from);
        pieces.add(new ChessPair<>(from, piece)); //put the piece back in the initial position

        return isValid;
    }

    public List<ChessPair<Position, Piece>> getPiecesByColor(Colors color){
        List<ChessPair<Position, Piece>> ret = new ArrayList<>();

        for (ChessPair<Position, Piece> pair : pieces)
            if (pair.getValue().getColor() == color)
                ret.add(pair);

        return ret;
    }

    public boolean isValidCoordinate(Position pos){
        return pos.getX() >= 'A' && pos.getX() <= 'H' && pos.getY() >= 1 && pos.getY() <= 8;
    }
}