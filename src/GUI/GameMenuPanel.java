package GUI;

import core.Main;

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

        this.setBackground(new Color(40, 58, 55));

        newGameButton.setBackground(new Color(160, 100, 70));
        newGameButton.setForeground(Color.WHITE);
        newGameButton.addActionListener(this);

        loadGameButton.setBackground(new Color(160, 100, 70));
        loadGameButton.setForeground(Color.WHITE);
        loadGameButton.addActionListener(this);

        exitButton.setBackground(new Color(160, 100, 70));
        exitButton.setForeground(Color.WHITE);
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
            parent.showPanel("LOAD_GAME");
        }
        if (e.getSource().equals(exitButton)){
            Main.getInstance().write();
            System.exit(0);
        }
    }
}
