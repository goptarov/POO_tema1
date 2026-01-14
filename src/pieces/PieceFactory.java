package pieces;

import board.Colors;
import board.Position;

public class PieceFactory {
    public static Piece createPiece(String type, Colors color, Position pos) {
        switch (type) {
            case "\u265A": return new King(color, pos);
            case "\u265B": return new Queen(color, pos);
            case "\u265C": return new Rook(color, pos);
            case "\u265D": return new Bishop(color, pos);
            case "\u265E": return new Knight(color, pos);
            case "\u265F": return new Pawn(color, pos);
            default: return null;
        }
    }
}
