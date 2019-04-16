/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.DesignationDTO;
import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.services.DesignationService;

/**
 * @author sunnyb
 *
 */
@Service
public class DesignationServiceImpl implements DesignationService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.DesignationService#getDesignations()
	 */
	@Override
	public List<DesignationDTO> getDesignations() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.DesignationService#
	 * getEmployeesByDesignationId(java.lang.Long)
	 */
	@Override
	public List<EmployeeDTO> getEmployeesByDesignationId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
