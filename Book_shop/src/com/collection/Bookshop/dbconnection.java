package com.collection.Bookshop;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbconnection {
    public Connection getDBConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/books", "root",""); // Update with your password
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return con; 
    }
}
