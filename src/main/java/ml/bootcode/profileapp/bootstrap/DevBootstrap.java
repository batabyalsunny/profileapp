/**
 * 
 */
package ml.bootcode.profileapp.bootstrap;

import java.util.Set;

import ml.bootcode.profileapp.models.*;
import ml.bootcode.profileapp.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author sunny
 *
 */
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	CountryRepository countryRepository;
	StateRepository stateRepository;
	CityRepository cityRepository;
	AddressRepository addressRepository;
	CompanyRepository companyRepository;
	DesignationRepository designationRepository;
	EmployeeRepository employeeRepository;

	public DevBootstrap(CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository, AddressRepository addressRepository, CompanyRepository companyRepository, DesignationRepository designationRepository, EmployeeRepository employeeRepository) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.addressRepository = addressRepository;
		this.companyRepository = companyRepository;
		this.designationRepository = designationRepository;
		this.employeeRepository = employeeRepository;
	}

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		initData();
	}

	private void initData() {

		// Create country.
		Country ind = new Country("India");
		countryRepository.save(ind);

		// Create States.
		State tel = new State("Telengana", ind);
		State ori = new State("Orissa", ind);
		State wb = new State("West Bengal", ind);

		stateRepository.save(tel);
		stateRepository.save(ori);
		stateRepository.save(wb);

		// Create Cities.
		City hyd = new City("Hydarabad", tel);
		City bbs = new City("Bhubaneswar", ori);
		City hwh = new City("Howrah", wb);
		City drg = new City("Durgapur", wb);

		cityRepository.save(hyd);
		cityRepository.save(bbs);
		cityRepository.save(hwh);
		cityRepository.save(drg);

		// Create Address.
		Address basantiNiwas = new Address("Basanti Niwas", "Sai Vihar");
		basantiNiwas.setCity(bbs);
		Address someHydr = new Address("Some Chowk", "Some Galli");
		someHydr.setCity(hyd);
		Address tagoreComplex = new Address("Tagore Complex", "Some Road");
		tagoreComplex.setCity(drg);

		addressRepository.save(basantiNiwas);
		addressRepository.save(someHydr);
		addressRepository.save(tagoreComplex);

		// Create Companies.
		Company mfs = new Company("Mindfire Solutions");
		Company infi = new Company("Infosys");
		Company att = new Company("AT & T");

		companyRepository.save(mfs);
		companyRepository.save(infi);
		companyRepository.save(att);

		// Create Designations.
		Designation se = new Designation("Software Engineer");
		Designation sa = new Designation("Senior Associate");

		designationRepository.save(se);
		designationRepository.save(sa);

		// Create Employees.
		Employee sunny = new Employee("Sunny", "Batabyal", "sunnyb@mindfiresolutions.com", "123456789");
		sunny.setAddress(basantiNiwas);
		sunny.setCompany(mfs);
		sunny.setDesignation(se);

		Employee ashish = new Employee("Ashish", "Roy", "ashish.roy@mindfiresolutions.com", "123456789");
		ashish.setAddress(someHydr);
		ashish.setCompany(att);
		ashish.setDesignation(sa);

		Employee subhankar = new Employee("Subhankar", "Das", "subhankard@mindfiresolutions.com", "123456789");
		subhankar.setAddress(tagoreComplex);
		subhankar.setCompany(infi);
		subhankar.setDesignation(se);

		employeeRepository.save(sunny);
		employeeRepository.save(ashish);
		employeeRepository.save(subhankar);
	}
}
