package open.source.gurlal.service;

import open.source.gurlal.domain.MovieDTO;
import open.source.gurlal.domain.ReviewDTO;
import open.source.gurlal.persistence.model.User;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface MovieReviewService {
	MovieDTO submitMovie(MovieDTO movie, User user) throws DataAccessException;
	MovieDTO editMovie(MovieDTO movie, User user) throws DataAccessException;
	MovieDTO submitReview(ReviewDTO review, User user) throws DataAccessException;
	Collection<MovieDTO> getAllMovies() throws DataAccessException;
	MovieDTO getMovie(Long id) throws DataAccessException;
}
