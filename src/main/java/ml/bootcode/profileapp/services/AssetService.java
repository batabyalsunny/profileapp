/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunnyb
 *
 */
public interface AssetService {

	public static final String ASSET_LOCATION = System.getProperty("user.home") + "/Downloads/uploads/";

	ResponseEntity<byte[]> getAsset(Long id) throws IOException;

	void addAsset(MultipartFile[] files, Long assetTypeId) throws IOException;
}
