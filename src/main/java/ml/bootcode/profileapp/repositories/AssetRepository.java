/**
 * 
 */
package ml.bootcode.profileapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ml.bootcode.profileapp.models.Asset;

/**
 * @author sunnyb
 *
 */
public interface AssetRepository extends JpaRepository<Asset, Long> {

}
