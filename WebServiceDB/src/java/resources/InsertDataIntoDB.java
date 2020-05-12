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
import resources.GetDataFromDB;

/**
 *
 * @author Sean
 */
public class InsertDataIntoDB {
    
    private static ArrayList<Movie> directorList = new ArrayList<>();
    private Movie director;
    private Movie movie;
    private Movie actor;
    private int directorID;
    GetDataFromDB getData = new GetDataFromDB();
    
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
    public boolean addMovie(String title, String description, int runTime,
            int year, int month, int day, String trailer, String directorFirstName, 
            String directorLastName, String genre) {
        
        boolean movieAdded = false;
        director = CheckIfDirectorExists(directorFirstName, directorLastName);
        movie = checkIfMovieExists(title);
        
        // if movie doesn't already exist, create movie entry
        if (movie == null) {
            // if director does not exist, add new director
            if(director == null) {
                System.out.println("null");
                directorID = addDirector(directorFirstName, directorLastName);
            }
            // otherwise, use ID of existing director
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
                    pstmt.setInt(7, getGenreID(genre));

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
        }
        else {
            System.out.println("Movie "+ movie.getTitle() + " already exists!");
            movieAdded = false;
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
     * Check if director already exists in database
     * @param firstName First name of director to search
     * @param lastName Last name of director to search
     * @return Director if match was found, null otherwise
     */
    public Movie CheckIfDirectorExists(String firstName, String lastName) {
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
    
    /**
     * Get the String representation of genre
     * @param genre Genre of movie
     * @return String representation of genre
     */
    public int getGenreID(String genre) {
        int ID = 0;
        String sql = "SELECT ID FROM GENRES WHERE GENRE='" + genre + "'";
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                ID = result.getInt(1);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return ID;        
    }
    
    /**
     * Check if movie already exists in database
     * @param title Title of the movie to check
     * @return Movie if match was found, null otherwise
     */
    public Movie checkIfMovieExists(String title) {
        ArrayList<Movie> movieList = getData.getAllMovies(); 
        for (Movie movie : movieList) {
            if (movie.getTitle().toUpperCase().equals(title.toUpperCase())) {
                return movie;
            }
        }    
        return null;
    }
    
    /**
     * This function adds an actor to the 'ACTORS' table, if the actor does not
     * already exist.
     * @param actorFirstName First name of actor to add
     * @param actorLastName Last name of actor to add
     * @param year Year actor was born
     * @param month Month actor was born
     * @param day Day actor was born
     * @param actorEmail Email address of actor
     * @param actorImage Image of actor
     * @return True if actor successfully added, false otherwise.
     */
    public boolean addActor(String actorFirstName, String actorLastName,
            int year, int month, int day, String actorEmail, String actorImage) {
        boolean actorAdded = false;
        String sql = "INSERT INTO ACTORS(FIRSTNAME, LASTNAME) VALUES(?,?)";
        
        actor = checkIfActorExists(actorFirstName, actorLastName);
        
        if (actor == null) {
            // use try with resource
            try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                
                pstmt.setString(1, actorFirstName);
                pstmt.setString(2, actorLastName);

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + actorFirstName + " " 
                                    + actorLastName + " has been added");
                }
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        addActorCredentials(generatedKeys.getInt(1), year, 
                                month, day, actorEmail, actorImage);
                    }
                    else {
                        throw new SQLException("Adding actor credentials failed, "
                                + "no ID obtained.");
                    }
                }
            } catch (SQLException sqle) {
                System.out.println("Message: " + sqle.getMessage());
                System.out.println("Code: " + sqle.getSQLState());
            } actorAdded = true;
        }
        else {
            System.out.println("Row for: " + actor.getFirstName() + " " + 
                    actor.getLastName() + " aleady exits in ACTORS table");
        }
        return actorAdded;
    }
    
    /**
     * Check if actor already exists in database
     * @param firstName First name of actor to search
     * @param lastName Last name of actor to search
     * @return Director if match was found, null otherwise
     */
    public Movie checkIfActorExists(String firstName, String lastName) {
        ArrayList<Movie> actorList = getData.getAllActors(); 
        for (Movie actor : actorList) {
            String fullName = (actor.getFirstName() + " " 
                    + actor.getLastName()).toUpperCase();
            if (fullName.equals((firstName + " " + lastName).toUpperCase())) {
                return actor;
            }
        }
        return null;
    }
    
    /**
     * This functions adds the credentials of the given actor to the 
     * 'ActorCredentials' table.
     * @param actorID ID of actor
     * @param year Year actor was born
     * @param month Month actor was born
     * @param day Day actor was born
     * @param actorEmail Email of address actor
     * @param actorImage Image of actor
     */
    public void addActorCredentials(int actorID, int year, int month, int day, 
            String actorEmail, String actorImage) {
        String sql = "INSERT INTO ACTORCREDENTIALS(ID, DOB, EMAIL, IMAGE) "
                + "VALUES(?,?,?,?)";
        
        // use try with resource
            try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                    PreparedStatement pstmt = connect.prepareStatement(sql);) {

                    pstmt.setInt(1, actorID);
                    pstmt.setDate(2, Date.valueOf(LocalDate.of(year, month, day).toString()));
                    pstmt.setString(3, actorEmail);
                    pstmt.setString(4, actorImage);

                    // execute statement 
                    if (pstmt.executeUpdate() == 1) {
                        System.out.println(
                                "Row for " + actorID + " has been added to the"
                                        + " ACTORCREDENTIALS table.");
                    }
            } catch (SQLException sqle) {
                System.out.println("Message: " + sqle.getMessage());
                System.out.println("Code: " + sqle.getSQLState());
            }
    }
    
    public void AddToActors_MoviesTable(String actorFirstName, 
            String actorLastName, String movieTitle) {
        
        actor = checkIfActorExists(actorFirstName, actorLastName);
        
        movie = checkIfMovieExists(movieTitle);
        
        
        if (actor == null && movie == null) {
            System.out.println("NEITHER");
            
        }
        else if (actor == null) {
            System.out.println("No record for actor '" + actorFirstName + " " + 
                    actorLastName + "' exists in this database, please add this"
                            + " actor first!");
        }
        else if (movie == null) {
            System.out.println("No record for movie '" + movieTitle +  
                    "' exists in this database, please add this"
                            + " movie first!");
        }
        else {
            System.out.println("Actor ID:    " + actor.getActorID());
            System.out.println("Actor Name:  " + actor.getFirstName() + " " + actor.getLastName());
            System.out.println("Movie ID:    " + movie.getMovieID());
            System.out.println("Movie Title: " + movie.getTitle());
            
            String sql = "INSERT INTO ACTORS_MOVIES(ACTORID, MOVIEID) "
                + "VALUES(?,?)";
        
            // use try with resource
            try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                    PreparedStatement pstmt = connect.prepareStatement(sql);) {

                    pstmt.setInt(1, actor.getActorID());
                    pstmt.setInt(2, movie.getMovieID());
                    // execute statement 
                    if (pstmt.executeUpdate() == 1) {
                        System.out.println(
                                "Row for " + actor.getFirstName() + " " + 
                                        actor.getLastName() + " & " +
                                        movie.getTitle() +                                    
                                        " has been added to the"
                                        + " ACTORS_MOVIES table.");
                    }
            } catch (SQLException sqle) {
                System.out.println("Message: " + sqle.getMessage());
                System.out.println("Code: " + sqle.getSQLState());
            }
            
        }
        
        
    }
}
