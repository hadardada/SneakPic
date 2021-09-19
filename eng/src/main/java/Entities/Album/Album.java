package Entities.Album;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


/* each album location (see class:AlbumsLocation) has only one album-id
 */
@Entity
public class Album {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // so the ID is generated automatically
    private Long id;
    private String name;
    private Timestamp uploadedDate;
    private String photographer;
    private int numOfRaters;
    private double rating;

    public Album(){};

    public Album (String name, Timestamp uploadedDate, String photographer){
        this.name = name;
        this.uploadedDate = uploadedDate;
        this.photographer = photographer;
        numOfRaters = 0;
        rating = 0;
    }
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumOfRaters() {
        return numOfRaters;
    }

    public void setNumOfRaters(int numOfRaters) {
        this.numOfRaters = numOfRaters;
    }

    private String albumPath;

    public String getName() {
        return name;
    }

    public Timestamp getUploadedDate() {
        return uploadedDate;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUploadedDate(Timestamp uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public Long getId() {
        return id;
    }
}
