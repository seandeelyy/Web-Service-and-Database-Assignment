/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Arrays;
import java.util.ListIterator;


/**
 *
 * @author Sean
 */
public class FillTables {
    
    private static final String URL = "jdbc:derby://localhost:1527/myDB";
    private static final String USER = "app";
    private static final String PASSWD = "app";

    /**
     * Adds actors to the ACTORS table
     * @return true if actors were sucessfully added, false otherwise
     */
    public boolean fillActorsTable() {
        
        boolean actorsAdded = false;
        
        Movie[] actors = {
            new Movie("Tom", "Hardy", "Tom Hardy.png"), 
            new Movie("Leonardo", "DiCaprio", "Leonardo DiCaprio.png"),
            new Movie("Michael", "Fassbender", "Michael Fassbender.png"), 
            new Movie("Christian", "Bale", "Christian Bale.png"), 
            new Movie("Heath", "Ledger", "Heath Ledger.png"), 
            new Movie("Will", "Ferrell", "Will Ferrell.png") 
        }; 
        
        List<Movie> actorList = Arrays.asList(actors);
        
        String sql = "INSERT INTO ACTORS(FIRSTNAME, LASTNAME, IMAGE) VALUES(?,?,?)";
        String sq2 = "INSERT INTO ACTORCREDENTIALS(EMAIL) VALUES(?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

            ListIterator<Movie> lit = actorList.listIterator();
            Movie m;
            while (lit.hasNext()) {
                m = lit.next();
                pstmt.setString(1, m.getFirstName());
                pstmt.setString(2, m.getLastName());
                pstmt.setString(3, m.getImage());

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + m.getFirstName() + " " 
                                    + m.getLastName() + " has been added");
                }
            } actorsAdded = true;
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return actorsAdded;
    }
    
    /**
     * Adds credentials to the ACTORCREDENTIALS table
     * @return true if credentials were sucessfully added, false otherwise
     */
    public boolean fillActorCredentialsTable() {
        
        boolean credentialsAdded = false;
        
        Movie[] emails = {
            new Movie("tomhardy@gmail.com"), new Movie("leonardodicaprio@gmail.com"), 
            new Movie("michael_fassbender@hotmail.com"), 
            new Movie("christianBale@yahoomail.com"), 
            new Movie("heath_ledger@gmail.com"), 
            new Movie("WillFerrel@studentmail.ul.ie"), 
        }; 
        
        List<Movie> emailList = Arrays.asList(emails);
        
        String sql = "INSERT INTO ACTORCREDENTIALS(EMAIL) VALUES(?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

            ListIterator<Movie> lit = emailList.listIterator();
            Movie m;
            while (lit.hasNext()) {
                m = lit.next();
                pstmt.setString(1, m.getGenre());

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + m.getGenre() + " has been added");
                }
            } credentialsAdded = true;
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return credentialsAdded;
    }

    /**
     * Adds directors to the DIRECTORS table
     * @return true if directors were sucessfully added, false otherwise
     */
    public boolean fillDirectorsTable() {
        
        boolean directorsAdded = false;
        
        Movie[] directors = {
            new Movie("Adam", "McKay"), new Movie("Christopher", "Nolan"),
            new Movie("Ridley", "Scott"), new Movie("John", "Hillcoat"), 
            new Movie("Alejandro", "Inarritu") 
        }; 
        
        List<Movie> directorList = Arrays.asList(directors);
        
        String sql = "INSERT INTO DIRECTORS(FIRSTNAME, LASTNAME) VALUES(?,?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

            ListIterator<Movie> lit = directorList.listIterator();
            Movie m;
            while (lit.hasNext()) {
                m = lit.next();
                pstmt.setString(1, m.getFirstName());
                pstmt.setString(2, m.getLastName());

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + m.getFirstName() + " " 
                                    + m.getLastName() + " has been added");
                }
            } directorsAdded = true;
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return directorsAdded;
    }
    
    /**
     * Adds genres to the GENRES table
     * @return true if genres were sucessfully added, false otherwise
     */
    public boolean fillGenresTable() {
        
        boolean genresAdded = false;
        
        Movie[] genres = {
            new Movie("Action"), new Movie("Sci-Fi"), new Movie("Adventure"), 
            new Movie("Crime"), new Movie("Comedy"), new Movie("Horror"),
            new Movie("Romance"), new Movie("Documentary"), new Movie("Drama"),
            new Movie("Thriller"), new Movie("Musical"), new Movie("Romantic Comedy")
        }; 
        
        List<Movie> genreList = Arrays.asList(genres);
        
        String sql = "INSERT INTO GENRES(GENRE) VALUES(?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

            ListIterator<Movie> lit = genreList.listIterator();
            Movie m;
            while (lit.hasNext()) {
                m = lit.next();
                pstmt.setString(1, m.getGenre());

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + m.getGenre() + " has been added");
                }
            } genresAdded = true;
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return genresAdded;
    }
    
    /**
     * Adds movies to the MOVIES table
     * @return true if movies were sucessfully added, false otherwise
     */
    public boolean fillMoviesTable() {
        
        boolean moviesAdded = false;
        
        Movie[] movies = {
            new Movie("The Dark Knight", "When the menace known as the Joker "
                    + "wreaks havoc and chaos on the people of Gotham, Batman "
                    + "must accept one of the greatest psychological and physical "
                    + "tests of his ability to fight injustice.", 152, 
                    LocalDate.of(2008, 7, 18), "https://youtu.be/EXeTwQWrcwY", 1001, 1),
            new Movie("Prometheus", "Following clues to the origin of mankind, "
                    + "a team finds a structure on a distant moon, but they soon "
                    + "realize they are not alone.", 124, 
                    LocalDate.of(2012, 6, 8), "https://youtu.be/34cEo0VhfGE", 1002, 3),
            new Movie("Alien: Covenant", "The crew of a colony ship, bound for "
                    + "a remote planet, discover an uncharted paradise with a "
                    + "threat beyond their imagination, and must attempt a "
                    + "harrowing escape.", 122, 
                    LocalDate.of(2017, 5, 19), "https://youtu.be/svnAD0TApb8", 1002, 2),
            new Movie("Lawless", "Set in Depression-era Franklin County, Virginia, "
                    + "a trio of bootlegging brothers are threatened by a new "
                    + "special deputy and other authorities angling for a cut "
                    + "of their profits.", 116, 
                    LocalDate.of(2012, 8, 29), "https://youtu.be/HibcC7w5l1g", 1003, 4),
            new Movie("The Revenant", "A frontiersman on a fur trading expedition "
                    + "in the 1820s fights for survival after being mauled by a "
                    + "bear and left for dead by members of his own hunting team.", 
                    156, LocalDate.of(2015, 1, 8), "https://youtu.be/LoebZZ8K5N0", 1004, 1),
            new Movie("Step Brothers", "Two aimless middle-aged losers still "
                    + "living at home are forced against their will to become "
                    + "roommates when their parents marry.", 98, 
                    LocalDate.of(2008, 7, 25), "https://youtu.be/CewglxElBK0", 1000, 5),
        }; 
        
        List<Movie> movieList = Arrays.asList(movies);
        
        String sql = "INSERT INTO MOVIES(TITLE, DESCRIPTION, RUNTIME, RELEASEDATE,"
                + "TRAILER, DIRECTOR, GENRE) VALUES(?,?,?,?,?,?,?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

            ListIterator<Movie> lit = movieList.listIterator();
            Movie m;
            while (lit.hasNext()) {
                m = lit.next();
                pstmt.setString(1, m.getTitle());
                pstmt.setString(2, m.getDescription());
                pstmt.setInt(3, m.getRunTime());
                pstmt.setDate(4, Date.valueOf(m.getReleaseDate().toString()));
                pstmt.setString(5, m.getTrailer());
                pstmt.setInt(6, m.getDirectorID());
                pstmt.setInt(7, m.getGenreID());

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + m.getTitle() + " has been added");
                }
            } moviesAdded = true;
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return moviesAdded;
    } 
    
    /**
     * Adds actors and movies to the ACTORS_MOVIES table
     * @return true if sucessfully added, false otherwise
     */
    public boolean fillActors_MoviesTable() {
        
        boolean Actors_MoviesAdded = false;
        
        Movie[] actors_movies = {
            new Movie(1000, 1003), new Movie(1000, 1004), new Movie(1001, 1004),
            new Movie(1002, 1001), new Movie(1002, 1002), new Movie(1003, 1000),
            new Movie(1004, 1000), new Movie(1005, 1005)
        }; 
        
        List<Movie> actor_movieList = Arrays.asList(actors_movies);
        
        String sql = "INSERT INTO ACTORS_MOVIES(ACTORID, MOVIEID) VALUES(?,?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

            ListIterator<Movie> lit = actor_movieList.listIterator();
            Movie m;
            while (lit.hasNext()) {
                m = lit.next();
                pstmt.setInt(1, m.getActorID());
                pstmt.setInt(2, m.getMovieID());

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for ActorID: " + m.getActorID() + " & MovieID: " 
                                    + m.getMovieID() + " has been added");
                }
            } Actors_MoviesAdded = true;
        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return Actors_MoviesAdded;
    }
}
