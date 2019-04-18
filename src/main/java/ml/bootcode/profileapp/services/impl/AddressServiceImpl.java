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
import ml.bootcode.profileapp.models.Address;
import ml.bootcode.profileapp.repositories.AddressRepository;
import ml.bootcode.profileapp.services.AddressService;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnyb
 *
 */
@Service
public class AddressServiceImpl implements AddressService {

	private AddressRepository addressRepository;
	private EntityValidator entityValidator;

	@Autowired
	private ModelMapper mapper;

	/**
	 * @param addressRepository
	 * @param entityValidator
	 */
	public AddressServiceImpl(AddressRepository addressRepository, EntityValidator entityValidator) {
		this.addressRepository = addressRepository;
		this.entityValidator = entityValidator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AddressService#getAddresses()
	 */
	@Override
	public List<AddressDTO> getAddresses() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		List<Address> addresses = addressRepository.findAll();

		return addresses.stream().map(address -> {
			return mapper.map(address, AddressDTO.class);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AddressService#getAddress(java.lang.Long)
	 */
	@Override
	public AddressDTO getAddress(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(entityValidator.validateAddress(id), AddressDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AddressService#addAddress(ml.bootcode.
	 * profileapp.dto.AddressDTO)
	 */
	@Override
	public AddressDTO addAddress(AddressDTO addressDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(addressRepository.save(mapper.map(addressDTO, Address.class)), AddressDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AddressService#updateAddress(java.lang.Long,
	 * ml.bootcode.profileapp.dto.AddressDTO)
	 */
	@Override
	public AddressDTO updateAddress(Long id, AddressDTO addressDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		// Validate the address ID.
		Address address = entityValidator.validateAddress(id);

		// Set the address ID to DTO.
		addressDTO.setId(id);

		return mapper.map(addressRepository.save(mapper.map(addressDTO, Address.class)), AddressDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AddressService#deleteAddress(java.lang.Long)
	 */
	@Override
	public void deleteAddress(Long id) {

		// Validate the address ID.
		Address address = entityValidator.validateAddress(id);

		addressRepository.delete(address);
	}
}
