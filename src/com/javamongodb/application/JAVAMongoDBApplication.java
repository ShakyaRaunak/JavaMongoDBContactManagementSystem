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
import com.mongodb.MongoClient;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Raunak Shakya
 */
public class JAVAMongoDBApplication {
    
    public static final ResourceBundle messages = MessageUtils.MESSAGES;

    JFrame frame = new JFrame("JAVA with MongoDB Project");
    JPanel panel = new JPanel();

    Font font = new Font("Comic Sans MS", Font.BOLD, 18);
    JLabel lblTitle = new JLabel("Contact Management System");

    JLabel lblFirstName = new JLabel(messages.getString("label.first.name"));
    JTextField txtFirstName = new JTextField(21);

    JLabel lblMiddleName = new JLabel(messages.getString("label.middle.name"));
    JTextField txtMiddleName = new JTextField(21);

    JLabel lblLastName = new JLabel(messages.getString("label.last.name"));
    JTextField txtLastName = new JTextField(21);

    JLabel lblGender = new JLabel(messages.getString("label.gender"));
    JRadioButton maleRadioButton = new JRadioButton(messages.getString("label.male"));
    JRadioButton femaleRadioButton = new JRadioButton(messages.getString("label.female"));
    ButtonGroup bg = new ButtonGroup();

    JLabel lblCity = new JLabel(messages.getString("label.city"));
    JTextField txtCity = new JTextField(21);

    JLabel lblStreet = new JLabel(messages.getString("label.street"));
    JTextField txtStreet = new JTextField(21);

    JLabel lblBlockNumber = new JLabel(messages.getString("label.block.number"));
    JTextField txtBlockNumber = new JTextField(21);

    JLabel lblCountry = new JLabel(messages.getString("label.country"));
    JTextField txtCountry = new JTextField(21);

    JLabel lblEmailAddress = new JLabel(messages.getString("label.email.address"));
    JTextField txtEmailAddress = new JTextField(21);

    JLabel lblMobileNumber = new JLabel(messages.getString("label.mobile.number"));
    JTextField txtMobileNumber = new JTextField(21);

    JLabel lblHomeContact = new JLabel(messages.getString("label.home.contact"));
    JTextField txtHomeContact = new JTextField(21);

    JButton showButton = new JButton("     " + messages.getString("common.show") + "     ");
    JButton saveButton = new JButton("     " + messages.getString("common.save") + "     ");
    JButton clearButton = new JButton("    " + messages.getString("common.clear") + "    ");

    String valueFirstName, valueMiddleName, valueLastName, valueGender, valueCity,
            valueStreet, valueBlockNumber, valueCountry, valueEmailAddress, valueMobileNumber, valueHomeContact;

    public JAVAMongoDBApplication() {
        panel.setLayout(new MigLayout());
        lblTitle.setFont(font);
        panel.add(lblTitle, "gapleft 4, wrap 25");

        panel.add(lblFirstName, "gapleft 5, wrap");
        panel.add(txtFirstName, "gapleft 15, wrap");    //, pushx, growx");

        panel.add(lblMiddleName, "gapleft 5, wrap");
        panel.add(txtMiddleName, "gapleft 15, wrap");   //, pushx, growx");

        panel.add(lblLastName, "gapleft 5, wrap");
        panel.add(txtLastName, "gapleft 15, wrap");

        panel.add(lblGender, "gapleft 5, wrap");
        maleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                valueGender = ae.getActionCommand();
            }
        });
        femaleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                valueGender = ae.getActionCommand();
            }
        });
        bg.add(maleRadioButton);
        bg.add(femaleRadioButton);
        panel.add(maleRadioButton, "gapleft 15, split2");
        panel.add(femaleRadioButton, "wrap");

        panel.add(lblCity, "gapleft 5, wrap");
        panel.add(txtCity, "gapleft 15, wrap");

        panel.add(lblStreet, "gapleft 5, wrap");
        panel.add(txtStreet, "gapleft 15, wrap");

        panel.add(lblBlockNumber, "gapleft 5, wrap");
        panel.add(txtBlockNumber, "gapleft 15, wrap");

        panel.add(lblCountry, "gapleft 5, wrap");
        panel.add(txtCountry, "gapleft 15, wrap");

        panel.add(lblEmailAddress, "gapleft 5, wrap");
        panel.add(txtEmailAddress, "gapleft 15, wrap");

        panel.add(lblMobileNumber, "gapleft 5, wrap");
        panel.add(txtMobileNumber, "gapleft 15, wrap");

        panel.add(lblHomeContact, "gapleft 5, wrap");
        panel.add(txtHomeContact, "gapleft 15, wrap 25");

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                final JPanel pn = new JPanel();
                pn.setLayout(new MigLayout());
                JLabel label = new JLabel("Enter Master Password to see all the records: ");
                JPasswordField pf = new JPasswordField(24);
                pf.addAncestorListener(new RequestFocusListener());
//                {
//                    @Override
//                    public void addNotify(){
//                        pn.addNotify();
//                        requestFocus();
//                    }
//                };
                pn.add(label, "wrap");
                pn.add(pf);
                int flag = 0;
                while (flag != 1) {
                    int okCxl = JOptionPane.showConfirmDialog(null, pn, "Password Required", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (okCxl == JOptionPane.OK_OPTION) {
                        String password = new String(pf.getPassword());
                        if (password.equals("rs")) {
                            //JOptionPane.showMessageDialog(new JFrame(), "Correct password", "Conformation", JOptionPane.INFORMATION_MESSAGE);
                            flag = 1;
                            new ShowAllRecords();
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "Incorrect password", "Conformation", JOptionPane.OK_OPTION);
                        }
                    } else {
                        flag = 1;
                    }
                }
            }
        });
        panel.add(showButton, "gapleft 3, split 3");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                valueFirstName = txtFirstName.getText().trim();
                valueMiddleName = txtMiddleName.getText().trim();
                valueLastName = txtLastName.getText().trim();
                valueCity = txtCity.getText().trim();
                valueStreet = txtStreet.getText().trim();
                valueBlockNumber = txtBlockNumber.getText().trim();
                valueCountry = txtCountry.getText().trim();
                valueEmailAddress = txtEmailAddress.getText().trim();
                valueMobileNumber = txtMobileNumber.getText().trim();
                valueHomeContact = txtHomeContact.getText().trim();
                if (valueFirstName.isEmpty() || valueMobileNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter at least First Name, Email & Mobile No.","Input Fields Empty",JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        // To connect to mongodb server
                        MongoClient mongoClient = new MongoClient(DatabaseUtils.HOST_NAME, DatabaseUtils.PORT_NUMBER);

                        // Now connect to your databases
                        DB db = mongoClient.getDB(DatabaseUtils.DATABASE_NAME);

                        //boolean auth = db.authenticate(myUserName, myPassword);
                        //System.out.println("Authentication: "+auth);
                        DBCollection coll = db.createCollection(DatabaseUtils.COLLECTION_NAME, null);
                        coll = db.getCollection(DatabaseUtils.COLLECTION_NAME);

                        BasicDBObject doc = new BasicDBObject("FirstName", valueFirstName).
                                append("MiddleName", valueMiddleName).
                                append("LastName", valueLastName).
                                append("Gender", valueGender).
                                append("City", valueCity).
                                append("Street", valueStreet).
                                append("BlockNumber", valueBlockNumber).
                                append("Country", valueCountry).
                                append("EmailAddress", valueEmailAddress).
                                append("MobileNumber", valueMobileNumber).
                                append("HomeContact", valueHomeContact);
                        coll.insert(doc);
                        JOptionPane.showMessageDialog(new JFrame(), "Data has been saved!", "Success Message", JOptionPane.INFORMATION_MESSAGE);

                    } catch (UnknownHostException | HeadlessException e) {
                        JOptionPane.showMessageDialog(new JFrame(), "Error occurred while saving data!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(saveButton);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                txtFirstName.setText("");
                txtMiddleName.setText("");
                txtLastName.setText("");
                bg.clearSelection();
                txtCity.setText("");
                txtStreet.setText("");
                txtBlockNumber.setText("");
                txtCountry.setText("");
                txtEmailAddress.setText("");
                txtMobileNumber.setText("");
                txtHomeContact.setText("");
            }
        });
        panel.add(clearButton);

        frame.add(panel);
        frame.setSize(340, 640);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(LayoutUtils.JTATTOO_APPLICATION_THEME);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(JAVAMongoDBApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                JAVAMongoDBApplication javaMongoDBApplication = new JAVAMongoDBApplication();
            }
        });
    }

}
