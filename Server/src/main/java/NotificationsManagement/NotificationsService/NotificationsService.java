package NotificationsManagement.NotificationsService;

import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Locations.UsersLocation.UsersLocation;
import Entities.Notification.Notification;
import Repositories.AlbumsLocationRepository.queryImp.RadiusSearch;
import Repositories.NotificationsRepository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class NotificationsService {

    @Autowired
    NotificationsRepository notificationsRepository;
    @Autowired
    RadiusSearch radiusSearch;

    public String getNotificationsForUser(String username) {
        Iterable<Notification> notificationsItr = notificationsRepository.findAllByUsernameOrderByCreatedOnDesc(username);
        StringBuilder builder = new StringBuilder();
        notificationsItr.forEach((element) -> {
            builder.append(this.getNotificationMsgByType(element.getTypeNoti(), element.getSourceId(), element.isWasRead()
                    ,element.getCreatedOn().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyy"))));
            if (!element.isWasWatched())
                element.setWasWatched(true);
            notificationsRepository.save(element);
            // set all notifications 'watched' field to be true - since this method is being called
            // only after user chooses to view notifications
        });
        String notificationElements = builder.toString();
        if (notificationElements.equals("")) // empty sting = no notifications
            notificationElements = "<label class=\"noNoti\"> No Notifications To Show </label>";
        return notificationElements;
    }

    public String getNotificationMsgByType(short type, Long sourceId, boolean wasRead, String creation) {
        String classRead;
        if (wasRead)
            classRead = "old";
        else
            classRead = "new";
        String albumLink = "<a class =\""+classRead+"\" href=\"/user/view-album/" + sourceId + "\">An album was taken in your recent location!" +
                "<label class=\"createdOn\"> "+creation+"</label></a>";
        String purchaseLink = "<a class =\""+classRead+"\" href=\"/user/view-purchase/" + sourceId + "\">A user purchased one of the photos you took!" +
                "<label class=\"createdOn\"> "+creation+"</label></a>";
        String test = "<a href=\"/user/test/" + sourceId + "\">Test!</a>";
        if (type == 0) {
            return test;
        } else if (type == 1) {
            return purchaseLink;
        } else if (type == 2) {
            return albumLink;
        }
        return "error";
    }

    /* This method receives AlbumLocation Object and finds all the users that matches by location and time
   (it calls to one of the "radius Search" methods)
   and then for each matching userlocation it creates a new notification from type "2" which is album notification type
   This method should be called after an album is uploaded.
    */

    public void searchForUsersLocationsAndNotify(AlbumsLocation albumLocation) {
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        short type = 2;
        Iterable<UsersLocation> usersLocations = radiusSearch.findAllUsersByDateTimeAnd150mRadius(albumLocation);
        usersLocations.forEach((element) -> {
                    notificationsRepository.save(new Notification(element.getUserName(), type,
                            albumLocation.getAlbumId(), now));
                }
        );
    }


    /** This method receives UserLocation Object and finds all the albums that matches by location and time
    (it calls to one of the "radius Search" methods)
    and then for each matching album creates a new notification from type "2" which is album notification type
    This method should be called after a user locations is being uploaded (manually, not via "check-in")
     */
        public void searchForAlbumsAndNotify (UsersLocation userLocation){
            Date date = new Date();
            String userName = userLocation.getUserName();
            Timestamp now = new Timestamp(date.getTime());
            short type = 2;
            Iterable<AlbumsLocation> albumsLocations = radiusSearch.findAllAlbumsByDateTimeAnd150mRadius(userLocation);
            albumsLocations.forEach((element)-> {
                if(!notificationsRepository.findFirstByUsernameAndSourceId(userName, element.getAlbumId()).isPresent())
                    {notificationsRepository.save(new Notification(userName, type,
                        element.getAlbumId(), now));}
            });

        }

        public void setNotificationToRead(String username, Long albumId){
            Notification currNoti = notificationsRepository.getFirstByUsernameAndSourceId(username,albumId);
            currNoti.setWasRead(true);
            notificationsRepository.save(currNoti);
        }


        public long getNumberOfUnwatchedNotificationsForUser(String username){
            return notificationsRepository.countByUsernameAndWasWatched(username, false);
        }
    }

