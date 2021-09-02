package Repositories.NotificationsRepository;

import Entities.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsRepository extends JpaRepository<Notification, Long> {
    Iterable<Notification> findAllByUsernameOrderByWasReadAsc(String username);
    Iterable<Notification> findAllByUsername(String username);

}
