package GUI;

import core.Main;
import game.Game;
import game.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoadGamePanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JLabel gameTitle;

    public LoadGamePanel(AppWindow parent) {
        this.parent = parent;

        this.setBackground(new Color(40, 58, 55));
        Main.getInstance().read();
        if (Main.getInstance().getCurrentUser().getActiveGames() == null){
            JTextField noGames = new JTextField("You have no active games.");
            noGames.setEditable(false);
            this.add(noGames);
        }
        for (Game g : Main.getInstance().getCurrentUser().getActiveGames()) {
            System.out.println("Game " + g.getId());
            JButton game = new JButton("Game " + g.getId());
            game.setBackground(new Color(160, 100, 70));
            game.setForeground(Color.WHITE);
            game.setActionCommand(String.valueOf(g.getId()));
            game.addActionListener(this);
            this.add(game);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int loadGameId = Integer.parseInt(e.getActionCommand());

        Game selectedGame = null;
        for (Game g : Main.getInstance().getCurrentUser().getActiveGames()) {
            if (g.getId() == loadGameId)
                selectedGame = g;
        }
        Main.getInstance().setCurrentGame(selectedGame);
        this.parent.showPanel("GAME");
    }
}
