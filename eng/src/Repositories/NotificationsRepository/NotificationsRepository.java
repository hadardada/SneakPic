package Repositories.NotificationsRepository;

import Entities.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findAllByUsernameOrderByCreatedOnDesc(String username);
    Iterable<Notification> findAllByUsername(String username);
    Notification getFirstByUsernameAndSourceId(String username, Long sourceId);
    Optional<Notification> findFirstByUsernameAndSourceId(String username, Long sourceId);
    long countByUsernameAndWasWatched(String username, boolean wasWatched);

}
