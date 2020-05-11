/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sean
 */
@XmlRootElement
public class Movie {
    
    private int movieID;
    private String title;
    private String description;
    private int runTime;
    private LocalDate releaseDate;
    private String release;             // java.lang.NoSuchMethodError: java.time.LocalDate.<init>()
    private String trailer;
    private String image;
    private int actorID;
    private String firstName;
    private String lastName;
    private String email;
    private int directorID;
    private int genreID;
    private String genre;
    private String runningTime;
    private String directorFullName;
    private ArrayList<String> actorNames;
    private ArrayList<String> movieNames;
   
    /**
     * Default Constructor
     */
    public Movie() {
    }

    /**
     * Creates a Movie object with given information
     * @param movieID ID of movie
     * @param title Title of movie
     * @param description Movie description
     * @param runTime Duration of movie in minutes
     * @param releaseDate Date of movie release 
     * @param actorID ID of actor
     * @param firstName First name of actor or director
     * @param lastName Last name of actor or director
     * @param directorID ID of director
     * @param genreID ID of genre
     * @param genre Genre of movie
     */
    public Movie(int movieID, String title, String description, int runTime, 
            LocalDate releaseDate, String trailer, int actorID, String firstName, 
            String lastName, int directorID, int genreID, String genre) {
        this.movieID = movieID;
        this.title = title;
        this.description = description;
        this.runTime = runTime;
        this.releaseDate = releaseDate;
        this.trailer = trailer;
        this.actorID = actorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.directorID = directorID;
        this.genreID = genreID;
        this.genre = genre;
    }
    
    /**
     * Creates a Movie (actor) object with given information
     * @param firstName First name of actor
     * @param lastName Last name of actor
     * @param image picture of actor
     */
    public Movie(String firstName, String lastName, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }
    
    /**
     * Creates a Movie (actor) object with given information
     * @param actorID ID of actor
     * @param firstName First name of actor
     * @param lastName Last name of actor
     * @param image picture of actor
     * @param movieNames Movies which actor has appeared in.
     */
    public Movie(int actorID, String firstName, String lastName, 
            String image, ArrayList<String> movieNames) {
        this.actorID = actorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.movieNames = movieNames;
    }
    
    /**
     * Creates a Movie (genre) object with given information
     * @param genre genre of the movie
     */
    public Movie(String genre) {
        this.genre = genre;
    }
    
    /**
     * Creates a Movie (director) object with given information
     * @param directorID ID of actor
     * @param firstName First name of actor or director
     * @param lastName Last name of actor or director
     * @param movieNames Movies directed
     */
    public Movie(int directorID, String firstName, String lastName,
            ArrayList<String> movieNames) {
        this.directorID = directorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.movieNames = movieNames;
    }
    /**
     * Creates a Movie (director) object with given information
     * @param directorID ID of actor
     * @param firstName First name of actor or director
     * @param lastName Last name of actor or director
     */
    public Movie(int directorID, String firstName, String lastName) {
        this.directorID = directorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Creates a Movie (director) object with given information
     * @param firstName First name of actor or director
     * @param lastName Last name of actor or director
     */
    public Movie(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Creates a Movie (movie) object with given information
     * @param title Title of movie
     * @param description Movie description
     * @param runTime Duration of movie in minutes
     * @param releaseDate Date of movie release 
     * @param trailer link to trailer
     * @param directorID ID of director
     * @param genreID ID of genre
     */
    public Movie(String title, String description, int runTime, 
            LocalDate releaseDate, String trailer, int directorID, int genreID) {
        this.title = title;
        this.description = description;
        this.runTime = runTime;
        this.releaseDate = releaseDate;
        this.trailer = trailer;
        this.directorID = directorID;
        this.genreID = genreID;
    }
    
    
    // probably not needed
    /**
     * Creates a Movie (movie) object with given information
     * @param movieID ID of movie
     * @param title Title of movie
     * @param description Movie description
     * @param runTime Duration of movie in minutes
     * @param releaseDate Date of movie release 
     * @param directorID ID of director
     * @param genreID ID of genre
     */
    public Movie(int movieID, String title, String description, int runTime, 
            LocalDate releaseDate, int directorID, int genreID) {
        this.movieID = movieID;
        this.title = title;
        this.description = description;
        this.runTime = runTime;
        this.releaseDate = releaseDate;
        this.directorID = directorID;
        this.genreID = genreID;
    }
    
    // probably not needed
    /**
     * Creates a Movie (movie) object with given information
     * @param movieID ID of movie
     * @param title Title of movie
     * @param description Movie description
     * @param runningTime Duration of movie as a String
     * @param release Date of movie release in String format
     * @param trailer Link to trailer
     * @param directorFullName Full name of director
     * @param genre Genre of movie
     * @param actorNames Names of actors which appeared in movie.
     */
    public Movie(int movieID, String title, String description, String runningTime, 
            String release, String trailer, String directorFullName, 
            String genre, ArrayList<String> actorNames) {
        this.movieID = movieID;
        this.title = title;
        this.description = description;
        this.runningTime = runningTime;
        this.release = release;
        this.trailer = trailer;
        this.directorFullName = directorFullName;
        this.genre = genre;
        this.actorNames = actorNames;
    }
    
    /**
     * Creates a Movie (Actors_Movies) object with given information
     * @param actorID ID of actor
     * @param movieID ID of movie
     */
    public Movie(int actorID, int movieID) {
        this.actorID = actorID;
        this.movieID = movieID;
    }
    
    /**
     * Copy Constructor
     * @param m original movie object
     */
    public Movie(Movie m) {
        this.movieID = m.movieID;
        this.title = m.title;
        this.description = m.description;
        this.runTime = m.runTime;
        this.releaseDate = m.releaseDate;
        this.actorID = m.actorID;
        this.firstName = m.firstName;
        this.lastName = m.lastName;
        this.directorID = m.directorID;
        this.genreID = m.genreID;
        this.genre = m.genre;
    }

    /**
     * Get the value of movieID
     * 
     * @return the value of movieID 
     */
    public int getMovieID() {
        return movieID;
    }
    
    /**
     * Set the value of movieID
     * 
     * @param movieID new value of movieID 
     */
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    /**
     * Get the value of title
     * 
     * @return the value of title 
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Set the value of title
     * 
     * @param title new value of title 
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get the value of description
     * 
     * @return the value of description 
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set the value of description
     * 
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Get the value of runTime
     * 
     * @return the value of runTime
     */
    public int getRunTime() {
        return runTime;
    }
    
    /**
     * Set the value of runTime
     * 
     * @param runTime new value of runTime 
     */
    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
    
    /**
     * Get the value of releaseDate
     * 
     * @return the value of releaseDate 
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    
    /**
     * Set the value of releaseDate
     * 
     * @param releaseDate new value of releaseDate 
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    /**
     * Get the value of trailer
     * 
     * @return the value of trailer 
     */
    public String getTrailer() {
        return trailer;
    }
    
    /**
     * Set the value of trailer
     * 
     * @param trailer new value of trailer 
     */
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    
    /**
     * Get the value of actorID
     * 
     * @return the value of actorID 
     */
    public int getActorID() {
        return actorID;
    }
    
    /**
     * Set the value of actorID
     * 
     * @param actorID new value of actorID 
     */
    public void setActorID(int actorID) {
        this.actorID = actorID;
    }
    
    /**
     * Get the value of firstName
     * 
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Set the value of firstName
     * 
     * @param firstName new value of firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Get the value of lastName
     * 
     * @return the value of lastName
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Set the value of lastName
     * 
     * @param lastName new value of lastName 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Get the value of email
     * 
     * @return the value of email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Set the value of email
     * 
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Get the value of directorID
     * 
     * @return the value of directorID
     */
    public int getDirectorID() {
        return directorID;
    }
    
    /**
     * Set the value of directorID
     * 
     * @param directorID new value of directorID 
     */
    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }
    
    /**
     * Get the value of genreID
     * 
     * @return the value of genreID
     */
    public int getGenreID() {
        return genreID;
    }
    
    /**
     * Set the value of genreID
     * 
     * @param genreID new value of genreID 
     */
    public void setGenreID(int genreID) {
        this.genreID = genreID;
    }
    
    /**
     * Get the value of genre
     * 
     * @return the value of genre
     */
    public String getGenre() {
        return genre;
    }
    
    /**
     * Set the value of genre
     * 
     * @param genre new value of genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**
     * Get the value of release
     * 
     * @return the value of release
     */
    public String getRelease() {
        return release;
    }

    /**
     * Set the value of release
     * 
     * @param release new value of release as a string
     */
    public void setRelease(String release) {
        this.release = release;
    }
    
    /**
     * Get the value of image
     * 
     * @return the value of image
     */
    public String getImage() {
        return image;
    }
    
    /**
     * Set the value of image
     * 
     * @param image new value of image
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    /**
     * Get the value of running time as String
     * 
     * @return the value of running time
     */
    public String getRunningTime() {
        return runningTime;
    }
    
    /**
     * Set the value of running time
     * 
     * @param runningTime new value of runningTime
     */
    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }
    
    /**
     * Get the value of the directors full name
     * 
     * @return the value of directorFullName
     */
    public String getDirectorFullName() {
        return directorFullName;
    }
    
    /**
     * Set the value of directorFullName
     * 
     * @param directorFullName Full name of director
     */
    public void setDirectorFullName(String directorFullName) {
        this.directorFullName = directorFullName;
    }

    /**
     * Get the value of actorNames
     * 
     * @return the value of actorNames
     */
    public ArrayList<String> getActorNames() {
        return actorNames;
    }
    
    /**
     * Set the value of actorNames
     * 
     * @param actorNames Names of actors
     */
    public void setActorNames(ArrayList<String> actorNames) {
        this.actorNames = actorNames;
    }
    
    /**
     * Get the value of movieNames
     * 
     * @return the value of movieNames
     */
    public ArrayList<String> getMovieNames() {
        return movieNames;
    }
    
    /**
     * Set the value of movieNames
     * 
     * @param movieNames Names of actors
     */
    public void setMovieNames(ArrayList<String> movieNames) {
        this.movieNames = movieNames;
    }
}
