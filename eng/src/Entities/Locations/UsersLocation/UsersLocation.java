package Entities.Locations.UsersLocation;

import Entities.User.User;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class UsersLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float latitude;
    private float longitude;
    private Date date;
    private Time time;
   // private Timestamp time; it might be better to use the timestamp instead
    private String userName;

    public UsersLocation(){}

    public UsersLocation(float latitude, float longitude, Date date, Time time, String userName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.time = time;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


    public void setId(long id) {
        this.id = id;
    }

    @Id
    public long getId() {
        return id;
    }
}
