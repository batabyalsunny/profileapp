package ml.bootcode.profileapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDTO {

	private Long id;
	private String addressLine1;
	private String addressLine2;

	@JsonProperty("city")
	private CityDTO cityDTO;

	public AddressDTO() {
	}

	public AddressDTO(String addressLine1, String addressLine2) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the cityDTO
	 */
	public CityDTO getCityDTO() {
		return cityDTO;
	}

	/**
	 * @param cityDTO the cityDTO to set
	 */
	public void setCityDTO(CityDTO cityDTO) {
		this.cityDTO = cityDTO;
	}
}
