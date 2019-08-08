package ml.bootcode.profileapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.services.EmployeeService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

	EmployeeService employeeService;

	/**
	 * @param employeeService
	 */
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping
	public List<EmployeeDTO> getEmployees() {
		return employeeService.getEmployees();
	}

	@GetMapping("{id}")
	public EmployeeDTO getEmployee(@PathVariable Long id) {
		return employeeService.getEmployee(id);
	}

	@PostMapping
	public EmployeeDTO addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		return employeeService.addEmployee(employeeDTO);
	}

	@PutMapping("{id}")
	public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
		return employeeService.updateEmployee(id, employeeDTO);
	}

	@DeleteMapping("{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
	}
}
