package board;

import exceptions.InvalidMoveException;
import pieces.*;
import board.Colors;

import java.util.TreeSet;

public class Board {
    public TreeSet<ChessPair<Position, Piece>> pieces = new TreeSet<>();

    public void initialize(){

        for (char col = 'A'; col <= 'H'; col++){
            Position whitePawn = new Position(col,2);
            Position blackPawn = new Position(col,7);
            Position whitePiece = new Position(col,1);
            Position blackPiece = new Position(col,8);

            pieces.add(new ChessPair<>(whitePawn, new Pawn(Colors.White, whitePawn)));
            pieces.add(new ChessPair<>(blackPawn, new Pawn(Colors.Black, blackPawn)));

            if (col == 'A' || col == 'H'){
                pieces.add(new ChessPair<>(whitePiece, new Rook(Colors.White, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new Rook(Colors.Black, blackPiece)));
            }
            if (col == 'B' || col == 'G'){
                pieces.add(new ChessPair<>(whitePiece, new Knight(Colors.White, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new Knight(Colors.Black, blackPiece)));
            }
            if (col == 'C' || col == 'F'){
                pieces.add(new ChessPair<>(whitePiece, new Bishop(Colors.White, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new Bishop(Colors.Black, blackPiece)));
            }
            if (col == 'D'){
                pieces.add(new ChessPair<>(whitePiece, new Queen(Colors.White, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new Queen(Colors.Black, blackPiece)));
            }
            if (col == 'E'){
                pieces.add(new ChessPair<>(whitePiece, new King(Colors.White, whitePiece)));
                pieces.add(new ChessPair<>(blackPiece, new King(Colors.Black, blackPiece)));
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

    public void movePiece(Position from, Position to) throws InvalidMoveException {
        if (!isValidCoordinate(from) || !isValidCoordinate(to))
            throw new InvalidMoveException("Move is out of bounds.");

        Piece piece = getPieceAt(from);
        if (piece == null)
            throw new InvalidMoveException("No piece at starting position.");

        if (!isValidMove(from, to))
            throw new InvalidMoveException("Illegal move for this piece.");

        if (getPieceAt(to) != null)
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
        if (piece == null)
            return false;

        if (piece.getPossibleMoves(this) == null || !piece.getPossibleMoves(this).contains(to))
            return false;

        //TODO: Check if moving this piece would put the king in check.

        return true;
    }

    public boolean isValidCoordinate(Position position){
        if (position.getX() < 'A' || position.getX() > 'H' || position.getY() < 1 || position.getY() > 8)
            return false;
        return true;
    }
}