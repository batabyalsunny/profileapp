/**
 * 
 */
package ml.bootcode.profileapp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author sunnyb
 *
 */
@Entity
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne(targetEntity = State.class)
	private State state;

	/**
	 * @param state
	 */
	public City(State state) {
		this.state = state;
	}

	/**
	 * @param id
	 * @param name
	 * @param state
	 */
	public City(Long id, String name, State state) {
		this.id = id;
		this.name = name;
		this.state = state;
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
}
