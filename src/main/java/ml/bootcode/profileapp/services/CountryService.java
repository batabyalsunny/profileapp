package ml.bootcode.profileapp.services;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.models.Country;

import java.util.List;

public interface CountryService {

    List<CountryDTO> getCountries();
    CountryDTO getCountry(Long id);
    CountryDTO addCountry();
    CountryDTO updateCountry(Long id);
    void deleteCountry(Long id);

    CountryDTO mapObjectToDto(Country country);
    void mapDtoToObject(CountryDTO countryDTO, Country country);
}
