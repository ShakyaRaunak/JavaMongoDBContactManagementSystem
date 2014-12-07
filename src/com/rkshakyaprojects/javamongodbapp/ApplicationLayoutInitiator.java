package com.rkshakyaprojects.javamongodbapp;

import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author raunakshakya
 */
public class ApplicationLayoutInitiator {

    public static Font font = new Font("Comic Sans MS", Font.BOLD, 18);

    public static JLabel registrationPanelTitle = new JLabel("Contact Management System");
    public static JLabel lblFirstName = new JLabel("First Name:");
    public static JLabel lblMiddleName = new JLabel("Middle Name:");
    public static JLabel lblLastName = new JLabel("Last Name:");
    public static JLabel lblGender = new JLabel("Gender:");
    public static JLabel lblCity = new JLabel("City:");
    public static JLabel lblStreet = new JLabel("Street:");
    public static JLabel lblBlockNumber = new JLabel("Block No.:");
    public static JLabel lblCountry = new JLabel("Country");
    public static JLabel lblEmail = new JLabel("Email:");
    public static JLabel lblMobileNumber = new JLabel("Mobile Number:");
    public static JLabel lblHomeContact = new JLabel("Home Contact:");

    public static JTextField firstName = new JTextField(21);
    public static JTextField middleName = new JTextField(21);
    public static JTextField lastName = new JTextField(21);
    public static JTextField city = new JTextField(21);
    public static JTextField street = new JTextField(21);
    public static JTextField blockNumber = new JTextField(21);
    public static JTextField country = new JTextField(21);
    public static JTextField email = new JTextField(21);
    public static JTextField mobileNumber = new JTextField(21);
    public static JTextField homeContact = new JTextField(21);

    public static JRadioButton radioButtonForMale = new JRadioButton("Male");
    public static JRadioButton radioButtonForFemale = new JRadioButton("Female");
    public static ButtonGroup buttonGroupForMaleAndFemale = new ButtonGroup();

    public static JButton buttonShow = new JButton("      Show      ");
    public static JButton buttonSave = new JButton("      Save      ");
    public static JButton buttonClear = new JButton("      Clear      ");

    public static JFrame frame = new JFrame("JAVA with MongoDB Project");
    public static JPanel registrationPanel = new JPanel();

    public static void initiateApplicationLayout() {
        registrationPanel.setLayout(new MigLayout());
        
        registrationPanelTitle.setFont(font);
        registrationPanel.add(registrationPanelTitle, "gapleft 4, wrap 25");
        
        registrationPanel.add(lblFirstName, "gapleft 5, wrap");
        registrationPanel.add(firstName, "gapleft 15, wrap");    //, pushx, growx");

        registrationPanel.add(lblMiddleName, "gapleft 5, wrap");
        registrationPanel.add(middleName, "gapleft 15, wrap");   //, pushx, growx");
        
        registrationPanel.add(lblLastName, "gapleft 5, wrap");
        registrationPanel.add(lastName, "gapleft 15, wrap");

        registrationPanel.add(lblGender, "gapleft 5, wrap");
        registrationPanel.add(radioButtonForMale, "gapleft 15, split2");
        registrationPanel.add(radioButtonForFemale, "wrap");

        registrationPanel.add(lblCity, "gapleft 5, wrap");
        registrationPanel.add(city, "gapleft 15, wrap");

        registrationPanel.add(lblStreet, "gapleft 5, wrap");
        registrationPanel.add(street, "gapleft 15, wrap");

        registrationPanel.add(lblBlockNumber, "gapleft 5, wrap");
        registrationPanel.add(blockNumber, "gapleft 15, wrap");

        registrationPanel.add(lblCountry, "gapleft 5, wrap");
        registrationPanel.add(country, "gapleft 15, wrap");

        registrationPanel.add(lblEmail, "gapleft 5, wrap");
        registrationPanel.add(email, "gapleft 15, wrap");

        registrationPanel.add(lblMobileNumber, "gapleft 5, wrap");
        registrationPanel.add(mobileNumber, "gapleft 15, wrap");

        registrationPanel.add(lblHomeContact, "gapleft 5, wrap");
        registrationPanel.add(homeContact, "gapleft 15, wrap 25");
        
        registrationPanel.add(buttonShow, "gapleft 3, split 3");
        registrationPanel.add(buttonSave);
        registrationPanel.add(buttonClear);

        frame.add(registrationPanel);
        frame.setSize(340, 640);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
