/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javamongodb.utils;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.awt.HeadlessException;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author raunakshakya
 */
public class DatabaseUtils {

    public static final String HOST_NAME = "localhost";
    public static final Integer PORT_NUMBER = 27017;
    public static final String DATABASE_NAME = "ContactManagementSystem";
    public static final String COLLECTION_NAME = "Members";

    public static DBCollection openDBConnection() {
        try {
            MongoClient mongoClient = new MongoClient(HOST_NAME, PORT_NUMBER); //connect to the mongodb server
            DB database = mongoClient.getDB(DATABASE_NAME); //connect to the database

            //boolean auth = db.authenticate(myUserName, myPassword);
            DBCollection collection = database.getCollection(COLLECTION_NAME);
            if (collection == null) {
                collection = database.createCollection(COLLECTION_NAME, null);
            }
            return collection;
        } catch (UnknownHostException | HeadlessException e) {
            JOptionPane.showMessageDialog(
                    new JFrame(),
                    MessageUtils.MESSAGES.getString("error.while.saving.data"),
                    MessageUtils.MESSAGES.getString("error.message"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return null;
    }
    
    public static void closeDBConnection() {
        
    }

}
