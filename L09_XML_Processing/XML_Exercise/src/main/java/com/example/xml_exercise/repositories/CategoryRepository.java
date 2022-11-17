package com.example.xml_exercise.repositories;

import com.example.xml_exercise.models.categories.Category;
import com.example.xml_exercise.models.categories.CategoryAndProductsInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new com.example.xml_exercise.models.categories.CategoryAndProductsInfoDTO(c.name,c.products.size," +
            "avg (p.price),sum (p.price)) " +
            "from Category c " +
            "join c.products p" +
            " group by c " +
            "order by c.products.size desc ")
    List<CategoryAndProductsInfoDTO> getAllCategoriesAndProducts();



}
