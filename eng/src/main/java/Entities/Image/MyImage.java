package Entities.Image;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MyImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // so the ID is generated automatically
    private Long id;

    String pathOriginal;
    String pathMarked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathOriginal() {
        return pathOriginal;
    }

    public void setPathOriginal(String pathOriginal) {
        this.pathOriginal = pathOriginal;
    }

    public String getPathMarked() {
        return pathMarked;
    }

    public void setPathMarked(String pathMarked) {
        this.pathMarked = pathMarked;
    }



}
