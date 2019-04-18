/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.CompanyDTO;
import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.models.Company;
import ml.bootcode.profileapp.repositories.CompanyRepository;
import ml.bootcode.profileapp.services.CompanyService;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnyb
 *
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	CompanyRepository companyRepository;
	ModelMapper mapper;
	EntityValidator entityValidator;

	/**
	 * @param companyRepository
	 * @param mapper
	 * @param entityValidator
	 */
	public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper mapper,
			EntityValidator entityValidator) {
		this.companyRepository = companyRepository;
		this.mapper = mapper;
		this.entityValidator = entityValidator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.CompanyService#getCompanies()
	 */
	@Override
	public List<CompanyDTO> getCompanies() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return companyRepository.findAll().stream().map(company -> {
			return mapper.map(company, CompanyDTO.class);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CompanyService#getCompany(java.lang.Long)
	 */
	@Override
	public CompanyDTO getCompany(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(entityValidator.validateCompany(id), CompanyDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.CompanyService#addCompany(ml.bootcode.
	 * profileapp.dto.CompanyDTO)
	 */
	@Override
	public CompanyDTO addCompany(CompanyDTO companyDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(companyRepository.save(mapper.map(companyDTO, Company.class)), CompanyDTO.class);
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

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		Company company = entityValidator.validateCompany(id);

		// Set ID to DTO.
		companyDTO.setId(id);

		return mapper.map(companyRepository.save(mapper.map(companyDTO, Company.class)), CompanyDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CompanyService#deleteCompany(java.lang.Long)
	 */
	@Override
	public void deleteCompany(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		companyRepository.delete(mapper.map(entityValidator.validateCompany(id), Company.class));
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

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return entityValidator.validateCompany(id).getEmployees().stream().map(employee -> {
			return mapper.map(employee, EmployeeDTO.class);
		}).collect(Collectors.toList());
	}
}
