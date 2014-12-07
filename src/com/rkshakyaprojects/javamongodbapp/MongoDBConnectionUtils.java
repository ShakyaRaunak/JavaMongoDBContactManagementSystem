package com.rkshakyaprojects.javamongodbapp;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;

/**
 *
 * @author raunakshakya
 */
public class MongoDBConnectionUtils {

    public static DB connectToDatabase() throws UnknownHostException {
        // To connect to mongodb server
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        // Now connect to your databases
        DB db = mongoClient.getDB("ContactManagementSystemDB");
        if (db != null) {
            return db;
        }
        return null;
    }

}
