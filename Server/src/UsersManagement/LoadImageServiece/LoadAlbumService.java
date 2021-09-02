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


    private static LoadAlbumService INSTANCE = null;

    public static LoadAlbumService getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new LoadAlbumService();

        return INSTANCE;
    }


    public Long addNewAlbum(AlbumDetails albumDetails, UserRepository userRepository, AlbumRepository albumRepos) {
        Album album = new Album();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user= userRepository.findByUsername(username);
        user.orElseThrow(()-> new UsernameNotFoundException("No Such User: "+ username));

        album.setPhotographer(username);
        Date date = new Date();
        album.setUploadedDate(new Timestamp(date.getTime()));
        album.setName(albumDetails.getName());

        albumRepos.save(album);
        Optional<Album> currentAlbum = albumRepos.findById(album.getId());

        //define user as a photographer now that an album had been uploaded by him
        user.get().setIsPhotographer(true); //TODO check if it actually being saved on DB

        //Save album Locations and evoke notifications
        AlbumsLocation albumLocation = new AlbumsLocation(album.getId(), albumDetails.getLat(), albumDetails.getLng(),
                albumDetails.getFromTime(),albumDetails.getToTime());
        albumLocationRepository.save(albumLocation);
        notificationsService.searchForUsersLocationsAndNotify(albumLocation);


        File f = new File("C:\\Users\\dhnhd\\Desktop\\SneakPic\\SneakPic\\Server\\src\\main\\resources\\static\\Albums\\" + currentAlbum.get().getId());
        f.mkdirs();
        File fMarked = new File("C:\\Users\\dhnhd\\Desktop\\SneakPic\\SneakPic\\Server\\src\\main\\resources\\static\\Albums\\"+currentAlbum.get().getId()+"\\" + "marked");
        fMarked.mkdir();
        return album.getId();
        //System.out.println("");




    }
}
