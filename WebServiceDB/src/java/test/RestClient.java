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

/**
 *
 * @author Sean
 */
@ApplicationScoped
@ManagedBean
public class RestClient {

    public String call() {
        Client client = ClientBuilder.newClient();

        WebTarget myResource = client.target("http://localhost:8080/WebServiceDB/services/MyRestService/object");
        SimpleObject o = myResource.request().get(SimpleObject.class);

        System.out.println(o);
        
        return "test";
    }
    
    public void testGetAllUsers(){
      Client client = ClientBuilder.newClient();
      GenericType<ArrayList<User>> list = new GenericType<ArrayList<User>>() {};
      ArrayList<User> users = client
         .target("http://localhost:8080/WebServiceDB/services/MyRestService/users")
         .request()
         .get(list);
      
        for (User u: users) {
            System.out.println("ID:   " + u.getUid() + "\n\tName: " + u.getFirstName() + 
                    " " + u.getSurname() + "\n\tType: " + u.getUtype());
        }
   }
    
    public void testGetUser(){
      Client client = ClientBuilder.newClient();
      
      User user = client
         .target("http://localhost:8080/WebServiceDB/services/MyRestService/users")
              .path("/{userid}")
              .resolveTemplate("userid", 1002)
         .request()
         .get(User.class);
      
        
            System.out.println("ID:   " + user.getUid() + "\n\tName: " + user.getFirstName() + 
                    " " + user.getSurname() + "\n\tType: " + user.getUtype());
   }
    
}