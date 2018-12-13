/**
 * 
 */
package ml.bootcode.profileapp.models;

import javax.persistence.*;
import java.util.List;

/**
 * @author sunny
 *
 */
@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private List<Employee> employees;

	public Company() {
	}

	public Company(String name) {
		this.name = name;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
