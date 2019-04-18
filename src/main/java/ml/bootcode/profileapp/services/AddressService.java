/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.AddressDTO;

/**
 * @author sunnyb
 *
 */
public interface AddressService {

	List<AddressDTO> getAddresses();

	AddressDTO getAddress(Long id);

	AddressDTO addAddress(AddressDTO addressDTO);

	AddressDTO updateAddress(Long id, AddressDTO addressDTO);

	void deleteAddress(Long id);
}
