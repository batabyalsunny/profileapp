package ml.bootcode.profileapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.components.EmployeeComponent;
import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.dto.SuccessResponseDTO;
import ml.bootcode.profileapp.services.EmployeeService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

	EmployeeService employeeService;
	EmployeeComponent employeeComponent;

	/**
	 * @param employeeService
	 * @param employeeComponent
	 */
	public EmployeeController(EmployeeService employeeService, EmployeeComponent employeeComponent) {
		this.employeeService = employeeService;
		this.employeeComponent = employeeComponent;
	}

	@GetMapping
	public List<EmployeeDTO> getEmployees() {
		return employeeService.getEmployees();
	}

	@GetMapping(params = { "page", "size" })
	public List<EmployeeDTO> getEmployees(@RequestParam("page") int page, @RequestParam("size") int size) {
		return employeeService.getEmployees(page, size);
	}

	@GetMapping(params = { "page", "size", "orderBy", "order" })
	public List<EmployeeDTO> getEmployees(@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("orderBy") String orderBy, @RequestParam("order") String order) {
		return employeeService.getEmployees(page, size, orderBy, order);
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

	@GetMapping("companies/{companyId}")
	public ResponseEntity<SuccessResponseDTO<List<EmployeeDTO>>> getByCompanyId(@PathVariable Long companyId) {
		return employeeComponent.getEmployeesOfCompany(companyId);
	}
}
