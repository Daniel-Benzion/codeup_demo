package com.codeup.codeup_demo.services;

import com.codeup.codeup_demo.models.User;
import com.codeup.codeup_demo.models.UserWithRoles;
import com.codeup.codeup_demo.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {

	private final UserRepository userDao;

	public UserDetailsLoader(UserRepository userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user found for " + username);
		}

		return new UserWithRoles(user);
	}
}
