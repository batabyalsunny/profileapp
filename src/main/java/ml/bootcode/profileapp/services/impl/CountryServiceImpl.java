package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.dto.StateDTO;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.repositories.CountryRepository;
import ml.bootcode.profileapp.services.CountryService;
import ml.bootcode.profileapp.util.EntityValidator;

@Service
public class CountryServiceImpl implements CountryService {

	private CountryRepository countryRepository;
	private EntityValidator entityValidator;

	@Autowired
	ModelMapper mapper;

	public CountryServiceImpl(CountryRepository countryRepository, EntityValidator entityValidator) {
		this.countryRepository = countryRepository;
		this.entityValidator = entityValidator;
	}

	@Override
	public List<CountryDTO> getCountries() {

		// Get all country objects from repository.
		List<Country> countries = countryRepository.findAll();

		// Convert to dto and return.
		return countries.stream().map(country -> {
			return mapper.map(country, CountryDTO.class);
		}).collect(Collectors.toList());
	}

	@Override
	public CountryDTO getCountry(Long id) {

		// Get the validated country from DB.
		Country country = entityValidator.validateCountry(id);

		return mapper.map(country, CountryDTO.class);
	}

	@Override
	public CountryDTO addCountry(CountryDTO countryDTO) {

		// Map DTO to object.
		Country country = mapper.map(countryDTO, Country.class);

		return mapper.map(countryRepository.save(country), CountryDTO.class);
	}

	@Override
	public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {

		// Get the validated country from DB.
		Country country = entityValidator.validateCountry(id);

		// Set the validated country ID to DTO.
		countryDTO.setId(country.getId());

		return mapper.map(countryRepository.save(mapper.map(countryDTO, Country.class)), CountryDTO.class);
	}

	@Override
	public void deleteCountry(Long id) {

		// Validate country.
		Country country = entityValidator.validateCountry(id);

		countryRepository.delete(country);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ml.bootcode.profileapp.services.CountryService#getStatesByCountryId(java.lang
	 * .Long)
	 */
	@Override
	public List<StateDTO> getStatesByCountryId(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		// Validate the country.
		Country country = entityValidator.validateCountry(id);

		// Get states and map to state dto.
		return country.getStates().stream().map(state -> {
			return mapper.map(state, StateDTO.class);
		}).collect(Collectors.toList());
	}
}
