package Entities.Locations.UsersLocation;

import Entities.User.User;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


@Entity
public class UsersLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private float latitude;
    private float longitude;
    private Timestamp fromTime;
    private Timestamp toTime;
    private String userName;

    public UsersLocation(){}

    public UsersLocation(float latitude, float longitude, Timestamp from , Timestamp to, String userName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.fromTime = from;
        this.toTime = to;
        this.userName = userName;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setId(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public Timestamp getFrom() {
        return fromTime;
    }

    public void setFrom(Timestamp from) {
        this.fromTime = from;
    }

    public Timestamp getTo() {
        return toTime;
    }

    public void setTo(Timestamp to) {
        this.toTime = to;
    }
}
