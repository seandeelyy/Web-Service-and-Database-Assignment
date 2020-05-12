/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Sean
 */
public class DeleteTables {
    
    private static final String URL = "jdbc:derby://localhost:1527/myDB";
    private static final String USER = "app";
    private static final String PASSWD = "app";

    /**
     * Delete the 'ActorCredentials' table.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteActorCredentialsTable() {
        boolean tableDeleted = false;
        
        String sql = "DROP TABLE ACTORCREDENTIALS";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableDeleted = true;
            
            System.out.println("ActorCredentials Table successfully deleted!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        
        return tableDeleted;
    }
    
    /**
     * Delete the 'Actors' table.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteActorsTable() {
        boolean tableDeleted = false;
        
        String sql = "DROP TABLE ACTORS";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableDeleted = true;
            
            System.out.println("Actors Table successfully deleted!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        
        return tableDeleted;
    }
    
    /**
     * Delete the 'Actors_Movies' table.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteActors_MoviesTable() {
        boolean tableDeleted = false;
        
        String sql = "DROP TABLE ACTORS_MOVIES";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableDeleted = true;
            
            System.out.println("Actors_Movies Table successfully deleted!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        
        return tableDeleted;
    }
    
    /**
     * Delete the 'Movies' table.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteMoviesTable() {
        boolean tableDeleted = false;
        
        String sql = "DROP TABLE MOVIES";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableDeleted = true;
            
            System.out.println("Movies Table successfully deleted!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        
        return tableDeleted;
    }
    
    /**
     * Delete the 'Directors' table.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteDirectorsTable() {
        boolean tableDeleted = false;
        
        String sql = "DROP TABLE DIRECTORS";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableDeleted = true;
            
            System.out.println("Directors Table successfully deleted!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        
        return tableDeleted;
    }
    
    /**
     * Delete the 'Genres' table.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteGenresTable() {
        boolean tableDeleted = false;
        
        String sql = "DROP TABLE Genres";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableDeleted = true;
            
            System.out.println("Genres Table successfully deleted!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        
        return tableDeleted;
    }
   
}
