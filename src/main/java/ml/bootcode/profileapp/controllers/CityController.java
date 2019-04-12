/**
 * 
 */
package ml.bootcode.profileapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.dto.CityDTO;
import ml.bootcode.profileapp.services.CityService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/cities")
public class CityController {

	CityService cityService;

	/**
	 * @param cityService
	 */
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}

	@GetMapping
	public List<CityDTO> getCities() {
		return cityService.getCities();
	}

	@GetMapping("{id}")
	public CityDTO getCity(@PathVariable Long id) {
		return cityService.getCity(id);
	}
}
