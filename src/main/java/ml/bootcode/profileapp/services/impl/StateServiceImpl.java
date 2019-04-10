/**
 *
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.StateDTO;
import ml.bootcode.profileapp.models.Country;
import ml.bootcode.profileapp.models.State;
import ml.bootcode.profileapp.repositories.StateRepository;
import ml.bootcode.profileapp.services.StateService;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnybatabyal
 *
 */
@Service
public class StateServiceImpl implements StateService {

	private StateRepository stateRepository;
	private EntityValidator entityValidator;

	@Autowired
	ModelMapper mapper;

	/**
	 * @param stateRepository
	 * @param countryService
	 */
	public StateServiceImpl(StateRepository stateRepository, EntityValidator entityValidator) {
		this.stateRepository = stateRepository;
		this.entityValidator = entityValidator;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#getStates()
	 */
	@Override
	public List<StateDTO> getStates() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		List<State> states = stateRepository.findAll();
		return states.stream().map(state -> {
			return mapper.map(state, StateDTO.class);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#getState()
	 */
	@Override
	public StateDTO getState(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		State state = entityValidator.validateState(id);
		return mapper.map(state, StateDTO.class);
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

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		// Validate country.
		Country country = entityValidator.validateCountry(stateDTO.getCountryDTO().getId());

		State state = mapper.map(stateDTO, State.class);

		// Set country.
		state.setCountry(country);

		return mapper.map(stateRepository.save(state), StateDTO.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#updateState(java.lang.Long,
	 * ml.bootcode.profileapp.dto.StateDTO)
	 */
	@Override
	public StateDTO updateState(Long id, StateDTO stateDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		// Validate state.
		State state = entityValidator.validateState(id);

		// Set state ID to DTO.
		stateDTO.setId(state.getId());

		// Update state.
		return mapper.map(stateRepository.save(mapper.map(stateDTO, State.class)), StateDTO.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#deleteState(java.lang.Long)
	 */
	@Override
	public void deleteState(Long id) {

		// Validate and delete state.
		stateRepository.delete(entityValidator.validateState(id));
	}
}
