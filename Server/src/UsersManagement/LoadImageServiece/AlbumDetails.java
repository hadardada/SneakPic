package UsersManagement.LoadImageServiece;

import LocationManagement.Position.Position;

import java.io.Serializable;
import java.sql.Timestamp;

public class AlbumDetails implements Serializable {
    private String name;
    private float lat;
    private float lng;
    private Timestamp fromTime;
    private Timestamp toTime;

    public AlbumDetails(){}

    public AlbumDetails(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Timestamp getFromTime() {
        return fromTime;
    }

    public void setFromTime(Timestamp fromTime) {
        this.fromTime = fromTime;
    }

    public Timestamp getToTime() {
        return toTime;
    }

    public void setToTime(Timestamp toTime) {
        this.toTime = toTime;
    }
}
