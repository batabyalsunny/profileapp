/**
 *
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.models.Role;
import ml.bootcode.profileapp.repositories.RoleRepository;

/**
 * @author sunnybatabyal
 *
 */
@Service
public class RoleService {

	private RoleRepository roleRepository;

	/**
	 * @param roleRepository
	 */
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	public Role getRoleById(Long id) {
		return validateRoleOptional(roleRepository.findById(id));
	}

	public Role getRoleByName(String name) {
		return validateRoleOptional(roleRepository.findByName(name));
	}

	public Role addRole(Role role) {
		if (roleRepository.findByName(role.getName()).isPresent()) {
			throw new RuntimeException("Role already exists");
		}

		return roleRepository.save(role);
	}

	public Role updateRole(Long id, Role role) {
		Role existingRole = validateRoleOptional(roleRepository.findById(id));

		existingRole.setName(role.getName());
		existingRole.setAuthorities(role.getAuthorities());

		return roleRepository.save(existingRole);
	}

	public void deleteRole(Long id) {
		roleRepository.delete(validateRoleOptional(roleRepository.findById(id)));
	}

	public Role validateRoleOptional(Optional<Role> roleOptional) {
		if (!roleOptional.isPresent()) {
			throw new RuntimeException("Role not found");
		}

		return roleOptional.get();
	}
}
