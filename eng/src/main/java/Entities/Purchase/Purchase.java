package Entities.Purchase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // so the ID is generated automatically
    private Long id;
    private Long imageId;
    private String buyerId;
    private String sellerId;
    private Timestamp purchaseDate;


    public Purchase(Long imageId, String buyerUserName, String sellerUsername){
        this.buyerId = buyerUserName;
        this.sellerId= sellerUsername;
        this.imageId = imageId;
        Date date = new Date();
        this.purchaseDate = new Timestamp(date.getTime());
    }

    public Purchase() {

    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerId() {
        return buyerId;
    }
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        imageId = imageId;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
