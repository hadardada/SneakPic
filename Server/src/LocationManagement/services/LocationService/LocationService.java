package LocationManagement.services.LocationService;

import Entities.Album.Album;
import Entities.Locations.UsersLocation.UsersLocation;
import Entities.User.User;
import LocationManagement.Position.Position;
import Repositories.AlbumsLocationRepository.AlbumLocationRepository;
import Repositories.UsersLocationRepository.UsersLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


public class LocationService {

    @Autowired
    private UsersLocationRepository usersLocationRepository;
    @Autowired
    private AlbumLocationRepository albumLocationRepository;

    public UsersLocation getUsersLocationObject(Position position, User user){
        Date date = new Date(position.getTime());
        Time time = new Time(position.getTime());
        UsersLocation location = new UsersLocation(position.getLat(), position.getLng(), date, time, user.getUsername());
        return usersLocationRepository.save(location);
    }

    //TODO getAlbumsLocationObject
    //public UsersLocation getAlbumsLocationObject(Position position, User user, Album albumId) {
       // Date date = new Date(position.getTime());
       // Time time = new Time(position.getTime());}


    /*
    This method is called when user is uploading his location
    it takes the user location details, and uses it to find matching album/s on a 100 meter radius
    returns a list of albums
    TODO put notifications here
    TODO put duration as well (now it's just on the very same time)

    public List<Album> findAlbumsOnThisDateAndTime (UsersLocation location, int duration){
        Time until = new Time(location.getTime().getTime() + duration);

    }

    //This method is activated when an album is uploaded
    public List<Album> findUsersOnThisDateAndTime{

    }

     */

}
