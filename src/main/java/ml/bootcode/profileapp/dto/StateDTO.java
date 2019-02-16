/**
 *
 */
package ml.bootcode.profileapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author sunnybatabyal
 *
 */
public class StateDTO {

	private Long id;
	private String name;
	@JsonProperty("country")
	private CountryDTO countryDTO;

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

	/**
	 * @return the countryDTO
	 */
	public CountryDTO getCountryDTO() {
		return countryDTO;
	}

	/**
	 * @param countryDTO the countryDTO to set
	 */
	public void setCountryDTO(CountryDTO countryDTO) {
		this.countryDTO = countryDTO;
	}
}
