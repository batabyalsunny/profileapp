/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.EmployeeDTO;

/**
 * @author sunnyb
 *
 */
public interface EmployeeService {

	List<EmployeeDTO> getEmployees();

	EmployeeDTO getEmployee(Long id);

	EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

	EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

	void deleteEmployee(Long id);
}
