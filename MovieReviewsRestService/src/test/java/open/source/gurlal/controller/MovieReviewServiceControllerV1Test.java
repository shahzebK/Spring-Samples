package open.source.gurlal.controller;


import open.source.gurlal.domain.MovieDTO;
import open.source.gurlal.domain.ReviewDTO;
import open.source.gurlal.persistence.model.User;
import open.source.gurlal.service.MovieReviewService;
import open.source.gurlal.service.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class MovieReviewServiceControllerV1Test {

	private MockMvc mockMvc;

	@Mock
	private MovieReviewService movieReviewService;

	@Mock
	private UserService userService;

	@InjectMocks
	private MovieReviewServiceControllerV1 controllerV1;


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(controllerV1)
				.setMessageConverters(new MappingJackson2HttpMessageConverter(),
						new Jaxb2RootElementHttpMessageConverter()).build();
	}


	@Test
	public void thatUserCanLogon() throws Exception {
		Authentication authentication = mock(Authentication.class);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user1 = mock(User.class);
		when(authentication.getName()).thenReturn("admin");
		when(user1.getFirstName()).thenReturn("Admin");
		when(user1.getLastName()).thenReturn("User");
		when(userService.getByUserName("admin")).thenReturn(user1);
		MvcResult result = mockMvc.perform(
				get("/MovieReviewService/V1/login").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void getAllMoviesReturnListOfMovies() throws Exception {

		when(movieReviewService.getAllMovies()).thenReturn(getAllMovies());
		ObjectMapper objectMapper = new ObjectMapper();
		String allMoviesJSON = objectMapper.writeValueAsString(getAllMovies());


		MvcResult result = mockMvc.perform(
				get("/MovieReviewService/V1/getAllMovies").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertThat(allMoviesJSON, is(result.getResponse().getContentAsString()));

	}

	@Test
	public void getMovieWillReturnMovieById() throws Exception {

		when(movieReviewService.getMovie(1l)).thenReturn(getSingleMovie());
		ObjectMapper objectMapper = new ObjectMapper();
		String singleMovieJSON = objectMapper.writeValueAsString(getSingleMovie());


		MvcResult result = mockMvc.perform(
				get("/MovieReviewService/V1/getMovie/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertThat(singleMovieJSON, is(result.getResponse().getContentAsString()));


	}


	@Test
	public void submitMovieWillAddMovieToRepository() throws Exception {
		Authentication authentication = mock(Authentication.class);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user1 = mock(User.class);
		when(authentication.getName()).thenReturn("admin");
		when(user1.getFirstName()).thenReturn("Admin");
		when(user1.getLastName()).thenReturn("User");
		when(userService.getByUserName("admin")).thenReturn(user1);

		MovieDTO movieDTO = getSingleMovie();
		ObjectMapper objectMapper = new ObjectMapper();
		String singleMovieJSON = objectMapper.writeValueAsString(movieDTO);

		when(movieReviewService.submitMovie(any(MovieDTO.class), any(User.class))).thenReturn(getSingleMovie());


		MvcResult result = mockMvc.perform(
				post("/MovieReviewService/V1/submitMovie").contentType(MediaType.APPLICATION_JSON).content(singleMovieJSON))
				.andExpect(status().isCreated()).andReturn();

		assertThat(singleMovieJSON, is(result.getResponse().getContentAsString()));

	}

	@Test
	public void editMovieWillUpdateMovieToRepository() throws Exception {
		Authentication authentication = mock(Authentication.class);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user1 = mock(User.class);
		when(authentication.getName()).thenReturn("admin");
		when(user1.getFirstName()).thenReturn("Admin");
		when(user1.getLastName()).thenReturn("User");
		when(userService.getByUserName("admin")).thenReturn(user1);

		MovieDTO movieDTO = getSingleMovie();
		ObjectMapper objectMapper = new ObjectMapper();
		String singleMovieJSON = objectMapper.writeValueAsString(movieDTO);

		when(movieReviewService.editMovie(any(MovieDTO.class), any(User.class))).thenReturn(getSingleMovie());


		MvcResult result = mockMvc.perform(
				post("/MovieReviewService/V1/editMovie/1").contentType(MediaType.APPLICATION_JSON).content(singleMovieJSON))
				.andExpect(status().isCreated()).andReturn();

		assertThat(singleMovieJSON, is(result.getResponse().getContentAsString()));

	}

	@Test
	public void reviewWillAddReviewToRepository() throws Exception {
		Authentication authentication = mock(Authentication.class);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user1 = mock(User.class);
		when(authentication.getName()).thenReturn("admin");
		when(user1.getFirstName()).thenReturn("Admin");
		when(user1.getLastName()).thenReturn("User");
		when(userService.getByUserName("admin")).thenReturn(user1);

		MovieDTO movieDTO = getSingleMovie();
		ReviewDTO reviewDTO1 = new ReviewDTO();
		reviewDTO1.setId(2l);
		reviewDTO1.setMovieId(1l);
		reviewDTO1.setReview("Review1");
		movieDTO.addReview(reviewDTO1);
		ObjectMapper objectMapper = new ObjectMapper();
		String singleMovieJSON = objectMapper.writeValueAsString(movieDTO);

		when(movieReviewService.submitReview(any(ReviewDTO.class), any(User.class))).thenReturn(movieDTO);


		MvcResult result = mockMvc.perform(
				post("/MovieReviewService/V1/review/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reviewDTO1)))
				.andExpect(status().isCreated()).andReturn();

		assertThat(singleMovieJSON, is(result.getResponse().getContentAsString()));
	}


	private  Collection<MovieDTO> getAllMovies() {
		Collection<MovieDTO> movies = new ArrayList<MovieDTO>();
		MovieDTO movieDTO1 = new MovieDTO();
		movieDTO1.setId(1l);
		movieDTO1.setMovieName("Test Movie 1");
		ReviewDTO reviewDTO1 = new ReviewDTO();
		reviewDTO1.setId(1l);
		reviewDTO1.setMovieId(1l);
		movieDTO1.addReview(reviewDTO1);
		movies.add(movieDTO1);
		MovieDTO movieDTO2 = new MovieDTO();
		movieDTO2.setId(2l);
		movieDTO2.setMovieName("Test Movie 1");
		ReviewDTO reviewDTO2 = new ReviewDTO();
		reviewDTO2.setId(2l);
		reviewDTO2.setMovieId(2l);
		movieDTO2.addReview(reviewDTO2);
		movies.add(movieDTO2);
		return movies;
	}

	private MovieDTO getSingleMovie() {
		MovieDTO movieDTO1 = new MovieDTO();
		movieDTO1.setId(1l);
		movieDTO1.setMovieName("Test Movie 1");
		ReviewDTO reviewDTO1 = new ReviewDTO();
		reviewDTO1.setId(1l);
		reviewDTO1.setMovieId(1l);
		movieDTO1.addReview(reviewDTO1);
		return movieDTO1;
	}

}
