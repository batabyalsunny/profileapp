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

import ml.bootcode.profileapp.models.Role;
import ml.bootcode.profileapp.services.impl.RoleService;

/**
 * @author sunnyb
 *
 */
//@RestController
//@RequestMapping("roles")
public class RoleController {

	private RoleService roleService;

	/**
	 * @param roleService
	 */
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping
	public List<Role> getRoles() {
		return roleService.getRoles();
	}

	@GetMapping("{id}")
	public Role getRole(@PathVariable Long id) {
		return roleService.getRoleById(id);
	}

	@PostMapping
	public Role addRole(@RequestBody Role role) {
		return roleService.addRole(role);
	}

	@PutMapping("{id}")
	public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
		return roleService.updateRole(id, role);
	}

	@DeleteMapping("{id}")
	public void deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
	}
}
