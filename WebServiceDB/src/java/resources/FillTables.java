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
    
    
    public boolean fillActorsTable() {
        
        Movie[] actors = {
            new Movie("Tom", "Hardy"), new Movie("Leonardo", "DiCaprio"),
            new Movie("Michael", "Fassbender"), new Movie("Christian", "Bale"), 
            new Movie("Heath", "Ledger"), new Movie("Will", "Ferrell") 
        }; 
        
        List<Movie> actorList = Arrays.asList(actors);
        
        String sql = "INSERT INTO ACTORS(FIRSTNAME, LASTNAME) VALUES(?,?)";
        
        // use try with resource
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                PreparedStatement pstmt = connect.prepareStatement(sql);) {

            ListIterator<Movie> lit = actorList.listIterator();
            Movie m;
            while (lit.hasNext()) {
                m = lit.next();
                pstmt.setString(1, m.getFirstName());
                pstmt.setString(2, m.getLastName());

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + m + " has been added");
                }
            }

        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return true;
    }
    
    
    public boolean fillDirectorsTable() {
        
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
                            "Row for " + m + " has been added");
                }
            }

        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return true;
    }
    
    
    public boolean fillGenresTable() {
        
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
                            "Row for " + m + " has been added");
                }
            }

        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return true;
    }
    
    public boolean fillMoviesTable() {
        
        Movie[] movies = {
            new Movie("The Dark Knight", "When the menace known as the Joker "
                    + "wreaks havoc and chaos on the people of Gotham, Batman "
                    + "must accept one of the greatest psychological and physical "
                    + "tests of his ability to fight injustice.", 152, 
                    LocalDate.of(2008, 7, 18), 1001, 1),
            new Movie("Prometheus", "Following clues to the origin of mankind, "
                    + "a team finds a structure on a distant moon, but they soon "
                    + "realize they are not alone.", 124, 
                    LocalDate.of(2012, 6, 8), 1002, 3),
            new Movie("Alien: Covenant", "The crew of a colony ship, bound for "
                    + "a remote planet, discover an uncharted paradise with a "
                    + "threat beyond their imagination, and must attempt a "
                    + "harrowing escape.", 122, 
                    LocalDate.of(2017, 5, 19), 1002, 2),
            new Movie("Lawless", "Set in Depression-era Franklin County, Virginia, "
                    + "a trio of bootlegging brothers are threatened by a new "
                    + "special deputy and other authorities angling for a cut "
                    + "of their profits.", 116, 
                    LocalDate.of(2012, 8, 29), 1003, 4),
            new Movie("The Revenant ", "A frontiersman on a fur trading expedition "
                    + "in the 1820s fights for survival after being mauled by a "
                    + "bear and left for dead by members of his own hunting team.", 
                    156, LocalDate.of(2015, 1, 8), 1004, 1),
            new Movie("Step Brothers", "Two aimless middle-aged losers still "
                    + "living at home are forced against their will to become "
                    + "roommates when their parents marry.", 98, 
                    LocalDate.of(208, 7, 25), 1000, 5),
        }; 
        
        List<Movie> movieList = Arrays.asList(movies);
        
        String sql = "INSERT INTO MOVIES(TITLE, DESCRIPTION, RUNTIME, RELEASEDATE,"
                + "DIRECTOR, GENRE) VALUES(?,?,?,?,?,?)";
        
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
                pstmt.setInt(5, m.getDirectorID());
                pstmt.setInt(6, m.getGenreID());

                // execute statement 
                if (pstmt.executeUpdate() == 1) {
                    System.out.println(
                            "Row for " + m + " has been added");
                }
            }

        } catch (SQLException sqle) {
            System.out.println("Message: " + sqle.getMessage());
            System.out.println("Code: " + sqle.getSQLState());
        }
        return true;
    }
    
}
