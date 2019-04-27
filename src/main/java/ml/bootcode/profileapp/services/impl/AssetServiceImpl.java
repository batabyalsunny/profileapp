/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ml.bootcode.profileapp.dto.AssetDTO;
import ml.bootcode.profileapp.dto.AssetTypeDTO;
import ml.bootcode.profileapp.models.Asset;
import ml.bootcode.profileapp.models.AssetType;
import ml.bootcode.profileapp.repositories.AssetRepository;
import ml.bootcode.profileapp.services.AssetService;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnyb
 *
 */
@Service
public class AssetServiceImpl implements AssetService {

	private AssetRepository assetRepository;
	private EntityValidator entityValidator;
	private ModelMapper mapper;

	@Autowired
	private ServletContext servletContext;

	/**
	 * @param assetRepository
	 */
	public AssetServiceImpl(AssetRepository assetRepository, EntityValidator entityValidator, ModelMapper mapper) {
		this.assetRepository = assetRepository;
		this.entityValidator = entityValidator;
		this.mapper = mapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AssetService#getAsset(java.lang.Long)
	 */
	@Override
	public ResponseEntity<byte[]> getAsset(Long id) throws IOException {
		// Validate the asset.
		Asset asset = entityValidator.validateAsset(id);

		HttpHeaders httpHeaders = new HttpHeaders();
		InputStream inputStream = servletContext.getResourceAsStream(asset.getPath());

		byte[] assetByte = org.apache.commons.io.IOUtils.toByteArray(inputStream);
		httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(assetByte, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AssetService#uploadAsset(org.springframework.
	 * web.multipart.MultipartFile)
	 */
	@Override
	public void addAsset(MultipartFile[] files, Long assetTypeId) throws IOException {

		for (MultipartFile file : files) {

			// Create a blank new file in assets directory.
			File newFile = new File(AssetService.ASSET_LOCATION + file.getOriginalFilename());
			newFile.createNewFile();

			// Stream the file into new file.
			FileOutputStream fileOutputStream = new FileOutputStream(newFile);
			fileOutputStream.write(file.getBytes());
			fileOutputStream.close();

			// Create the Dto for assets record.
			AssetDTO assetDTO = new AssetDTO();
			assetDTO.setRealName(file.getOriginalFilename());
			assetDTO.setAssetName(file.getOriginalFilename());
			assetDTO.setMimeType(file.getContentType());
			assetDTO.setPath(AssetService.ASSET_LOCATION + file.getOriginalFilename());

			mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

			AssetType assetType = entityValidator.validateAssetType(assetTypeId);

			assetDTO.setAssetType(mapper.map(assetType, AssetTypeDTO.class));

			// Save asset record.
			assetRepository.save(mapper.map(assetDTO, Asset.class));

		}
	}

}
