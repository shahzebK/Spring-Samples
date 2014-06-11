package open.source.gurlal.persistence.repos;


import open.source.gurlal.persistence.model.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface MovieRepository extends CrudRepository<Movie, Long> {
	Collection<Movie> findAll() throws DataAccessException;
	Movie findById(Long id) throws DataAccessException;
}
