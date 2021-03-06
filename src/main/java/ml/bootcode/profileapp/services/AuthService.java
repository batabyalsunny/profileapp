/**
 * 
 */
package ml.bootcode.profileapp.services;

import ml.bootcode.profileapp.dto.LoginRequestDto;
import ml.bootcode.profileapp.dto.LoginResponseDto;

/**
 * @author sunnyb
 *
 */
public interface AuthService {

	public LoginResponseDto login(LoginRequestDto loginRequestDto) throws RuntimeException;
}
