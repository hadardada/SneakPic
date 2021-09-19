package Controllers.Purchase;

import Entities.Album.Album;
import Entities.Image.MyImage;
import Repositories.AlbumRepository.AlbumRepository;
import Repositories.MyImageRepository.MyImageRepository;
import Repositories.PurchaseRepository.PurchaseRepository;
import Repositories.UserRepository.UserRepository;
import UsersManagement.LoadImageServiece.AlbumDetails;
import UsersManagement.LoadImageServiece.ViewAlbumService;
import UsersManagement.PurchaseService.PurchaseService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Jsp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PurchaseController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    MyImageRepository myImageRepository;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    ViewAlbumService viewAlbumService;

    private Gson gson = new Gson();

    @RequestMapping(value = "/user/payment/{albumId}/{imgId}")
    public String showPaymentPage() {
        //returns payment html page
        return "payment";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/add-purchase/{albumId}/{imgId}")
    public ResponseEntity<URL> addPurchase(@PathVariable Long albumId, @PathVariable Long imgId) {
        //get buyer from logged in user
        String buyer = SecurityContextHolder.getContext().getAuthentication().getName();
        //get seller from album's photographer
        String seller = albumRepository.getAlbumById(albumId).get().getPhotographer();

        //if the buyer is the album's photographer
        if (buyer.equals(seller))
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        //else
        URL url = viewAlbumService.getImgUrlForUser(albumId, imgId); //get download temporary URL
        if (purchaseService.addNewPurchase(buyer, seller, albumId, imgId)) //if it's a new purchase
           return new ResponseEntity(gson.toJson(url), HttpStatus.OK);

        else //if buyer already purchased this img, still gets the url but get a massage from server
            return new ResponseEntity(gson.toJson(url), HttpStatus.ALREADY_REPORTED);
    }
}