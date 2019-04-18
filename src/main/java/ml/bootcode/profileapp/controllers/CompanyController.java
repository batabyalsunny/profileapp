/**
 * 
 */
package ml.bootcode.profileapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.dto.CompanyDTO;
import ml.bootcode.profileapp.dto.EmployeeDTO;
import ml.bootcode.profileapp.services.CompanyService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {

	CompanyService companyService;

	/**
	 * @param companyService
	 */
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@GetMapping
	public List<CompanyDTO> getCompanies() {
		return companyService.getCompanies();
	}

	@GetMapping("{id}")
	public CompanyDTO getCompany(@PathVariable Long id) {
		return companyService.getCompany(id);
	}

	@PostMapping
	public CompanyDTO addCompany(@RequestBody CompanyDTO companyDTO) {
		return companyService.addCompany(companyDTO);
	}

	@PutMapping("{id}")
	public CompanyDTO updateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
		return companyService.updateCompany(id, companyDTO);
	}

	@DeleteMapping("{id}")
	public void deleteCompany(@PathVariable Long id) {
		companyService.deleteCompany(id);
	}

	@GetMapping("{id}/employees")
	public List<EmployeeDTO> getEmployeesByCompanyId(@PathVariable Long id) {
		return companyService.getEmployeeByCompanyId(id);
	}
}
