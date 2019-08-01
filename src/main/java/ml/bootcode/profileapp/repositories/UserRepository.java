/**
 *
 */
package ml.bootcode.profileapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ml.bootcode.profileapp.models.User;

/**
 * @author sunnybatabyal
 *
 */
//@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
