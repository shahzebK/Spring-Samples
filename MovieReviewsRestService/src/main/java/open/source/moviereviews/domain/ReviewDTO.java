package open.source.moviereviews.domain;

/**
 * Created with IntelliJ IDEA.
 * User: moviereviews
 * Date: 5/13/14
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReviewDTO {
	private Long id;
	private Long movieId;
	private String review;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
}
