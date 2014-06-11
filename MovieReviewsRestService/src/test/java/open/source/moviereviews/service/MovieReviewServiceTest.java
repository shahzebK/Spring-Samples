package open.source.moviereviews.service;

import open.source.moviereviews.domain.MovieDTO;
import open.source.moviereviews.domain.ReviewDTO;
import open.source.moviereviews.persistence.model.Movie;
import open.source.moviereviews.persistence.model.Review;
import open.source.moviereviews.persistence.model.User;
import open.source.moviereviews.persistence.repos.ReviewRepository;
import open.source.moviereviews.persistence.repos.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieReviewServiceTest {

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private ReviewRepository reviewRepository;

	@InjectMocks
	private MovieReviewServiceImpl movieReviewService;

	@Mock
	private User user1;

	@Mock
	private User user2;

	@Test
	public void  submitMovieWillAddNewMovie() {
		MovieDTO movie = new MovieDTO();
		movie.setMovieName("Test Movie 1 ?");
		Movie persistedMovie = mock(Movie.class);
		when(persistedMovie.getMovieName()).thenReturn(movie.getMovieName());
		when(persistedMovie.getId()).thenReturn(1l);
		when(persistedMovie.getCreatedBy()).thenReturn(user1);
		when(movieRepository.save(any(Movie.class))).thenReturn(persistedMovie);

		MovieDTO submittedMovie = movieReviewService.submitMovie(movie, user1);

		assertThat(submittedMovie.getMovieName(), is(movie.getMovieName()));
		assertThat(submittedMovie.getId(), is(1l));
	}

	@Test
	public void  editMovieWillAddNewMovie() {
		MovieDTO movie = new MovieDTO();
		movie.setMovieName("Test Movie 1 update 1?");
		movie.setId(1l);
		Movie persistedMovie = mock(Movie.class);
		when(persistedMovie.getMovieName()).thenReturn(movie.getMovieName());
		when(persistedMovie.getId()).thenReturn(1l);
		when(persistedMovie.getCreatedBy()).thenReturn(user1);
		when(movieRepository.save(any(Movie.class))).thenReturn(persistedMovie);
		when(movieRepository.findOne(1l)).thenReturn(persistedMovie);

		MovieDTO updatedMovie = movieReviewService.editMovie(movie, user1);

		assertThat(updatedMovie.getMovieName(), is(movie.getMovieName()));
		assertThat(updatedMovie.getId(), is(1l));
	}

	@Test
	public void  getMovieWillReturnMovieById() {
		Movie persistedMovie = new Movie();
		persistedMovie.setMovieName("Brave Heart");
		persistedMovie.setMovieDescription("cast: Mel gibson");
		persistedMovie.setId(1l);
		persistedMovie.setCreatedBy(user1);

																																																					when(movieRepository.findById(1l)).thenReturn(persistedMovie);

		MovieDTO movie = movieReviewService.getMovie(1l);
		assertThat(movie.getMovieName(), is(persistedMovie.getMovieName()));
		assertThat(movie.getId(), is(1l));
	}

	@Test
	public void  getAllMovieWillReturnAllMovies() {
		Movie persistedMovie = new Movie();
		persistedMovie.setMovieName("Brave Heart");
		persistedMovie.setMovieDescription("cast: Mel gibson");
		persistedMovie.setId(1l);
		persistedMovie.setCreatedBy(user1);

		Movie persistedMovie1 = new Movie();
		persistedMovie1.setMovieName("Gladiator");
		persistedMovie1.setMovieDescription("cast: Russel");
		persistedMovie1.setId(2l);
		persistedMovie1.setCreatedBy(user1);

		ArrayList<Movie> movieList = new ArrayList<Movie>();
		movieList.add(persistedMovie);
		movieList.add(persistedMovie1);

		when(movieRepository.findAll()).thenReturn(movieList);

		Collection<MovieDTO> movies = movieReviewService.getAllMovies();
		assertThat(movies.size(), is(2) );
	}

	@Test
	public void  submitReviewWillUpdateTheMovieWithNewReview() {

		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setMovieId(1l);
		reviewDTO.setReview("Review 2");

		Movie persistedMovie1 = new Movie();
		persistedMovie1.setMovieName("Gladiator");
		persistedMovie1.setMovieDescription("cast: Russel");
		persistedMovie1.setId(1l);
		persistedMovie1.setCreatedBy(user1);
		Review review = new Review();
		review.setId(1l);
		review.setCreatedBy(user1);
		review.setMovie(persistedMovie1);
		review.setReview(reviewDTO.getReview());
		persistedMovie1.addReview(review);


		when(movieRepository.findOne(1l)).thenReturn(persistedMovie1);
		when(movieRepository.save(any(Movie.class))).thenReturn(persistedMovie1);

		MovieDTO movie = movieReviewService.submitReview(reviewDTO, user1);
		assertThat(movie.getReviews().size(), is(2) );
	}
}
