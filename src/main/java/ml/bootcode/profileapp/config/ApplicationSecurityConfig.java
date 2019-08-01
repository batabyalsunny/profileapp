/**
 *
 */
package ml.bootcode.profileapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import ml.bootcode.profileapp.config.security.JwtTokenFilterConfigurer;
import ml.bootcode.profileapp.config.security.JwtTokenProvider;

/**
 * @author sunnyb
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private JwtTokenProvider jwtTokenProvider;
	private AuthenticationEntryPoint authenticationEntryPoint;
	private LogoutSuccessHandler logoutSuccessHandler;

	/**
	 * @param jwtTokenProvider
	 * @param authenticationEntryPoint
	 * @param logoutSuccessHandler
	 */
	public ApplicationSecurityConfig(JwtTokenProvider jwtTokenProvider,
			AuthenticationEntryPoint authenticationEntryPoint, LogoutSuccessHandler logoutSuccessHandler) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.logoutSuccessHandler = logoutSuccessHandler;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Disable cors and csrf.
		http.cors().and().csrf().disable();

		// Set authentication entry point.
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

		// Set logout success handler.
		http.logout().logoutSuccessHandler(logoutSuccessHandler);

		// Disable session management and set to stateless.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Enable the login and registration routes without auth.
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/auth/register").permitAll()
				.antMatchers(HttpMethod.POST, "/auth/login").permitAll().anyRequest().authenticated();

		// Apply Jwt security filter.
		http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return authenticationManager();
	}

}
