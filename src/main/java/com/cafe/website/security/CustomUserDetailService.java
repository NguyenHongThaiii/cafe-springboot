package com.cafe.website.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cafe.website.entity.User;
import com.cafe.website.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	private UserRepository userRepository;

	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user;
		if (email.contains("@")) { // Nếu truyền vào là email
			user = userRepository.findByEmail(email)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		} else { // Nếu truyền vào là email

			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
}
