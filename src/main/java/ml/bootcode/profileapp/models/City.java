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
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@ManyToOne
	private State state;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "city")
	private List<Address> addresses;

	/**
	 * @param state
	 */
	public City(State state) {
		this.state = state;
	}

	/**
	 * @param name
	 * @param state
	 */
	public City(String name, State state) {
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
}