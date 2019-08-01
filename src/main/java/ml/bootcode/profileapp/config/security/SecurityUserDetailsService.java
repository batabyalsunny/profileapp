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

import ml.bootcode.profileapp.models.Employee;
import ml.bootcode.profileapp.repositories.EmployeeRepository;

/**
 * @author sunnyb
 *
 */
@Component
public class SecurityUserDetailsService implements UserDetailsService {

	private EmployeeRepository employeeRepository;

	/**
	 * @param userRepository
	 * @param employeeRepository
	 */
	public SecurityUserDetailsService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Employee> employeeOptional = employeeRepository.findByEmail(username);

		if (!employeeOptional.isPresent()) {
			throw new BadCredentialsException("Bad credentials");
		}

		return new SecurityUserDetails(employeeOptional.get());
	}

}
