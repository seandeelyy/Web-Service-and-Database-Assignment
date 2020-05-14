/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import resources.Movie;
import java.util.ArrayList;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Sean
 */
@ApplicationScoped
@ManagedBean
public class RestClient {
    
    private final String REST_SERVICE_URL = "http://localhost:8080/WebServiceDB/services/MyRestService";
    private int tablesCreated;
    private int movieAdded;
    private int actorAdded;
    private int Actors_MoviesAdded;
    private boolean tablesNotDeleted;
    private String actorName;
    private int actorID;
    private String genre;
    private String movieTitle;
    private String directorName;
    private int directorID;
    
    ArrayList<Movie> actors = new ArrayList<>();
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Movie> directors = new ArrayList<>();

    /**
     * 1. Create Tables
     * Creates and fills the tables as shown in 'tables.png' using @POST.
     * @return Home page "index.xhtml".
     */
    public String testCreateTables() {
        Client client = ClientBuilder.newClient();        
        Form form = new Form();
        Response callResult = client
                .target(REST_SERVICE_URL).path("/tables/create")
                .request(MediaType.APPLICATION_XML).post(Entity.entity(form, 
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                Response.class);
        tablesCreated = (callResult.getStatus() == 200) ? 1 : 2;
        
        return "index";
    }
    
    /**
     * 2. One-to-One Query
     * Retrieves all information about an actor from the 'Actors' table and
     * from the 'ActorCredentials' table using @GET.
     * @param passedActorName Name of actor to search
     * @param flag Flag used to indicate which actorName to use.
     * @return Page containing information about actor.
     */
    public String testGetActorByName(String passedActorName, boolean flag){
        Client client = ClientBuilder.newClient();
        actors = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        if(flag) actorName = passedActorName;
        actors = client.target(REST_SERVICE_URL).path("/actors/{actorname}")
                .resolveTemplate("actorname", actorName).request().get(list);
        
        return "allActors";
    }
    
    /**
     * 3/4. One-to-Many Query AND Many-to-Many Query
     * Retrieves movies from the database based on their Genre (One-to-Many) AND
     * also retrieves the actors featured in said movies from the bridge table
     * 'Actors_Movies' (Many-to-Many) using @GET.
     * @return Page containing information about movie(s).
     */
    public String testGetMovieByGenre(){
        Client client = ClientBuilder.newClient();
        movies = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        movies = client.target(REST_SERVICE_URL).path("/movies/genre/{genre}")
                .resolveTemplate("genre", genre).request().get(list);
        
        return "allMovies";
    }
    
    /**
     * 5. Add an entry to a table with a One-to-One relationship
     * This inserts an actor, 'John Travolta' in this case, into the 'Actors'
     * table and adds his credentials to the 'ActorCredentials' table using @POST.
     * @param actorFirstName First name of actor to add
     * @param actorLastName Last name of actor to add
     * @param year Year actor was born
     * @param month Month actor was born
     * @param day Day actor was born
     * @param actorEmail Email address of actor.
     * @param actorImage Image of actor.
     * @return Home page "index.xhtml" where a message will be displayed 
     * indicating whether or not the actor was sucessfully added or not.
     */
    public String testAddActor(String actorFirstName, String actorLastName,
            int year, int month, int day, String actorEmail, String actorImage){
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("actorFirstName", actorFirstName);
        form.param("actorLastName", actorLastName);
        form.param("year", Integer.toString(year));
        form.param("month", Integer.toString(month));
        form.param("day", Integer.toString(day));
        form.param("actorEmail", actorEmail);
        form.param("actorImage", actorImage);
        
        Response callResult = client
                .target(REST_SERVICE_URL).path("/actors/addActor")
                .request(MediaType.APPLICATION_XML).post(Entity.entity(form, 
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);        
        actorAdded = (callResult.getStatus() == 200) ? 1 : 2;
      
        return "index";
    }
    
    /**
     * 6. Add an entry to a table with a One-to-Many relationship
     * This inserts the movie 'Pulp Fiction' into the 'Movies' table and also
     * retrieves the Director ID (One-to-Many) of the Director from the 
     * 'Directors' table AND retrieves the Genre ID (One-to-Many) of the Genre 
     * from the 'Genres' table using @POST.
     * @param title Title of Movie to add to database
     * @param description Description of movie to add.
     * @param runTime Running time of the movie in minutes
     * @param year Year the movie was released
     * @param month Month the movie was released
     * @param day Day the movie was released
     * @param trailer Link to movie trailer
     * @param directorFirstName First name of movie Director
     * @param directorLastName Last name of movie Director
     * @param genre Genre of movie as a String.
     * @return Home page "index.xhtml" where a message will be displayed 
     * indicating whether or not the movie was sucessfully added or not.
     */ 
    public String testAddMovie(String title, String description, int runTime,
            int year, int month, int day, String trailer, String directorFirstName, 
            String directorLastName, String genre){
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("title", title);
        form.param("description", description);
        form.param("runTime", Integer.toString(runTime));
        form.param("year", Integer.toString(year));
        form.param("month", Integer.toString(month));
        form.param("day", Integer.toString(day));
        form.param("trailer", trailer);
        form.param("directorFirstName", directorFirstName);
        form.param("directorLastName", directorLastName);
        form.param("genre", genre);

        Response callResult = client
                .target(REST_SERVICE_URL).path("/movies/addMovie")
                .request(MediaType.APPLICATION_XML).post(Entity.entity(form, 
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE),Response.class);        
        movieAdded = (callResult.getStatus() == 200) ? 1 : 2;
      
        return "index";
    }
    
    /**
     * 7. Add an entry to a table with a Many-to-Many relationship
     * This inserts an Actors ID and a Movies ID into the 'Actors_Movies' bridge
     * table (Many-to-Many) IF the Actor and Movie already exist in the database.
     * @param actorFirstName First name of Actor who is to be added
     * @param actorLastName Last name of Actor who is to be added
     * @param title Title of Movie to be added
     * @return Home page "index.xhtml" where a message will be displayed 
     * indicating whether or not the operation was successful or not.
     */
    public String testAddActors_Movies(String actorFirstName, String actorLastName,
            String title){
        Client client = ClientBuilder.newClient();
        Form form = new Form();
        form.param("actorFirstName", actorFirstName);
        form.param("actorLastName", actorLastName);
        form.param("title", title);
        
        Response callResult = client
                .target(REST_SERVICE_URL).path("/actors/movies/addActors_Movies")
                .request(MediaType.APPLICATION_XML).post(Entity.entity(form, 
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
        Actors_MoviesAdded = (callResult.getStatus() == 200) ? 1 : 2;
      
        return "index";
    }

    /**
     * 8. Delete/Drop Tables
     * This will delete the tables which were created in step 1 using @DELETE
     * @return Goodbye page 'tablesDeleted.xhtml' if tables are deleted
     */
    public String testDeleteTables() {
        Client client = ClientBuilder.newClient();
      
        Response callResult = client
                .target(REST_SERVICE_URL).path("/tables/delete")
                .request(MediaType.APPLICATION_XML).delete(Response.class);
        if(callResult.getStatus() == 200) {
            return "tablesDeleted";
        }
        else {
            tablesNotDeleted = true;
            return "index";
        }
    }
    
    // *************************************************************************
    // The following are EXTRA web services which can be invoked by running
    // 'extraServices.xhtml'
    // *************************************************************************
    
    /**
     * This function returns all Actors present in the database from the 'Actors'
     * table along with all of their credentials from the 'ActorCredentials' 
     * table using @GET.
     * @return 'allActors.xhtml' which will display the resulting information.
     */
    public String testGetAllActors(){
        Client client = ClientBuilder.newClient();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};        
        actors = client.target(REST_SERVICE_URL).path("/actors")
                .request().get(list);
        return "allActors";
    }
    
    /**
     * Returns all actors
     * @return all actors
     */   
    public ArrayList<Movie> getActors() {
        return actors;
    }
    
    // Get list of all directors
    // @GET
    /**
     * This function returns all Directors present in the database from the
     * 'Directors' table along with all the movies which they directed using @GET.
     * @return 'allDirectors.xhtml' which will display the resulting information.
     */
    public String testGetAllDirectors(){
        Client client = ClientBuilder.newClient();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        
        directors = client.target(REST_SERVICE_URL).path("/directors")
                .request().get(list);
        return "allDirectors";
    }
    
    /**
     * Returns all directors
     * @return all directors
     */ 
    public ArrayList<Movie> getDirectors() {
        return directors;
    }
    
    // Get list of all movies
    // @GET
    /**
     * This function returns all Movies present in the database from the 'Movies'
     * table along with the Genre ('Genres' table) and the name of the Director 
     * who directed it ('Directors' table) using @GET.
     * @return 'allMovies.xhtml' which will display the resulting information.
     */
    public String testGetAllMovies(){
        Client client = ClientBuilder.newClient();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        movies = client.target(REST_SERVICE_URL).path("/movies").request().get(list);
        return "allMovies";
    }
    
    /**
     * Returns all movies
     * @return all movies
     */   
    public ArrayList<Movie> getMovies() {
        return movies;
    }
    
    /**
     * Retrieves all information about an actor from the 'Actors' table and
     * from the 'ActorCredentials' table using @GET.
     * @param passedActorID ID of actor to search
     * @param flag Flag used to indicate which actorID to use.
     * @return Page containing information about actor.
     */
    public String testGetActorByID(int passedActorID, boolean flag){
        Client client = ClientBuilder.newClient();
        actors = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        if(flag) actorID = passedActorID;
        actors = client.target(REST_SERVICE_URL).path("/actor/{actorid}")
                .resolveTemplate("actorid", actorID).request().get(list);
        
        return "allActors";
    }
    
    /**
     * This function retrieves directors from the 'Directors' table based on 
     * the directors name.
     * @param passedDirectorName Name of director to search
     * @param flag Determines which directorName should be used.
     * @return 'allDirectors.xhtml' which will display the resulting information 
     * about any director(s) which were found
     */
    public String testGetDirectorByName(String passedDirectorName, boolean flag){
        Client client = ClientBuilder.newClient();
        directors = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        if(flag) directorName = passedDirectorName;
        directors = client.target(REST_SERVICE_URL).path("/directors/{directorname}")
                .resolveTemplate("directorname", directorName).request().get(list);

        return "allDirectors";
    }
    
    /**
     * This function retrieves a director from the 'Directors' table based on 
     * the directors ID.
     * @param passedDirectorID Name of director to search
     * @param flag Determines which directorName should be used.
     * @return 'allDirectors.xhtml' which will display the resulting information 
     * about any director(s) which were found
     */
    public String testGetDirectorByID(int passedDirectorID, boolean flag){
        Client client = ClientBuilder.newClient();
        directors = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        if(flag) directorID = passedDirectorID;
        directors = client.target(REST_SERVICE_URL).path("/director/{directorid}")
                .resolveTemplate("directorid", directorID).request().get(list);

        return "allDirectors";
    }
    
    /**
     * This function retrieves movies from the 'Movies' table based on the title.
     * @param passedMovieTitle Title of movie to search
     * @param flag Determines which movieTitle should be used.
     * @return 'allMovies.xhtml' which will display the resulting information 
     * about movie(s) which were found
     */
    public String testGetMovieByTitle(String passedMovieTitle, boolean flag){
        Client client = ClientBuilder.newClient();
        movies = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        if(flag) movieTitle = passedMovieTitle;
        movies = client.target(REST_SERVICE_URL).path("/movies/{title}")
                .resolveTemplate("title", movieTitle).request().get(list);
        
        return "allMovies";
    }
    
    /**
     * Get the value of tablesCreated
     * 
     * @return the value of tablesCreated
     */
    public int getTablesCreated() {
        return tablesCreated;
    }
    
    /**
     * Set the value of tablesCreated
     * 
     * @param tablesCreated new value of tablesCreated
     */
    public void setTablesCreated(int tablesCreated) {
        this.tablesCreated = tablesCreated;
    }
    
    /**
     * Get the value of movieAdded
     * 
     * @return the value of movieAdded
     */
    public int getMovieAdded() {
        return movieAdded;
    }
    
    /**
     * Set the value of movieAdded
     * 
     * @param movieAdded new value of movieAdded
     */
    public void setMovieAdded(int movieAdded) {
        this.movieAdded = movieAdded;
    }
    
    /**
     * Get the value of actorAdded
     * 
     * @return the value of actorAdded
     */
    public int getActorAdded() {
        return actorAdded;
    }
    
    /**
     * Set the value of actorAdded
     * 
     * @param actorAdded new value of actorAdded 
     */
    public void setActorAdded(int actorAdded) {
        this.actorAdded = actorAdded;
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
     * Get the value of actorName
     * 
     * @return the value of actorName
     */
    public String getActorName() {
        return actorName;
    }
    
    /**
     * Set the value of actorName
     * 
     * @param actorName new value of actorName
     */
    public void setActorName(String actorName) {
        this.actorName = actorName;
    }
    
    /**
     * Get the value of directorName
     * 
     * @return the value of directorName
     */
    public String getDirectorName() {
        return directorName;
    }
    
    /**
     * Set the value of directorName
     * 
     * @param directorName new value of directorName 
     */
    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }
    
    /**
     * Get the value of movieTitle
     * 
     * @return the value of movieTitle
     */
    public String getMovieTitle() {
        return movieTitle;
    }
    
    /**
     * Set the value of movieTitle
     * 
     * @param movieTitle new value of movieTitle
     */
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
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
     * Get the value of tablesNotDeleted
     * 
     * @return the value of tablesNotDeleted
     */
    public boolean isTablesNotDeleted() {
        return tablesNotDeleted;
    }
    
    /**
     * Set the value of tablesNotDeleted
     * 
     * @param tablesNotDeleted new value of tablesNotDeleted 
     */
    public void setTablesNotDeleted(boolean tablesNotDeleted) {
        this.tablesNotDeleted = tablesNotDeleted;
    }

    
    /**
     * Get the value of Actors_MoviesAdded
     * 
     * @return the value of Actors_MoviesAdded
     */
    public int getActors_MoviesAdded() {
        return Actors_MoviesAdded;
    }
    
    /**
     * Set the value of Actors_MoviesAdded
     * 
     * @param Actors_MoviesAdded new value of Actors_MoviesAdded
     */
    public void setActors_MoviesAdded(int Actors_MoviesAdded) {
        this.Actors_MoviesAdded = Actors_MoviesAdded;
    }

}