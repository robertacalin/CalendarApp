package client.gui.RegisterFrame;

import client.controller.AccountController;
import client.gui.LoginFrame.LoginFrame;
import lib.dto.AccountDto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JPanel panel1;
    private JButton cancelButton;
    private JButton registerButton;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;

    AccountController accountController = AccountController.getInstance();

    public RegisterFrame() {
        setContentPane(panel1);
        setTitle("Register");
        setSize(400,200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cancelButton.isEnabled()) {
                    dispose();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accountController.alreadyExists(textField3.getText())) {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel,
                            "Username-ul exista deja!!!",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                    textField3.setText("");
                } else if (!(new String(passwordField1.getPassword()).equals(new String(passwordField2.getPassword())))) {
                    JPanel panel = new JPanel();
                    JOptionPane.showMessageDialog(panel,
                            "Campurile parola si confirma parola trebuie sa fie identice",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                    passwordField1.setText("");
                    passwordField2.setText("");
                } else  {
                    var cont = new AccountDto(
                            textField3.getText(),
                            new String(passwordField1.getPassword())
                    );

                    AccountController.getInstance().persist(cont);
                    dispose();
                    new LoginFrame();
                }
            }
        });
    }
}
