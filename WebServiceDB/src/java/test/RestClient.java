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
import javax.ws.rs.client.WebTarget;
import resources.SimpleObject;
import javax.ws.rs.core.GenericType;
import resources.User;
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
    
    private String REST_SERVICE_URL = "http://localhost:8080/WebServiceDB/services/MyRestService/movies";
    private int tablesCreated;
    private int movieAdded;
    private int uid;
    private String actorName;
    private String genre;
    private String movieTitle;
    private String directorName;
    
    ArrayList<Movie> actors = new ArrayList<>();
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Movie> directors = new ArrayList<>();
    
    // Get list of all users
    // @GET
    public void testGetAllUsers(){
        Client client = ClientBuilder.newClient();
        GenericType<ArrayList<User>> list = new GenericType<ArrayList<User>>() {};
        
        ArrayList<User> users = client.target(REST_SERVICE_URL).request().get(list);
      
        for (User u: users) {
            System.out.println("ID:   " + u.getUid() + "\n\tName: " + u.getFirstName() + 
                    " " + u.getSurname() + "\n\tType: " + u.getUtype());
        }
    }
    
    // Get list of all actors
    // @GET
    public String testGetAllActors(){
        Client client = ClientBuilder.newClient();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        
        actors = client.target("http://localhost:8080/WebServiceDB/services/MyRestService/actors").request().get(list);
        return "allActors";
    }
    
    public ArrayList<Movie> getActors() {
        return actors;
    }
    
    // Get list of all movies
    // @GET
    public String testGetAllMovies(){
        Client client = ClientBuilder.newClient();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        
        movies = client.target(REST_SERVICE_URL).request().get(list);
        return "allMovies";
    }
    
    public ArrayList<Movie> getMovies() {
        return movies;
    }
    
    // Get list of all directors
    // @GET
    public String testGetAllDirectors(){
        Client client = ClientBuilder.newClient();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        
        directors = client.target("http://localhost:8080/WebServiceDB/services/MyRestService/directors")
                .request().get(list);
        return "allDirectors";
    }
    
    public ArrayList<Movie> getDirectors() {
        return directors;
    }
    
    
    // Get actor(s) by name
    // @GET
    public String testGetActor(String passedActorName, boolean flag){
        Client client = ClientBuilder.newClient();
        actors = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        if(flag) actorName = passedActorName;
        actors = client.target("http://localhost:8080/WebServiceDB/services/MyRestService/actors")
                .path("/{actorname}").resolveTemplate("actorname", actorName).request().get(list);
        return "allActors";
    }
    
    // Get movie(s) by genre
    // @GET
    public String testGetMovieByGenre(){
        Client client = ClientBuilder.newClient();
        movies = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        movies = client.target("http://localhost:8080/WebServiceDB/services/MyRestService/movies")
                .path("genre/{genre}").resolveTemplate("genre", genre).request().get(list);
        
        return "allMovies";
    }
    
    // Get movie(s) by title
    // @GET
    public String testGetMovieByTitle(String passedMovieTitle, boolean flag){
        Client client = ClientBuilder.newClient();
        movies = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        if(flag) movieTitle = passedMovieTitle;
        movies = client.target("http://localhost:8080/WebServiceDB/services/MyRestService/movies")
                .path("/{title}").resolveTemplate("title", movieTitle).request().get(list);
        
        return "allMovies";
    }
    
    // Get director by name
    // @GET
    public String testGetDirector(String passedDirectorName, boolean flag){
        Client client = ClientBuilder.newClient();
        directors = new ArrayList<>();
        GenericType<ArrayList<Movie>> list = new GenericType<ArrayList<Movie>>() {};
        if(flag) directorName = passedDirectorName;
        directors = client.target("http://localhost:8080/WebServiceDB/services/MyRestService/directors")
                .path("/{directorname}").resolveTemplate("directorname", directorName).request().get(list);
      
        for (Movie director: directors) {
            System.out.println("ID:   " +director.getDirectorID() + "\n\tName: " + director.getFirstName() + 
            " " + director.getLastName());
        }
        
        return "allDirectors";
    }
    
    // Creates and fills tables
    // @POST
    public String testCreateTables() {
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
      
        Response callResult = client
                .target("http://localhost:8080/WebServiceDB/services/MyRestService/tables")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                Response.class);
        if(callResult.getStatus() == 200) {
            tablesCreated = 1;
            System.out.println(callResult.getStatus());
        }
        else {
            System.out.println(callResult.getStatus());
            tablesCreated = 2;
        }
        return "index";
    }
    
    // Add a User
    // @POST
    public String testAddUser() {
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("fname", "Sean");
        form.param("sname", "Deely");
        form.param("type", "Freelancer");
      
        Response callResult = client
                .target(REST_SERVICE_URL).request(MediaType.APPLICATION_XML)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                Response.class);
      
        System.out.println(callResult.getLocation());
      
        return "index";
    }
    
    
    // Get User by UID
    // @GET
    public String testGetUser(){
        Client client = ClientBuilder.newClient();
        User user = client.target(REST_SERVICE_URL).path("/{userid}")
                .resolveTemplate("userid", uid).request().get(User.class);
      
        System.out.println("ID:   " + user.getUid() + "\n\tName: " + user.getFirstName() + 
            " " + user.getSurname() + "\n\tType: " + user.getUtype());
        return "test";
    }
    
    
    // Update a User
    // @PUT
    public String testUpdateUser(int uid, String type){
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("uid", Integer.toString(uid));
        form.param("type", type);

        String callResult = client
                .target(REST_SERVICE_URL).request(MediaType.APPLICATION_XML)
                .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), 
                String.class);
      
        return "index";
    }
    
    // Adds a movie to the database
    // @PUT
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
                .target("http://localhost:8080/WebServiceDB/services/MyRestService/test").request(MediaType.APPLICATION_XML)
                .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), 
                Response.class);
        
        if(callResult.getStatus() == 200) {
            movieAdded = 1;
            System.out.println(callResult.getStatus());
        }
        else {
            System.out.println(callResult.getStatus());
            movieAdded = 2;
        }
      
        return "index";
    }
    
    // Adds a movie to the database
    // @PUT
    public String testAddActor(String actorFirstName, String actorLastName){
        Client client = ClientBuilder.newClient();
        
        Form form = new Form();
        form.param("actorFirstName", actorFirstName);
        form.param("actorLastName", actorLastName);
        
        Response callResult = client
                .target("http://localhost:8080/WebServiceDB/services/MyRestService/addActor").request(MediaType.APPLICATION_XML)
                .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), 
                Response.class);
        
//        if(callResult.getStatus() == 200) {
//            movieAdded = 1;
//            System.out.println(callResult.getStatus());
//        }
//        else {
//            System.out.println(callResult.getStatus());
//            movieAdded = 2;
//        }
      
        return "index";
    }
    
    
    // Delete a User
    // @DELETE
    public String testDeleteUser(){
        Client client = ClientBuilder.newClient();
      
        String user = client.target(REST_SERVICE_URL).path("/{userid}")
                .resolveTemplate("userid", 1016)
                .request().delete(String.class);

        return "test";
    }

    public int getTablesCreated() {
        return tablesCreated;
    }

    public void setTablesCreated(int tablesCreated) {
        this.tablesCreated = tablesCreated;
    }

    public int getMovieAdded() {
        return movieAdded;
    }

    public void setMovieAdded(int movieAdded) {
        this.movieAdded = movieAdded;
    }
    
    
    
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }  
    
}