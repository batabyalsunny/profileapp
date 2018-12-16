/**
 *
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	/**
	 * @param stateRepository
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
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ml.bootcode.profileapp.services.StateService#deleteState(java.lang.Long)
	 */
	@Override
	public void deleteState(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public StateDTO mapObjectToDto(State state) {

		StateDTO stateDTO = new StateDTO();

		stateDTO.setId(state.getId());
		stateDTO.setName(state.getName());
		stateDTO.setCities(state.getCities());
		stateDTO.setCountry(state.getCountry());

		return stateDTO;
	}

	@Override
	public void mapDtoToObject(StateDTO stateDTO, State state) {

		state.setName(stateDTO.getName());
		state.setCountry(stateDTO.getCountry());
	}

	@Override
	public State validateState(Long id) {

		Optional<State> stateOptional = stateRepository.findById(id);

		if (!stateOptional.isPresent())
			throw new RuntimeException("Requested resource not found");

		return stateOptional.get();
	}
}
