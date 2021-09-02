package Repositories.MyImageRepository;

import Entities.Album.Album;
import Entities.Image.MyImage;
import Entities.Notification.Notification;
import Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyImageRepository extends JpaRepository<MyImage, Long> {
    //Optional<Album> findByName(String username);
    Optional<MyImage> findById(int id);
    Iterable<MyImage> findAllByAlbumId(Long albumId);
    Iterable<MyImage> findAllByAlbumIdOrderByIdAsc(Long albumId);
    Optional<MyImage> findFirstByAlbumIdOrderByIdAsc(Long albumId);
    Optional<MyImage> findFirstByAlbumIdOrderByIdDesc(Long albumId);
    Optional<MyImage> findFirstByIdIsLessThanOrderByIdAsc(Long id);
    Optional<MyImage> findFirstByIdIsLessThanAndAlbumIdOrderByIdDesc(Long id, Long albumId);
    Optional<MyImage> findFirstByIdIsGreaterThanAndAlbumIdOrderByIdAsc(Long id, Long albumId);

}
