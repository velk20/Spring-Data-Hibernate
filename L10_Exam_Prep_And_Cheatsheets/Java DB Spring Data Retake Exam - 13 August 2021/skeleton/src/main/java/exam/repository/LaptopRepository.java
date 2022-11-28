package exam.repository;

import exam.model.dtos.ExportBestLaptopsDTO;
import exam.model.entities.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop,Long> {
    Optional<Laptop> findByMacAddress(String macAddress);

    @Query("select new exam.model.dtos.ExportBestLaptopsDTO(l.macAddress,l.cpuSpeed," +
            "l.ram,l.storage,l.price,l.shop.name,l.shop.town.name) from Laptop  as l " +
            "order by l.cpuSpeed desc ,l.ram desc,l.storage desc, l.macAddress ascs")
    List<ExportBestLaptopsDTO> exportBestLaptop();
}
