/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import resources.SimpleObject;
import resources.User;
import resources.Movie;
import resources.GetUserDataFromDB;
import resources.GetDataFromDB;
import resources.InsertDataIntoDB;
import resources.CreateTables;
import resources.FillTables;

/**
 *
 * @author Sean
 */
@Path("/MyRestService")
@ApplicationPath("/services")
public class RestService extends Application {
    
    GetUserDataFromDB userData = new GetUserDataFromDB();
    GetDataFromDB movieData = new GetDataFromDB();
    InsertDataIntoDB insertData = new InsertDataIntoDB();
    CreateTables createTables = new CreateTables();
    FillTables fillTables = new FillTables();
    
    // http://localhost:8080/WebServiceDB/services/MyRestService/users
    
    @GET
    @Path("/users")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<User> getUsers() { 
       return userData.getAllUsers();
    }    

    @GET
    @Path("/users/{userid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public User getUser(@PathParam("userid") int userid) { 
       return userData.getUser(userid);
    }
    
    @POST
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createUser(@FormParam("fname") String fname,      
        @FormParam("sname") String lname,
        @FormParam("type") String utype,
        @Context HttpServletResponse servletResponse) throws IOException{
      
        System.out.println("First Name: " + fname);
        System.out.println("Surname:    " + lname);
        System.out.println("User Type:  " + utype);
        
        userData.addUser(fname, lname, utype);
        
        return Response.created(URI.create("/users/" + fname)).build();
    }
    
    @PUT
    @Path("/users")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateUser(@FormParam("uid") int uid,
            @FormParam("type") String type,
            @Context HttpServletResponse servletResponse) throws IOException{

        userData.editUser(uid, type);
        return "";
    }
    
    @PUT
    @Path("/test")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String addMovie(@FormParam("title") String title,
            @FormParam("description") String description,
            @FormParam("runTime") int runTime,
            @FormParam("year") int year,
            @FormParam("month") int month,
            @FormParam("day") int day,
            @FormParam("trailer") String trailer,
            @FormParam("directorFirstName") String directorFirstName,
            @FormParam("directorLastName") String directorLastName,
            @FormParam("genre") String genre,
            @Context HttpServletResponse servletResponse) throws IOException{
        
        insertData.fillMoviesTable(title, description, runTime, year, month, 
                day, trailer, directorFirstName, directorLastName, genre);
        return "";
    }
   
   
    @DELETE
    @Path("/users/{userid}")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String deleteUser(@PathParam("userid") int uid ) {
        userData.deleteUser(uid);
        return "";
    }
    
    @POST
    @Path("/tables")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createTable() {
        
        if (createTables.createActorsTable() &&
            fillTables.fillActorsTable() &&
//            createTables.createActorCredentialsTable() &&
//            fillTables.fillActorCredentialsTable() &&
            createTables.createDirectorsTable() &&
            fillTables.fillDirectorsTable() &&
            createTables.createGenresTable() &&
            fillTables.fillGenresTable() &&
            createTables.createMoviesTable() &&
            fillTables.fillMoviesTable() &&
            createTables.createActors_MoviesTable() &&
            fillTables.fillActors_MoviesTable()) {
            
            System.out.println(Response.ok().build());
            return Response.ok().build();
        }
        else {
            System.out.println(Response.noContent().build());
             return Response.noContent().build();
        }
    }  
    
    @GET
    @Path("/movies")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getMovies() { 
        ArrayList<Movie> movies = movieData.getAllMovies();
        
        for (Movie m : movies) {
            System.out.println(m.getReleaseDate() + " " + m.getActorID()
            + " " + m.getFirstName());
        }
        return movies;
    }
    
    @GET
    @Path("/movies/{title}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getMovieByTitle(@PathParam("title") String title) { 
       return movieData.getMovieByTitle(title);
    }

    @GET
    @Path("/actors")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getActors() { 
        ArrayList<Movie> actors = movieData.getAllActors();
        
        for (Movie a : actors) {
            System.out.println(a.getActorID() + ": " + a.getFirstName()
            + " " + a.getLastName());
        }
        return actors;
    }
    
    @GET
    @Path("/actors/{actorname}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getActorByName(@PathParam("actorname") String actorname) { 
       return movieData.getActorByName(actorname);
    }
    
    @GET
    @Path("/actor/{actorid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Movie getActorByID(@PathParam("actorid") int actorid) { 
       return movieData.getActorByID(actorid);
    }
    
    @GET
    @Path("/directors")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getDirectors() { 
        ArrayList<Movie> directors = movieData.getAllDirectors();
        
        for (Movie d : directors) {
            System.out.println(d.getDirectorID() + ": " + d.getFirstName()
            + " " + d.getLastName());
        }
        return directors;
    }
    
    @GET
    @Path("/directors/{directorname}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getDirectorByName(@PathParam("directorname") String directorname) { 
       return movieData.getDirectorByName(directorname);
    }
    
    @GET
    @Path("/director/{directorid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Movie getDirectorByID(@PathParam("directorid") int directorid) { 
       return movieData.getDirectorByID(directorid);
    }
    
    @GET
    @Path("movies/genre/{genre}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getGenre(@PathParam("genre") String genre) { 
        ArrayList<Movie> movies = movieData.getMovieByGenre(genre);
        for (Movie m: movies) {
            System.out.println(m.getTitle());
        }
       return movieData.getMovieByGenre(genre);
    }

}