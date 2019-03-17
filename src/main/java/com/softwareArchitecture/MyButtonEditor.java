package com.softwareArchitecture;

/**
 * @author zdd
 * @date 2018-12-04 14:21
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class MyButtonEditor extends AbstractCellEditor implements
        TableCellEditor {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6546334664166791132L;

    private MainWindow mainWindow;

    private JPanel panel;

    private JButton button;

    private int s_id;

    private int status = 0;

    private int row = -1;

    public MyButtonEditor() {
    }

    public MyButtonEditor(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        initButton();

        initPanel();

        panel.add(this.button, BorderLayout.CENTER);
    }

    private void initButton() {
        button = new JButton();

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*int res = JOptionPane.showConfirmDialog(null,
                        "Do you want to add 1 to it?", "choose one",
                        JOptionPane.YES_NO_OPTION);*/

                //if(res ==  JOptionPane.YES_OPTION){
                // mainWindow.getTable().updateUI();
                Object[][] data = mainWindow.getData();
                data[row][1] = status == 1 ? 2 : 1;
                //更新数据库
                mainWindow.getChatServer().onChange((Integer) data[row][0],(Integer)data[row][1]);


                //刷新
                mainWindow.getTableModel().fireTableDataChanged();
                //}
                //stopped!!!!
                fireEditingStopped();

            }
        });

    }

    private void initPanel() {
        panel = new JPanel();

        panel.setLayout(new BorderLayout());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        s_id = (Integer) value;

        this.row = row;
        status = (Integer) table.getValueAt(row, 1);

        switch (status) {
            case 0: {
                button.setText("-----");
                button.setEnabled(false);
                break;
            }
            case 1: {
                button.setEnabled(true);
                button.setText("关机");
                break;
            }
            case 2: {
                button.setEnabled(true);
                button.setText("开机");
                break;
            }
            default:
                break;
        }


        //    button.setText(value == null ? "" : String.valueOf(value));

        return panel;
    }

    public Object getCellEditorValue() {
        return s_id;
    }

}