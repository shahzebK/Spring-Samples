package open.source.moviereviews.domain;

import java.util.ArrayList;
import java.util.List;


public class MovieDTO {
	private Long id;
	private String movieName;
	private String movieDescription;
	private List<ReviewDTO> reviews;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public List<ReviewDTO> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDTO> reviews) {
		this.reviews = reviews;
	}

	public String getMovieDescription() {
		return movieDescription;
	}

	public void setMovieDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}

	public void  addReview(ReviewDTO answer) {
		if(this.reviews == null) this.reviews = new ArrayList<ReviewDTO>();
		this.reviews.add(answer);
	}
}
