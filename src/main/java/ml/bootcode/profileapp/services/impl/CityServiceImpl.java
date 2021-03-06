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

import ml.bootcode.profileapp.dto.AddressDTO;
import ml.bootcode.profileapp.dto.CityDTO;
import ml.bootcode.profileapp.models.City;
import ml.bootcode.profileapp.repositories.CityRepository;
import ml.bootcode.profileapp.services.CityService;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnyb
 *
 */
@Service
public class CityServiceImpl implements CityService {

	CityRepository cityRepository;
	EntityValidator entityValidator;

	@Autowired
	ModelMapper mapper;

	/**
	 * @param cityRepository
	 */
	public CityServiceImpl(CityRepository cityRepository, EntityValidator entityValidator) {
		this.cityRepository = cityRepository;
		this.entityValidator = entityValidator;
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
		return mapper.map(entityValidator.validateCity(id), CityDTO.class);
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

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(cityRepository.save(mapper.map(cityDTO, City.class)), CityDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CityService#updateCity(ml.bootcode.profileapp
	 * .dto.CityDTO)
	 */
	@Override
	public CityDTO updateCity(Long id, CityDTO cityDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		// Validate the city ID to update.
		City city = entityValidator.validateCity(id);

		// Set the city id to DTO.
		cityDTO.setId(city.getId());

		return mapper.map(cityRepository.save(mapper.map(cityDTO, City.class)), CityDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.CityService#deleteCity(java.lang.Long)
	 */
	@Override
	public void deleteCity(Long id) {
		cityRepository.delete(entityValidator.validateCity(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.CityService#getAddressesByCityId(java.lang.
	 * Long)
	 */
	@Override
	public List<AddressDTO> getAddressesByCityId(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		// Validate city ID and get the city.
		City city = entityValidator.validateCity(id);

		return city.getAddresses().stream().map(address -> {
			return mapper.map(address, AddressDTO.class);
		}).collect(Collectors.toList());
	}
}
