package ml.bootcode.profileapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import ml.bootcode.profileapp.dto.CountryDTO;
import ml.bootcode.profileapp.services.CountryService;

@RestController
@RequestMapping("api/v1/countries")
public class CountryController {

	CountryService countryService;

	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping("")
	public List<CountryDTO> getCountries() {
		return countryService.getCountries();
	}

	@GetMapping("{id}")
	public CountryDTO getCountry(@PathVariable Long id) {
		return countryService.getCountry(id);
	}

	@PostMapping("")
	public CountryDTO createCountry(@RequestBody CountryDTO countryDTO) {
		return countryService.addCountry(countryDTO);
	}

	@PutMapping("{id}")
	public CountryDTO updateCountry(@PathVariable Long id, @RequestBody CountryDTO countryDTO) {
		return countryService.updateCountry(id, countryDTO);
	}

	@DeleteMapping("{id}")
	public void deleteCountry(@PathVariable Long id) {
		countryService.deleteCountry(id);
	}
}
