package open.source.gurlal.persistence.repos;


import open.source.gurlal.persistence.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
