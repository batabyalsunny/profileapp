package ml.bootcode.profileapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.services.CountryService;

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
