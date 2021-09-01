package UsersManagement.LoadImageServiece;

import LocationManagement.Position.Position;

import java.io.Serializable;
import java.sql.Timestamp;

public class AlbumDetails implements Serializable {
    //private Long id;
    private String name;
    //private Position position;
    //private Timestamp uploadedDate;
    //private String photographer;


    public AlbumDetails(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Position getPosition() {
//        return position;
//    }
//
//    public void setPosition(Position position) {
//        this.position = position;
//    }
}
