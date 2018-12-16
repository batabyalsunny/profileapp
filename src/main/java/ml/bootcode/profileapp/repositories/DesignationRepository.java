package ml.bootcode.profileapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.bootcode.profileapp.models.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
}
