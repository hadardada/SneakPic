package LocationManagement.LocationService;

import Entities.Locations.UsersLocation.UsersLocation;
import Entities.User.User;
import LocationManagement.Position.Position;
import Repositories.AlbumsLocationRepository.AlbumLocationRepository;
import Repositories.UsersLocationRepository.UsersLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Service
public class LocationService {

    @Autowired
    private UsersLocationRepository usersLocationRepository;
    @Autowired
    private AlbumLocationRepository albumLocationRepository;

    public UsersLocation getUsersLocationObject(Position position, User user){
        Timestamp from = new Timestamp(position.getFromTime());
        Timestamp to = new Timestamp(position.getToTime());
        UsersLocation location = new UsersLocation(position.getLat(), position.getLng(), from, to, user.getUsername());
        return usersLocationRepository.save(location);
    }

    //TODO getAlbumsLocationObject
    //public UsersLocation getAlbumsLocationObject(Position position, User user, Album albumId) {
       // Date date = new Date(position.getTime());
       // Time time = new Time(position.getTime());}



/*
    public List<Album> findAlbumsOnThisDateAndTime (UsersLocation location,User user, int duration){
        //TODO put notifications here
        //TODO put duration as well (now it's just on the very same time)
       // Time until = new Time(location.getTime().getTime() + duration);


    }


    //This method is activated when an album is uploaded
    public List<Album> findUsersOnThisDateAndTime{

    }


 */


}
