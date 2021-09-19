package NotificationsManagement.NotificationsService;

import Entities.Album.Album;
import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Locations.UsersLocation.UsersLocation;
import Entities.Notification.Notification;
import Entities.User.User;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.AlbumsLocationRepository.queryImp.RadiusSearch;
import Repositories.NotificationsRepository.NotificationsRepository;
import Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class NotificationsService {

    @Autowired
    NotificationsRepository notificationsRepository;
    @Autowired
    RadiusSearch radiusSearch;

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    UserRepository userRepository;

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

    //This method creates an html element for each notification based on its type
    //0 is for testing the notifications functionality (not for users)
    //1 is for notification about a new purchase (for photographer)
    //2 is for notification about a location matches an album (for all users)
    public String getNotificationMsgByType(short type, Long sourceId, boolean wasRead, String creation) {
        String classRead;
        if (wasRead)
            classRead = "old";
        else
            classRead = "new";

        if (type == 0) {
            return "<a href=\"/user/test/" + sourceId + "\">Test!</a>";
        } else if (type == 1) {
            //date and time shown on purchase notification are of the notification creation time
            //(notification is created right after a purchase is made)
            return "<a class =\""+classRead+"\" href=\"/user/view-purchase/" + sourceId + "\">A user purchased one of the photos you took!" +
                    "<label class=\"createdOn\"> "+creation+"</label></a>";
        } else if (type == 2) {
            //date and time shown on album notification are of the album upload time
            //(album notification can be created a while after the upload time of an album,
            //because of the check-in retroactive option)
            String notificationTime =albumRepository.getAlbumById(sourceId).get().getUploadedDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyy"));
            return "<a class =\""+classRead+"\" href=\"/user/view-album/" + sourceId + "\">An album was taken in your recent location!" +
                    "<label class=\"createdOn\"> "+notificationTime+"</label></a>";
        }
        return "error";
    }


    /** This method receives AlbumLocation Object and finds all the users that matches by location and time
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
            if(!notificationsRepository.findFirstByUsernameAndSourceIdAndTypeNoti(userName, element.getAlbumId(), type).isPresent())
            //first checks whether this album already triggered a notification for user in the past
            {notificationsRepository.save(new Notification(userName, type,
                    element.getAlbumId(), now));}
        });

    }

    public void createPurchaseNotification(String seller, Long imgId){
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        short type = 1; //1 for purchase notification type
        notificationsRepository.save(new Notification(seller, type,
                imgId, now));
    }

    public void setNotificationToRead(String username, Long albumId){
        Notification currNoti = notificationsRepository.getFirstByUsernameAndSourceId(username,albumId);
        currNoti.setWasRead(true);
        notificationsRepository.save(currNoti);
    }


    public long getNumberOfUnwatchedNotificationsForUser(String username){
        return notificationsRepository.countByUsernameAndWasWatched(username, false);
    }

    public boolean castARate(Long albumId, String voter, int rate){
        //Since only a user who had been notified about an album should be allowed to vote
        //user's rate is found in the notification database
        short type = 2;
        Notification noti = notificationsRepository.getFirstByUsernameAndSourceIdAndTypeNoti(voter,albumId, type);
        if (rate == 0)
            noti.setRate(rate);
        else
            return false;
        notificationsRepository.save(noti);

        //set album rating
        Album ratedAlbum = albumRepository.getAlbumById(albumId).get();
        int raters = ratedAlbum.getNumOfRaters();
        double currRating = ratedAlbum.getRating();
        double newRating = (currRating*raters + rate)/(raters+1);
        ratedAlbum.setRating(newRating);
        ratedAlbum.setNumOfRaters(raters+1);
        albumRepository.save(ratedAlbum);

        //now set photographer rating:
        int numOfAlbums =( (List<Album>) albumRepository.getAlbumsByPhotographer(ratedAlbum.getPhotographer())).size();
        User photographer = userRepository.getFirstByUsername(ratedAlbum.getPhotographer());
        double currUserRating = photographer.getRating();
        double newUserRating = (numOfAlbums*currRating-currRating+newRating)/(numOfAlbums);
        userRepository.save(photographer);
        return true;
    }


}

