package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.repositories.CountryRepository;
import ml.bootcode.profileapp.services.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	CountryRepository countryRepository;

	public CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public List<CountryDTO> getCountries() {

		// Get all country objects from repository.
		List<Country> countries = countryRepository.findAll();

		// Convert to dto and return.
		return countries.stream().map(country -> {
			return mapObjectToDto(country);
		}).collect(Collectors.toList());
	}

	@Override
	public CountryDTO getCountry(Long id) {

		// Get the validated country from DB.
		Country country = validateCountry(id);

		return mapObjectToDto(country);
	}

	@Override
	public CountryDTO addCountry(CountryDTO countryDTO) {

		// Create new country object.
		Country country = new Country();

		// Map DTO to object.
		mapDtoToObject(countryDTO, country);

		return mapObjectToDto(countryRepository.save(country));
	}

	@Override
	public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {

		// Get the validated country from DB.
		Country country = validateCountry(id);

		// Map DTO to object.
		mapDtoToObject(countryDTO, country);

		return mapObjectToDto(countryRepository.save(country));
	}

	@Override
	public void deleteCountry(Long id) {

		// Validate country.
		Country country = validateCountry(id);

		countryRepository.delete(country);
	}

	@Override
	public CountryDTO mapObjectToDto(Country country) {

		// Create new DTO object.
		CountryDTO countryDTO = new CountryDTO();

		// Set all the properties from the country object.
		countryDTO.setId(country.getId());
		countryDTO.setName(country.getName());
		countryDTO.setStates(country.getStates());

		return countryDTO;
	}

	@Override
	public void mapDtoToObject(CountryDTO countryDTO, Country country) {

		// Set all the properties from countryDTO.
		country.setName(countryDTO.getName());
	}

	@Override
	public Country validateCountry(Long id) {

		// Get country optional.
		Optional<Country> countryOptional = countryRepository.findById(id);

		// Check if country is present.
		if (!countryOptional.isPresent()) {
			throw new RuntimeException("Requested resource not found");
		}

		return countryOptional.get();
	}
}
