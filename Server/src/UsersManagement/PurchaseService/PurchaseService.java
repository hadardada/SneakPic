package UsersManagement.PurchaseService;

import Entities.Purchase.Purchase;
import NotificationsManagement.NotificationsService.NotificationsService;
import Repositories.PurchaseRepository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    NotificationsService notificationsService;

    //This method creates and adds new Purchase instance to DB
    //Returns True if such purchase never been made before (seller+buyer+imgId)
    //Returns False otherwise.
    public boolean addNewPurchase (String buyer, String seller, Long albumId, Long imgId){
        if (purchaseRepository.getByBuyerIdAndSellerIdAndImageId(buyer, seller, imgId).isPresent())
            return false;
        Purchase newPurchase = new Purchase(imgId, buyer, seller);
        purchaseRepository.save(newPurchase);
        notificationsService.createPurchaseNotification(seller, imgId);
        return true;
    }
}
