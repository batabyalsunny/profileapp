package ml.bootcode.profileapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ml.bootcode.profileapp.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
