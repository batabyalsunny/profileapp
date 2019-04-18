/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.DesignationDTO;
import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.models.Designation;
import ml.bootcode.profileapp.repositories.DesignationRepository;
import ml.bootcode.profileapp.services.DesignationService;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnyb
 *
 */
@Service
public class DesignationServiceImpl implements DesignationService {

	DesignationRepository designationRepository;
	ModelMapper mapper;
	EntityValidator entityValidator;

	/**
	 * @param designationRepository
	 * @param mapper
	 * @param entityValidator
	 */
	public DesignationServiceImpl(DesignationRepository designationRepository, ModelMapper mapper,
			EntityValidator entityValidator) {
		this.designationRepository = designationRepository;
		this.mapper = mapper;
		this.entityValidator = entityValidator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.DesignationService#getDesignations()
	 */
	@Override
	public List<DesignationDTO> getDesignations() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return designationRepository.findAll().stream().map(designation -> {
			return mapper.map(designation, DesignationDTO.class);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.DesignationService#getDesignation(java.lang.
	 * Long)
	 */
	@Override
	public DesignationDTO getDesignation(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(entityValidator.validateDesignation(id), DesignationDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.DesignationService#addDesignation(ml.bootcode
	 * .profileapp.dto.DesignationDTO)
	 */
	@Override
	public DesignationDTO addDesignation(DesignationDTO designationDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(designationRepository.save(mapper.map(designationDTO, Designation.class)),
				DesignationDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.DesignationService#updateDesignation(java.
	 * lang.Long, ml.bootcode.profileapp.dto.DesignationDTO)
	 */
	@Override
	public DesignationDTO updateDesignation(Long id, DesignationDTO designationDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		Designation designation = entityValidator.validateDesignation(id);

		// Set ID to the DTO.
		designationDTO.setId(id);

		return mapper.map(designationRepository.save(mapper.map(designationDTO, Designation.class)),
				DesignationDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.DesignationService#deleteDesignation(java.
	 * lang.Long)
	 */
	@Override
	public void deleteDesignation(Long id) {
		designationRepository.delete(entityValidator.validateDesignation(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.DesignationService#
	 * getEmployeesByDesignationId(java.lang.Long)
	 */
	@Override
	public List<EmployeeDTO> getEmployeesByDesignationId(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return entityValidator.validateDesignation(id).getEmployees().stream().map(employee -> {
			return mapper.map(employee, EmployeeDTO.class);
		}).collect(Collectors.toList());
	}

}
