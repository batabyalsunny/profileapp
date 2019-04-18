/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.models.Employee;
import ml.bootcode.profileapp.repositories.EmployeeRepository;
import ml.bootcode.profileapp.services.EmployeeService;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnyb
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	EmployeeRepository employeeRepository;
	ModelMapper mapper;
	EntityValidator entityValidator;

	/**
	 * @param employeeRepository
	 * @param mapper
	 * @param entityValidator
	 */
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper,
			EntityValidator entityValidator) {
		this.employeeRepository = employeeRepository;
		this.mapper = mapper;
		this.entityValidator = entityValidator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.EmployeeService#getEmployees()
	 */
	@Override
	public List<EmployeeDTO> getEmployees() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return employeeRepository.findAll().stream().map(employee -> {
			return mapper.map(employee, EmployeeDTO.class);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.EmployeeService#getEmployee(java.lang.Long)
	 */
	@Override
	public EmployeeDTO getEmployee(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(entityValidator.validateEmployee(id), EmployeeDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.EmployeeService#addEmployee(ml.bootcode.
	 * profileapp.dto.EmployeeDTO)
	 */
	@Override
	public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(employeeRepository.save(mapper.map(employeeDTO, Employee.class)), EmployeeDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.EmployeeService#updateEmployee(java.lang.
	 * Long, ml.bootcode.profileapp.dto.EmployeeDTO)
	 */
	@Override
	public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		Employee employee = entityValidator.validateEmployee(id);

		// Set ID to DTO.
		employeeDTO.setId(id);

		return mapper.map(employeeRepository.save(mapper.map(employeeDTO, Employee.class)), EmployeeDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.EmployeeService#deleteEmployee(java.lang.
	 * Long)
	 */
	@Override
	public void deleteEmployee(Long id) {
		employeeRepository.delete(entityValidator.validateEmployee(id));
	}

}
