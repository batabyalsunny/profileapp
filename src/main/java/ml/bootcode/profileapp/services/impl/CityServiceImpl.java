/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.CityDTO;
import ml.bootcode.profileapp.models.City;
import ml.bootcode.profileapp.repositories.CityRepository;
import ml.bootcode.profileapp.services.CityService;

/**
 * @author sunnyb
 *
 */
@Service
public class CityServiceImpl implements CityService {

	CityRepository cityRepository;

	@Autowired
	ModelMapper mapper;

	/**
	 * @param cityRepository
	 */
	public CityServiceImpl(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.CityService#getCities()
	 */
	@Override
	public List<CityDTO> getCities() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		List<City> cities = cityRepository.findAll();

		return cities.stream().map(city -> {
			return mapper.map(city, CityDTO.class);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.CityService#getCity(java.lang.Long)
	 */
	@Override
	public CityDTO getCity(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CityService#addCity(ml.bootcode.profileapp.
	 * dto.CityDTO)
	 */
	@Override
	public CityDTO addCity(CityDTO cityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CityService#updateCity(ml.bootcode.profileapp
	 * .dto.CityDTO)
	 */
	@Override
	public CityDTO updateCity(CityDTO cityDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.CityService#deleteCity(java.lang.Long)
	 */
	@Override
	public void deleteCity(Long id) {
		// TODO Auto-generated method stub

	}

}
