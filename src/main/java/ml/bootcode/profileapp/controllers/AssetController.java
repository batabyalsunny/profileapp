/**
 * 
 */
package ml.bootcode.profileapp.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ml.bootcode.profileapp.dto.AssetDTO;
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

	@GetMapping
	public List<AssetDTO> getAssets() {
		return assetService.getAssets();
	}

	@GetMapping("{id}")
	public AssetDTO getAsset(@PathVariable Long id) {
		return assetService.getAsset(id);
	}

	@PostMapping
	public List<AssetDTO> uploadAsset(@RequestParam("files") MultipartFile[] files,
			@RequestParam("type") Long assetTypeId) {
		try {
			return assetService.addAssets(files, assetTypeId);
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
		}
		return null;
	}

	@PutMapping("{id}")
	public AssetDTO updateAsset(@PathVariable Long id, @RequestBody AssetDTO assetDto) {
		return assetService.updateAsset(id, assetDto);
	}

	@DeleteMapping("{id}")
	public void deleteAsset(@PathVariable Long id) {
		assetService.deleteAsset(id);
	}

	@GetMapping("{id}/render")
	public ResponseEntity<byte[]> renderAsset(@PathVariable Long id) {
		try {
			return assetService.renderAsset(id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@GetMapping("{id}/stream")
	public void streamAsset(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			assetService.streamAsset(id, request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GetMapping("{id}/download")
	public long downloadAsset(@PathVariable Long id) throws IOException, URISyntaxException {
		return assetService.downloadAsset(id);
	}
}
