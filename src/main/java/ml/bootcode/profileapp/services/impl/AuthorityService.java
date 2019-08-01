/**
 *
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.models.Authority;
import ml.bootcode.profileapp.repositories.AuthorityRepository;

/**
 * @author sunnybatabyal
 *
 */
@Service
public class AuthorityService {

	private AuthorityRepository authorityRepository;

	/**
	 * @param authorityRepository
	 */
	public AuthorityService(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	public List<Authority> getAuthorities() {
		return authorityRepository.findAll();
	}

	public Authority getAuthorityById(Long id) {
		return validateAuthorityOptional(authorityRepository.findById(id));
	}

	public Authority getAuthorityByName(String name) {
		return validateAuthorityOptional(authorityRepository.findByName(name));
	}

	public Authority addAuthority(Authority authority) {
		if (authorityRepository.findByName(authority.getName()).isPresent()) {
			throw new RuntimeException("Authority already exists");
		}

		return authorityRepository.save(authority);
	}

	public Authority updateAuthority(Long id, Authority authority) {
		Authority existingAuthority = validateAuthorityOptional(authorityRepository.findById(id));

		existingAuthority.setName(authority.getName());
		existingAuthority.setRoles(authority.getRoles());

		return authorityRepository.save(existingAuthority);
	}

	public void deleteAuthority(Long id) {
		authorityRepository.delete(validateAuthorityOptional(authorityRepository.findById(id)));
	}

	public Authority validateAuthorityOptional(Optional<Authority> authorityOptional) {
		if (!authorityOptional.isPresent()) {
			throw new RuntimeException("Authority not found");
		}

		return authorityOptional.get();
	}
}
