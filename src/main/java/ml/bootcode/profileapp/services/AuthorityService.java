/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.util.List;
import java.util.Optional;

import ml.bootcode.profileapp.models.Authority;

/**
 * @author sunnyb
 *
 */
public interface AuthorityService {

	public List<Authority> getAuthorities();

	public Authority getAuthorityById(Long id);

	public Authority getAuthorityByName(String name);

	public Authority addAuthority(Authority authority);

	public Authority updateAuthority(Long id, Authority authority);

	public void deleteAuthority(Long id);

	public Authority validateAuthorityOptional(Optional<Authority> authorityOptional);
}
