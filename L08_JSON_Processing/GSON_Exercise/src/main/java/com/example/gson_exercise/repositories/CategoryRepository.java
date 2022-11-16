package com.example.gson_exercise.repositories;

import com.example.gson_exercise.models.dtos.CategoryProductDTO;
import com.example.gson_exercise.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select " +
            "new com.example.gson_exercise.models.dtos.CategoryProductDTO(" +
            "c.name," +
            "count(p)," +
            "AVG(p.price)," +
            "sum (p.price))" +
            " from Category  as c " +
            "join c.products as p " +
            "group by c " +
            "order by c.products.size desc ")
    List<CategoryProductDTO> getCategoriesByProductsCount();

}
