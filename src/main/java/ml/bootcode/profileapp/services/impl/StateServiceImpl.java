/**
 *
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.StateDTO;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.models.State;
import ml.bootcode.profileapp.repositories.StateRepository;
import ml.bootcode.profileapp.services.CountryService;
import ml.bootcode.profileapp.services.StateService;

/**
 * @author sunnybatabyal
 *
 */
@Service
public class StateServiceImpl implements StateService {

	private StateRepository stateRepository;
	private CountryService countryService;

	/**
	 * @param stateRepository
	 * @param countryService
	 */
	public StateServiceImpl(StateRepository stateRepository, CountryService countryService) {
		this.stateRepository = stateRepository;
		this.countryService = countryService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#getStates()
	 */
	@Override
	public List<StateDTO> getStates() {

		List<State> states = stateRepository.findAll();
		return states.stream().map(this::mapObjectToDto).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#getState()
	 */
	@Override
	public StateDTO getState(Long id) {

		State state = validateState(id);
		return mapObjectToDto(state);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * ml.bootcode.profileapp.services.StateService#addState(ml.bootcode.profileapp.
	 * dto.StateDTO)
	 */
	@Override
	public StateDTO addState(StateDTO stateDTO) {

		State state = new State();
		mapDtoToObject(stateDTO, state);

		return mapObjectToDto(stateRepository.save(state));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#updateState(java.lang.Long,
	 * ml.bootcode.profileapp.dto.StateDTO)
	 */
	@Override
	public StateDTO updateState(Long id, StateDTO stateDTO) {

		// Validate state.
		State state = validateState(id);
		mapDtoToObject(stateDTO, state);

		// Update state.
		return mapObjectToDto(stateRepository.save(state));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#deleteState(java.lang.Long)
	 */
	@Override
	public void deleteState(Long id) {

		// Validate and delete state.
		stateRepository.delete(validateState(id));
	}

	@Override
	public StateDTO mapObjectToDto(State state) {

		StateDTO stateDTO = new StateDTO();

		stateDTO.setId(state.getId());
		stateDTO.setName(state.getName());

		stateDTO.setCountryDTO(countryService.mapObjectToDto(state.getCountry()));

		return stateDTO;
	}

	@Override
	public void mapDtoToObject(StateDTO stateDTO, State state) {

		state.setName(stateDTO.getName());

		// Validate the country id.
		Country country = countryService.validateCountry(stateDTO.getCountryDTO().getId());

		state.setCountry(country);
	}

	@Override
	public State validateState(Long id) {

		Optional<State> stateOptional = stateRepository.findById(id);

		if (!stateOptional.isPresent())
			throw new RuntimeException("Requested state not found");

		return stateOptional.get();
	}
}
