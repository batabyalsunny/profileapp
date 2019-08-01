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
import org.springframework.security.crypto.password.PasswordEncoder;

import ml.bootcode.profileapp.config.security.JwtTokenProvider;
import ml.bootcode.profileapp.dto.LoginRequestDto;
import ml.bootcode.profileapp.dto.LoginResponseDto;
import ml.bootcode.profileapp.dto.UserDto;
import ml.bootcode.profileapp.models.User;
import ml.bootcode.profileapp.repositories.UserRepository;

/**
 * @author sunnybatabyal
 *
 */
//@Service
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private AuthenticationManager authenticationManager;
	private JwtTokenProvider jwtTokenProvider;

	/**
	 * @param userRepository
	 * @param passwordEncoder
	 * @param authenticationManager
	 * @param jwtTokenProvider
	 */
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public List<UserDto> getUsers() {
		return userRepository.findAll().stream().map(user -> mapUserToUserDto(user)).collect(Collectors.toList());
	}

	public UserDto getUserById(Long id) {
		return mapUserToUserDto(validateUserOptional(userRepository.findById(id)));
	}

	public UserDto getUserByEmail(String email) {
		return mapUserToUserDto(validateUserOptional(userRepository.findByEmail(email)));
	}

	public UserDto addUser(UserDto userDto) {
		if (userRepository.findByEmail(userDto.getEmail()).isPresent())
			throw new RuntimeException("Email Id already exists");

		User user = new User();

		return mapUserToUserDto(userRepository.save(mapUserDtoToUser(user, userDto)));
	}

	public UserDto updateUser(Long id, UserDto userDto) {
		return mapUserToUserDto(
				userRepository.save(mapUserDtoToUser(validateUserOptional(userRepository.findById(id)), userDto)));
	}

	public void deleteUser(Long id) {
		userRepository.delete(validateUserOptional(userRepository.findById(id)));
	}

	public User validateUserOptional(Optional<User> userOptional) {
		if (!userOptional.isPresent())
			throw new RuntimeException("User Not Found");

		return userOptional.get();
	}

	public UserDto mapUserToUserDto(User user) {
		UserDto userDto = new UserDto();

		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setRoles(user.getRoles());

		return userDto;
	}

	public LoginResponseDto login(LoginRequestDto loginRequestDto) throws AuthenticationException {

		LoginResponseDto loginResponseDto = new LoginResponseDto();

		String email = loginRequestDto.getUsername();
		String password = loginRequestDto.getPassword();

		try {
			// Try authenticating.
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

			// Get authenticated user roles.
			Optional<User> userOptional = userRepository.findByEmail(email);

			if (!userOptional.isPresent())
				throw new AuthenticationException("User not found");

			List<String> roles = userOptional.get().getRoles().stream().map(role -> role.getName())
					.collect(Collectors.toList());

			loginResponseDto.setToken(jwtTokenProvider.createToken(email, roles));
		} catch (Exception e) {
			throw new AuthenticationException("Invalid username/password supplied");
		}

		return loginResponseDto;
	}

	public User mapUserDtoToUser(User user, UserDto userDto) {
		user.setEmail(userDto.getEmail());
		user.setRoles(userDto.getRoles());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		return user;
	}
}
