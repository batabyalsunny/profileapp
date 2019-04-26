/**
 * 
 */
package ml.bootcode.profileapp.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ml.bootcode.profileapp.dto.AddressDTO;
import ml.bootcode.profileapp.services.AddressService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/addresses")
public class AddressController {

	AddressService addressService;

	/**
	 * @param addressService
	 */
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@GetMapping
	public List<AddressDTO> getAddresses() {
		return addressService.getAddresses();
	}

	@GetMapping("{id}")
	public AddressDTO getAddress(@PathVariable Long id) {
		return addressService.getAddress(id);
	}

	@PostMapping
	public AddressDTO addCity(@RequestBody AddressDTO addressDTO) {
		return addressService.addAddress(addressDTO);
	}

	@PutMapping("{id}")
	public AddressDTO updateCity(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
		return addressService.updateAddress(id, addressDTO);
	}

	@DeleteMapping("{id}")
	public void deleteCity(@PathVariable Long id) {
		addressService.deleteAddress(id);
	}
}
