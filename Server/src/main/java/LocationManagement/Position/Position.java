package LocationManagement.Position;

import java.io.Serializable;

public class Position implements Serializable {
    private float lat;
    private float lng;
    private long fromTime;
    private long toTime;
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

    public long getFromTime() {
        return fromTime;
    }

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public long getToTime() {
        return toTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }
}
