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
    
    private String REST_SERVICE_URL = "http://localhost:8080/WebServiceDB/services/MyRestService/users";
    private int uid;  
    
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
    
    
    // Delete a User
    // @DELETE
    public String testDeleteUser(){
        Client client = ClientBuilder.newClient();
      
        String user = client.target(REST_SERVICE_URL).path("/{userid}")
                .resolveTemplate("userid", 1016)
                .request().delete(String.class);

        return "test";
    }
    
    
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}