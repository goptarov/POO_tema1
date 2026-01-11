package GUI;

import game.User;
import core.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JTextField usernameField = new JTextField(15);;
    private JPasswordField passwordField = new JPasswordField(15);
    private JButton loginButton = new JButton("Login");

    public LoginPanel(AppWindow parent) {
        this.parent = parent;

        loginButton.addActionListener(this);

        this.add(usernameField);
        this.add(passwordField);
        this.add(loginButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Main.getInstance().read();
        User u = Main.getInstance().login(usernameField.getText(), new String(passwordField.getPassword()));
        if (u == null){
            JTextArea incorrect = new JTextArea("Username or password is incorrect.");
            this.add(incorrect);
        }
        else{
            Main.getInstance().setCurrentUser(u);
            parent.showPanel("GAME_MENU");
        }
    }
}
