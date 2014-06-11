package open.source.moviereviews.security;

import open.source.moviereviews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		open.source.moviereviews.persistence.model.User dbUser = userService.getByUserName(username);
		UserDetails user = new User(username, dbUser.getPassword(), true, true, true, true, authList);
		return user;
	}
}
