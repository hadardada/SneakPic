package LocationManagement.LocationService;

import Entities.Album.Album;
import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Locations.UsersLocation.UsersLocation;
import Entities.User.User;
import LocationManagement.Position.Position;
import Repositories.AlbumsLocationRepository.AlbumLocationRepository;
import Repositories.AlbumsLocationRepository.queryImp.RadiusSearch;
import Repositories.UsersLocationRepository.UsersLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LocationService {

    @Autowired
    private UsersLocationRepository usersLocationRepository;
    @Autowired
    private AlbumLocationRepository albumLocationRepository;
    @Autowired
    private RadiusSearch radiusSearch;

    public void uploadUserLocation(Position position, User user){
        UsersLocation usersLocation = getUsersLocationObject(position, user);
        List<AlbumsLocation> list = radiusSearch.findAllAlbumsByDateTimeAnd150mRadius(usersLocation);
    }
    public UsersLocation getUsersLocationObject(Position position, User user){
        Timestamp from = position.getFromTime();
        Timestamp to;
        if (!position.getManually()) // if this location was sent via "check-in"
            to = from; // then there's no time range
        else // location was sent via "location-search", manually
            to = position.getToTime();
        UsersLocation location = new UsersLocation(position.getLat(), position.getLng(), from, to, user.getUsername());
        return usersLocationRepository.save(location);
    }

    //TODO getAlbumsLocationObject
    //public UsersLocation getAlbumsLocationObject(Position position, User user, Album albumId) {
       // Date date = new Date(position.getTime());
       // Time time = new Time(position.getTime());}




    public List<AlbumsLocation> findAlbumsOnThisDateAndTime (UsersLocation location, User user){
        //TODO put notifications here
        List<AlbumsLocation> list = radiusSearch.findAllAlbumsByDateTimeAnd150mRadius(location);
        return list;
    }

/*
    //This method is activated when an album is uploaded
    public List<Album> findUsersOnThisDateAndTime{

    }


 */


}
