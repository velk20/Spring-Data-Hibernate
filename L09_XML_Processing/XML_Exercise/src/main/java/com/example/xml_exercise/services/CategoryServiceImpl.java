package com.example.xml_exercise.services;

import com.example.xml_exercise.models.categories.CategoriesByProductsDTO;
import com.example.xml_exercise.models.categories.Category;
import com.example.xml_exercise.models.categories.CategoryAndProductsInfoDTO;
import com.example.xml_exercise.models.categories.import_data.CreateCategoryListDTO;
import com.example.xml_exercise.repositories.CategoryRepository;
import com.example.xml_exercise.utils.FilesPaths;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final JAXBContext context;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper,
                               @Qualifier("createCategory") JAXBContext context) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.context = context;
    }


    @Override
    public void seedCategories() throws JAXBException, IOException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BufferedReader fileReader = Files.newBufferedReader(Paths.get(FilesPaths.CATEGORIES_FILE_PATH));
        CreateCategoryListDTO categories = (CreateCategoryListDTO) unmarshaller.unmarshal(fileReader);
        categories.getCategories()
                .stream()
                .map(categoryDTO -> mapper.map(categoryDTO, Category.class))
                .forEach(this.categoryRepository::save);
    }

    @Override
    public void getAllCategoriesByProductsCount() throws JAXBException {
        List<CategoryAndProductsInfoDTO> categoriesAndProducts = this.categoryRepository.getAllCategoriesAndProducts();
        CategoriesByProductsDTO categories = new CategoriesByProductsDTO(categoriesAndProducts);

        JAXBContext jaxbContext = JAXBContext.newInstance(CategoriesByProductsDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(categories, new File(FilesPaths.CATEGORIES_BY_PRODUCTS_PATH));
    }
}
