/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunnyb
 *
 */
public interface AssetService {

	public static final String ASSET_LOCATION = System.getProperty("user.home") + "/Downloads/uploads/";

	void addAsset(MultipartFile[] files, Long assetTypeId) throws IOException;
}
