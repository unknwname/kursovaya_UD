package Tables;


import Adders.AddCustomerMenu;
import Objects.Customer;
import SQL.SQLDatabaseConnection;
import Validations.Validation;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomersTable extends JFrame {
    public static DefaultTableModel tableModel;
    private JTable table1;

    private JTextField jtfFilter = new JTextField();

    private static String[] columnsHeader = new String[]{"id", "Имя", "Фамилия","Отчество", "Номер ТФ"};

    public static void addRow(String FName, String LName,String Patronymic, String Phone_num) {
        int idx = SQLDatabaseConnection.max_id("ID", "Customers")+1;
        tableModel.addRow(new Object[]{Integer.toString(idx), FName, LName,Patronymic, Phone_num});
        Customer customer = new Customer(idx, FName, LName,Patronymic, Phone_num);
        SQLDatabaseConnection.customersList.add(customer);
    }

    public static void refresh() {
        SQLDatabaseConnection.customersList.clear();
        tableModel.setRowCount(0);
        fill();
    }

    public CustomersTable(String isAdmin) {
        super("Клиенты");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnsHeader);

        fill();

        this.table1 = new JTable(tableModel);

        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(table1);
        contents.add(new JScrollPane(table1));
        getContentPane().add(contents, "Center");

        JButton add = new JButton("Добавить");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddCustomerMenu();
            }
        });

        JButton save = new JButton("Сохранить строку");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        JButton remove = new JButton("Удалить");
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (table1.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Выберите строку!", "Внимание!", JOptionPane.WARNING_MESSAGE);
                } else {
                    int idx = table1.getSelectedRow();
                    String t_id = table1.getValueAt(idx, 0).toString();
                    int id = Integer.parseInt(t_id);

                    SQLDatabaseConnection.drop_row(id, "Customers", "ID");
                    tableModel.removeRow(idx);
                    SQLDatabaseConnection.remove_from_list(id, 3);
                    refresh();
                }
            }
        });

        JButton refresh = new JButton("Обновить");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        JPanel buttons = new JPanel();
        if (isAdmin == "1") {
            buttons.add(add);
            buttons.add(remove);
            buttons.add(save);
            buttons.add(refresh);
        }
        getContentPane().add(buttons, "South");

        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(table1.getModel());

        table1.setRowSorter(rowSorter);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Поиск:"), BorderLayout.WEST);
        panel.add(jtfFilter, BorderLayout.CENTER);
        getContentPane().add(panel, "North");

        jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

        });
        setSize(1000, 400);
        setVisible(true);
    }


    private static void fill() {
        SQLDatabaseConnection.customersList.clear();
        tableModel.setRowCount(0);
        SQLDatabaseConnection.get_customers();
        if (SQLDatabaseConnection.customersList.size() != 0) {
            for (Customer customer : SQLDatabaseConnection.customersList) {
                tableModel.addRow(new Object[]{customer.getId(),customer.getFName(),customer.getLName(),customer.getPatronymic(),customer.getPhone_num()});
            }
        }
    }

    private void save() {
        int idx_row = table1.getEditingRow();

        if (table1.isEditing() == false) {
            if (table1.getSelectedRow() != -1) {

                idx_row = table1.getSelectedRow();

                String FName = table1.getValueAt(idx_row, 1).toString();
                String t_id = table1.getValueAt(idx_row,0).toString();
                int id = Integer.parseInt(t_id);


                if ((Validation.hasSpace(FName) == false)&&(Validation.isLetters(FName)==true)) {
                    String SName = table1.getValueAt(idx_row, 2).toString();
                    String Patronymic = table1.getValueAt(idx_row,3).toString();
                    if (((Validation.hasSpace(SName) == false)&&(Validation.isLetters(SName)==true))&&(Validation.hasSpace(Patronymic) == false)&&(Validation.isLetters(Patronymic)==true)) {
                        String Phone_num = table1.getValueAt(idx_row, 4).toString();
                        if (Validation.isPhoneNum(Phone_num)) {
                                    SQLDatabaseConnection.update_table("UPDATE Customers SET FName = '" + FName + "', LName = '" + SName + "', Patronymic = '" + Patronymic + "', Phone_num = " + Phone_num + " WHERE ID = " + id);
                            } else {
                                JOptionPane.showMessageDialog(null, "Проверьте корректность данных!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
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
        }
    }

