/**
 * 
 */
package ml.bootcode.profileapp.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author sunnyb
 *
 */
public interface AssetService {

	public static final String ASSET_LOCATION = System.getProperty("user.home") + "/Downloads/uploads/";

	void getAsset(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException;

	void addAsset(MultipartFile[] files, Long assetTypeId) throws IOException;
}
