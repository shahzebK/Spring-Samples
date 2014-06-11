package open.source.gurlal.service;


import open.source.gurlal.domain.MovieDTO;
import open.source.gurlal.domain.ReviewDTO;
import open.source.gurlal.persistence.model.Movie;
import open.source.gurlal.persistence.model.Review;
import open.source.gurlal.persistence.model.User;
import open.source.gurlal.persistence.repos.ReviewRepository;
import open.source.gurlal.persistence.repos.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class MovieReviewServiceImpl implements MovieReviewService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public MovieDTO submitMovie(MovieDTO movie, User user) throws DataAccessException {
		Movie modelMovie =  new Movie();
		modelMovie.setId(null);
		modelMovie.setCreatedBy(user);
		modelMovie.setMovieName(movie.getMovieName());
		modelMovie = movieRepository.save(modelMovie);
		movie.setId(modelMovie.getId());
		return movie;
	}

	@Override
	public MovieDTO editMovie(MovieDTO movie, User user) throws DataAccessException {
		Movie persistedMovie = movieRepository.findOne(movie.getId());
		if(persistedMovie != null && persistedMovie.getCreatedBy().equals(user)) {
			persistedMovie.setMovieName(movie.getMovieName());
			persistedMovie = movieRepository.save(persistedMovie);
			return movie;
		} else {
			throw new RuntimeException("Unauthorised");
		}
	}

	@Override
	public MovieDTO submitReview(ReviewDTO review, User user) throws DataAccessException {
		Movie persistedMovie = movieRepository.findOne(review.getMovieId());
		Review modelReview = new Review();
		modelReview.setId(null);
		modelReview.setCreatedBy(user);
		modelReview.setReview(review.getReview());
		modelReview.setMovie(persistedMovie);
		persistedMovie.addReview(modelReview);
		persistedMovie = movieRepository.save(persistedMovie);
		return persistedMovie.toDTO();
	}

	@Override
	public Collection<MovieDTO> getAllMovies() throws DataAccessException {
		Collection<MovieDTO> movies = new ArrayList<MovieDTO>();
		Collection<Movie> persistedMovies = movieRepository.findAll();

		for(Movie persistedMovie : persistedMovies) {
			movies.add(persistedMovie.toDTO());
		}
		return movies;
	}

	@Override
	public MovieDTO getMovie(Long id) throws DataAccessException {
		Movie movie = movieRepository.findById(id);
		return movie == null ? null : movie.toDTO();
	}
}
