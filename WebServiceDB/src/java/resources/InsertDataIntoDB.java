/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import resources.User;

/**
 *
 * @author Sean
 */
public class InsertDataIntoDB {
    
    private static ArrayList<Movie> directorList = new ArrayList<>();
    private Movie director;
    private int directorID;
    
    private static final String URL = "jdbc:derby://localhost:1527/myDB";
    private static final String USER = "app";
    private static final String PASSWD = "app";
    
    /**
     * Adds a movie to the MOVIES table
     * @param title Title of movie to be added
     * @param description Description of movie to be added
     * @param runTime Duration of movie to be added in minutes
     * @param year Year of release
     * @param month Month of release
     * @param day Day of release
     * @param trailer Trailer of movie to be added
     * @param directorFirstName First name of director 
     * @param directorLastName Last name of director
     * @param genre Genre of movie to be added as a String
     * @return true if movie was added, false otherwise
     */
    public boolean fillMoviesTable(String title, String description, int runTime,
            int year, int month, int day, String trailer, String directorFirstName, 
            String directorLastName, String genre) {
        
        boolean movieAdded = false;
        director = getDirectorByName(directorFirstName, directorLastName);
        
        if(director == null) {
            System.out.println("null");
            directorID = addDirector(directorFirstName, directorLastName);
        }
        else {
            directorID = director.getDirectorID();
        }
        String sql = "INSERT INTO MOVIES(TITLE, DESCRIPTION, RUNTIME, RELEASEDATE,"
                + "TRAILER, DIRECTOR, GENRE) VALUES(?,?,?,?,?,?,?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

                pstmt.setString(1, title);
                pstmt.setString(2, description);
                pstmt.setInt(3, runTime);
                pstmt.setDate(4, Date.valueOf(LocalDate.of(year, month, day).toString()));
                pstmt.setString(5, trailer);
                pstmt.setInt(6, directorID);
                pstmt.setInt(7, 4);

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + title + " has been added");
                }
             movieAdded = true;
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return movieAdded;
    }
    
    /**
     * Get all directors
     * @return all directors
     */
    public ArrayList<Movie> getAllDirectors() {
        directorList = new ArrayList<>();
        String sql = "SELECT * FROM Directors";
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                // get data out - note: index starts at 1 !!!!
                director = (new Movie(result.getInt(1), result.getString(2),
                        result.getString(3)));
                directorList.add(director);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return directorList;
    }
    
    /**
     * Get directors by name
     * @param firstName First name of director to search
     * @param lastName Last name of director to search
     * @return All directors matching name
     */
    public Movie getDirectorByName(String firstName, String lastName) {
        ArrayList<Movie> directorsFound = new ArrayList<>();
        ArrayList<Movie> directorList = getAllDirectors();
        for (Movie director : directorList) {
            String fullName = (director.getFirstName() + " " 
                    + director.getLastName()).toUpperCase();
            if (fullName.equals((firstName + " " + lastName).toUpperCase())) {
                return director;
            }
        }
        return null;
    }
    
     /**
     * Adds a director to the DIRECTORS table
     * @param firstName First name of director to add
     * @param lastName Last name of director to add
     * @return ID of newly added director.
     */
    public int addDirector(String firstName, String lastName) {        
        int ID = 0;
        String sql = "INSERT INTO DIRECTORS(FIRSTNAME, LASTNAME) VALUES(?,?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
            PreparedStatement pstmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            // execute statement 
            if (pstmt.executeUpdate() == 1) {
                System.out.println(
                        "Row for " + firstName + " " 
                                + lastName + " has been added");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ID = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Adding director failed, no ID obtained.");
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return ID;
    }
}
