package Entities.Locations.AlbumsLocation;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class AlbumsLocation {
    @Id
    private long albumId;
    private float latitude;
    private float longitude;
    private Date date;
    private Time time;
}
