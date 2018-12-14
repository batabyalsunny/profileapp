package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.models.Country;

public interface CountryService {

	List<CountryDTO> getCountries();

	CountryDTO getCountry(Long id);

	CountryDTO addOrUpdateCountry(CountryDTO countryDTO);

	void deleteCountry(Long id);

	CountryDTO mapObjectToDto(Country country);

	void mapDtoToObject(CountryDTO countryDTO, Country country);
}
