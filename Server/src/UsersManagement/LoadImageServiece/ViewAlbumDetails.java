package UsersManagement.LoadImageServiece;

import Entities.Album.Album;
import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.User.User;
import Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class ViewAlbumDetails {
       private Long albumId;
    private String name;
    private String photographer;
    private float lat;
    private float lng;
    private String fromTime;
    private String toTime;
    private String uploadTime;

    public ViewAlbumDetails(Album album, AlbumsLocation location, User photographer){
        this.albumId =album.getId();
        this.name = album.getName();
        this.photographer = photographer.getFirstLetterIsCapitalize();
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
        this.fromTime = location.getFromTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyy"));
        this.toTime = location.getToTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyy"));
        this.uploadTime = album.getUploadedDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyy"));
    }

}

