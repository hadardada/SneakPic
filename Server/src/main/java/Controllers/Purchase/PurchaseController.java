package Controllers.Purchase;

import Entities.Album.Album;
import Entities.Image.MyImage;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.UserRepository.UserRepository;
import UsersManagement.LoadImageServiece.AlbumDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PurchaseController {

    //@Autowired
   // private PurchaseService purchaseService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    MyImageRepository myImageRepository;


    @RequestMapping( value = "/user/add-purchase")
    public String showRelevantAlbums(){
        //lets say for now, that all relevant albums are even. thaen we collect by position

        //ModelAndView listOfAlbums = new ModelAndView("Albums");
//        List<Album> albums = albumRepository.findAll().stream().filter(s->s.getId()%2 ==0).collect(Collectors.toList());
//        model.addAttribute(albums);
        //listOfAlbums.addObject(albums);
        Long id = Long.getLong("28");
        //Album album28 = albumRepository.getById(id);
        List<MyImage> imagesOfAlbum = myImageRepository.findAll().stream().filter(i->i.getAlbumId() == id).collect(Collectors.toList());

        List<String> imagesMarkedPath = new ArrayList<>();
        imagesOfAlbum.forEach(i -> imagesMarkedPath.add(i.getPathMarked().substring(70)));

       // model.addAttribute("images",imagesMarkedPath);

        return "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                generateImageTags(imagesMarkedPath) +
                "</body>\n" +
                "</html>";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/add-purchase/{path}")
    public void addNewAlbum(@RequestBody AlbumDetails albumDetails, @PathVariable String path) throws IOException {
       // this.currentAlbumId = loadAlbumService.addNewAlbum(albumDetails, userRepository, albumRepository);
        System.out.println("here");
    }


    private String generateImageTags(List<String> paths){
        StringBuilder sb = new StringBuilder();
        for (String path: paths) {
            //sb.append("<form id=\"singlePurchase\" method=\"post\" action=\"/user/add-purchase"+ path +">\n");
//            sb.append("<li>\n");
//            sb.append("<form method=\"post\" action=\"/user/add-purchase"+ path +">\n");

            sb.append("<img height=300 width=300 src=\""+path+"\">\n");
//            sb.append("<button type=\"button\" name=\"purchase\">\n" +
//                    "<input type=\"submit\" value=\"Purchase\">\n");

//            sb.append("</form>\n");
//            sb.append("</li>\n");
           // sb.append("</form>\n");

        }

        return sb.toString();
    }
}
