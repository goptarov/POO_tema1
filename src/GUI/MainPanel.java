package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    private AppWindow parent;
    private JTextArea textArea = new JTextArea("Welcome to the game!");
    private JButton loginButton = new JButton("Login");
    private JButton createAccountButton = new JButton("Create account");
    private JButton exitButton = new JButton("Exit");

    public MainPanel(AppWindow parent){
        this.parent = parent;

        loginButton.setBackground(Color.GRAY);
        loginButton.addActionListener(this);

        createAccountButton.setBackground(Color.GRAY);
        createAccountButton.addActionListener(this);

        exitButton.setBackground(Color.GRAY);
        exitButton.addActionListener(this);

        textArea.setEditable(false);
        this.add(textArea);
        this.add(loginButton);
        this.add(createAccountButton);
        this.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton)e.getSource();
        if (button.getText().equals("Login")){
            parent.showPanel("LOGIN");
        }
        if (button.getText().equals("Create account")){
            parent.showPanel("CREATE_ACCOUNT");
        }
        if (button.getText().equals("Exit")){
            System.exit(0);
        }
    }
}
