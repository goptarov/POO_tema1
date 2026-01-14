package GUI;

import core.Main;
import game.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccPanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField =  new JPasswordField(15);
    private JButton createAccountButton = new JButton("Create account");
    private JButton backButton = new JButton("Back");

    public CreateAccPanel(AppWindow parent) {
        this.parent = parent;

        this.setBackground(new Color(40, 58, 55));

        createAccountButton.setBackground(new Color(160, 100, 70));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.addActionListener(this);

        backButton.setBackground(new Color(160, 100, 70));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);

        this.add(usernameField);
        this.add(passwordField);
        this.add(createAccountButton);
        this.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == createAccountButton) {
            Main.getInstance().newAccount(usernameField.getText(), new String(passwordField.getPassword())); //also sets current user
            Main.getInstance().write();
            parent.showPanel("GAME_MENU");
        }
        else if (e.getSource() == backButton) {
            parent.showPanel("MAIN");
        }
    }
}
