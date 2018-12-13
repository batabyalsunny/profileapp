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
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "state")
	private List<City> cities;

	@ManyToOne
	private Country country;

	/**
	 * 
	 */
	public State() {
	}

	/**
	 * @param name
	 * @param country
	 */
	public State(String name, Country country) {
		this.name = name;
		this.country = country;
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

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	/**
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

}
