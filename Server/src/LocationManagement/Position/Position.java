package LocationManagement.Position;

import java.io.Serializable;

public class Position implements Serializable {
    private float lat;
    private float lng;
    private long time;
    private int duration;
    private boolean manually; //false: check in on actual time. true: retroactively

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
