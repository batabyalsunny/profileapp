/**
 * 
 */
package ml.bootcode.profileapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.bootcode.profileapp.models.Company;

/**
 * @author sunny
 *
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
