package Entities.Locations.AlbumsLocation;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
public class AlbumsLocation {
    @Id
    private Long albumId;
    private float latitude;
    private float longitude;
    private Timestamp fromTime;
    private Timestamp toTime;

    public  AlbumsLocation(){}

    public AlbumsLocation(Long albumId, float latitude, float longitude, Timestamp from , Timestamp to) {
        this.albumId = albumId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fromTime = from;
        this.toTime = to;
    }
    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
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


