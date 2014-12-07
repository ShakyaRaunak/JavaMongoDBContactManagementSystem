/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rkshakyaprojects.javamongodbapp;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
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
public class ContactRecordsView extends JFrame implements ActionListener, TableModelListener {

    private JScrollPane pane;
    private Container cnt;
    private MyTableModel model;
    private JTable jtable;
    private JButton editBtn, deleteBtn;
    private JPanel btnPanel, tablePanel;
    private static int onoffedit = 0;
    private static boolean editable;

    public ContactRecordsView() {
        super("All the Contact Informations");

        cnt = getContentPane();
        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        editBtn = new JButton("   Enable Edit Records  ");
        deleteBtn = new JButton("  Delete Records  ");
        editBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        btnPanel.add(deleteBtn);
        btnPanel.add(editBtn);

        tablePanel = new JPanel();
        tablePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        model = new MyTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return editable;
            }
        };
        model.addColumn("First Name");
        model.addColumn("Middle Name");
        model.addColumn("Last Name");
        model.addColumn("Gender");
        model.addColumn("City");
        model.addColumn("Street");
        model.addColumn("BlockNo.");
        model.addColumn("Country");
        model.addColumn("Email");
        model.addColumn("MobileNo.");
        model.addColumn("TelNo.");

        jtable = new JTable(model);
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

        jtable.setPreferredScrollableViewportSize(new Dimension(980, 500));
        jtable.setFillsViewportHeight(true);
        jtable.getTableHeader().setReorderingAllowed(false);
        jtable.setAutoCreateRowSorter(true);

        try {
            DB db = MongoDBConnectionUtils.connectToDatabase();

            DBCollection coll = db.createCollection("memberInformationColl", null);
            coll = db.getCollection("memberInformationColl");

            DBCursor cursor = coll.find();

            while (cursor.hasNext()) {
                DBObject doc = cursor.next();
                Object[] data = new Object[]{doc.get("FirstName"), doc.get("MiddleName"), doc.get("LastName"),
                    doc.get("Gender"), doc.get("City"), doc.get("Street"), doc.get("BlockNumber"), doc.get("Country"),
                    doc.get("EmailAddress"), doc.get("MobileNumber"), doc.get("HomeContact")};
                model.addRow(data);
            }

            jtable.getModel().addTableModelListener(this);
            pane = new JScrollPane(jtable);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Error!");
        }

        tablePanel.add(pane);

        cnt.setLayout(new BoxLayout(cnt, BoxLayout.Y_AXIS));
        cnt.add(btnPanel);
        cnt.add(tablePanel);

        setSize(1000, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JAVAMongoDBApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        new ContactRecordsView();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == editBtn) {
            if (onoffedit == 0) {
                editBtn.setText("   Disable Edit Records  ");
                editable = true;
                onoffedit = 1;
            } else if (onoffedit == 1) {
                editBtn.setText("   Enable Edit Records   ");
                editable = false;
                onoffedit = 0;
            }
        } else if (ae.getSource() == deleteBtn) {
            if (jtable.getSelectedRows().length > 0) {
                int i = JOptionPane.showConfirmDialog(null, "Are you sure to delete?", "Delete Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (i == JOptionPane.YES_OPTION) {
                    int j[] = jtable.getSelectedRows();
                    try {
                        DB db = MongoDBConnectionUtils.connectToDatabase();

                        DBCollection coll = db.getCollection("memberInformationColl");
                        for (int count = 0; count < j.length; count++) {
                            String mobileno = (String) jtable.getValueAt(j[count], 9);

                            //Remove the document...
                            coll.remove(new BasicDBObject().append("MobileNumber", mobileno));

                        }
                        removeSelectedRows(jtable);

                    } catch (Exception e) {
                        System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "First Select the rows to delete...", "Delete Failure", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void removeSelectedRows(JTable table) {
        DefaultTableModel myModel = (DefaultTableModel) this.jtable.getModel();
        int[] rows = table.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            myModel.removeRow(rows[i] - i);
        }
    }

    @Override
    public void tableChanged(TableModelEvent tme) {
        int row = tme.getFirstRow();
        int col = tme.getColumn();
        String changedColName = null;
        switch (col) {
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
        }

        model = (MyTableModel) tme.getSource();
//      String changedColName = model.getColumnName(col);
        Object newData = model.getValueAt(row, col);
        Object mobileno = model.getValueAt(row, 9);

        try {
            DB db = MongoDBConnectionUtils.connectToDatabase();
            DBCollection coll = db.getCollection("memberInformationColl");

            BasicDBObject query = new BasicDBObject("MobileNumber", new BasicDBObject("$regex", mobileno.toString()));
            DBCursor cursor = coll.find(query);
            DBObject doc = cursor.next();
            Object id = doc.get("_id");
            Object prevData = doc.get(changedColName);
            //System.out.println(prevData.toString());
            
            if (!(newData.toString().equals(prevData.toString()))) {
                BasicDBObject updateDocument = new BasicDBObject();
                updateDocument.append("$set", new BasicDBObject().append(changedColName, newData.toString()));
                BasicDBObject searchQuery = new BasicDBObject().append("_id", id);

                int i = JOptionPane.showConfirmDialog(null, "Are you sure to save?", "Save Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (i == JOptionPane.YES_OPTION) {
                    //Update the document...
                    coll.update(searchQuery, updateDocument);
                    JOptionPane.showMessageDialog(null, "Database has been modified... ", "Update Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (UnknownHostException | HeadlessException e) {

        }
    }

}
