package Tables;


import Adders.AddContractMenu;
import Objects.Contract;
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
import java.sql.Date;

public class ContractsTable extends JFrame {
    private static String _id;
    private static String _isAdmin;
    public static DefaultTableModel tableModel;
    private JTable table1;

    private JTextField jtfFilter = new JTextField();

    private static String[] columnsHeader = new String[]{"id", "id рабочего", "id клиента", "id работы", "Дата"};

    public static void addRow(int worker_id, int customer_id, int work_id, Date date) {
        int idx = SQLDatabaseConnection.max_id("id", "Contracts")+1;
        tableModel.addRow(new Object[]{Integer.toString(idx), worker_id, customer_id, work_id, date.toString()});
        Contract contract = new Contract(idx, worker_id, customer_id, work_id, date);
        SQLDatabaseConnection.contractsList.add(contract);
    }

    public static void refresh() {
        SQLDatabaseConnection.contractsList.clear();
        tableModel.setRowCount(0);
        fill();
    }

    public ContractsTable(String isAdmin, String id) {
        super("Договоры");
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        _id = id;
        _isAdmin = isAdmin;

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
                new AddContractMenu(_isAdmin, _id);
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

                    SQLDatabaseConnection.drop_row(id, "Contracts", "id");
                    tableModel.removeRow(idx);
                    SQLDatabaseConnection.remove_from_list(id, 5);
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
        buttons.add(add);
        if (isAdmin == "1") {
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
        SQLDatabaseConnection.contractsList.clear();
        tableModel.setRowCount(0);
        SQLDatabaseConnection.get_contracts();
        if(_isAdmin=="0")
        {
            if (SQLDatabaseConnection.contractsList.size()!=0) {
                for (Contract contract : SQLDatabaseConnection.contractsList) {
                    if(contract.getCustomer_id()==Integer.parseInt(_id)) {
                        Object[] temp = new Object[]{contract.getId(),contract.getWorker_id(), contract.getCustomer_id(), contract.getService_id(), contract.getC_date()};
                        tableModel.addRow(temp);
                    }
                }
            }
        }
        else {
            if (SQLDatabaseConnection.contractsList.size()!=0) {
                for (Contract contract : SQLDatabaseConnection.contractsList) {
                        Object[] temp = new Object[]{contract.getId(),contract.getWorker_id(), contract.getCustomer_id(), contract.getService_id(), contract.getC_date()};
                        tableModel.addRow(temp);
                }
            }
        }
    }

    private void save() {
        int idx_row = table1.getEditingRow();

        if (table1.isEditing() == false) {
            if (table1.getSelectedRow() != -1) {

                idx_row = table1.getSelectedRow();

                String worker_id = table1.getValueAt(idx_row, 1).toString();
                String t_id = table1.getValueAt(idx_row,0).toString();
                int id = Integer.parseInt(t_id);

                if ((Validation.isNum(worker_id) == true)&&(Validation.isWorker(worker_id)==true)) {
                    String customer_id = table1.getValueAt(idx_row, 2).toString();
                    if ((Validation.isNum(customer_id) == true)&&(Validation.isCustomer(customer_id)==true)) {
                        String service_id = table1.getValueAt(idx_row, 3).toString();
                        if ((Validation.isNum(service_id)==true)&&(Validation.isService(service_id)==true)) {
                            String date = table1.getValueAt(idx_row, 4).toString();
                            if (Validation.isDate(date) == true) {
                                SQLDatabaseConnection.update_table("UPDATE Contracts SET Worker_id = " + worker_id + ", Customer_id = " + customer_id + ", Service_id = " + service_id + ", C_date = '" + date + "' WHERE ID = " + id);
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
}

