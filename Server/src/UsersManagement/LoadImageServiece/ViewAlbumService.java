package UsersManagement.LoadImageServiece;

import Entities.Image.MyImage;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ViewAlbumService {
    @Autowired
    MyImageRepository myImageRepository;

    @Autowired
    AlbumRepository albumRepository;
    //private static String absolutePath = "C:\\Users\\dhnhd\\Desktop\\SneakPic\\SneakPic\\Server\\src\\main\\resources\\static\\";

    public boolean isThePhotographerWatching (Long albumId,String viewerUsername ){
        String albumPhotographer = albumRepository.getById(albumId).getPhotographer();
        if (albumPhotographer.equals(viewerUsername))
            return true;
        else
            return false;
    }

    public String generateImageTags(Long albumId, String viewerUsername){
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
                if (images.indexOf(elem)%2 ==0)
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


    public String getRelativePath(Long imgId, Long albumId, boolean isThePhotographer){
        if (isThePhotographer)// meaning the one who views the album is the photographer and can watch without watermarks
            return "/Albums/"+albumId.toString()+"/"+imgId.toString()+".jpeg";
        else // view album with watermarks
            return "/Albums/"+albumId.toString()+"/marked/"+imgId.toString()+"copy.jpeg";
    }

    public boolean isImageFirst (Long albumId, Long imgId){
        return (myImageRepository.findFirstByAlbumIdOrderByIdAsc(albumId).get().getId().equals(imgId));
    }

    public boolean isImageLast (Long albumId, Long imgId){
        return (myImageRepository.findFirstByAlbumIdOrderByIdDesc(albumId).get().getId().equals(imgId));
    }

    public Long getPrevImg (Long albumId, Long imgId){
     Long prevId = myImageRepository.findFirstByIdIsLessThanAndAlbumIdOrderByIdDesc(imgId, albumId).get().getId();
     return prevId;
    }

    public Long getNextImg(Long albumId, Long imgId){
        Long nextId = myImageRepository.findFirstByIdIsGreaterThanAndAlbumIdOrderByIdAsc(imgId, albumId).get().getId();
        return nextId;
    }
}
