package GUI;

import game.Game;
import game.Move;
import game.Player;
import pieces.Piece;

import javax.swing.*;
import java.awt.*;

import core.Main;


public class AppWindow extends JFrame implements GameObserver {
    private MainPanel mainPanel = new MainPanel(this);
    private LoginPanel loginPanel = new LoginPanel(this);
    private GameMenuPanel gameMenuPanel = new GameMenuPanel(this);
    private NewGameCreationPanel newGameCreationPanel = new NewGameCreationPanel(this);
    private LoadGamePanel loadGamePanel;
    private CreateAccPanel createAccPanel = new CreateAccPanel(this);
    private GamePanel gamePanel = new GamePanel(this);

    public AppWindow(){
        super("Chess game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 300));
        getContentPane().setBackground(Color.darkGray);
        //setLayout(new SpringLayout());
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    public void showPanel(String newPanel) {
        this.getContentPane().removeAll();
        switch (newPanel){
            case "MAIN": this.getContentPane().add(new MainPanel(this)); break;

            case "LOGIN": this.getContentPane().add(new LoginPanel(this)); break;

            case "CREATE_ACCOUNT":
                //create new account, add it to Main's users list
                this.getContentPane().add(new CreateAccPanel(this));
                break;

            case "GAME_MENU":
                this.getContentPane().add(new  GameMenuPanel(this)); break;

            case "NEW_GAME":
                this.getContentPane().add(new NewGameCreationPanel(this));
                //create new game, add it to Main's games list
                break;

            case "LOAD_GAME":
                this.getContentPane().add(new LoadGamePanel(this));
                break;

        }
        this.revalidate();
        this.repaint();
    }
    public void showPanel(String newPanel, int x){} //is this needed?

    void onMovePiece(Move move){}
    void onPieceCaptured(Piece piece){}
    void onPlayerSwitch(Player currentPlayer){}
}
