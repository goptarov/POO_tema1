package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenuPanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JButton newGameButton = new JButton("New Game");
    private JButton loadGameButton = new JButton("Load Game");
    private JButton exitButton  = new JButton("Exit");
    public GameMenuPanel(AppWindow parent) {
        this.parent = parent;

        newGameButton.setBackground(Color.GRAY);
        newGameButton.addActionListener(this);

        loadGameButton.setBackground(Color.GRAY);
        loadGameButton.addActionListener(this);

        exitButton.setBackground(Color.GRAY);
        exitButton.addActionListener(this);

        this.add(newGameButton);
        this.add(loadGameButton);
        this.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(newGameButton)){
            parent.showPanel("NEW_GAME");
        }
        if (e.getSource().equals(loadGameButton)){
            parent.showPanel("CHOOSE_GAME_LOAD");
        }
        if (e.getSource().equals(exitButton)){
            System.exit(0);
        }
    }
}
