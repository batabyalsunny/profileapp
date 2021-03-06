/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.models.Employee;

/**
 * @author sunnyb
 *
 */
public interface EmployeeService {

	List<EmployeeDTO> getEmployees();

	List<EmployeeDTO> getEmployees(int page, int size);

	EmployeeDTO getEmployee(Long id);

	EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

	EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

	void deleteEmployee(Long id);

	List<Employee> getEmployeesOfCompany(Long id);

	List<EmployeeDTO> getEmployees(int page, int size, String orderBy, String order);
}
