/**
 * 
 */
package ml.bootcode.profileapp.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("{id}")
	public ResponseEntity<byte[]> getAsset(@PathVariable Long id) {
		try {
			return assetService.getAsset(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping
	public void uploadAsset(@RequestParam("files") MultipartFile[] files, @RequestParam("type") Long assetTypeId) {
		try {
			assetService.addAsset(files, assetTypeId);
		} catch (IOException ioEx) {

		}
	}
}
