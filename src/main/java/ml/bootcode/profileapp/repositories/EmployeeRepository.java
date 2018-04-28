/**
 * 
 */
package ml.bootcode.profileapp.repositories;

import org.springframework.data.repository.CrudRepository;

import ml.bootcode.profileapp.models.Employee;

/**
 * @author sunny
 *
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
