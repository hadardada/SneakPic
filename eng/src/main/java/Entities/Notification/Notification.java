package Entities.Notification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Notification {
    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO) // so the ID is generated automatically
    private Long id;

    private String username; // to whom this notification sent to
    private short typeNoti;
    private Long sourceId; // id of the object (album / purchase) who triggered this notification
    boolean wasRead;
    Timestamp createdOn;

    public Notification(){}

    public Notification(String username, short type, Long sourceId, Timestamp createdOn) {
        this.id = new Date().getTime();
        this.username = username;
        this.typeNoti = type;
        this.sourceId = sourceId;
        this.wasRead = false; // new notifications have never been read
        this.createdOn = createdOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public short getTypeNoti() {
        return typeNoti;
    }

    public void setTypeNoti(short typeNoti) {
        this.typeNoti = typeNoti;
    }

    public boolean isWasRead() {
        return wasRead;
    }

    public void setWasRead(boolean wasRead) {
        this.wasRead = wasRead;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }


    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public void setIdNoti(Long idNoti) {
        this.id = idNoti;
    }

    @Id
    public Long getIdNoti() {
        return id;
    }
}
