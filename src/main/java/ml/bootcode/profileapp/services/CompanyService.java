/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.CompanyDTO;
import ml.bootcode.profileapp.dto.EmployeeDTO;

/**
 * @author sunnyb
 *
 */
public interface CompanyService {

	List<CompanyDTO> getCompanies();

	CompanyDTO getCompany(Long id);

	CompanyDTO addCompany(CompanyDTO companyDTO);

	CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);

	void deleteCompany(Long id);

	List<EmployeeDTO> getEmployeeByCompanyId(Long id);
}
