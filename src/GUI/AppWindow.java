package GUI;

import game.Game;
import game.Move;
import game.Player;
import game.User;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;

import core.Main;


public class AppWindow extends JFrame implements GameObserver {
    private MainPanel mainPanel = new MainPanel(this);

    public AppWindow(){
        super("Chess game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 300));
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public void showPanel(String newPanel) {
        this.getContentPane().removeAll();
        switch (newPanel){
            case "MAIN": this.getContentPane().add(new MainPanel(this)); break;

            case "LOGIN": this.getContentPane().add(new LoginPanel(this)); break;

            case "CREATE_ACCOUNT":
                this.getContentPane().add(new CreateAccPanel(this)); break;

            case "GAME_MENU":
                this.getContentPane().add(new GameMenuPanel(this)); break;

            case "NEW_GAME":
                this.getContentPane().add(new NewGameCreationPanel(this)); break;

            case "LOAD_GAME":
                this.getContentPane().add(new LoadGamePanel(this)); break;

            case "GAME":
                JPanel container = new JPanel(new BorderLayout());

                GameInfoBar gameInfoBar = new GameInfoBar();

                container.add(gameInfoBar, BorderLayout.NORTH);
                container.add(new GameControlsBar(this), BorderLayout.SOUTH);
                container.add(new GamePanel(this, gameInfoBar), BorderLayout.CENTER);
                this.getContentPane().add(container);
                break;

            case "GAME_END":
                this.getContentPane().add(new GameEndPanel(this)); break;
        }
        this.revalidate();
        this.repaint();
    }


    public void onMovePiece(GameInfoBar gameInfoBar, Move move){
        Game currGame = Main.getInstance().getCurrentGame();
        User currUser = Main.getInstance().getCurrentUser();
        gameInfoBar.update(move);

        if (currGame.checkForCheckMate()) {
            if (currGame.getCurrentPlayer().getName().equals("Computer")){
                currGame.switchPlayer(); //switch ca sa pot accesa punctele playerului om temporar.
                currUser.setPoints(currUser.getPoints() + 300 + currGame.getCurrentPlayer().getPoints());
                currGame.switchPlayer();
            }
            else{
                currUser.setPoints(currUser.getPoints() + currGame.getCurrentPlayer().getPoints() - 300);
            }
            this.showPanel("GAME_END");
        }

        this.revalidate();
        this.repaint();
    }
    public void onPlayerSwitch(){
        Main.getInstance().getCurrentGame().switchPlayer();
    }
}
