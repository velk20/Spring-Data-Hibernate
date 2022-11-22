package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.ApartmentType;
import softuni.exam.models.entities.Offer;

import java.util.List;

@Repository

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByApartmentApartmentTypeOrderByApartmentAreaDescPriceAsc(ApartmentType apartmentType);

}
