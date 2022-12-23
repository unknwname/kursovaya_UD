import SQL.SQLDatabaseConnection;

import javax.swing.*;
import Objects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class LoginMenu extends JFrame {

    JTextField usernameField = new JTextField(15);
    JPasswordField passwordField = new JPasswordField(15);

    private void checker(String username, String password)
    {
        SQLDatabaseConnection.usersRefresh();
        int checker = 0;
        for (User user : SQLDatabaseConnection.usersList) {
            System.out.println(username);
            System.out.println(password);

            if ((Objects.equals(user.getUsername(), username))==true && (Objects.equals(user.getPassword(), password))==true) {
                checker = 1;
                setVisible(false);

                if (user.isAdmin() ==true) {
                    new MainMenu("1", user.getUsername());
                }
                else
                {
                    new MainMenu("0", user.getUsername());
                }
            }
        }
        if(checker==0)
        {
            JOptionPane.showMessageDialog(null,"Неверные данные!", "Ошибка!", JOptionPane.WARNING_MESSAGE);
        }
    }
    public LoginMenu(){
        super("Login");

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setLocation(700, 200);
        setSize(500,180);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel label1 = new JLabel("Логин:");
        JLabel label2 = new JLabel("Пароль:");

        usernameField.setToolTipText("Логин");
        passwordField.setToolTipText("Пароль");
        passwordField.setEchoChar('*');

        JPanel grid = new JPanel(new GridLayout(3, 2, 5, 0) );
        JButton button = new JButton("Войти");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                checker(username,password);
            }
        });

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(button, gbc);

        add(panel);

        setVisible(true);
    }
}
