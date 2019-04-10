/**
 *
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.StateDTO;
import ml.bootcode.profileapp.models.State;
import ml.bootcode.profileapp.repositories.StateRepository;
import ml.bootcode.profileapp.services.StateService;

/**
 * @author sunnybatabyal
 *
 */
@Service
public class StateServiceImpl implements StateService {

	private StateRepository stateRepository;

	@Autowired
	ModelMapper mapper;

	/**
	 * @param stateRepository
	 * @param countryService
	 */
	public StateServiceImpl(StateRepository stateRepository) {
		this.stateRepository = stateRepository;
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

		State state = validateState(id);
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
		
		//

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		State state = mapper.map(stateDTO, State.class);

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

		// Validate state.
		State state = validateState(id);

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
		stateRepository.delete(validateState(id));
	}

	@Override
	public State validateState(Long id) {

		Optional<State> stateOptional = stateRepository.findById(id);

		if (!stateOptional.isPresent())
			throw new RuntimeException("Requested state not found");

		return stateOptional.get();
	}
}
