package open.source.gurlal.integration;


import open.source.gurlal.domain.MovieDTO;
import open.source.gurlal.domain.ReviewDTO;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ReviewsServiceControllerV1Test {
	private static final Logger LOG = LoggerFactory.getLogger(ReviewsServiceControllerV1Test.class);
	private String jSessionId = null;


	@Before
	public void login() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String,String> headers = new LinkedMultiValueMap<String, String>();

		String encodedUsernamePassword = Base64.encodeBase64String("admin:password".getBytes()).trim();
		LOG.debug("Authorization: Basic "+encodedUsernamePassword);
		headers.add("Authorization", "Basic "+encodedUsernamePassword);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> result = restTemplate.exchange("http://localhost:9090/MovieReviewService/V1/login", HttpMethod.GET, entity, String.class);

		String setCookie = result.getHeaders().get("Set-Cookie").get(0);
		jSessionId = StringUtils.split(setCookie, ";")[0];
		LOG.debug("jSessionId: "+jSessionId);
	}

	@Test
	public void submitMovieWillAddMovieToRepositoryUsingJSessionId() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		MovieDTO movieDTO = getSingleMovie();
		ObjectMapper objectMapper = new ObjectMapper();
		String singleMovieJSON = objectMapper.writeValueAsString(movieDTO);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Cookie", jSessionId);
		requestHeaders.add("Accept", "application/json");
		requestHeaders.add("Content-type", "application/json");

		HttpEntity requestEntity = new HttpEntity(singleMovieJSON, requestHeaders);
		ResponseEntity<String> result = restTemplate.exchange("http://localhost:9090/MovieReviewService/V1/submitMovie", HttpMethod.POST, requestEntity, String.class);
		assertThat(result.getStatusCode(), is(HttpStatus.CREATED));
		MovieDTO resultMovie = objectMapper.readValue(result.getBody(), MovieDTO.class);
		assertThat(resultMovie.getId(), is(notNullValue()));
	}

	private MovieDTO getSingleMovie() {
		MovieDTO movieDTO1 = new MovieDTO();
		movieDTO1.setId(null);
		movieDTO1.setMovieName("Test Movie 1");
		ReviewDTO reviewDTO1 = new ReviewDTO();
		reviewDTO1.setId(1l);
		reviewDTO1.setMovieId(1l);
		movieDTO1.addReview(reviewDTO1);
		return movieDTO1;
	}
}
