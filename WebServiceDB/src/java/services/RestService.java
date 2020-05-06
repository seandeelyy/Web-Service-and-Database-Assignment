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
import resources.GetUserDataFromDB;

/**
 *
 * @author Sean
 */
@Path("/MyRestService")
@ApplicationPath("/services")
public class RestService extends Application {
    
    GetUserDataFromDB userData = new GetUserDataFromDB();
    
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
   
   
    @DELETE
    @Path("/users/{userid}")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String deleteUser(@PathParam("userid") int uid ) {
        userData.deleteUser(uid);
        return "";
   }

}