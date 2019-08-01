/**
 *
 */
package ml.bootcode.profileapp.bootstrap;

import java.util.Arrays;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ml.bootcode.profileapp.models.Address;
import ml.bootcode.profileapp.models.AssetType;
import ml.bootcode.profileapp.models.Authority;
import ml.bootcode.profileapp.models.City;
import ml.bootcode.profileapp.models.Company;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.models.Designation;
import ml.bootcode.profileapp.models.Employee;
import ml.bootcode.profileapp.models.State;
import ml.bootcode.profileapp.repositories.AddressRepository;
import ml.bootcode.profileapp.repositories.AssetRepository;
import ml.bootcode.profileapp.repositories.AssetTypeRepository;
import ml.bootcode.profileapp.repositories.AuthorityRepository;
import ml.bootcode.profileapp.repositories.CityRepository;
import ml.bootcode.profileapp.repositories.CompanyRepository;
import ml.bootcode.profileapp.repositories.CountryRepository;
import ml.bootcode.profileapp.repositories.DesignationRepository;
import ml.bootcode.profileapp.repositories.EmployeeRepository;
import ml.bootcode.profileapp.repositories.StateRepository;

/**
 * @author sunny
 *
 */
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private CountryRepository countryRepository;
	private StateRepository stateRepository;
	private CityRepository cityRepository;
	private AddressRepository addressRepository;
	private CompanyRepository companyRepository;
	private DesignationRepository designationRepository;
	private EmployeeRepository employeeRepository;
	private AssetTypeRepository assetTypeRepository;
	private AssetRepository assetRepository;
	private AuthorityRepository authorityRepository;
	private PasswordEncoder passwordEncoder;

	/**
	 * @param countryRepository
	 * @param stateRepository
	 * @param cityRepository
	 * @param addressRepository
	 * @param companyRepository
	 * @param designationRepository
	 * @param employeeRepository
	 * @param assetTypeRepository
	 * @param assetRepository
	 * @param authorityRepository
	 * @param passwordEncoder
	 */
	public DevBootstrap(CountryRepository countryRepository, StateRepository stateRepository,
			CityRepository cityRepository, AddressRepository addressRepository, CompanyRepository companyRepository,
			DesignationRepository designationRepository, EmployeeRepository employeeRepository,
			AssetTypeRepository assetTypeRepository, AssetRepository assetRepository,
			AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.addressRepository = addressRepository;
		this.companyRepository = companyRepository;
		this.designationRepository = designationRepository;
		this.employeeRepository = employeeRepository;
		this.assetTypeRepository = assetTypeRepository;
		this.assetRepository = assetRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.
	 * springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initData();
	}

	private void initData() {

		// Create authorities.
		Authority viewAuthority = new Authority();
		viewAuthority.setName("VIEW");
		Authority downloadAuthority = new Authority();
		downloadAuthority.setName("DOWNLOAD");
		Authority editAuthority = new Authority();
		editAuthority.setName("EDIT");
		Authority uploadAuthority = new Authority();
		uploadAuthority.setName("UPLOAD");

		authorityRepository.save(viewAuthority);
		authorityRepository.save(downloadAuthority);
		authorityRepository.save(editAuthority);
		authorityRepository.save(uploadAuthority);

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
		se.setAuthorities(Arrays.asList(viewAuthority, uploadAuthority));

		Designation sa = new Designation("Senior Associate");
		sa.setAuthorities(Arrays.asList(viewAuthority, uploadAuthority, editAuthority, downloadAuthority));

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

		// Create asset types.
		AssetType profilePic = new AssetType();
		profilePic.setType("Profile Picture");

		AssetType document = new AssetType();
		document.setType("Document");

		assetTypeRepository.save(profilePic);
		assetTypeRepository.save(document);
	}
}
