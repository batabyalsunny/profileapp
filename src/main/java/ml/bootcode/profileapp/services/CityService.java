/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.CityDTO;

/**
 * @author sunnyb
 *
 */
public interface CityService {

	List<CityDTO> getCities();

	CityDTO getCity(Long id);

	CityDTO addCity(CityDTO cityDTO);

	CityDTO updateCity(Long id, CityDTO cityDTO);

	void deleteCity(Long id);
}
