/**
 * 
 */
package ml.bootcode.profileapp.components;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.dto.SuccessResponseDTO;
import ml.bootcode.profileapp.services.EmployeeService;

/**
 * @author sunnyb
 *
 */
@Component
public class EmployeeComponent {

	private EmployeeService employeeService;
	private ModelMapper mapper;

	/**
	 * @param employeeService
	 * @param mapper
	 */
	public EmployeeComponent(EmployeeService employeeService, ModelMapper mapper) {
		this.employeeService = employeeService;
		this.mapper = mapper;
	}

	public ResponseEntity<SuccessResponseDTO<List<EmployeeDTO>>> getEmployeesOfCompany(Long id) {

		return new ResponseEntity<>(
				new SuccessResponseDTO<>(employeeService.getEmployeesOfCompany(id).stream().map(employee -> {
					return mapper.map(employee, EmployeeDTO.class);
				}).collect(Collectors.toList())), HttpStatus.OK);
	}
}
