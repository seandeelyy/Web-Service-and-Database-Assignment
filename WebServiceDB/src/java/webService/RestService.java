/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/MyRestService")
@ApplicationPath("/resources")
public class RestService extends Application {
    
    private static ArrayList<SimpleObject> users = new ArrayList<>();
    private SimpleObject user;
    
    private static final String URL = "jdbc:derby://localhost:1527/myDB";
    private static final String USER = "app";
    private static final String PASSWD = "app";

    // http://localhost:8080/WebServiceDB/resources/MyRestService/sayHello
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
        return new SimpleObject(1, "Test");
    }
    
    @GET
    @Path("/getUsers")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<SimpleObject> getUsers() {
        
        users = new ArrayList<>();
        String sql = "SELECT UID, FNAME FROM USERS";
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                // get data out - note: index starts at 1 !!!!
                user = (new SimpleObject(result.getInt(1), result.getString(2)));
                users.add(user);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("ERROR OCCURRED");

        }
        return users;
    }
    
    @GET
    @Path("/getUsersByID")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public ArrayList<SimpleObject> getUsersByID(@QueryParam("id") String id) {
        
        users = new ArrayList<>();
        String sql = "SELECT UID, FNAME FROM USERS WHERE UID=" + id;
        
        try (Connection connect = DriverManager.getConnection(URL, USER, PASSWD);
                Statement stmt = connect.createStatement();) {
            
            // execute statement - note DB needs to perform full processing
            // on calling executeQuery
            ResultSet result = stmt.executeQuery(sql);

            // while there are results
            while (result.next()) {
                // get data out - note: index starts at 1 !!!!
                user = (new SimpleObject(result.getInt(1), result.getString(2)));
                users.add(user);
            }           
            // deal with any potential exceptions
            // note: all resources are closed automatically - no need for finally
        } catch (SQLException sqle) {
            System.out.println("ERROR OCCURRED");

        }
        return users;
    }
    


}