package client.gui.LoginFrame;

import client.controller.AccountController;
import client.gui.EventFrame.EventFrame;
import client.gui.RegisterFrame.RegisterFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registerButton;
    private JButton loginButton;

    public LoginFrame() {
        setContentPane(panel1);
        setTitle("Login");
        setSize(300,150);
        setLocationRelativeTo(null);
        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterFrame();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!AccountController.getInstance().validateUser(textField1.getText(), new String(passwordField1.getPassword()))) {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel,
                            "User sau parola incorecte",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                    textField1.setText("");
                    passwordField1.setText("");
                } else {
                    dispose();
                    var contDto = AccountController.getInstance().getContByUsername(textField1.getText());
                    new EventFrame(contDto);
                }
            }
        });
    }
}
