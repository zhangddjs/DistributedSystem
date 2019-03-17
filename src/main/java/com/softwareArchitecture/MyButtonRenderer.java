package com.softwareArchitecture;

/**
 * @author zdd
 * @date 2018-12-04 14:21
 */
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyButtonRenderer implements TableCellRenderer {
    private JPanel panel;

    private JButton button;

    private int s_id;

    private int status = 0;

    public MyButtonRenderer() {
        initButton();

        initPanel();

        panel.add(button, BorderLayout.CENTER);
    }

    private void initButton() {
        button = new JButton();

    }

    private void initPanel() {
        panel = new JPanel();

        panel.setLayout(new BorderLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        s_id = (Integer) value;

        status = (Integer)table.getValueAt(row, 1);
        switch (status)
        {
            case 0:
            {
                button.setText("-----");
                button.setEnabled(false);
                break;
            }
            case 1:
            {
                button.setText("关机");
                button.setEnabled(true);
                break;
            }
            case 2:
            {
                button.setText("开机");
                button.setEnabled(true);
                break;
            }
            default:break;
        }
     //   button.setText(value == null ? "" : String.valueOf(value));

        return panel;
    }

}