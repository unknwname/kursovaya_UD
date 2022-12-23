package Adders;

import Tables.CustomersTable;
import Validations.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//https://tproger.ru/articles/java-regex-ispolzovanie-reguljarnyh-vyrazhenij-na-praktike/
//String number = number.replaceAll("^[^0-9\\+]|(?<=.)[^0-9]+", "");  String regex = "^[A-Za-z0-9+_.-]+@(.+)$";



public class AddCustomerMenu extends JFrame {

    // private Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(строка);
    JLabel label2 = new JLabel("Имя:");
    JLabel label3 = new JLabel("Фамилия:");
    JLabel label4 = new JLabel("Отчество:");
    JLabel label5 = new JLabel("Номер ТФ:");

    public AddCustomerMenu()
    {
        super("Добавление");
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField FName = new JTextField(15);
        JTextField SName = new JTextField(15);
        JTextField Patronymic = new JTextField(15);
        JTextField Phone_num = new JTextField(15);

        JButton add = new JButton("Добавить");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(FName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(SName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(Patronymic, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(label5, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(Phone_num, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(add, gbc);



        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String t_FName = FName.getText();

                if ((Validation.hasSpace(t_FName) == false) && (Validation.isLetters(t_FName) == true)) {
                    String t_SName = SName.getText();
                    String t_Patronymic = Patronymic.getText();
                    if (((Validation.hasSpace(t_SName) == false) && (Validation.isLetters(t_SName) == true))&&((Validation.hasSpace(t_Patronymic) == false) && (Validation.isLetters(t_Patronymic) == true))) {
                        String t_Phone_num = Phone_num.getText();
                        if (Validation.isPhoneNum(t_Phone_num)) {
                            String query = "Insert into Customers (FName, LName, Patronymic, Phone_num) values ('" + t_FName + "','" + t_SName + "','" + t_Patronymic + "','"+t_Phone_num+"')";
                            SQL.SQLDatabaseConnection.insert_row(query);
                            setVisible(false);
                            CustomersTable.refresh();
                            JOptionPane.showMessageDialog(null, "Объект добавлен!", "Готово!", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Проверьте корректность данных!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Проверьте корректность данных!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Проверьте корректность данных!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(panel);
        setVisible(true);
    }
}



