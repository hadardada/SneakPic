package UsersManagement.LoadImageServiece;

import Entities.Album.Album;
import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Locations.UsersLocation.UsersLocation;
import Entities.User.User;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.AlbumsLocationRepository.AlbumLocationRepository;
import Repositories.UserRepository.UserRepository;
import NotificationsManagement.NotificationsService.NotificationsService;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoadAlbumService {
    @Autowired
    private NotificationsService notificationsService;
    @Autowired
    private AlbumLocationRepository albumLocationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;

    private static String absolutePath = "C:\\Users\\dhnhd\\IdeaProjects\\Hey\\Server\\src\\main\\resources\\static\\Albums\\";
    private static String bucketName = "sneakpicbucket";

    public Long addNewAlbum(AlbumDetails albumDetails) {
        Album album = new Album();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user= userRepository.getFirstByUsername(username);

        album.setPhotographer(username);
        Date date = new Date();
        album.setUploadedDate(new Timestamp(date.getTime()));
        album.setName(albumDetails.getName());

        albumRepository.save(album);
        Optional<Album> currentAlbum = albumRepository.findById(album.getId());

        //define user as a photographer now that an album had been uploaded by him
        if (!user.isPhotographer()){
            user.setIsPhotographer(true); //TODO check if it actually being saved on DB
            userRepository.save(user);
        }

        //Save album Locations and evoke notifications
        AlbumsLocation albumLocation = new AlbumsLocation(album.getId(), albumDetails.getLat(), albumDetails.getLng(),
                albumDetails.getFromTime(),albumDetails.getToTime());
        albumLocationRepository.save(albumLocation);
        notificationsService.searchForUsersLocationsAndNotify(albumLocation);



        File f = new File(this.absolutePath + currentAlbum.get().getId());
        f.mkdirs();

        //create "marked" folder for photos with watermarks
        File fMarked = new File(absolutePath+currentAlbum.get().getId()+"\\" + "marked");
        fMarked.mkdir();
        return album.getId();
    }
}
