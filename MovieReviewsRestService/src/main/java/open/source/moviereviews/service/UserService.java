package open.source.moviereviews.service;

import open.source.moviereviews.persistence.model.User;

public interface UserService {
	User getById(Long id);
	User getByUserName(String userName);
}
