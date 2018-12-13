/**
 * 
 */
package ml.bootcode.profileapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import ml.bootcode.profileapp.models.Employee;
import org.springframework.stereotype.Repository;

/**
 * @author sunny
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
