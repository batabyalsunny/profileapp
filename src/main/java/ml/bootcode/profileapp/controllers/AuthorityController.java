/**
 * 
 */
package ml.bootcode.profileapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.models.Authority;
import ml.bootcode.profileapp.services.AuthorityService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/authorities")
public class AuthorityController {

	private AuthorityService authorityService;

	/**
	 * @param authorityService
	 */
	public AuthorityController(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	@GetMapping
	public List<Authority> getAuthorities() {
		return authorityService.getAuthorities();
	}

	@GetMapping("{id}")
	public Authority getAuthority(@PathVariable Long id) {
		return authorityService.getAuthorityById(id);
	}

	@PostMapping
	public Authority addAuthority(@RequestBody Authority authority) {
		return authorityService.addAuthority(authority);
	}

	@PutMapping("{id}")
	public Authority updateAuthority(@PathVariable Long id, @RequestBody Authority authority) {
		return authorityService.updateAuthority(id, authority);
	}

	@DeleteMapping("{id}")
	public void deleteAuthority(@PathVariable Long id) {
		authorityService.deleteAuthority(id);
	}
}
