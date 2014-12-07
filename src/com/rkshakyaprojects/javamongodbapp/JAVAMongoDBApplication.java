package com.rkshakyaprojects.javamongodbapp;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;
import java.util.HashMap;
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

    private String firstName,
            middleName,
            lastName,
            gender,
            city,
            street,
            blockNumber,
            country,
            email,
            mobileNumber,
            homeContact;

    private final JRadioButton radioButtonForMale = ApplicationLayoutInitiator.radioButtonForMale;
    private final JRadioButton radioButtonForFemale = ApplicationLayoutInitiator.radioButtonForFemale;
    private final ButtonGroup buttonGroupForMaleAndFemale = ApplicationLayoutInitiator.buttonGroupForMaleAndFemale;
    private final JButton buttonShow = ApplicationLayoutInitiator.buttonShow;
    private final JButton buttonSave = ApplicationLayoutInitiator.buttonSave;
    private final JButton buttonClear = ApplicationLayoutInitiator.buttonClear;

    public JAVAMongoDBApplication() {
        ApplicationLayoutInitiator.initiateApplicationLayout();

        radioButtonForMale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gender = ae.getActionCommand();
            }
        });
        radioButtonForFemale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gender = ae.getActionCommand();
            }
        });
        buttonGroupForMaleAndFemale.add(radioButtonForMale);
        buttonGroupForMaleAndFemale.add(radioButtonForFemale);

        buttonShow.addActionListener(new ActionListener() {
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
                            new ContactRecordsView();
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "Incorrect password", "Conformation", JOptionPane.OK_OPTION);
                        }
                    } else {
                        flag = 1;
                    }
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                HashMap personMap = extractPersonDataFromInputFields();
                
                if (firstName.isEmpty() || mobileNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter at least First Name, Email & Mobile No.", "Input Fields Empty", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        DB db = MongoDBConnectionUtils.connectToDatabase();

                        //boolean auth = db.authenticate(myUserName, myPassword);
                        //System.out.println("Authentication: "+auth);
                        DBCollection coll = db.createCollection("memberInformationColl", null);
                        coll = db.getCollection("memberInformationColl");

                        BasicDBObject doc = new BasicDBObject("FirstName", firstName).
                                append("MiddleName", middleName).
                                append("LastName", lastName).
                                append("Gender", gender).
                                append("City", city).
                                append("Street", city).
                                append("BlockNumber", blockNumber).
                                append("Country", country).
                                append("EmailAddress", email).
                                append("MobileNumber", mobileNumber).
                                append("HomeContact", homeContact);
                        coll.insert(doc);
                        JOptionPane.showMessageDialog(new JFrame(), "Data has been saved!", "Success Message", JOptionPane.INFORMATION_MESSAGE);

                    } catch (UnknownHostException | HeadlessException e) {
                        JOptionPane.showMessageDialog(new JFrame(), "Error occurred while saving data!", "Error Message", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                resetInputFieldsForRegistration();
            }
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(JAVAMongoDBApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                JAVAMongoDBApplication javaMongoDBApplication = new JAVAMongoDBApplication();
            }
        });
    }

    public void resetInputFieldsForRegistration() {
        ApplicationLayoutInitiator.firstName.setText("");
        ApplicationLayoutInitiator.middleName.setText("");
        ApplicationLayoutInitiator.lastName.setText("");
        ApplicationLayoutInitiator.buttonGroupForMaleAndFemale.clearSelection();
        ApplicationLayoutInitiator.city.setText("");
        ApplicationLayoutInitiator.street.setText("");
        ApplicationLayoutInitiator.blockNumber.setText("");
        ApplicationLayoutInitiator.country.setText("");
        ApplicationLayoutInitiator.email.setText("");
        ApplicationLayoutInitiator.mobileNumber.setText("");
        ApplicationLayoutInitiator.homeContact.setText("");
    }

    public HashMap extractPersonDataFromInputFields() {
        HashMap personMap = new HashMap();
        personMap.put(firstName, ApplicationLayoutInitiator.firstName.getText().trim());
        personMap.put(middleName, ApplicationLayoutInitiator.middleName.getText().trim());
        personMap.put(lastName, ApplicationLayoutInitiator.lastName.getText().trim());
        personMap.put(city, ApplicationLayoutInitiator.city.getText().trim());
        personMap.put(street, ApplicationLayoutInitiator.street.getText().trim());
        personMap.put(email, ApplicationLayoutInitiator.email.getText().trim());
        personMap.put(blockNumber, ApplicationLayoutInitiator.blockNumber.getText().trim());
        personMap.put(mobileNumber, ApplicationLayoutInitiator.mobileNumber.getText().trim());
        personMap.put(homeContact, ApplicationLayoutInitiator.homeContact.getText().trim());
        return personMap;
    }

}
