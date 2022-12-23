package Adders;

import SQL.SQLDatabaseConnection;
import Tables.ServicesTable;
import Tables.WorkersTable;
import Validations.Validation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//https://tproger.ru/articles/java-regex-ispolzovanie-reguljarnyh-vyrazhenij-na-praktike/
//String number = number.replaceAll("^[^0-9\\+]|(?<=.)[^0-9]+", "");  String regex = "^[A-Za-z0-9+_.-]+@(.+)$";



public class AddWorkerMenu extends JFrame {
    JLabel label1 = new JLabel("Имя:");
    JLabel label2 = new JLabel("Фамилия:");
    JLabel label3 = new JLabel("Отчество:");
    JLabel label4 = new JLabel("Квалификация:");

    public AddWorkerMenu()
    {
        super("Добавление");
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField fname = new JTextField(15);
        JTextField lname = new JTextField(15);
        JTextField patronymic = new JTextField(15);
        JTextField qualification = new JTextField(15);

        JButton add = new JButton("Добавить");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(fname, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(lname, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(label3, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(patronymic, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(label4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(qualification, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(add, gbc);



        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String FName = fname.getText();

                if (Validation.isLetters(FName)==true) {
                    String LName = lname.getText();
                    String Qualification = qualification.getText();
                    if ((Validation.isLetters(LName) == true)&&(Validation.isLetters(Qualification))) {
                        String Patronymic = patronymic.getText();
                        if (Validation.isLetters(Patronymic)) {

                            String query = "Insert into Workers (FName, LName, Patronymic, Qualification) values ('"+FName+"','"+LName+"','"+Patronymic+"','"+Qualification+"')";
                            SQLDatabaseConnection.insert_row(query);
                            setVisible(false);
                            WorkersTable.refresh();
                            JOptionPane.showMessageDialog(null,"Объект добавлен!","Готово!",JOptionPane.INFORMATION_MESSAGE);
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



