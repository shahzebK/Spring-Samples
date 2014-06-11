package open.source.gurlal.controller;

import open.source.gurlal.domain.MovieDTO;
import open.source.gurlal.domain.ReviewDTO;
import open.source.gurlal.persistence.model.User;
import open.source.gurlal.service.MovieReviewService;
import open.source.gurlal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@Controller
@RequestMapping("/MovieReviewService/V1")
public class MovieReviewServiceControllerV1 {
	private static final Logger LOG = LoggerFactory.getLogger(MovieReviewServiceControllerV1.class);

	@Autowired
	private MovieReviewService movieReviewService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getByUserName(authentication.getName());
		String welcomeMessage =  "Welcome " + user.getFirstName() + " " + user.getLastName();
		LOG.debug(welcomeMessage);

		return welcomeMessage;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAllMovies")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Collection<MovieDTO> getAllMovies() {
		LOG.debug("In getAllMovies");

		return movieReviewService.getAllMovies();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getMovie/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public MovieDTO getMovie(@PathVariable Long id) {
		LOG.debug("In getMovie with id = " + id);
		return movieReviewService.getMovie(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/submitMovie")
	public ResponseEntity<MovieDTO> submitMovie(@RequestBody MovieDTO movie, UriComponentsBuilder builder) {
		LOG.debug("In submitMovie with movie = " + movie);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getByUserName(authentication.getName());
		MovieDTO newMovie =  movieReviewService.submitMovie(movie, user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				builder.path("/MovieReviewService/V1/getMovie/{id}")
						.buildAndExpand(newMovie.getId().toString()).toUri());

		return new ResponseEntity<MovieDTO>(newMovie, headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/editMovie/{id}")
	public ResponseEntity<MovieDTO> editMovie(@PathVariable Long id, @RequestBody MovieDTO movie, UriComponentsBuilder builder) {
		LOG.debug("In editMovie with id = " + id);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getByUserName(authentication.getName());
		ResponseEntity<MovieDTO> response = null;
		MovieDTO newMovie =  movieReviewService.editMovie(movie, user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
			builder.path("/MovieReviewService/V1/getMovie/{id}")
				.buildAndExpand(newMovie.getId().toString()).toUri());
		response = new ResponseEntity<MovieDTO>(newMovie, headers, HttpStatus.CREATED);
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/review")
	public ResponseEntity<MovieDTO> submitReview(@RequestBody ReviewDTO review, UriComponentsBuilder builder) {
		LOG.debug("In submitReview for movie with id = " + review.getMovieId());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getByUserName(authentication.getName());
		MovieDTO newMovie =  movieReviewService.submitReview(review, user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				builder.path("/MovieReviewService/V1/getMovie/" + review.getMovieId())
						.buildAndExpand(newMovie.getId().toString()).toUri());
		return new ResponseEntity<MovieDTO>(newMovie, headers, HttpStatus.CREATED);
	}
}
