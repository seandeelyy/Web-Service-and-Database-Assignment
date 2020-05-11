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
import java.time.Duration;
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
    private static ArrayList<String> actorNames = new ArrayList<>();
    private static ArrayList<String> movieNames = new ArrayList<>();
    private static ArrayList<Integer> actorIDs = new ArrayList<>();
    private static ArrayList<Integer> movieIDs = new ArrayList<>();
    private Movie movie;
    private Movie actor;
    private Movie director;
    private String genre;
    private String directorName;
    private String actorName;
    private String movieName;
    private int actorID;
    private int movieID;
    
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
                        result.getString(3), getRunTime(result.getInt(4)), 
                        result.getDate(5).toString(), result.getString(6), 
                        getDirector(result.getInt(7)), getGenre(result.getInt(8)),
                        getActorsInMovie(result.getInt(1))));
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
     * Get movie by title name
     * @param title Title of the movie to search
     * @return Movies matching title
     */
    public ArrayList<Movie> getMovieByTitle(String title) {
        ArrayList<Movie> moviesFound = new ArrayList<>();
        ArrayList<Movie> movieList = getAllMovies(); 
        for (Movie movie : movieList) {
            if (movie.getTitle().toUpperCase().contains(title.toUpperCase())) {
                moviesFound.add(movie);
            }
        }    
        return moviesFound;
    }
    
    /**
     * Get movie by genre
     * @param genre genre of the movie to search
     * @return movies belonging to requested genre
     */
    public ArrayList<Movie> getMovieByGenre(String genre) {
        ArrayList<Movie> moviesFound = new ArrayList<>();
        ArrayList<Movie> movieList = getAllMovies(); 
        for (Movie movie : movieList) {
            if (movie.getGenre().equals(genre)) {
                moviesFound.add(movie);
            }
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
                        result.getString(3), result.getString(4),
                        getMoviesFtActor(result.getInt(1))));
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
     * Get actor by id
     * @param actorid id of actor to search
     * @return actor matching id
     */
    public Movie getActorByID(int actorid) {
        ArrayList<Movie> actorList = getAllActors();
        for (Movie actor : actorList) {
            if (actor.getActorID() == actorid) {
                return actor;
            }
        }
        return null;
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
                        result.getString(3), getMoviesFtDirector(result.getInt(1))));
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
    
    /**
     * Get director by id
     * @param directorid id of actor to search
     * @return actor matching id
     */
    public Movie getDirectorByID(int directorid) {
        ArrayList<Movie> directorList = getAllDirectors();
        for (Movie director : directorList) {
            if (director.getDirectorID() == directorid) {
                return director;
            }
        }
        return null;
    }
    
    /**
     * Get directors name
     * @param directorID ID to search for
     * @return String representation of Full Name
     */
    public String getDirector(int directorID) {
        directorName  = "";
        String sql = "SELECT FIRSTNAME, LASTNAME FROM DIRECTORS WHERE ID=" + 
                Integer.toString(directorID);
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                directorName = result.getString(1) + " " + result.getString(2);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return directorName;        
    }
    
    /**
     * Get the String representation of genre
     * @param genreID ID to search for
     * @return String representation of genre
     */
    public String getGenre(int genreID) {
        genre = "";
        String sql = "SELECT GENRE FROM GENRES WHERE ID=" + 
                Integer.toString(genreID);
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                genre = result.getString(1);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return genre;        
    }
    
    /**
     * Converts the time in minutes (int) to a more readable String format
     * @param t Duration of movie in minutes
     * @return String represenation of run time.
     */
    public String getRunTime(int t) {
        int hours = t / 60;
        int minutes = t % 60;
        return String.format("%dh %02dmin", hours, minutes );
    }
    
    /**
     * Creates a list of actor IDs from the 'ACTORS_MOVIES' bridge table so 
     * that the details for each actor in each movie can be retrieved.
     * @param movieID ID of movie
     * @return A list of Strings, i.e, a list of actor names
     */
    public ArrayList<String> getActorsInMovie(int movieID) {
        String sql = "SELECT ACTORID FROM ACTORS_MOVIES WHERE MOVIEID=" + 
                Integer.toString(movieID);
        actorIDs = new ArrayList<>();
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                actorID = result.getInt(1);
                actorIDs.add(actorID);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return getActorNames(actorIDs);
    }
    
    /**
     * Uses the actor IDs obtained from the getActorsInMovie function (above) 
     * to search the 'ACTORS' table and retrieve the name of each actor;
     * @param actorIDs ID of each actor in bridge table 'ACTORS_MOVIES'.
     * @return A list of Strings, i.e, a list of actor names
     */
    public ArrayList<String> getActorNames(ArrayList<Integer> actorIDs) {
        actorNames = new ArrayList<>();
        for (Integer actorID: actorIDs) {
            String sql = "SELECT FIRSTNAME, LASTNAME FROM ACTORS WHERE ID=" + 
                    Integer.toString(actorID);

            try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                    Statement stmt = connect.createStatement();) {

                // execute statement - note DB needs to perform full processing
                // on calling executeQuery
                ResultSet result = stmt.executeQuery(sql);

                // while there are results
                while (result.next()) {
                    actorName = result.getString(1) + " " + result.getString(2);
                    actorNames.add(actorName);
                }           
                // deal with any potential exceptions
                // note: all resources are closed automatically - no need for finally
            } catch (SQLException sqle) {
                System.out.println("Message: " + sqle.getMessage());
                System.out.println("Code: " + sqle.getSQLState());
            }
        }
        return actorNames;
    }
    
    /**
     * Creates a list of movie IDs from the 'ACTORS_MOVIES' bridge table so 
     * that the movie titles can be retrieved from the 'MOVIES' table which 
     * feature the actor with ID == actorID.
     * @param actorID ID of actor 
     * @return A list of Strings, i.e, a list of movie titles
     */
    public ArrayList<String> getMoviesFtActor(int actorID) {
        String sql = "SELECT MOVIEID FROM ACTORS_MOVIES WHERE ACTORID=" + 
                Integer.toString(actorID);
        movieIDs = new ArrayList<>();
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                movieID = result.getInt(1);
                movieIDs.add(movieID);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return getMovieNames(movieIDs);
    }
    
    /**
     * Uses the movie IDs obtained from the getMoviesFtActor function (above) 
     * to search the 'MOVIES' table and retrieve the title of each movie.
     * @param movieIDs ID of each movie in bridge table 'ACTORS_MOVIES'.
     * @return A list of Strings, i.e, a list of movie titles
     */
    public ArrayList<String> getMovieNames(ArrayList<Integer> movieIDs) {
        movieNames = new ArrayList<>();
        for (Integer movieID: movieIDs) {
            String sql = "SELECT TITLE FROM MOVIES WHERE ID=" + 
                    Integer.toString(movieID);

            try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                    Statement stmt = connect.createStatement();) {

                // execute statement - note DB needs to perform full processing
                // on calling executeQuery
                ResultSet result = stmt.executeQuery(sql);

                // while there are results
                while (result.next()) {
                    movieName = result.getString(1);
                    movieNames.add(movieName);
                }           
                // deal with any potential exceptions
                // note: all resources are closed automatically - no need for finally
            } catch (SQLException sqle) {
                System.out.println("Message: " + sqle.getMessage());
                System.out.println("Code: " + sqle.getSQLState());
            }
        }
        return movieNames;
    }
    
    /**
     * Searches the 'MOVIES' table for any movies which match directorID
     * @param directorID ID of director
     * @return A list of Strings, i.e, a list of movie titles directed by directorID
     */
    public ArrayList<String> getMoviesFtDirector(int directorID) {
        movieNames = new ArrayList<>();
        String sql = "SELECT TITLE FROM MOVIES WHERE DIRECTOR=" + 
                Integer.toString(directorID);
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                movieName = result.getString(1);
                movieNames.add(movieName);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return movieNames;
    }
}
