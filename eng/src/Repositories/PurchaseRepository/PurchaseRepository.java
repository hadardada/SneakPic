package Repositories.PurchaseRepository;

import Entities.Album.Album;
import Entities.Purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findImageId(int id);

}
