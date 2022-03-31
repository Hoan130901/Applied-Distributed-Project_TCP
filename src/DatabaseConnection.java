/*
 * *********************
 * Author    :Ngoc Khai Hoan Nguyen
 * Student ID:12127818
 * File Name :DatabaseConnection.java
 * Date      :5/03/2021
 * Purpose   :Assignment 2
 * *********************
 */

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    static java.sql.Connection connection;      // connection to the database
    static PreparedStatement queryCheckUser; // preparedstatement
    static PreparedStatement queryCheckPassword; // preparedstatement
    static PreparedStatement getTable;
    static ResultSet rs;                       // resultset

    public DatabaseConnection(String URL) {
        try {
            //connect to the database
            URL = "jdbc:mysql://localhost:3306/mysql";//String URL database connectivity
            connection = DriverManager.getConnection(URL, "root", "Hoanduong05");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public boolean checkUser(String user) throws SQLException {//check User 
        //statement to get userID from the table and check with user input
        queryCheckUser = connection.prepareStatement("SELECT USERID FROM users WHERE USERID=?");
        queryCheckUser.setString(1, user);//set ? in the statement as user input
        rs = queryCheckUser.executeQuery();//execute check user statement
        if (rs.next() == false) {//check every row if user exist 
            return false;//invalid userID
        } else {
            return true;//correct userID
        }
    }

    public boolean checkPassword(String user, String password) throws SQLException {//check Password
        //statement to get userID and userPassword to check with user input
        queryCheckPassword = connection.prepareStatement("SELECT USERPASSWORD FROM users WHERE USERID =? AND USERPASSWORD =?"); //get userid
        //set input to compare 
        queryCheckPassword.setString(1, user);
        queryCheckPassword.setString(2, password);
        rs = queryCheckPassword.executeQuery();// excute query
        if (rs.next() == false) {//check every row if user exist 
            System.out.println("User not found");//invalid user
            return false;
        } else {
            System.out.println("User authenticated");//valid user
            return true;
        }
    }
}
