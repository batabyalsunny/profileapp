/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ml.bootcode.profileapp.dto.AssetDTO;

/**
 * @author sunnyb
 *
 */
public interface AssetService {

	public static final String ASSET_LOCATION = System.getProperty("user.home") + "/Downloads/uploads/";

	List<AssetDTO> getAssets();

	AssetDTO getAsset(Long id);

	List<AssetDTO> addAssets(MultipartFile[] files, Long assetTypeId) throws IOException;

	AssetDTO updateAsset(Long id, AssetDTO assetDto);

	void deleteAsset(Long id);

	ResponseEntity<byte[]> renderAsset(Long id) throws IOException;

	void streamAsset(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException;

	void downloadAsset(Long id, HttpServletRequest request, HttpServletResponse response);
}
