package ml.bootcode.profileapp.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ml.bootcode.profileapp.models.Address;
import ml.bootcode.profileapp.models.City;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.models.State;
import ml.bootcode.profileapp.repositories.AddressRepository;
import ml.bootcode.profileapp.repositories.CityRepository;
import ml.bootcode.profileapp.repositories.CountryRepository;
import ml.bootcode.profileapp.repositories.StateRepository;

@Component
public class EntityValidator {

	CountryRepository countryRepository;
	StateRepository stateRepository;
	CityRepository cityRepository;
	AddressRepository addressRepository;

	/**
	 * @param countryRepository
	 * @param stateRepository
	 * @param cityRepository
	 * @param addressRepository
	 */
	public EntityValidator(CountryRepository countryRepository, StateRepository stateRepository,
			CityRepository cityRepository, AddressRepository addressRepository) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.addressRepository = addressRepository;
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

}
