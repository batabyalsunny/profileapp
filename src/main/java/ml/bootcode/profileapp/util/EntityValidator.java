package ml.bootcode.profileapp.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ml.bootcode.profileapp.models.Address;
import ml.bootcode.profileapp.models.City;
import ml.bootcode.profileapp.models.Company;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.models.Designation;
import ml.bootcode.profileapp.models.Employee;
import ml.bootcode.profileapp.models.State;
import ml.bootcode.profileapp.repositories.AddressRepository;
import ml.bootcode.profileapp.repositories.CityRepository;
import ml.bootcode.profileapp.repositories.CompanyRepository;
import ml.bootcode.profileapp.repositories.CountryRepository;
import ml.bootcode.profileapp.repositories.DesignationRepository;
import ml.bootcode.profileapp.repositories.EmployeeRepository;
import ml.bootcode.profileapp.repositories.StateRepository;

@Component
public class EntityValidator {

	CountryRepository countryRepository;
	StateRepository stateRepository;
	CityRepository cityRepository;
	AddressRepository addressRepository;
	CompanyRepository companyRepository;
	DesignationRepository designationRepository;
	EmployeeRepository employeeRepository;

	/**
	 * @param countryRepository
	 * @param stateRepository
	 * @param cityRepository
	 * @param addressRepository
	 * @param companyRepository
	 * @param designationRepository
	 * @param employeeRepository
	 */
	public EntityValidator(CountryRepository countryRepository, StateRepository stateRepository,
			CityRepository cityRepository, AddressRepository addressRepository, CompanyRepository companyRepository,
			DesignationRepository designationRepository, EmployeeRepository employeeRepository) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.addressRepository = addressRepository;
		this.companyRepository = companyRepository;
		this.designationRepository = designationRepository;
		this.employeeRepository = employeeRepository;
	}

	public Country validateCountry(Long id) {

		// Get country optional.
		Optional<Country> countryOptional = countryRepository.findById(id);

		// Check if country is present.
		if (!countryOptional.isPresent())
			throw new RuntimeException("Requested country not found");

		return countryOptional.get();
	}

	public State validateState(Long id) {

		Optional<State> stateOptional = stateRepository.findById(id);

		if (!stateOptional.isPresent())
			throw new RuntimeException("Requested state not found");

		return stateOptional.get();
	}

	public City validateCity(Long id) {

		Optional<City> cityOptional = cityRepository.findById(id);

		if (!cityOptional.isPresent())
			throw new RuntimeException("Requested city not found");

		return cityOptional.get();
	}

	public Address validateAddress(Long id) {

		Optional<Address> addressOptional = addressRepository.findById(id);

		if (!addressOptional.isPresent())
			throw new RuntimeException("Requested address not found");

		return addressOptional.get();
	}

	public Company validateCompany(Long id) {

		Optional<Company> companyOptional = companyRepository.findById(id);

		if (!companyOptional.isPresent()) {
			throw new RuntimeException("Requested company not found");
		}

		return companyOptional.get();
	}

	public Designation validateDesignation(Long id) {

		Optional<Designation> designationOptional = designationRepository.findById(id);

		if (!designationOptional.isPresent()) {
			throw new RuntimeException("Requested designation not found.");
		}

		return designationOptional.get();
	}

	public Employee validateEmployee(Long id) {

		Optional<Employee> employeeOptional = employeeRepository.findById(id);

		if (!employeeOptional.isPresent()) {
			throw new RuntimeException("Requested employee not found.");
		}

		return employeeOptional.get();
	}

}
