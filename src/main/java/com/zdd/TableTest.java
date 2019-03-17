package com.zdd;

/**
 * @author zdd
 * @date 2018-12-04 12:59
 */
import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * JTable的例子
 */
public class TableTest {

    private JFrame frame = null;

    private JTable table = null;

    private Table_Model model = null;

    private JScrollPane s_pan = null;

    public TableTest() {







        frame = new JFrame("TableTest");
        model = new Table_Model(20);
        table = new JTable(model);
        String[] age = { "开机", "关机", "---"};
        JComboBox com = new JComboBox(age);
        JButton btn = new JButton();
        TableColumnModel tcm = table.getColumnModel();
        //     tcm.getColumn(2).setCellEditor(new DefaultCellEditor(btn));
        tcm.getColumn(2).setCellEditor(new DefaultCellEditor(com)); // 设置某列采用JComboBox组件


        model.addRow("宋江", 1, "开机");
        model.addRow("孙二娘", 0, "---");
        model.addRow("武松", 2, "开机");

        s_pan = new JScrollPane(table);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(s_pan, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(300, 200);
        frame.setVisible(true);

        //model.addRow(2); // 在某处插入一空行

        //table.updateUI(); // 刷新


    }

    public static void main(String args[]) {
        new TableTest();
    }

}

class Table_Model extends AbstractTableModel {
    private static final long serialVersionUID = -3094977414157589758L;

    private Vector content = null;

    private String[] title_name = { "端口", "状态", "操作" };

    public Table_Model() {
        content = new Vector();
    }

    public Table_Model(int count) {
        content = new Vector(count);
    }

    /**
     * 加入一空行
     * @param row 行号
     */
    public void addRow(int row) {
        Vector v = new Vector(3);
        v.add(0, null);
        v.add(1, null);
        v.add(2, null);
        content.add(row, v);
    }

    /**
     * 加入一行内容
     */
    public void addRow(String port, int status, String action) {
        Vector v = new Vector(3);
        v.add(0, port);
        //v.add(1, new Boolean(status)); // JCheckBox是Boolean的默认显示组件，这里仅仅为了看效果，其实用JComboBox显示***更合适
        v.add(1, status);
        v.add(2, action); // 本列在前面已经设置成了JComboBox组件，这里随便输入什么字符串都没关系

        content.add(v);
    }

    public void removeRow(int row) {
        content.remove(row);
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(rowIndex == 2) {
            return false;
        }
        return true;
    }

    public void setValueAt(Object value, int row, int col) {
        ((Vector) content.get(row)).remove(col);
        ((Vector) content.get(row)).add(col, value);
        this.fireTableCellUpdated(row, col);
    }

    public String getColumnName(int col) {
        return title_name[col];
    }

    public int getColumnCount() {
        return title_name.length;
    }

    public int getRowCount() {
        return content.size();
    }

    public Object getValueAt(int row, int col) {
        return ((Vector) content.get(row)).get(col);
    }

    public Class getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    }

}