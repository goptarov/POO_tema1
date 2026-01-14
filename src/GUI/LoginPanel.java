package GUI;

import game.User;
import core.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JTextField usernameField = new JTextField(15);;
    private JPasswordField passwordField = new JPasswordField(15);
    private JButton loginButton = new JButton("Login");
    private JButton backButton = new JButton("Back");

    public LoginPanel(AppWindow parent) {
        this.parent = parent;

        this.setBackground(new Color(40, 58, 55));

        loginButton.setBackground(new Color(160, 100, 70));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);

        backButton.setBackground(new Color(160, 100, 70));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);

        this.add(usernameField);
        this.add(passwordField);
        this.add(loginButton);
        this.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == loginButton) {
            Main.getInstance().read();
            User u = Main.getInstance().login(usernameField.getText(), new String(passwordField.getPassword()));
            if (u == null) {
                JTextArea incorrect = new JTextArea("Username or password is incorrect.");
                this.add(incorrect);
                this.revalidate();
            } else {
                Main.getInstance().setCurrentUser(u);
                parent.showPanel("GAME_MENU");
            }
        }
        else if (e.getSource() == backButton) {
            parent.showPanel("MAIN");
        }
    }
}
