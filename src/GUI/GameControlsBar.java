package GUI;

import core.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControlsBar extends JPanel implements ActionListener {
    AppWindow parent;

    JButton resign = new JButton("Resign");
    JButton saveExit = new JButton("Save & Exit");
    JButton menu = new JButton("Back to menu");

    public GameControlsBar(AppWindow parent) {
        this.parent = parent;
        this.setBackground(new Color(40, 58, 55));

        this.setLayout(new GridLayout(1, 3, 40, 40));

        resign.setBackground(new Color(160, 100, 70));
        resign.setForeground(Color.WHITE);
        resign.addActionListener(this);
        saveExit.setBackground(new Color(160, 100, 70));
        saveExit.setForeground(Color.WHITE);
        saveExit.addActionListener(this);
        menu.setBackground(new Color(160, 100, 70));
        menu.setForeground(Color.WHITE);
        menu.addActionListener(this);

        this.add(resign);
        this.add(saveExit);
        this.add(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == resign){
            //lose 150 points and end game
            Main.getInstance().getCurrentGame().switchPlayer();
            Main.getInstance().getCurrentUser().setPoints(Main.getInstance().getCurrentUser().getPoints() - 150);
            parent.showPanel("GAME_END");
        }
        if (e.getSource() == saveExit){
            //returns to menu with saving the game state
            Main.getInstance().write();
            parent.showPanel("GAME_MENU");
        }
        if (e.getSource() == menu){
            //returns to menu without saving game state.
            Main.getInstance().setCurrentGame(null);
            parent.showPanel("GAME_MENU");
        }
    }
}
