package Entities.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class User {
    @Id
    @Column(length = 64)
    String username;
    String password;
    String firstName;
    String lastName;
    boolean isAdmin;
    boolean isPhotographer;
    double rating;
    //TODO maybe each user (whether they a photografer or not) has a list of: albums, purchases, and so on

    ////////////////////////// constructors, getters and setters//////////////////////////////////
    public User() {
    }

    public User(String firstName, String lastName, String username, String password, boolean isPhotographer) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isAdmin = false;
        this.isPhotographer = isPhotographer;
        this.rating = 0;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public boolean getIsAdmin() {return this.isAdmin;}


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPhotographer() {
        return isPhotographer;
    }

    public void setPhotographer(boolean photographer) {
        isPhotographer = photographer;
    }

    public Double getRating() {
        return rating;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Rating field is not primitive double so it can be null in case there are no ratings for the
     * photographer yet.
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * This method should be called automatically when a user uploads a photo / album
     * Also, it is up to the user to decided whether s/he would be considered as a photographer
     * (on the registration form)
     */
    public void setIsPhotographer(boolean isPhotographer) {
        this.isPhotographer = isPhotographer;
    }

    //Returns first+last name with first letter capitalized (since they are all stored in DB with lower case)
    public String getFirstLetterIsCapitalize(){
        String nameString = this.getFirstName()+" "+getLastName();
        String[] names = nameString.split(" ");
        String res ="";
        for(String name: names){
            name = name.substring(0,1).toUpperCase()+name.substring(1);
            res = res + name + " ";
        }
        return res.substring(0,res.length()-1);
    }
}
