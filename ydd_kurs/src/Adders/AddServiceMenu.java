package Adders;

import Tables.ServicesTable;
import Validations.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//https://tproger.ru/articles/java-regex-ispolzovanie-reguljarnyh-vyrazhenij-na-praktike/
//String number = number.replaceAll("^[^0-9\\+]|(?<=.)[^0-9]+", "");  String regex = "^[A-Za-z0-9+_.-]+@(.+)$";



public class AddServiceMenu extends JFrame {

    // private Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(строка);
    JLabel label2 = new JLabel("Наименование:");
    JLabel label3 = new JLabel("Цена:");
    public AddServiceMenu()
    {
        super("Добавление");
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField name = new JTextField(15);
        JTextField Price = new JTextField(15);

        JButton add = new JButton("Добавить");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(name, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(Price, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(add, gbc);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String t_name = name.getText();
                String t_price = Price.getText();
                if(Validation.isNum(t_price)==true)
                {
                    String query = ("Insert into Services (S_name, Price) values ('"+t_name+"',"+Integer.parseInt(t_price)+")");
                    SQL.SQLDatabaseConnection.insert_row(query);
                    setVisible(false);
                    ServicesTable.refresh();
                    JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Проверьте корректность данных!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(panel);
        setVisible(true);
    }
}



