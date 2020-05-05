/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webService;

import java.util.ArrayList;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Sean
 */
@ApplicationScoped
@ManagedBean
public class RestClient {
    
    private static ArrayList<SimpleObject> users = new ArrayList<>();

    public String call() {
        
//        users = new ArrayList<>();
        Client client = ClientBuilder.newClient();

        SimpleObject o = client.target("http://localhost:8080/WebServiceDB/resources/MyRestService/object")
                .request().get(SimpleObject.class);
		
        System.out.println(o);
 
        users = client.target("http://localhost:8080/WebServiceDB/resources/MyRestService/getUsers")
                .request().get(new GenericType<ArrayList<SimpleObject>>() {});
        
        
        System.out.println(users);
        for (SimpleObject s: users) {
            System.out.println(s.getId() + ": " + s.getName());
        }
        return "test";
    }
    
    
    public ArrayList<SimpleObject> getUsers() {
        return users;
    }
	
}