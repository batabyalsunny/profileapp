/**
 * 
 */
package ml.bootcode.profileapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ml.bootcode.profileapp.models.AssetType;

/**
 * @author sunnyb
 *
 */
public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {

}
