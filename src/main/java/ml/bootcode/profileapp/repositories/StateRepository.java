package ml.bootcode.profileapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.bootcode.profileapp.models.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
