/**
 * 
 */
package ml.bootcode.profileapp.models;

import javax.persistence.*;
import java.util.List;

/**
 * @author sunnyb
 *
 */
@Entity
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
	private List<State> states;

	/**
	 * 
	 */
	public Country() {
	}

	/**
	 * @param id
	 * @param name
	 */
	public Country(String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}
}
