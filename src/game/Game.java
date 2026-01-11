package game;

import board.Board;
import board.ChessPair;
import board.Colors;
import board.Position;
import exceptions.InvalidCommandException;
import exceptions.InvalidMoveException;
import pieces.King;
import pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int id;
    private Board board;
    private List<Player> players;
    private List<Move> moves;
    private int playerIndex;

    public Game(int gameId, Player p1, Player p2, Board board) {
        this.id = gameId;
        this.board = board;
        this.players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        this.moves = new ArrayList<>();
        this.playerIndex = 0;
    }

    public void start(){
        board.initialize();
        moves.clear();
        playerIndex = 0;
    }

    public void resume(){
        System.out.println("Resuming game " + this.id);
    }

    public void switchPlayer(){
        playerIndex = 1 - playerIndex;
    }

    public boolean checkForCheckMate() {
        Position whiteKing = null;
        Position blackKing = null;

        for (ChessPair<Position, Piece> pair : board.pieces) {
            Piece p = pair.getValue();
            if (p instanceof King) {
                if (p.getColor() == Colors.WHITE) whiteKing = pair.getKey();
                else blackKing = pair.getKey();
            }
        }

        boolean whiteInCheck = isKingInCheck(whiteKing, Colors.WHITE);
        boolean blackInCheck = isKingInCheck(blackKing, Colors.BLACK);

        if (whiteInCheck) {
            return !hasAnyValidMoves(Colors.WHITE);
        }
        if (blackInCheck) {
            return !hasAnyValidMoves(Colors.BLACK);
        }

        return false;
    }

    private boolean isKingInCheck(Position kingPos, Colors kingColor) {
        if (kingPos == null) return false;

        List<ChessPair<Position, Piece>> pieceListCopy = new ArrayList<>(board.pieces);
        for (ChessPair<Position, Piece> pair : pieceListCopy) {
            Piece enemy = pair.getValue();
            if (enemy.getColor() != kingColor) {
                if (enemy.checkForCheck(board, kingPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasAnyValidMoves(Colors color) {
        List<ChessPair<Position, Piece>> pieceListCopy = new ArrayList<>(board.pieces);
        for (ChessPair<Position, Piece> pair : pieceListCopy) {
            Piece piece = pair.getValue();

            if (piece.getColor() == color) {
                Position currentPos = pair.getKey();
                for (Position dest : piece.getPossibleMoves(board)) {
                    if (board.isValidMove(currentPos, dest)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Player getCurrentPlayer(){
        return players.get(playerIndex);
    }

    public void setCurrentPlayerColor(Colors color){
        if (color == Colors.WHITE)
           playerIndex = 0;
        else if (color == Colors.BLACK)
           playerIndex = 1;
    }

    public void addMove(Player player, Position from, Position to, Piece captured){
        Move move = new Move(player.color, from, to, captured);
        moves.add(move);
    }

    public void executeMove(Position from, Position to) throws InvalidMoveException, InvalidCommandException {
        if (!board.isValidCoordinate(from) || !board.isValidCoordinate(to))
            throw new InvalidCommandException("Invalid move command");
        Piece movingPiece = board.getPieceAt(from);
        if (movingPiece == null || movingPiece.getColor() != getCurrentPlayer().getColor())
            throw new InvalidMoveException("Invalid piece selected");
        if (!board.isValidMove(from, to))
            throw new InvalidMoveException("Illegal move.");

        Piece capturedPiece = board.getPieceAt(to);
        board.movePiece(from, to);

        getCurrentPlayer().recordCapture(capturedPiece);
        addMove(getCurrentPlayer(), from, to, capturedPiece);
    }

    public List<Move> getMoves(){
        return moves;
    }

    public int getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }
    public List<Player> getPlayers(){
        return this.players;
    }
}
