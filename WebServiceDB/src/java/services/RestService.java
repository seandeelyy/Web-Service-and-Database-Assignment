/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
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

    // http://localhost:8080/WebServiceDB/services/MyRestService/sayHello
    @GET
    @Path("/sayHello")
    public String getHelloMsg() {
        return "Hello World";
    }

    @GET
    @Path("/echo")
    public Response getEchoMsg(@QueryParam("message") String msg) {
        return Response.ok("Your message was: " + msg).build();
    }

    @GET
    @Path("/object")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public SimpleObject getObject() {
        return new SimpleObject(420, "Test");
    }
    
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
}