package GUI;

import core.Main;
import game.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccPanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JTextField usernameField = new JTextField(15);
    private JPasswordField passwordField =  new JPasswordField(15);
    private JButton createAccountButton = new JButton("Create account");

    public CreateAccPanel(AppWindow parent) {
        this.parent = parent;

        createAccountButton.addActionListener(this);

        this.add(usernameField);
        this.add(passwordField);
        this.add(createAccountButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Main.getInstance().newAccount(usernameField.getText(), new String(passwordField.getPassword())); //also sets current user
        Main.getInstance().write();
        parent.showPanel("GAME");
    }
}
