/**
 * 
 */
package ml.bootcode.profileapp.config.security;

import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import ml.bootcode.profileapp.models.User;
import ml.bootcode.profileapp.repositories.UserRepository;

/**
 * @author sunnyb
 *
 */
@Component
public class SecurityUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	/**
	 * @param userRepository
	 */
	public SecurityUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByEmail(username);

		if (!userOptional.isPresent()) {
			throw new BadCredentialsException("Bad credentials");
		}

		return new SecurityUserDetails(userOptional.get());
	}

}
