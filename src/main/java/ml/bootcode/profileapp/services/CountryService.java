package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.dto.StateDTO;
import ml.bootcode.profileapp.models.Country;

public interface CountryService {

	List<CountryDTO> getCountries();

	CountryDTO getCountry(Long id);

	CountryDTO addCountry(CountryDTO countryDTO);

	CountryDTO updateCountry(Long id, CountryDTO countryDTO);

	void deleteCountry(Long id);

	List<StateDTO> getStatesByCountryId(Long id);

	CountryDTO mapObjectToDto(Country country);

	void mapDtoToObject(CountryDTO countryDTO, Country country);

	Country validateCountry(Long id);
}
