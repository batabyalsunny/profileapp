/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.util.List;

import ml.bootcode.profileapp.dto.AssetDTO;
import ml.bootcode.profileapp.dto.AssetTypeDTO;

/**
 * @author sunnyb
 *
 */
public interface AssetTypeService {

	List<AssetTypeDTO> getAssetTypes();

	AssetTypeDTO getAssetType(Long id);

	AssetTypeDTO addAssetType(AssetTypeDTO assetTypeDTO);

	AssetTypeDTO updateAssetType(Long id, AssetTypeDTO assetTypeDTO);

	void deleteAssetType(Long id);

	List<AssetDTO> getAssetsByTypeId(Long id);
}
