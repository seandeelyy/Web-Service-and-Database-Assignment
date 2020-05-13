/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.IOException;
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
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import resources.Movie;
import resources.GetDataFromDB;
import resources.InsertDataIntoDB;
import resources.CreateTables;
import resources.DeleteTables;
import resources.FillTables;

/**
 *
 * @author Sean
 */
@Path("/MyRestService")
@ApplicationPath("/services")
public class RestService extends Application {
    
    GetDataFromDB movieData = new GetDataFromDB();
    InsertDataIntoDB insertData = new InsertDataIntoDB();
    CreateTables createTables = new CreateTables();
    FillTables fillTables = new FillTables();
    DeleteTables deleteTables = new DeleteTables();
    
    // 1. Create Tables
    @POST
    @Path("/tables/create")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createTable() {        
        if (createTables.createActorsTable() &&
            fillTables.fillActorsTable() &&
            createTables.createActorCredentialsTable() &&
            fillTables.fillActorCredentialsTable() &&
            createTables.createDirectorsTable() &&
            fillTables.fillDirectorsTable() &&
            createTables.createGenresTable() &&
            fillTables.fillGenresTable() &&
            createTables.createMoviesTable() &&
            fillTables.fillMoviesTable() &&
            createTables.createActors_MoviesTable() &&
            fillTables.fillActors_MoviesTable()) {
            return Response.ok().build();
        }
        else {
             return Response.noContent().build();
        }
    } 
    
    // 2. One-to-One Query
    @GET
    @Path("/actors/{actorname}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getActorByName(@PathParam("actorname") String actorname) { 
       return movieData.getActorByName(actorname);
    }
    
    // 3/4. One-to-Many Query AND Many-to-Many Query
    @GET
    @Path("movies/genre/{genre}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getMovieByGenre(@PathParam("genre") String genre) { 
       return movieData.getMovieByGenre(genre);
    }
    
    // 5. Add an entry to a table with a One-to-One relationship
    @PUT
    @Path("/actors/addActor")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addActor(
            @FormParam("actorFirstName") String actorFirstName,
            @FormParam("actorLastName") String actorLastName,
            @FormParam("year") int year,
            @FormParam("month") int month,
            @FormParam("day") int day,
            @FormParam("actorEmail") String actorEmail,
            @FormParam("actorImage") String actorImage,
            @Context HttpServletResponse servletResponse) throws IOException{
        
        if (insertData.addActor(actorFirstName, actorLastName, year, month, 
                day, actorEmail, actorImage)) {
            return Response.ok().build();
        }
        else {
             return Response.noContent().build();
        }
    }
    
    // 6. Add an entry to a table with a One-to-Many relationship
    @PUT
    @Path("/movies/addMovie")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addMovie(@FormParam("title") String title,
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
        if (insertData.addMovie(title, description, runTime, year, month, 
                day, trailer, directorFirstName, directorLastName, genre)) {
            return Response.ok().build();
        }
        else {
             return Response.noContent().build();
        }
    }
    
    // 7. Add an entry to a table with a Many-to-Many relationship
    @PUT
    @Path("/actors/movies/addActors_Movies")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addToActors_MoviesTable(
            @FormParam("actorFirstName") String actorFirstName,
            @FormParam("actorLastName") String actorLastName,
            @FormParam("title") String title,
            @Context HttpServletResponse servletResponse) throws IOException{
        
        if (insertData.addToActors_MoviesTable(actorFirstName,actorLastName, title)) {
            return Response.ok().build();
        }
        else {
            return Response.noContent().build();
        }
    }
    
    // 8. Delete/Drop Tables
    @DELETE
    @Path("/tables/delete")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteTables() {
        if (deleteTables.deleteActorCredentialsTable() &&
            deleteTables.deleteActorsTable() &&
            deleteTables.deleteActors_MoviesTable() &&
            deleteTables.deleteMoviesTable() &&
            deleteTables.deleteDirectorsTable() &&
            deleteTables.deleteGenresTable()) {
            return Response.ok().build();
        }
        else {
            return Response.noContent().build();
        }
    } 
    
    // *************************************************************************
    // The following are EXTRA web services which can be invoked by running
    // 'extraServices.xhtml'
    // *************************************************************************
    
    // Retrieve all actors from the database ('Actors' table) along with their
    // credentials from the 'ActorCredentials' table and the movies in which they
    // have featured in from the 'Actors_Movies' table.
    @GET
    @Path("/actors")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getActors() { 
        ArrayList<Movie> actors = movieData.getAllActors();
        return actors;
    }
    
    // Retrieve all directors from the database ('Directors' table) along with 
    // the name of any movies which they have directed ('Movies' table).
    @GET
    @Path("/directors")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getDirectors() { 
        ArrayList<Movie> directors = movieData.getAllDirectors();
        return directors;
    }
    
    // Retrieve all movies from the database ('Movies' table) along with the 
    // name of the director who directed it ('Directors' table), the name of 
    // any actors who featured in it ('Actors_Movies' table) and the genre 
    // of the movie itself from the 'Genres' table.
    @GET
    @Path("/movies")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getMovies() { 
        ArrayList<Movie> movies = movieData.getAllMovies();
        return movies;
    }
    
    // Retrieve an actor from the database, based on their actorID.
    @GET
    @Path("/actor/{actorid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Movie getActorByID(@PathParam("actorid") int actorid) { 
       return movieData.getActorByID(actorid);
    }
    
    // Return any directors from the 'Directors' table which match the passed
    // parameter and also any movie which they may have directed from the 
    // 'Movies' table.
    @GET
    @Path("/directors/{directorname}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getDirectorByName(@PathParam("directorname") 
            String directorname) { 
       return movieData.getDirectorByName(directorname);
    }
    
    // Retrieve a director from the database, based on their directorID.
    @GET
    @Path("/director/{directorid}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Movie getDirectorByID(@PathParam("directorid") int directorid) { 
       return movieData.getDirectorByID(directorid);
    }
    
    // Return any movies from the 'Movies' table which match the passed 
    // parameter along with the name of the director who directed it 
    // ('Directors' table), the name of any actors who featured in it 
    // ('Actors_Movies' table) and the genre of the movie itself from the 
    // 'Genres' table.
    @GET
    @Path("/movies/{title}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<Movie> getMovieByTitle(@PathParam("title") String title) { 
       return movieData.getMovieByTitle(title);
    }

}