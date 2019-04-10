package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.dto.StateDTO;

public interface CountryService {

	List<CountryDTO> getCountries();

	CountryDTO getCountry(Long id);

	CountryDTO addCountry(CountryDTO countryDTO);

	CountryDTO updateCountry(Long id, CountryDTO countryDTO);

	void deleteCountry(Long id);

	List<StateDTO> getStatesByCountryId(Long id);
}
