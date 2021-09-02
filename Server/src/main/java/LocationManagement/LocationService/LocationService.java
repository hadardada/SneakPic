package LocationManagement.LocationService;

import Entities.Album.Album;
import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Locations.UsersLocation.UsersLocation;
import Entities.User.User;
import LocationManagement.Position.Position;
import NotificationsManagement.NotificationsService.NotificationsService;
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
    private NotificationsService notificationsService;

    /**This method creates a UserLocation Object and evokes notifications**/
    public void uploadUserLocation(Position position, User user){
        UsersLocation usersLocation = getUsersLocationObject(position, user);
        notificationsService.searchForAlbumsAndNotify(usersLocation);
    }


    public UsersLocation getUsersLocationObject(Position position, User user){
        UsersLocation location = new UsersLocation(position.getLat(), position.getLng(), position.getFromTime(),
                position.getToTime(), user.getUsername());
        return usersLocationRepository.save(location);
    }

}
