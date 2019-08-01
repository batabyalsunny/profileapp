/**
 *
 */
package ml.bootcode.profileapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.bootcode.profileapp.models.Authority;

/**
 * @author sunnybatabyal
 *
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Optional<Authority> findByName(String name);
}
