/**
 * 
 */
package ml.bootcode.profileapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import ml.bootcode.profileapp.dto.AssetDTO;
import ml.bootcode.profileapp.dto.AssetTypeDTO;
import ml.bootcode.profileapp.models.AssetType;
import ml.bootcode.profileapp.repositories.AssetTypeRepository;
import ml.bootcode.profileapp.services.AssetTypeService;
import ml.bootcode.profileapp.util.EntityValidator;

/**
 * @author sunnyb
 *
 */
@Service
public class AssetTypeServiceImpl implements AssetTypeService {

	private AssetTypeRepository assetTypeRepository;
	private ModelMapper mapper;
	private EntityValidator entityValidator;

	/**
	 * @param assetTypeRepository
	 * @param mapper
	 * @param entityValidator
	 */
	public AssetTypeServiceImpl(AssetTypeRepository assetTypeRepository, ModelMapper mapper,
			EntityValidator entityValidator) {
		this.assetTypeRepository = assetTypeRepository;
		this.mapper = mapper;
		this.entityValidator = entityValidator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ml.bootcode.profileapp.services.AssetTypeService#getAssetTypes()
	 */
	@Override
	public List<AssetTypeDTO> getAssetTypes() {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		List<AssetType> assetTypes = assetTypeRepository.findAll();

		return assetTypes.stream().map(assetType -> {
			return mapper.map(assetType, AssetTypeDTO.class);
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AssetTypeService#getAssetType(java.lang.Long)
	 */
	@Override
	public AssetTypeDTO getAssetType(Long id) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(entityValidator.validateAssetType(id), AssetTypeDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AssetTypeService#addAssetType(ml.bootcode.
	 * profileapp.dto.AssetTypeDTO)
	 */
	@Override
	public AssetTypeDTO addAssetType(AssetTypeDTO assetTypeDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		return mapper.map(assetTypeRepository.save(mapper.map(assetTypeDTO, AssetType.class)), AssetTypeDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AssetTypeService#updateAssetType(java.lang.
	 * Long, ml.bootcode.profileapp.dto.AssetTypeDTO)
	 */
	@Override
	public AssetTypeDTO updateAssetType(Long id, AssetTypeDTO assetTypeDTO) {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

		// Validate the ID.
		AssetType assetType = entityValidator.validateAssetType(id);

		// Set the ID to DTO.
		assetTypeDTO.setId(id);

		return mapper.map(assetTypeRepository.save(mapper.map(assetTypeDTO, AssetType.class)), AssetTypeDTO.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AssetTypeService#deleteAssetType(java.lang.
	 * Long)
	 */
	@Override
	public void deleteAssetType(Long id) {
		assetTypeRepository.delete(entityValidator.validateAssetType(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ml.bootcode.profileapp.services.AssetTypeService#getAssetsByTypeId(java.lang.
	 * Long)
	 */
	@Override
	public List<AssetDTO> getAssetsByTypeId(Long id) {

		AssetType assetType = entityValidator.validateAssetType(id);

		return assetType.getAssets().stream().map(asset -> {
			return mapper.map(asset, AssetDTO.class);
		}).collect(Collectors.toList());
	}
}
