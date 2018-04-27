/**
 * 
 */
package ml.bootcode.profileapp.repositories;

import org.springframework.data.repository.CrudRepository;

import ml.bootcode.profileapp.models.Company;

/**
 * @author sunny
 *
 */
public interface CompanyRepository extends CrudRepository<Company, Long> {

}
