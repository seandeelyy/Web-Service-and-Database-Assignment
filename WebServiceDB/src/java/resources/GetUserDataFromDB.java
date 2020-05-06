/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import resources.User;

/**
 *
 * @author Sean
 */
public class GetUserDataFromDB {
    
    private static ArrayList<User> userList = new ArrayList<>();
    private User user;
    
    private static final String URL = "jdbc:derby://localhost:1527/myDB";
    private static final String USER = "app";
    private static final String PASSWD = "app";
    
    public ArrayList<User> getAllUsers() {
        userList = new ArrayList<>();
        
        String sql = "SELECT UID, FNAME, LNAME, UTYPE FROM USERS";
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                // get data out - note: index starts at 1 !!!!
                user = (new User(result.getInt(1), result.getString(2), 
                        result.getString(3), result.getString(4)));
                userList.add(user);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("ERROR OCCURRED");

        }
        
        return userList;
    }
    
    public User getUser(int id) {
        ArrayList<User> users = getAllUsers();
        
        for (User u: users) {
            if( u.getUid() == id) {
                return u;
            }
        }
        return null;
    }
    
    public int addUser(String FName, String LName, String utype) {
        int status = 0;
        String sql = "INSERT INTO USERS(FNAME, LNAME, UTYPE)"
                + "VALUES(?,?,?)";
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

            // set sql parameters with received values
            pstmt.setString(1, FName);
            pstmt.setString(2, LName);
            // please note: Date is java.sql.Date not java.util.Date
            pstmt.setString(3, utype);

            // execute statement 
            if (pstmt.executeUpdate() == 1) {
                System.out.println(
                        "Row for " + FName + " " + LName + ", Type " + utype
                        + " has been added");
                status = 1;
            }

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("ADDING USER FAILED!");
        }
        
        return status;
    }
    
    public void editUser(int UID, String utype) {
        
        String sql = "UPDATE USERS SET UTYPE='" + utype + "' WHERE UID=" +
                Integer.toString(UID);
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            stmt.executeUpdate(sql);

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("EDITING USER FAILED!");
        }
    }
    
    public void deleteUser(int uid) {
        
        String query = "DELETE FROM USERS WHERE UID=" + Integer.toString(uid);
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                    Statement stmt = connect.createStatement();) {
                stmt.executeUpdate(query);
                
                System.out.println("User #" + uid + " deleted");
              
            } catch (SQLException sql) {
                System.out.println("User not deleted");
            }
    }
    
}
