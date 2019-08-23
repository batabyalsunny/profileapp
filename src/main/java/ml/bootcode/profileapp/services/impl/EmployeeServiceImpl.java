/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.models.Employee;
import ml.bootcode.profileapp.repositories.EmployeeRepository;
import ml.bootcode.profileapp.services.EmployeeService;
import ml.bootcode.profileapp.util.EmailSender;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnyb
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeRepository employeeRepository;
	private ModelMapper mapper;
	private EntityValidator entityValidator;
	private PasswordEncoder passwordEncoder;
	private EmailSender emailSender;

	/**
	 * @param employeeRepository
	 * @param mapper
	 * @param entityValidator
	 * @param passwordEncoder
	 * @param mailSender
	 */
	public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper,
			EntityValidator entityValidator, PasswordEncoder passwordEncoder, EmailSender emailSender) {
		this.employeeRepository = employeeRepository;
		this.mapper = mapper;
		this.entityValidator = entityValidator;
		this.passwordEncoder = passwordEncoder;
		this.emailSender = emailSender;
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

	@Override
	public List<EmployeeDTO> getEmployees(int page, int size) {

		Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, size));

		return employees.getContent().stream().map(employee -> {
			return mapper.map(employee, EmployeeDTO.class);
		}).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeDTO> getEmployees(int page, int size, String orderBy, String order) {

		Sort sort = order.equals(Direction.DESC.toString()) ? Sort.by(Order.desc(orderBy))
				: Sort.by(Order.asc(orderBy));

		Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, size, sort));

		return employees.getContent().stream().map(employee -> {
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

		Employee employee = mapper.map(employeeDTO, Employee.class);
		employee.setPassword(passwordEncoder.encode(employeeDTO.getPassword()));

		EmployeeDTO dto = mapper.map(employeeRepository.save(employee), EmployeeDTO.class);

		// Send mail.
		String message = "Dear " + employee.getFirstName() + ", \n\n"
				+ "Thanks for joining. We Are glad to see you on board!\n\n" + "Regards\nSunny";

		emailSender.send(employee.getEmail(), message);

		return dto;
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

	@Override
	public List<Employee> getEmployeesOfCompany(Long id) {
		return employeeRepository.findByCompany(entityValidator.validateCompany(id));
	}
}
