package ml.bootcode.profileapp.controllers;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.services.CountryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CountryController {

    CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("countries")
    public List<CountryDTO> getCountries() {
        return countryService.getCountries();
    }
}
