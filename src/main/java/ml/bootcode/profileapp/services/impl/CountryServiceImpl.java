package ml.bootcode.profileapp.services.impl;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.repositories.CountryRepository;
import ml.bootcode.profileapp.services.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return countries.stream()
                .map(country -> {
                    return mapObjectToDto(country);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CountryDTO getCountry(Long id) {
        return null;
    }

    @Override
    public CountryDTO addCountry() {
        return null;
    }

    @Override
    public CountryDTO updateCountry(Long id) {
        return null;
    }

    @Override
    public void deleteCountry(Long id) {

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
}
