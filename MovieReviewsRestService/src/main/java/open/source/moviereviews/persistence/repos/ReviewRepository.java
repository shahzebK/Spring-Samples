package open.source.moviereviews.persistence.repos;


import open.source.moviereviews.persistence.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
