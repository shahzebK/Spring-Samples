package open.source.gurlal.service;

import open.source.gurlal.persistence.model.User;

public interface UserService {
	User getById(Long id);
	User getByUserName(String userName);
}
