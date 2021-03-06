package ml.bootcode.profileapp.util;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import ml.bootcode.profileapp.exceptions.ApiException;
import ml.bootcode.profileapp.models.Address;
import ml.bootcode.profileapp.models.Asset;
import ml.bootcode.profileapp.models.AssetType;
import ml.bootcode.profileapp.models.City;
import ml.bootcode.profileapp.models.Company;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.models.Designation;
import ml.bootcode.profileapp.models.Employee;
import ml.bootcode.profileapp.models.State;
import ml.bootcode.profileapp.repositories.AddressRepository;
import ml.bootcode.profileapp.repositories.AssetRepository;
import ml.bootcode.profileapp.repositories.AssetTypeRepository;
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
	AssetRepository assetRepository;
	AssetTypeRepository assetTypeRepository;

	/**
	 * @param countryRepository
	 * @param stateRepository
	 * @param cityRepository
	 * @param addressRepository
	 * @param companyRepository
	 * @param designationRepository
	 * @param employeeRepository
	 * @param assetTypeRepository
	 */
	public EntityValidator(CountryRepository countryRepository, StateRepository stateRepository,
			CityRepository cityRepository, AddressRepository addressRepository, CompanyRepository companyRepository,
			DesignationRepository designationRepository, EmployeeRepository employeeRepository,
			AssetRepository assetRepository, AssetTypeRepository assetTypeRepository) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.addressRepository = addressRepository;
		this.companyRepository = companyRepository;
		this.designationRepository = designationRepository;
		this.employeeRepository = employeeRepository;
		this.assetRepository = assetRepository;
		this.assetTypeRepository = assetTypeRepository;
	}

	public Country validateCountry(Long id) {

		// Get country optional.
		Optional<Country> countryOptional = countryRepository.findById(id);

		// Check if country is present.
		if (!countryOptional.isPresent())
			throw new ApiException("Requested country not found", HttpStatus.NOT_FOUND);

		return countryOptional.get();
	}

	public State validateState(Long id) {

		Optional<State> stateOptional = stateRepository.findById(id);

		if (!stateOptional.isPresent())
			throw new ApiException("Requested state not found", HttpStatus.NOT_FOUND);

		return stateOptional.get();
	}

	public City validateCity(Long id) {

		Optional<City> cityOptional = cityRepository.findById(id);

		if (!cityOptional.isPresent())
			throw new ApiException("Requested city not found", HttpStatus.NOT_FOUND);

		return cityOptional.get();
	}

	public Address validateAddress(Long id) {

		Optional<Address> addressOptional = addressRepository.findById(id);

		if (!addressOptional.isPresent())
			throw new ApiException("Requested address not found", HttpStatus.NOT_FOUND);

		return addressOptional.get();
	}

	public Company validateCompany(Long id) {

		Optional<Company> companyOptional = companyRepository.findById(id);

		if (!companyOptional.isPresent()) {
			throw new ApiException("Requested company not found", HttpStatus.NOT_FOUND);
		}

		return companyOptional.get();
	}

	public Designation validateDesignation(Long id) {

		Optional<Designation> designationOptional = designationRepository.findById(id);

		if (!designationOptional.isPresent()) {
			throw new ApiException("Requested designation not found.", HttpStatus.NOT_FOUND);
		}

		return designationOptional.get();
	}

	public Employee validateEmployee(Long id) {

		Optional<Employee> employeeOptional = employeeRepository.findById(id);

		if (!employeeOptional.isPresent()) {
			throw new ApiException("Requested employee not found.", HttpStatus.NOT_FOUND);
		}

		return employeeOptional.get();
	}

	public Asset validateAsset(Long id) {

		Optional<Asset> assetOptional = assetRepository.findById(id);

		if (!assetOptional.isPresent()) {
			throw new ApiException("Requested asset not found.", HttpStatus.NOT_FOUND);
		}

		return assetOptional.get();
	}

	public AssetType validateAssetType(Long id) {

		Optional<AssetType> assetTypeOptional = assetTypeRepository.findById(id);

		if (!assetTypeOptional.isPresent()) {
			throw new ApiException("Requested asset type not found.", HttpStatus.NOT_FOUND);
		}

		return assetTypeOptional.get();
	}

}
