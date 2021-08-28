package Repositories.AlbumsLocationRepository.AlbumLocationSpecification;

import Entities.Locations.AlbumsLocation.AlbumsLocation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;

public class AlbumLocationSpecification {

    private EntityManager em;
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<AlbumsLocation> query = builder.createQuery(AlbumsLocation.class);
    Root<AlbumsLocation> root = query.from(AlbumsLocation.class);

        public static Specification<AlbumsLocation> doesAlbumTimeMatches(long checkInTime) {
            return new Specification<AlbumsLocation>(){
                @Override
                public Predicate toPredicate(Root<AlbumsLocation> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return cb.equal(root.get(AlbumsLocation.getTime()), today);;
                }
               // public Predicate toPredicate (Root < T > root, CriteriaQuery query, CriteriaBuilder cb){
                  //  return cb.equal(root.get(AlbumsLocation.date), today);
              //  }
            } ;
        }

        public static Specification<AlbumsLocation> isLongTermCustomer() {
            return new Specification<AlbumsLocation> {
                public Predicate toPredicate(Root<T> root, CriteriaQuery query, CriteriaBuilder cb) {
                    return cb.lessThan(root.get(Customer_.createdAt), new LocalDate.minusYears(2));
                }
            };
        }
}
