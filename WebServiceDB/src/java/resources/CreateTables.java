/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.*;

/**
 *
 * @author Sean
 */
public class CreateTables {
    
    private static final String URL = "jdbc:derby://localhost:1527/myDB";
    private static final String USER = "app";
    private static final String PASSWD = "app";
    
    /**
     * Adds a Actor table to the database
     * @return true if table was successfully created, false otherwise
     */
    public boolean createActorsTable() {
        
        boolean tableCreated = false;
        
        String sql = "CREATE TABLE ACTORS(ID INTEGER Primary Key "
                + "GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1), "
                + "FIRSTNAME VARCHAR(50), LASTNAME VARCHAR(50), IMAGE VARCHAR(50))";
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableCreated = true;
            
            System.out.println("Actors Table successfully created!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return tableCreated;
    }
    
    /**
     * Adds a Director table to the database
     * @return true if table was successfully created, false otherwise
     */
    public boolean createDirectorsTable() {
        
        boolean tableCreated = false;
        
        String sql = "CREATE TABLE DIRECTORS(ID INTEGER Primary Key "
                + "GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1), "
                + "FIRSTNAME VARCHAR(30), LASTNAME VARCHAR(30))";
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableCreated = true;
            
            System.out.println("Directors Table successfully created!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return tableCreated;
    }
    
    /**
     * Adds a Genre table to the database
     * @return true if table was successfully created, false otherwise
     */
    public boolean createGenresTable() {
        
        boolean tableCreated = false;
        
        String sql = "CREATE TABLE GENRES(ID INTEGER Primary Key "
                + "GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "GENRE VARCHAR(30))";
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableCreated = true;
            
            System.out.println("Genres Table successfully created!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return tableCreated;
    }
    
    /**
     * Adds a Movie table to the database
     * @return true if table was successfully created, false otherwise
     */
    public boolean createMoviesTable() {
        
        boolean tableCreated = false;
        
        String sql = "CREATE TABLE MOVIES(ID INTEGER Primary Key "
                + "GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1), "
                + "TITLE VARCHAR(255), DESCRIPTION VARCHAR(255), "
                + "RUNTIME INTEGER, RELEASEDATE DATE, TRAILER VARCHAR(50),"
                + "DIRECTOR INTEGER, GENRE INTEGER, FOREIGN KEY(DIRECTOR) "
                + "REFERENCES DIRECTORS(ID), FOREIGN KEY(GENRE) REFERENCES GENRES(ID))";
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableCreated = true;
            
            System.out.println("Movies Table successfully created!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return tableCreated;
    }
    
    /**
     * Adds a Actors_Movies (bridge) table to the database
     * @return true if table was successfully created, false otherwise
     */
    public boolean createActors_MoviesTable() {
        
        boolean tableCreated = false;
        
        String sql = "CREATE TABLE ACTORS_MOVIES(ACTORID INTEGER NOT NULL," 
                + "MOVIEID INTEGER NOT NULL, PRIMARY KEY(ActorID, MovieID))";
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {

            // execute statement 
            stmt.executeUpdate(sql);
            tableCreated = true;
            
            System.out.println("Actors_Movies Table successfully created!");

            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return tableCreated;
    }
}
