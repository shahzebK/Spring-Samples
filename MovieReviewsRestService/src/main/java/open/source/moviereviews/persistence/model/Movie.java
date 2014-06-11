package open.source.moviereviews.persistence.model;

import open.source.moviereviews.domain.MovieDTO;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String movieName;
	private String movieDescription;
	@ManyToOne
	@JoinColumn(name = "createdBy")
	private User createdBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "movie", fetch = FetchType.EAGER)
	private Set<Review> reviewSet;

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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Set<Review> getReviewSet() {
		return reviewSet;
	}

	public void setReviewSet(Set<Review> reviewSet) {
		this.reviewSet = reviewSet;
	}

	public String getMovieDescription() {
		return movieDescription;
	}

	public void setMovieDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}

	public MovieDTO toDTO() {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(this.getId());
		movieDTO.setMovieName(this.getMovieName());
		movieDTO.setMovieDescription(this.getMovieDescription());
		if(this.getReviewSet() != null) {
			for(Review review : this.getReviewSet()) {
				movieDTO.addReview(review.toDTO());
			}
		}
		return movieDTO;
	}

	public void addReview(Review review) {
		if(this.reviewSet == null) {
			this.reviewSet = new HashSet<Review>();
		}
		this.reviewSet.add(review);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Movie movie = (Movie) o;

		if (createdBy != null ? !createdBy.equals(movie.createdBy) : movie.createdBy != null) return false;
		if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
		if (movieDescription != null ? !movieDescription.equals(movie.movieDescription) : movie.movieDescription != null)
			return false;
		if (movieName != null ? !movieName.equals(movie.movieName) : movie.movieName != null) return false;
		if (reviewSet != null ? !reviewSet.equals(movie.reviewSet) : movie.reviewSet != null) return false;

		return true;
	}
}
