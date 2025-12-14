package Game;

import board.Board;
import board.ChessPair;
import board.Colors;
import board.Position;
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

    public Game(int gameId, Player p1, Player p2) {
        this.id = gameId;
        this.board = new Board();
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

    /*
    public void resume(){}
    */

    public void switchPlayer(){
        playerIndex = 1 - playerIndex;
    }

    public boolean checkForCheckMate(){
        Position whiteKing = null, blackKing = null;
        boolean whiteInCheck = false, blackInCheck = false;

        for (ChessPair<Position, Piece> pair : board.pieces) {
            if (pair.getValue() instanceof King){
                if (pair.getValue().getColor().equals(Colors.BLACK))
                    blackKing = pair.getKey();
                if (pair.getValue().getColor().equals(Colors.WHITE))
                    whiteKing = pair.getKey();
            }
        }

        for (ChessPair<Position, Piece> pair : board.pieces) {
            if (pair.getValue().checkForCheck(board, whiteKing))
                whiteInCheck = true;
            if (pair.getValue().checkForCheck(board, blackKing))
                blackInCheck = true;
        }

        //Worlds worst code ever.
        if (whiteInCheck) {
            for (ChessPair<Position, Piece> pair : board.pieces) {
                if (pair.getValue().getColor().equals(Colors.BLACK)) continue;
                if (pair.getValue().getPossibleMoves(board) != null)
                    return false;
            }
            return true;
        }
        else if (blackInCheck) {
            for (ChessPair<Position, Piece> pair : board.pieces) {
                if (pair.getValue().getColor().equals(Colors.WHITE)) continue;
                if (pair.getValue().getPossibleMoves(board) != null)
                    return false;
            }
            return true;
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

    public int getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }
}
