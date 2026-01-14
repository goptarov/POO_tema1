package GUI;

import core.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEndPanel extends JPanel implements ActionListener {
        private AppWindow parent;
        private int totalPoints = Main.getInstance().getCurrentUser().getPoints();
        private JTextArea endMessage = new JTextArea();
        private JTextArea userTotalPointsMessage = new JTextArea("");
        private JTextArea pointsMessage = new JTextArea();
        private JButton backToMenu = new JButton("Back to Menu");

        public GameEndPanel(AppWindow parent) {
            this.parent = parent;

            this.setBackground(new Color(40, 58, 55));

            if (Main.getInstance().getCurrentGame().getCurrentPlayer().getName().equals("Computer")) {
                endMessage.setText("You lost!");
                Main.getInstance().getCurrentGame().switchPlayer();
                pointsMessage.setText("In this game you lost " + Main.getInstance().getCurrentGame().getCurrentPlayer().getPoints() + " points.");
                Main.getInstance().getCurrentGame().switchPlayer();
            }
            else{
                endMessage.setText("You won!");
                pointsMessage.setText("In this game you won " + Main.getInstance().getCurrentGame().getCurrentPlayer().getPoints() + " points.");
            }
            userTotalPointsMessage.setText("Your user total points now: " + totalPoints);

            endMessage.setEditable(false);
            userTotalPointsMessage.setEditable(false);

            backToMenu.setBackground(new Color(160, 100, 70));
            backToMenu.setForeground(Color.WHITE);
            backToMenu.addActionListener(this);

            this.add(endMessage);
            this.add(pointsMessage);
            this.add(userTotalPointsMessage);
            this.add(backToMenu);
        }

        @Override
        public void actionPerformed(ActionEvent e){
            Main.getInstance().getCurrentUser().removeGame(Main.getInstance().getCurrentGame());
            Main.getInstance().removeGame(Main.getInstance().getCurrentGame());
            Main.getInstance().setCurrentGame(null);
            Main.getInstance().write();
            parent.showPanel("GAME_MENU");
        }
}
