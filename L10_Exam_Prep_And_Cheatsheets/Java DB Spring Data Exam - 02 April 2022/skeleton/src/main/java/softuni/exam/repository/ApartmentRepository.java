package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Apartment;
import softuni.exam.models.entities.Town;

import java.util.Optional;

@Repository

public interface ApartmentRepository  extends JpaRepository<Apartment,Long> {
    Optional<Apartment> findByTownAndArea(Town town, double area);

    Apartment findById(long id);
}
