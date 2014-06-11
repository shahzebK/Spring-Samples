package open.source.gurlal.persistence.model;

import open.source.gurlal.domain.ReviewDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "movie")
	private Movie movie;
	private String review;
	@ManyToOne
	@JoinColumn(name = "createdBy")
	private User createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Review review = (Review) o;

		if (this.review != null ? !this.review.equals(review.review) : review.review != null) return false;
		if (createdBy != null ? !createdBy.equals(review.createdBy) : review.createdBy != null) return false;
		if (id != null ? !id.equals(review.id) : review.id != null) return false;
		if (movie != null ? !movie.equals(review.movie) : review.movie != null) return false;

		return true;
	}

	public ReviewDTO toDTO() {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setId(this.getId());
		reviewDTO.setReview(this.getReview());
		if(this.getMovie() != null) {
			reviewDTO.setMovieId(this.getMovie().getId());
		}
		return reviewDTO;
	}

}
