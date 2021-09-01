package Entities.Album;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


/* each album location (see class:AlbumsLocation) has only one album-id
 */
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // so the ID is generated automatically
    private Long id;
    private String name;
    private Timestamp uploadedDate;
    private String photographer;
}
