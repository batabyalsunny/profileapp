/**
 *
 */
package ml.bootcode.profileapp.controllers;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.dto.LoginRequestDto;
import ml.bootcode.profileapp.dto.LoginResponseDto;
import ml.bootcode.profileapp.services.AuthService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

	private AuthService authService;

	/**
	 * @param authService
	 */
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("login")
	public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) throws AuthenticationException {
		return authService.login(loginRequestDto);
	}

//	@PostMapping("register")
//	public UserDto register(@RequestBody UserDto userDto) {
//		return userService.addUser(userDto);
//	}
}
