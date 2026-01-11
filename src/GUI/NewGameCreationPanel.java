package GUI;

import board.Board;
import board.Colors;
import core.Main;
import game.Game;
import game.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameCreationPanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JTextField playerName = new JTextField(20);
    private JComboBox<Colors> humanColor = new JComboBox<>();
    private JButton startGame = new JButton("Start Game");

    public NewGameCreationPanel(AppWindow parent) {
        this.parent = parent;

        startGame.addActionListener(this);

        humanColor.addItem(Colors.WHITE);
        humanColor.addItem(Colors.BLACK);

        this.add(playerName);
        this.add(humanColor);
        this.add(startGame);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int newGameID = Main.getInstance().getGames().size() + 1;

        Player human = new Player(playerName.getText(), (Colors) humanColor.getSelectedItem());
        Colors computerColor = (humanColor.getSelectedItem() == Colors.WHITE) ? Colors.BLACK : Colors.WHITE;
        Player computer = new Player("Computer", computerColor);

        Game newGame = new Game(newGameID, human, computer, new Board());

        if (human.getColor() == Colors.BLACK)
            newGame = new Game(newGameID, computer, human, new Board());

        Main.getInstance().getCurrentUser().addGame(newGame);
        Main.getInstance().addGame(newGame);
        Main.getInstance().setCurrentGame(newGame);
        Main.getInstance().write();
        parent.showPanel("GAME");
    }
}
