package ml.bootcode.profileapp.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.repositories.CountryRepository;
import ml.bootcode.profileapp.repositories.StateRepository;

@Component
public class EntityValidator {

	CountryRepository countryRepository;
	StateRepository stateRepository;

	/**
	 * @param countryRepository
	 * @param stateRepository
	 */
	public EntityValidator(CountryRepository countryRepository, StateRepository stateRepository) {
		this.countryRepository = countryRepository;
		this.stateRepository = stateRepository;
	}

	public Country validateCountry(Long id) {

		// Get country optional.
		Optional<Country> countryOptional = countryRepository.findById(id);

		// Check if country is present.
		if (!countryOptional.isPresent())
			throw new RuntimeException("Requested country not found");

		return countryOptional.get();
	}

}
