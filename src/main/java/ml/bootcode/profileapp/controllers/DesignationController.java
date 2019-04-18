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

import ml.bootcode.profileapp.dto.DesignationDTO;
import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.services.DesignationService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/designations")
public class DesignationController {

	DesignationService designationService;

	/**
	 * @param designationService
	 */
	public DesignationController(DesignationService designationService) {
		this.designationService = designationService;
	}

	@GetMapping
	public List<DesignationDTO> getDesignations() {
		return designationService.getDesignations();
	}

	@GetMapping("{id}")
	public DesignationDTO getDesignation(@PathVariable Long id) {
		return designationService.getDesignation(id);
	}

	@PostMapping
	public DesignationDTO addDesignation(@RequestBody DesignationDTO designationDTO) {
		return designationService.addDesignation(designationDTO);
	}

	@PutMapping("{id}")
	public DesignationDTO updateDesignation(@PathVariable Long id, @RequestBody DesignationDTO designationDTO) {
		return designationService.updateDesignation(id, designationDTO);
	}

	@DeleteMapping("{id}")
	public void deleteDesignation(@PathVariable Long id) {
		designationService.deleteDesignation(id);
	}

	@GetMapping("{id}/employees")
	public List<EmployeeDTO> getEmployeesByDesignationId(@PathVariable Long id) {
		return designationService.getEmployeesByDesignationId(id);
	}
}
