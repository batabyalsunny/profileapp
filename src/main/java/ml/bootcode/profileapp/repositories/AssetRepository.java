/**
 * 
 */
package ml.bootcode.profileapp.repositories;

import org.springframework.data.repository.CrudRepository;

import ml.bootcode.profileapp.models.Asset;

/**
 * @author sunnyb
 *
 */
public interface AssetRepository extends CrudRepository<Asset, Long> {

}
