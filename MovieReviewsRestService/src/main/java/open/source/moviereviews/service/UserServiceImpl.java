package open.source.moviereviews.service;

import open.source.moviereviews.persistence.model.User;
import open.source.moviereviews.persistence.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public User getById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User getByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}
}
