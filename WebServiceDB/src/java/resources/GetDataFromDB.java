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
import java.time.LocalDate;
import resources.Movie;

/**
 *
 * @author Sean
 */
public class GetDataFromDB {
    
    private static ArrayList<Movie> movieList = new ArrayList<>();
    private static ArrayList<Movie> actorList = new ArrayList<>();
    private static ArrayList<Movie> directorList = new ArrayList<>();
    private Movie movie;
    private Movie actor;
    private Movie director;
    
    private static final String URL = "jdbc:derby://localhost:1527/myDB";
    private static final String USER = "app";
    private static final String PASSWD = "app";
    
    /**
     * Get all movies
     * @return all movies
     */
    public ArrayList<Movie> getAllMovies() {
        movieList = new ArrayList<>();
        String sql = "SELECT * FROM MOVIES";
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                // get data out - note: index starts at 1 !!!!
                movie = (new Movie(result.getInt(1), result.getString(2), 
                        result.getString(3), result.getInt(4), result.getDate(5).toLocalDate(),
                        result.getInt(6), result.getInt(7)));
                movieList.add(movie);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());

        }
        return movieList;
    }
    
    /**
     * Get movie by genre
     * @param genre genre of the movie to search
     * @return movies belonging to requested genre
     */
    public ArrayList<Movie> getMovieByGenre(String genre) {
        ArrayList<Movie> moviesFound = new ArrayList<>();
        ArrayList<Movie> movieList = getAllMovies();
        int genreID = 0;
        
        String sql = "SELECT ID FROM GENRES WHERE GENRE='" + genre + "'";
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);
            while (result.next()) {
                genreID = result.getInt(1);
            }   
            for (Movie movie : movieList) {
                if (movie.getGenreID() == genreID) {
                    moviesFound.add(movie);
                }
            }
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());

        }        
        return moviesFound;
    }
    
    /**
     * Get all actors
     * @return all actors
     */
    public ArrayList<Movie> getAllActors() {
        actorList = new ArrayList<>();
        String sql = "SELECT * FROM ACTORS";
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                // get data out - note: index starts at 1 !!!!
                actor = (new Movie(result.getInt(1), result.getString(2), 
                        result.getString(3)));
                actorList.add(actor);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());

        }
        return actorList;
    }
    
    /**
     * Get actors by name
     * @param name name of actor to search
     * @return all actors matching name
     */
    public ArrayList<Movie> getActorByName(String name) {
        ArrayList<Movie> actorsFound = new ArrayList<>();
        ArrayList<Movie> actorList = getAllActors();
        for (Movie actor : actorList) {
            String fullName = (actor.getFirstName() + " " 
                    + actor.getLastName()).toUpperCase();
            if (fullName.contains(name.toUpperCase())) {
                actorsFound.add(actor);
            }
        }
        return actorsFound;
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
     * @param name name of director to search
     * @return all directors matching name
     */
    public ArrayList<Movie> getDirectorByName(String name) {
        ArrayList<Movie> directorsFound = new ArrayList<>();
        ArrayList<Movie> directorList = getAllDirectors();
        for (Movie director : directorList) {
            String fullName = (director.getFirstName() + " " 
                    + director.getLastName()).toUpperCase();
            if (fullName.contains(name.toUpperCase())) {
                directorsFound.add(director);
            }
        }
        return directorsFound;
    }
}
