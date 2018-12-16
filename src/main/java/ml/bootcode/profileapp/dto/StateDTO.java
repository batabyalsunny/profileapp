/**
 *
 */
package ml.bootcode.profileapp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ml.bootcode.profileapp.models.City;
import ml.bootcode.profileapp.models.Country;

/**
 * @author sunnybatabyal
 *
 */
public class StateDTO {

	private Long id;
	private String name;
	@JsonIgnore
	private List<City> cities;
	private Country country;

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
