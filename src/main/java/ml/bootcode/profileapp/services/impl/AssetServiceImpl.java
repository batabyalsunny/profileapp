/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import ml.bootcode.profileapp.util.MultipartFileSender;
import ml.bootcode.profileapp.util.ResumableDownload;

/**
 * @author sunnyb
 *
 */
@Service
public class AssetServiceImpl implements AssetService {

	private AssetRepository assetRepository;
	private EntityValidator entityValidator;
	private ModelMapper mapper;

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
	 * @see ml.bootcode.profileapp.services.AssetService#getAssets()
	 */
	@Override
	public List<AssetDTO> getAssets() {
		return assetRepository.findAll().stream().map(asset -> {
			return mapper.map(asset, AssetDTO.class);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AssetService#getAsset(java.lang.Long)
	 */
	@Override
	public AssetDTO getAsset(Long id) {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return mapper.map(entityValidator.validateAsset(id), AssetDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AssetService#addAssets(org.springframework.
	 * web.multipart.MultipartFile[], java.lang.Long)
	 */
	@Override
	public List<AssetDTO> addAssets(MultipartFile[] files, Long assetTypeId) throws IOException {

		List<AssetDTO> assetDTOs = new ArrayList<>();

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
			assetDTOs.add(mapper.map(assetRepository.save(mapper.map(assetDTO, Asset.class)), AssetDTO.class));

		}

		return assetDTOs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AssetService#updateAsset(java.lang.Long)
	 */
	@Override
	public AssetDTO updateAsset(Long id, AssetDTO assetDto) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		Asset asset = entityValidator.validateAsset(id);

		asset.setAssetName(assetDto.getAssetName());
		asset.setAssetType(entityValidator.validateAssetType(assetDto.getAssetType().getId()));

		return mapper.map(assetRepository.save(asset), AssetDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AssetService#deleteAsset()
	 */
	@Override
	public void deleteAsset(Long id) {
		assetRepository.delete(entityValidator.validateAsset(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AssetService#renderAsset(java.lang.Long)
	 */
	@Override
	public ResponseEntity<byte[]> renderAsset(Long id) throws IOException {

		// Validate the asset.
		Asset asset = entityValidator.validateAsset(id);

		File file = new File(asset.getPath());

		if (!file.exists()) {
			throw new RuntimeException("File not found");
		}

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.valueOf(asset.getMimeType()));

		InputStream inputStream = new FileInputStream(file);

		byte[] assetByte = IOUtils.toByteArray(inputStream);
		httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());

		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(assetByte, httpHeaders, HttpStatus.OK);
		return responseEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AssetService#streamAsset(java.lang.Long,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void streamAsset(Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {

		// Validate the asset.
		Asset asset = entityValidator.validateAsset(id);

		File file = new File(asset.getPath());

		if (!file.exists()) {
			throw new RuntimeException("File not found");
		}

		MultipartFileSender.fromFile(file).with(request).with(response).serveResource();
	}

	@Override
	public long downloadAsset(Long id) throws IOException, URISyntaxException {
		return ResumableDownload.downloadFileWithResume("http://localhost:8080/api/v1/assets/" + id + "/render",
				"file-" + id + ".jpeg");
	}
}
