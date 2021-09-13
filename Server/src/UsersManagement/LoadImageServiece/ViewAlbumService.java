package UsersManagement.LoadImageServiece;

import Entities.Album.Album;
import Entities.Image.MyImage;
import Entities.Locations.AlbumsLocation.AlbumsLocation;
import Entities.Notification.Notification;
import Entities.User.User;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.AlbumsLocationRepository.AlbumLocationRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.NotificationsRepository.NotificationsRepository;
import Repositories.UserRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ViewAlbumService {
    @Autowired
    MyImageRepository myImageRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    AlbumLocationRepository albumLocationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationsRepository notificationsRepository;
    //private static String absolutePath = "C:\\Users\\dhnhd\\Desktop\\SneakPic\\SneakPic\\Server\\src\\main\\resources\\static\\";

    public boolean isThePhotographerWatching(Long albumId, String viewerUsername) {
        String albumPhotographer = albumRepository.getById(albumId).getPhotographer();
        if (albumPhotographer.equals(viewerUsername))
            return true;
        else
            return false;
    }

    public String generateImageTags(Long albumId, String viewerUsername) {
        //dictates whether the viewer is the photographer or not
        boolean isPhotographer = isThePhotographerWatching(albumId, viewerUsername);
        //creates image elements to send to browser
        //the images are ordered in 4 divs
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"row\">\n");
        List<MyImage> images = (List<MyImage>) myImageRepository.findAllByAlbumIdOrderByIdAsc(albumId);
        int imagesQuantity = images.size();
        if (imagesQuantity == 0)//no photos to show
            sb.append("<div class= noPhotos> No photos on this album</div>");
        else {
            StringBuilder column1 = new StringBuilder("<div class=\"column\">\n");
            StringBuilder column2 = new StringBuilder("<div class=\"column\">\n");
            // StringBuilder column3 = new StringBuilder("<div class=\"column\">\n");
            //StringBuilder column4 = new StringBuilder("<div class=\"column\">\n");

            images.forEach((elem) -> {
                Long imgId = elem.getId();
                String imgString = "<a href=\"/user/view-image/" + albumId.toString() + "/" + imgId.toString() +
                        "\"><img src=\"" + getRelativePath(imgId, albumId, isPhotographer) + "\"></a>\n";
                //each iteration add the img to one of the dives, decided by the altering index
                if (images.indexOf(elem) % 2 == 0)
                    column1.append(imgString);
                else
                    column2.append(imgString);
            });
            column1.append("</div>\n");
            column2.append("</div>\n");

            sb.append(column1);
            sb.append(column2);

        }
        sb.append("</div>\n"); // closes the "row" div
        return sb.toString();
    }


    public String getRelativePath(Long imgId, Long albumId, boolean isThePhotographer) {
        if (isThePhotographer)// meaning the one who views the album is the photographer and can watch without watermarks
            return "/Albums/" + albumId.toString() + "/" + imgId.toString() + ".jpeg";
        else // view album with watermarks
            return "/Albums/" + albumId.toString() + "/marked/" + imgId.toString() + "copy.jpeg";
    }

    public boolean isImageFirst(Long albumId, Long imgId) {
        return (myImageRepository.findFirstByAlbumIdOrderByIdAsc(albumId).get().getId().equals(imgId));
    }

    public boolean isImageLast(Long albumId, Long imgId) {
        return (myImageRepository.findFirstByAlbumIdOrderByIdDesc(albumId).get().getId().equals(imgId));
    }

    public Long getPrevImg(Long albumId, Long imgId) {
        Long prevId = myImageRepository.findFirstByIdIsLessThanAndAlbumIdOrderByIdDesc(imgId, albumId).get().getId();
        return prevId;
    }

    public Long getNextImg(Long albumId, Long imgId) {
        Long nextId = myImageRepository.findFirstByIdIsGreaterThanAndAlbumIdOrderByIdAsc(imgId, albumId).get().getId();
        return nextId;
    }

    public String generateAlbumInfo(Long albumId) {
        Album album = albumRepository.findById(albumId).get();
        AlbumsLocation locationInfo = albumLocationRepository.findByAlbumId(albumId);
        LocalDateTime timeOf = locationInfo.getFromTime().toLocalDateTime();
        User photographer = userRepository.getById(album.getPhotographer());
        return "<div class=\"photographer-bar\">\n" +
                "    <p>Album Name: <span class=\"field\">" + album.getName() + "</span></p>\n" +
                "    Date:<span class=\"field\"> " + timeOf.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "</span><br>\n" +
                "    Photographer: <span class=\"field\">" + photographer.getFirstName() + " " + photographer.getLastName() + "<a href =\"/user/view-photographer/" +
                album.getPhotographer() + "\"> <i class=\"fas fa-user-circle\"></i></a></span><br>\n" +
                "    Rate Album:<span class=\"stars\"id=\"rate\">" + this.printStars(albumId) + "</span><br></div>";
    }

    public String printStars(Long albumId) {
        String viewerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        StringBuilder sb = new StringBuilder();
        if (notificationsRepository.findFirstByUsernameAndSourceId(viewerUsername, albumId).isPresent()) {
            //if viewer already rated this album
            if (notificationsRepository.findFirstByUsernameAndSourceId(viewerUsername, albumId).get().getRate() > 0) {
                sb.append(" already rated by you");
                return sb.toString();
            }
        }

        for (int i = 1; i < 6; i++) {
            sb.append("<a id=\"star" + i + "\"><i class=\"fas fa-star\"></i></a>");
        }
        return sb.toString();

    }
}
