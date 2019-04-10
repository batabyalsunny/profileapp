/**
 *
 */
package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.StateDTO;

/**
 * @author sunnybatabyal
 *
 */
public interface StateService {

	List<StateDTO> getStates();

	StateDTO getState(Long id);

	StateDTO addState(StateDTO stateDTO);

	StateDTO updateState(Long id, StateDTO stateDTO);

	void deleteState(Long id);
}
