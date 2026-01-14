package GUI;

import board.ChessPair;
import board.Position;
import core.Main;
import exceptions.InvalidCommandException;
import exceptions.InvalidMoveException;
import game.Game;
import game.Move;
import game.Player;
import board.Colors;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel {
    private AppWindow parent;
    private Game game;
    private Position selectedPos = null;
    private List<Position> validMoves = new ArrayList<>();
    private JButton[][] squares = new JButton[8][8];
    private Colors humanPlayerColor = Colors.WHITE;
    private GameInfoBar gameInfoBar;

    public GamePanel(AppWindow parent, GameInfoBar gameInfoBar) {
        this.parent = parent;
        this.gameInfoBar = gameInfoBar;

        if (Main.getInstance().getCurrentGame() == null) return;
        this.game = Main.getInstance().getCurrentGame();

        System.out.println(game.getId());

        if (game.getPlayers().getFirst().getName().equals("Computer")) {
            humanPlayerColor = Colors.BLACK;
        }

        this.setLayout(new GridLayout(8, 8));
        this.setPreferredSize(new Dimension(500, 500));

        initializeGrid();
        updateBoardUI();

        if (game.getCurrentPlayer().getName().equals("Computer"))
            makeComputerMove();
    }

    private void initializeGrid() {
        if (humanPlayerColor == Colors.WHITE) {
            for (int row = 8; row >= 1; row--) {
                for (char col = 'A'; col <= 'H'; col++) {
                    Position currentPos = new Position(col, row);
                    JButton btn = new JButton();

                    if ((row + (col - 'A')) % 2 != 0)
                        btn.setBackground(new Color(160, 100, 70));
                    else
                        btn.setBackground(Color.LIGHT_GRAY);

                    btn.setForeground(new Color(40, 40, 40));
                    btn.setOpaque(true);
                    btn.setBorderPainted(false);

                    btn.addActionListener(e -> handleClick(currentPos));

                    squares[8 - row][col - 'A'] = btn;

                    this.add(btn);
                }
            }
        }
        else {
            for (int row = 1; row <= 8; row++) {
                for (char col = 'H'; col >= 'A'; col--) {
                    Position currentPos = new Position(col, row);
                    JButton btn = new JButton();

                    if ((row + (col - 'A')) % 2 != 0)
                        btn.setBackground(new Color(160, 100, 70));
                    else
                        btn.setBackground(Color.LIGHT_GRAY);

                    btn.setForeground(new Color(40, 40, 40));
                    btn.setOpaque(true);
                    btn.setBorderPainted(false);

                    btn.addActionListener(e -> handleClick(currentPos));

                    squares[row - 1]['H' - col] = btn;

                    this.add(btn);
                }
            }
        }
    }

    public void updateBoardUI() {
        for (int row = 8; row >= 1; row--) {
            for (char col = 'A'; col <= 'H'; col++) {
                Position pos = new Position(col, row);
                JButton btn = getButtonAt(pos);
                Piece p = game.getBoard().getPieceAt(pos);

                if (p != null) {
                    String pieceName = p.type() + "";
                    if (p.getColor() == Colors.WHITE) btn.setForeground(Color.WHITE);
                    else btn.setForeground(Color.BLACK);
                    btn.setFont(new Font(null, Font.PLAIN, 40));
                    btn.setText(pieceName);
                }
                else
                    btn.setText("");
            }
        }
    }

    private void handleClick(Position clickPos) {
        if (selectedPos != null && validMoves.contains(clickPos)) {
            try {
                Piece movingPiece = game.getBoard().getPieceAt(selectedPos);
                Piece captured = game.getBoard().getPieceAt(clickPos);

                game.executeMove(selectedPos, clickPos);

                if (game.getBoard().needsPromotion(clickPos)) {
                    showPromotionDialog(clickPos);
                }

                parent.onMovePiece(this.gameInfoBar, new Move(movingPiece.getColor(), selectedPos, clickPos, captured));

                parent.onPlayerSwitch();

                clearSelection();
                updateBoardUI();

                if (game.getCurrentPlayer().getName().equals("Computer")) {
                    makeComputerMove();
                }

                return;

            } catch (InvalidCommandException | InvalidMoveException e) {
                System.out.println(e.getMessage());
                clearSelection();
                updateBoardUI();
                return;
            }
        }
        if (selectedPos != null) clearSelection(); //when clicking an invalid square when a piece was selected.

        Piece selected = game.getBoard().getPieceAt(clickPos);
        if (selected != null && selected.getColor() == humanPlayerColor) {
            selectedPos = clickPos;

            for (Position possibleMove : game.getBoard().getPieceAt(selectedPos).getPossibleMoves(game.getBoard())) {
                if (game.getBoard().isValidMove(selectedPos, possibleMove))
                    validMoves.add(possibleMove);
            }
            highlightPossibleMoves();
        } else {
            clearSelection();
            updateBoardUI();
        }
    }

    private void makeComputerMove() {
        List<Position> computerValidMoves = new ArrayList<>();
        List<Position> correspondingFromPositions = new ArrayList<>();

        Player computer = game.getCurrentPlayer();
        List<ChessPair<Position, Piece>> piecesList = computer.getOwnedPieces(game.getBoard());

        for (ChessPair<Position, Piece> pair : piecesList) {
            Position from = pair.getKey();
            for (Position to : pair.getValue().getPossibleMoves(game.getBoard())) {
                if (game.getBoard().isValidMove(from, to)) {
                    computerValidMoves.add(to);
                    correspondingFromPositions.add(from);
                }
            }
        }

        if (!computerValidMoves.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(computerValidMoves.size());

            Position from = correspondingFromPositions.get(randomIndex);
            Position to = computerValidMoves.get(randomIndex);

            try {
                Piece movingPiece = game.getBoard().getPieceAt(from);
                Piece captured = game.getBoard().getPieceAt(to);

                game.executeMove(from, to);

                if (game.getBoard().needsPromotion(to))
                    game.getBoard().promotePawn(to, "Q"); //computer always promotes to Queen.

                parent.onMovePiece(this.gameInfoBar, new Move(movingPiece.getColor(), from, to, captured));

                parent.onPlayerSwitch();

                updateBoardUI();
            } catch (InvalidMoveException | InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showPromotionDialog(Position pos) {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};

        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose promotion piece:",
                "Promote pawn",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null
        );

        String pieceType = switch (choice) {
            case 0 -> "Q";
            case 1 -> "R";
            case 2 -> "B";
            case 3 -> "N";
            default -> "Q";
        };

        game.getBoard().promotePawn(pos, pieceType);
        updateBoardUI();
    }

    private JButton getButtonAt(Position pos) {
        if (humanPlayerColor == Colors.WHITE)
            return squares[8 - pos.getY()][pos.getX() - 'A'];
        else
            return squares[pos.getY() - 1]['H' - pos.getX()];
    }

    private void highlightPossibleMoves() {
        for (Position pos : validMoves) {
            getButtonAt(pos).setBackground(new Color (110, 65, 125));
        }
    }

    private void clearSelection() {
        selectedPos = null;
        validMoves.clear();

        for (int row = 8; row >= 1; row--) {
            for (char col = 'A'; col <= 'H'; col++) {
                JButton btn = getButtonAt(new Position(col, row));
                if ((row + (col - 'A')) % 2 != 0)
                    btn.setBackground(new Color(160, 100, 70));
                else
                    btn.setBackground(Color.LIGHT_GRAY);
            }
        }
    }
}
