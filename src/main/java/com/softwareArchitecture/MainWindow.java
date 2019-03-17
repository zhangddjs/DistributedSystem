package com.softwareArchitecture;

/**
 * @author zdd
 * @date 2018-12-04 14:22
 */

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainWindow {

    private JFrame frame;
    private JTable table;
    private int dataRow = 0;
    private DefaultTableModel tableModel;
    private ChatServer chatServer = new ChatServer(this);

    private Object[][] data = new Object[500][3];

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public int getDataRow() {
        return dataRow;
    }

    public void setDataRow(int dataRow) {
        this.dataRow = dataRow;
    }

    public Object[][] getData() {
        return data;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public ChatServer getChatServer() {
        return chatServer;
    }

    public void setChatServer(ChatServer chatServer) {
        this.chatServer = chatServer;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.frame.setVisible(true);
        window.chatServer.start();
    }

    @Test
    public void initData() throws SQLException {
        DBTool dbTool = new DBTool();
        ResultSet rs = dbTool.query();

        while (rs.next()) {
//            data[dataRow] = new Object[]{rs.getInt(1), rs.getInt(2), rs.getInt(3)};
            data[dataRow][0] = rs.getInt(2);
            data[dataRow][1] = rs.getInt(3);
            //if
            data[dataRow][2] = rs.getInt(1);
            dataRow++;
        }
        dbTool.close();
    }

    /**
     * 修改dataRow
     * @return Object[][]
     * @throws SQLException
     */
    public void reInitData() throws SQLException {
        DBTool dbTool = new DBTool();
        ResultSet rs = dbTool.query();
        Object[][] data = new Object[500][3];
        int row = 0;
        while (rs.next()) {
            data[row][1] = rs.getInt(3);
            data[row][0] = rs.getInt(2);
            //if
            data[row][2] = rs.getInt(1);
            row++;
        }
        dataRow = row;
        this.data = data;
        dbTool.close();
    }


    public MainWindow() {
        try {
            initData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(10, 10, 414, 242);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 394, 222);
        panel.add(scrollPane);


        tableModel = new DefaultTableModel() {
            @Override
            public Object getValueAt(int row, int column) {
                return data[row][column];
            }

            @Override
            public int getRowCount() {
                return dataRow;
            }

            @Override
            public int getColumnCount() {
                return 3;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                data[row][column] = aValue;
                fireTableCellUpdated(row, column);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2) {
                    return true;
                } else {
                    return false;
                }
            }

            public String getColumnName(int col) {
                return new String[]{"端口", "状态", "操作"}[col];
            }
        };
        table = new JTable(tableModel);
        scrollPane.setViewportView(table);

     /*   table.setModel(new DefaultTableModel() {
            @Override
            public Object getValueAt(int row, int column) {
                return data[row][column];
            }

            @Override
            public int getRowCount() {
                return dataRow;
            }

            @Override
            public int getColumnCount() {
                return 3;
            }
            @Override
            public void setValueAt(Object aValue, int row, int column){
                data[row][column] = aValue;
                fireTableCellUpdated(row, column);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2) {
                    return true;
                } else {
                    return false;
                }
            }
        });*/

        table.getColumnModel().getColumn(2).setCellEditor(
                new MyButtonEditor(this));

        table.getColumnModel().getColumn(2).setCellRenderer(
                new MyButtonRenderer());

        table.setRowSelectionAllowed(false);
    }

}
