package LocationManagement.Position;

import java.io.Serializable;
import java.sql.Timestamp;

public class Position implements Serializable {
    private float lat;
    private float lng;
    private Timestamp fromTime;
    private Timestamp toTime;
    private boolean manually; //false: check in on actual time. true: retroactively

    public Position(){}

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

    public void setFromTime(Timestamp from) {
        this.fromTime = from;
    }

    public Timestamp getToTime() {
        return toTime;
    }

    public void setToTime(Timestamp to) {
        this.toTime = to;
    }

    public boolean getManually(){return this.manually;}
}
