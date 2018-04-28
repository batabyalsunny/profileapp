/**
 * 
 */
package ml.bootcode.profileapp.bootstrap;

import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ml.bootcode.profileapp.models.Company;
import ml.bootcode.profileapp.models.Employee;
import ml.bootcode.profileapp.repositories.CompanyRepository;
import ml.bootcode.profileapp.repositories.EmployeeRepository;

/**
 * @author sunny
 *
 */
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
	
	private CompanyRepository companyRepository;
	private EmployeeRepository employeeRepository;
	
	/**
	 * @param companyRepository
	 * @param employeeRepository
	 */
	public DevBootstrap(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
		this.companyRepository = companyRepository;
		this.employeeRepository = employeeRepository;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initData();
	}

	private void initData() {
		
		Company mfs = new Company("Mindfire Solutions");
		Employee sunny = new Employee("Sunny", "Batabyal", "sunnyb@mindfiresolutions.com", "123456789" );
		mfs.getEmployees().add(sunny);
		sunny.getCompany().add(mfs);
		
		
		companyRepository.save(mfs);
		employeeRepository.save(sunny);
	}
}
