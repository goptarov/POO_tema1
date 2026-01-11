package GUI;

import core.Main;
import game.Game;
import game.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoadGamePanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JLabel gameTitle;

    public LoadGamePanel(AppWindow parent) {
        this.parent = parent;

        Main.getInstance().read();
        for (Game g : Main.getInstance().getCurrentUser().getActiveGames()) {
            JButton game = new JButton("Game " + g.getId());
            game.addActionListener(this);
            this.add(game);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        //Main.getInstance().getCurrentUser().
        this.parent.showPanel("GAME");
    }
}
