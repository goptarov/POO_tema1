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

        this.setBackground(new Color(40, 58, 55));

        loginButton.setBackground(new Color(160, 100, 70));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);

        createAccountButton.setBackground(new Color(160, 100, 70));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.addActionListener(this);

        exitButton.setBackground(new Color(160, 100, 70));
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);

        textArea.setFont(new Font("Arial", Font.BOLD, 20));
        textArea.setBackground(new Color(40, 58, 55));
        textArea.setForeground(Color.WHITE);
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
