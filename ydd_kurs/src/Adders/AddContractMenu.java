package Adders;

import SQL.SQLDatabaseConnection;
import Tables.ContractsTable;
import Validations.Validation;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//https://tproger.ru/articles/java-regex-ispolzovanie-reguljarnyh-vyrazhenij-na-praktike/
//String number = number.replaceAll("^[^0-9\\+]|(?<=.)[^0-9]+", "");  String regex = "^[A-Za-z0-9+_.-]+@(.+)$";



public class AddContractMenu extends JFrame {
    private static String _id;
    private static String _isAdmin;

    // private Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(строка);
    JLabel label2 = new JLabel("id рабочего:");
    JLabel label3 = new JLabel("id клиента:");
    JLabel label4 = new JLabel("id работы:");
    JLabel label5 = new JLabel("Дата:");

    public AddContractMenu(String isAdmin, String id )
    {
        super("Добавление");
        _id = id;
        _isAdmin = isAdmin;
        setSize(500,200);
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField worker_id = new JTextField(10);
        JTextField customer_id = new JTextField(10);
        JTextField work_id = new JTextField(10);

        DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter = new DateFormatter(date);
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);

        JFormattedTextField ftfDate = new JFormattedTextField(dateFormatter);
        ftfDate.setColumns(10);
        ftfDate.setValue(new Date());

        JButton add = new JButton("Добавить");

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        System.out.println(_isAdmin);
        if (_isAdmin=="0")
        {
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(label2, gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            panel.add(worker_id, gbc);

            JLabel label10= new JLabel("id клиента:");
            JLabel label11 = new JLabel("        "+_id);

            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(label10, gbc);
            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(label11, gbc);

            customer_id.setText(_id);

            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(label4, gbc);

            gbc.gridx = 1;
            gbc.gridy = 4;
            panel.add(work_id, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            panel.add(label5, gbc);

            gbc.gridx = 1;
            gbc.gridy = 5;
            panel.add(ftfDate, gbc);

            gbc.gridx = 1;
            gbc.gridy = 6;
            panel.add(add, gbc);
        }
        else
        {
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(label2, gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            panel.add(worker_id, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(label3, gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            panel.add(customer_id, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(label4, gbc);

            gbc.gridx = 1;
            gbc.gridy = 4;
            panel.add(work_id, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            panel.add(label5, gbc);

            gbc.gridx = 1;
            gbc.gridy = 5;
            panel.add(ftfDate, gbc);

            gbc.gridx = 1;
            gbc.gridy = 6;
            panel.add(add, gbc);
        }

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String t_worker_id = worker_id.getText();

                if ((Validation.isNum(t_worker_id) == true)&&(Validation.isWorker(t_worker_id)==true)) {
                    String t_customer_id = customer_id.getText();
                    if ((Validation.isNum(t_customer_id) == true)&&(Validation.isCustomer(t_customer_id)==true)) {
                        String t_service_id = work_id.getText();
                        if (Validation.isNum(t_service_id)==true)
                         {
                            if(Validation.isServiceContract(t_service_id)==false)
                            {
                                if(Validation.isService(t_service_id))
                                {
                                    String date = ftfDate.getText();
                                    if (Validation.isDate(date) == true) {
                                        int id = SQLDatabaseConnection.max_id("id","Contracts")+1;
                                        String query = "SET IDENTITY_INSERT contracts ON Insert into Contracts (id, Worker_id, Customer_id, Service_id, C_date) values ("+id+","+ t_worker_id + "," + t_customer_id + "," + t_service_id + ", '" + date + "') SET IDENTITY_INSERT contracts Off";
                                        SQL.SQLDatabaseConnection.insert_row(query);
                                        setVisible(false);
                                        ContractsTable.refresh();
                                        JOptionPane.showMessageDialog(null, "Объект добавлен!", "Готово!", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                    else {
                                        JOptionPane.showMessageDialog(null, "Проверьте корректность данных!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                else {
                                    JOptionPane.showMessageDialog(null,"Такой работы нет!","Ошибка!",JOptionPane.ERROR_MESSAGE);
                                    work_id.setText("");
                                }

                            }
                            else {
                                JOptionPane.showMessageDialog(null,"Эта работа уже выполняется!","Ошибка!",JOptionPane.ERROR_MESSAGE);
                                work_id.setText("");
                            }
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



