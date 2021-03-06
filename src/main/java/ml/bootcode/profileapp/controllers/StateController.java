/**
 *
 */
package ml.bootcode.profileapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.dto.CityDTO;
import ml.bootcode.profileapp.dto.StateDTO;
import ml.bootcode.profileapp.services.StateService;

/**
 * @author sunnybatabyal
 *
 */
@RestController
@RequestMapping("api/v1/states")
public class StateController {

	private StateService stateService;

	/**
	 * @param stateService
	 */
	public StateController(StateService stateService) {
		this.stateService = stateService;
	}

	@GetMapping
	public List<StateDTO> getStates() {
		return stateService.getStates();
	}

	@GetMapping("{id}")
	public StateDTO getState(@PathVariable Long id) {
		return stateService.getState(id);
	}

	@PostMapping
	public StateDTO createState(@RequestBody StateDTO stateDTO) {
		return stateService.addState(stateDTO);
	}

	@PutMapping("{id}")
	public StateDTO updateCountry(@PathVariable Long id, @RequestBody StateDTO stateDTO) {
		return stateService.updateState(id, stateDTO);
	}

	@DeleteMapping("{id}")
	public void deleteCountry(@PathVariable Long id) {
		stateService.deleteState(id);
	}

	@GetMapping("{id}/cities")
	public List<CityDTO> getCities(@PathVariable Long id) {
		return stateService.getCitiesByStateId(id);
	}
}
