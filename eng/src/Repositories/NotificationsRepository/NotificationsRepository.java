package Repositories.NotificationsRepository;

import Entities.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findAllByUsernameOrderByCreatedOnDesc(String username);
    Iterable<Notification> findAllByUsername(String username);
    Iterable<Notification> findAllByUsernameAndTypeNoti(String username, short typeNoti);
    Notification getFirstByUsernameAndSourceIdAndTypeNoti(String username, Long sourceId, short typeNoti);
    Optional<Notification> findFirstByUsernameAndSourceIdAndTypeNoti(String username, Long sourceId, short typeNoti);
    long countByUsernameAndWasWatched(String username, boolean wasWatched);
    Notification getFirstByUsernameAndSourceId(String username, Long sourceId);


}
