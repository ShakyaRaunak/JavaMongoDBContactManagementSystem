/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javamongodb.application;

import com.javamongodb.utils.DatabaseUtils;
import com.javamongodb.utils.LayoutUtils;
import com.javamongodb.utils.MessageUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Raunak Shakya
 */
public class ShowAllRecords extends JFrame implements ActionListener, TableModelListener {

    public final ResourceBundle messages = MessageUtils.MESSAGES;

    private JScrollPane jScrollPane;
    private Container container;
    private MyTableModel myTableModel;
    private final JTable jtable;
    private JButton editButton, deleteButton;
    private JPanel buttonsPanel, tablePanel;
    private static int editToggle = 0;
    private static boolean isEditable;

    public ShowAllRecords() {
        super("All the Contact Informations");

        container = getContentPane();
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        editButton = new JButton("   " + messages.getString("enable.edit.records") + "  ");
        deleteButton = new JButton("  " + messages.getString("delete.records") + "  ");
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(editButton);

        tablePanel = new JPanel();
        tablePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        myTableModel = new MyTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return isEditable;
            }
        };
        addColumnsToTable(myTableModel);
        jtable = new JTable(myTableModel);
        setColumnWidths(jtable);
        jtable.setPreferredScrollableViewportSize(new Dimension(980, 500));
        jtable.setFillsViewportHeight(true);
        jtable.getTableHeader().setReorderingAllowed(false);
        jtable.setAutoCreateRowSorter(true);
        getRecordsOnTable();
        tablePanel.add(jScrollPane);

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(buttonsPanel);
        container.add(tablePanel);

        setSize(LayoutUtils.WINDOW_WIDTH, LayoutUtils.WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void getRecordsOnTable() {
        try {
            DBCollection collection = DatabaseUtils.openDBConnection();
            DBCursor cursor = collection.find();
            while (cursor.hasNext()) {
                DBObject doc = cursor.next();
                Object[] data = new Object[]{doc.get("FirstName"), doc.get("MiddleName"), 
                    doc.get("LastName"), doc.get("Gender"), doc.get("City"), doc.get("Street"), 
                    doc.get("BlockNumber"), doc.get("Country"), doc.get("EmailAddress"), 
                    doc.get("MobileNumber"), doc.get("HomeContact")};
                myTableModel.addRow(data);
            }
            DatabaseUtils.closeDBConnection();
            jtable.getModel().addTableModelListener(this);
            jScrollPane = new JScrollPane(jtable);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }
    }

    private void addColumnsToTable(MyTableModel myTableModel) {
        myTableModel.addColumn("First Name");
        myTableModel.addColumn("Middle Name");
        myTableModel.addColumn("Last Name");
        myTableModel.addColumn("Gender");
        myTableModel.addColumn("City");
        myTableModel.addColumn("Street");
        myTableModel.addColumn("BlockNo.");
        myTableModel.addColumn("Country");
        myTableModel.addColumn("Email");
        myTableModel.addColumn("MobileNo.");
        myTableModel.addColumn("TelNo.");
    }

    private void setColumnWidths(JTable jtable) {
        jtable.getColumnModel().getColumn(0).setPreferredWidth(90);
        jtable.getColumnModel().getColumn(1).setPreferredWidth(100);
        jtable.getColumnModel().getColumn(2).setPreferredWidth(90);
        jtable.getColumnModel().getColumn(3).setPreferredWidth(60);
        jtable.getColumnModel().getColumn(4).setPreferredWidth(90);
        jtable.getColumnModel().getColumn(5).setPreferredWidth(90);
        jtable.getColumnModel().getColumn(6).setPreferredWidth(80);
        jtable.getColumnModel().getColumn(7).setPreferredWidth(80);
        jtable.getColumnModel().getColumn(8).setPreferredWidth(150);
        jtable.getColumnModel().getColumn(9).setPreferredWidth(90);
        jtable.getColumnModel().getColumn(10).setPreferredWidth(80);
    }

    public void removeSelectedRows(JTable table) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) this.jtable.getModel();
        int[] rows = table.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            defaultTableModel.removeRow(rows[i] - i);
        }
    }

    private void update(String columnName, String newValue, String mobileNumber) {
        try {
            DBCollection collection = DatabaseUtils.openDBConnection();
            BasicDBObject query = new BasicDBObject("MobileNumber", new BasicDBObject("$regex", mobileNumber));
            DBCursor cursor = collection.find(query);
            DBObject doc = cursor.next();
            Object id = doc.get("_id");
            Object prevData = doc.get(columnName);
            if (!(newValue.equals(prevData.toString()))) {
                BasicDBObject updateDocument = new BasicDBObject();
                updateDocument.append("$set", new BasicDBObject().append(columnName, newValue));
                BasicDBObject searchQuery = new BasicDBObject().append("_id", id);
                int i = JOptionPane.showConfirmDialog(
                        null,
                        messages.getString("question.to.save"),
                        messages.getString("title.save.confirm"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
                );
                if (i == JOptionPane.YES_OPTION) {
                    collection.update(searchQuery, updateDocument); //update the document
                    JOptionPane.showMessageDialog(
                            null,
                            messages.getString("notification.database.updated"),
                            messages.getString("title.update.confirm"),
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
            DatabaseUtils.closeDBConnection();
        } catch (Exception exception) {
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(LayoutUtils.JTATTOO_APPLICATION_THEME);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JAVAMongoDBApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        ShowAllRecords showAllRecords = new ShowAllRecords();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == editButton) {
            if (editToggle == 0) {
                editButton.setText("   " + messages.getString("disable.edit.records") + "  ");
                isEditable = true;
                editToggle = 1;
            } else if (editToggle == 1) {
                editButton.setText("   " + messages.getString("enable.edit.records") + "   ");
                isEditable = false;
                editToggle = 0;
            }
        } else if (ae.getSource() == deleteButton) {
            if (jtable.getSelectedRows().length > 0) {
                int i = JOptionPane.showConfirmDialog(
                        null,
                        messages.getString("question.to.delete"),
                        messages.getString("title.delete.confirm"),
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
                );
                if (i == JOptionPane.YES_OPTION) {
                    int j[] = jtable.getSelectedRows();
                    try {
                        DBCollection collection = DatabaseUtils.openDBConnection();
                        for (int count = 0; count < j.length; count++) {
                            String mobileno = (String) jtable.getValueAt(j[count], 9);
                            collection.remove(new BasicDBObject().append("MobileNumber", mobileno)); //remove the document
                        }
                        removeSelectedRows(jtable);
                    } catch (Exception e) {
                        //System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "First Select the rows to delete...", "Delete Failure", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    @Override
    public void tableChanged(TableModelEvent tme) {
        int row = tme.getFirstRow();
        int column = tme.getColumn();
        String changedColName = null;
        switch (column) {
            case 0:
                changedColName = "FirstName";
                break;
            case 1:
                changedColName = "MiddleName";
                break;
            case 2:
                changedColName = "LastName";
                break;
            case 3:
                changedColName = "Gender";
                break;
            case 4:
                changedColName = "City";
                break;
            case 5:
                changedColName = "Street";
                break;
            case 6:
                changedColName = "BlockNumber";
                break;
            case 7:
                changedColName = "Country";
                break;
            case 8:
                changedColName = "EmailAddress";
                break;
            case 9:
                changedColName = "MobileNumber";
                break;
            case 10:
                changedColName = "HomeContact";
                break;
            default:
                throw new RuntimeException("Column could not be updated.");
        }

        myTableModel = (MyTableModel) tme.getSource();
        //String changedColName = myTableModel.getColumnName(col);
        String newValue = myTableModel.getValueAt(row, column).toString();
        String mobileNumber = myTableModel.getValueAt(row, 9).toString();
        update(changedColName, newValue, mobileNumber);
    }

}
