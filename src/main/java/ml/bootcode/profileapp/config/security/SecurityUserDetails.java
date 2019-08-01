/**
 * 
 */
package ml.bootcode.profileapp.config.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ml.bootcode.profileapp.models.Employee;

/**
 * @author sunnyb
 *
 */
public class SecurityUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Employee user;

	/**
	 * @param user
	 */
	public SecurityUserDetails(Employee user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public Employee getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Employee user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getDesignation().getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isActive();
	}

}
