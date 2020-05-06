/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sean
 */
@XmlRootElement
public class User {
    
    private int uid;
    private String firstName;
    private String surname;
    private String utype;

    
    /**
     * Default constructor
     */
    public User() {

    }
    
    /**
     * Creates a User object with given information.
     * @param uid user's unique id
     * @param firstName First name of user
     * @param surname Surname of user
     * @param utype user type (admin/freelancer/job provider)
     */ 
    public User(int uid, String firstName, String surname, String utype) {
        super();
        this.uid = uid;
        this.firstName = firstName;
        this.surname = surname;
        this.utype = utype;
    }
    
    public User(String firstName, String surname, String utype) {
        super();
        this.firstName = firstName;
        this.surname = surname;
        this.utype = utype;
    }
 
        
    /**
     * Copy constructor
     */
    public User(User u) {
        this.uid = u.uid;
        this.firstName = u.firstName;
        this.surname = u.surname;
        this.utype = u.utype;
    }
    
    
    
    /**
     * Get the value of userid
     *
     * @return the value of userid
     */
    public int getUid() {
        return uid;
    }

    /**
     * Set the value of userid
     *
     * @param uid new value of userid
     */
    public void setUid(int uid) {
        this.uid = uid;
    }
    
    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Set the value of firstName
     *
     * @param firstName new value of firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Get the value of surname
     *
     * @return the value of surname
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * Set the value of surname
     *
     * @param surname new value of surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     * Get the value of usertype
     *
     * @return the value of usertype
     */
    public String getUtype() {
        return utype;
    }

    /**
     * Set the value of utype
     *
     * @param utype new value of usertype
     */
    public void setUtype(String utype) {
        this.utype = utype;
    }
    
}
