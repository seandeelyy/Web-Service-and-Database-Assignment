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
import data.SimpleObject;

/**
 *
 * @author Sean
 */
@ApplicationScoped
@ManagedBean
public class RestClient {

    public String call() {
        Client client = ClientBuilder.newClient();

        SimpleObject o = client.target("http://localhost:8080/WebServiceDB/services/MyRestService/object").request().get(SimpleObject.class);

        System.out.println(o);
        
        return "test";
    }
	
}