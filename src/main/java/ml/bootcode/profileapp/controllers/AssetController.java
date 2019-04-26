/**
 * 
 */
package ml.bootcode.profileapp.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ml.bootcode.profileapp.services.AssetService;

/**
 * @author sunnyb
 *
 */
@RestController
@RequestMapping("api/v1/assets")
public class AssetController {

	private AssetService assetService;

	/**
	 * @param assetService
	 */
	public AssetController(AssetService assetService) {
		this.assetService = assetService;
	}

	@PostMapping
	public void uploadAsset(@RequestParam("files") MultipartFile[] files, @RequestParam("type") Long assetTypeId) {
		try {
			assetService.addAsset(files, assetTypeId);
		} catch (IOException ioEx) {

		}
	}
}
