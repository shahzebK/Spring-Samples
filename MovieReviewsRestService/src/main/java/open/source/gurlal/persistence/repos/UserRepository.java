package open.source.gurlal.persistence.repos;


import open.source.gurlal.persistence.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	User findById(Long id) throws DataAccessException;
	User findByUserName(String userName) throws DataAccessException;
}
