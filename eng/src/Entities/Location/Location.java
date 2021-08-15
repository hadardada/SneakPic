package Entities.Location;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.format.DateTimeFormatter;

@Entity
public class Location {
    @Id
    Long id;
    float latitude;
    float longitude;
    Local
    String userName;


    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
