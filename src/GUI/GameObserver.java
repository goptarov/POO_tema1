package GUI;

import game.Move;

public interface GameObserver {
    void onMovePiece(GameInfoBar gameInfoBar, Move move);
    void onPlayerSwitch();
}
