/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.config.security.JwtTokenProvider;
import ml.bootcode.profileapp.dto.LoginRequestDto;
import ml.bootcode.profileapp.dto.LoginResponseDto;
import ml.bootcode.profileapp.models.Employee;
import ml.bootcode.profileapp.repositories.EmployeeRepository;
import ml.bootcode.profileapp.services.AuthService;

/**
 * @author sunnyb
 *
 */
@Service
public class AuthServiceImpl implements AuthService {

	private EmployeeRepository employeeRepository;
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;

	/**
	 * @param employeeRepository
	 * @param authenticationManager
	 * @param jwtTokenProvider
	 */
	public AuthServiceImpl(EmployeeRepository employeeRepository, AuthenticationManager authenticationManager,
			JwtTokenProvider jwtTokenProvider) {
		this.employeeRepository = employeeRepository;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) throws AuthenticationException {
		LoginResponseDto loginResponseDto = new LoginResponseDto();

		String email = loginRequestDto.getUsername();
		String password = loginRequestDto.getPassword();

		try {
			// Try authenticating.
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

			// Get authenticated user roles.
			Optional<Employee> userOptional = employeeRepository.findByEmail(email);

			if (!userOptional.isPresent())
				throw new AuthenticationException("User not found");

			List<String> roles = userOptional.get().getDesignation().getAuthorities().stream()
					.map(authority -> authority.getName()).collect(Collectors.toList());

			loginResponseDto.setToken(jwtTokenProvider.createToken(email, roles));
		} catch (Exception e) {
			throw new AuthenticationException("Invalid username/password supplied");
		}

		return loginResponseDto;
	}

}
