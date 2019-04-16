/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.CompanyDTO;
import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.services.CompanyService;

/**
 * @author sunnyb
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.CompanyService#getCompanies()
	 */
	@Override
	public List<CompanyDTO> getCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CompanyService#getCompany(java.lang.Long)
	 */
	@Override
	public CompanyDTO getCompany(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.CompanyService#addCompany(ml.bootcode.
	 * profileapp.dto.CompanyDTO)
	 */
	@Override
	public CompanyDTO addCompany(CompanyDTO companyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CompanyService#updateCompany(java.lang.Long,
	 * ml.bootcode.profileapp.dto.CompanyDTO)
	 */
	@Override
	public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CompanyService#deleteCompany(java.lang.Long)
	 */
	@Override
	public void deleteCompany(Long id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CompanyService#getEmployeeByCompanyId(java.
	 * lang.Long)
	 */
	@Override
	public List<EmployeeDTO> getEmployeeByCompanyId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
