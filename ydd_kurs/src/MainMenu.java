import SQL.SQLDatabaseConnection;
import Tables.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame
{
    JButton oneTButton = new JButton("Договоры");
    JButton twoTButton = new JButton("Работы");
    JButton threeTButton = new JButton("Клиенты");
    JButton fourTButton = new JButton("Рабочие");
    public MainMenu(String isAdmin, String id) {
        super("Главное меню");
        setSize(320,320);
        setLocation(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        oneTButton.setPreferredSize(new Dimension(160,120));
        twoTButton.setPreferredSize(new Dimension(160,120));
        threeTButton.setPreferredSize(new Dimension(160,120));
        fourTButton.setPreferredSize(new Dimension(160,120));

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(oneTButton, gbc);

        if(isAdmin=="1")
        {
            gbc.gridx = 1;
            gbc.gridy = 0;
            panel.add(twoTButton, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(threeTButton, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            panel.add(fourTButton, gbc);
        }

        SQLDatabaseConnection.getWorkers();
        SQLDatabaseConnection.get_customers();
        SQLDatabaseConnection.get_services();

        oneTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ContractsTable(isAdmin, id);
            }
        });

        twoTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ServicesTable(isAdmin);
            }
        });

        threeTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomersTable(isAdmin);
            }
        });

        fourTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WorkersTable(isAdmin);
            }
        });

        add(panel);

        setVisible(true);
    }
}
