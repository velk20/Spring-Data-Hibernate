package com.example.gson_exercise.repositories;

import com.example.gson_exercise.models.dtos.ExportProductDTO;
import com.example.gson_exercise.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select new com.example.gson_exercise.models.dtos.ExportProductDTO(" +
            "p.name," +
            "p.price," +
            "concat(p.seller.firstName,' ',p.seller.lastName)) from " +
            "Product p " +
            "where p.buyer is null and p.price between :startRange and :endRange" +
            " order by p.price asc ")
    List<ExportProductDTO> productsInRange(BigDecimal startRange, BigDecimal endRange);
}
