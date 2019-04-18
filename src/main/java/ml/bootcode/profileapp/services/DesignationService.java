/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.DesignationDTO;
import ml.bootcode.profileapp.dto.EmployeeDTO;

/**
 * @author sunnyb
 *
 */
public interface DesignationService {

	public List<DesignationDTO> getDesignations();

	public DesignationDTO getDesignation(Long id);

	public DesignationDTO addDesignation(DesignationDTO designationDTO);

	public DesignationDTO updateDesignation(Long id, DesignationDTO designationDTO);

	public void deleteDesignation(Long id);

	public List<EmployeeDTO> getEmployeesByDesignationId(Long id);
}
