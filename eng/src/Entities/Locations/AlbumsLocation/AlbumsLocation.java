package Entities.Locations.AlbumsLocation;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class AlbumsLocation {
    @Id
    private Long albumId;
    private float latitude;
    private float longitude;
    private Date date;
    private Time time;
}
