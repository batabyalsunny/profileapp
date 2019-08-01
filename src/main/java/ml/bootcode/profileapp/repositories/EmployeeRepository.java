/**
 * 
 */
package ml.bootcode.profileapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.bootcode.profileapp.models.Employee;

/**
 * @author sunny
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmail(String email);
}
