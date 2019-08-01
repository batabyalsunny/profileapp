/**
 *
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.models.Authority;
import ml.bootcode.profileapp.repositories.AuthorityRepository;
import ml.bootcode.profileapp.services.AuthorityService;

/**
 * @author sunnybatabyal
 *
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

	private AuthorityRepository authorityRepository;

	/**
	 * @param authorityRepository
	 */
	public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	@Override
	public List<Authority> getAuthorities() {
		return authorityRepository.findAll();
	}

	@Override
	public Authority getAuthorityById(Long id) {
		return validateAuthorityOptional(authorityRepository.findById(id));
	}

	@Override
	public Authority getAuthorityByName(String name) {
		return validateAuthorityOptional(authorityRepository.findByName(name));
	}

	@Override
	public Authority addAuthority(Authority authority) {
		if (authorityRepository.findByName(authority.getName()).isPresent()) {
			throw new RuntimeException("Authority already exists");
		}

		return authorityRepository.save(authority);
	}

	@Override
	public Authority updateAuthority(Long id, Authority authority) {
		Authority existingAuthority = validateAuthorityOptional(authorityRepository.findById(id));

		existingAuthority.setName(authority.getName());
		existingAuthority.setDesignations(authority.getDesignations());

		return authorityRepository.save(existingAuthority);
	}

	@Override
	public void deleteAuthority(Long id) {
		authorityRepository.delete(validateAuthorityOptional(authorityRepository.findById(id)));
	}

	@Override
	public Authority validateAuthorityOptional(Optional<Authority> authorityOptional) {
		if (!authorityOptional.isPresent()) {
			throw new RuntimeException("Authority not found");
		}

		return authorityOptional.get();
	}
}
