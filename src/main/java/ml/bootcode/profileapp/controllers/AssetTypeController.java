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

import ml.bootcode.profileapp.dto.AssetDTO;
import ml.bootcode.profileapp.dto.AssetTypeDTO;
import ml.bootcode.profileapp.services.AssetTypeService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/asset-types")
public class AssetTypeController {

	private AssetTypeService assetTypeService;

	/**
	 * @param assetTypeService
	 */
	public AssetTypeController(AssetTypeService assetTypeService) {
		this.assetTypeService = assetTypeService;
	}

	@GetMapping
	public List<AssetTypeDTO> getAssetTypes() {
		return assetTypeService.getAssetTypes();
	}

	@GetMapping("{id}")
	public AssetTypeDTO getAssetType(@PathVariable Long id) {
		return assetTypeService.getAssetType(id);
	}

	@PostMapping
	public AssetTypeDTO addAssetType(@RequestBody AssetTypeDTO assetTypeDTO) {
		return assetTypeService.addAssetType(assetTypeDTO);
	}

	@PutMapping("{id}")
	public AssetTypeDTO updateAssetType(@PathVariable Long id, @RequestBody AssetTypeDTO assetTypeDTO) {
		return assetTypeService.updateAssetType(id, assetTypeDTO);
	}

	@DeleteMapping("{id}")
	public void deleteCity(@PathVariable Long id) {
		assetTypeService.deleteAssetType(id);
	}

	@GetMapping("{id}/assets")
	public List<AssetDTO> getAssets(@PathVariable Long id) {
		return assetTypeService.getAssetsByTypeId(id);
	}
}
