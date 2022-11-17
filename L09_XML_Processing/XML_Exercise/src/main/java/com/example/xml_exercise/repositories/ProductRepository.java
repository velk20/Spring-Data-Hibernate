package com.example.xml_exercise.repositories;

import com.example.xml_exercise.models.products.Product;
import com.example.xml_exercise.models.products.import_data.ProductInRangeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new com.example.xml_exercise.models.products.import_data.ProductInRangeDTO(p.name,p.price,concat" +
            "(p.seller.firstName,' ',p.seller.lastName)) " +
            "from " +
            "Product as p " +
            "where p.price between :start and :end and p.buyer is null " +
            "order by p.price")
    List<ProductInRangeDTO> getAllProductsInRange(BigDecimal start, BigDecimal end);
}
