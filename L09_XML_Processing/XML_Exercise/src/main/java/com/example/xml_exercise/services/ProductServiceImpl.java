package com.example.xml_exercise.services;

import com.example.xml_exercise.models.products.Product;
import com.example.xml_exercise.models.products.import_data.CreateProductListDTO;
import com.example.xml_exercise.models.products.import_data.ProductInRangeDTO;
import com.example.xml_exercise.models.products.import_data.ProductsInRangeDTO;
import com.example.xml_exercise.repositories.CategoryRepository;
import com.example.xml_exercise.repositories.ProductRepository;
import com.example.xml_exercise.repositories.UserRepository;
import com.example.xml_exercise.utils.FilesPaths;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;
    private final JAXBContext context;
    private final Random random;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper mapper,
                              @Qualifier("createProduct") JAXBContext context, Random random) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.context = context;
        this.random = random;
    }


    @Override
    public void seedProducts() throws JAXBException, IOException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        BufferedReader fileReader = Files.newBufferedReader(Paths.get(FilesPaths.PRODUCTS_FILE_PATH));
        CreateProductListDTO products = (CreateProductListDTO) unmarshaller.unmarshal(fileReader);
        products.getProducts()
                .stream()
                .map(productDTO -> mapper.map(productDTO, Product.class))
                .forEach( product -> {
                    int usersCount = (int) userRepository.count();
                    int categoriesCount = (int) categoryRepository.count();
                    int sellerId = random.nextInt(1, usersCount);
                    int buyerId = random.nextInt(1, usersCount);
                    int categoriesId = random.nextInt(3, categoriesCount);
                    product.setSeller(this.userRepository.getReferenceById((long) sellerId));
                    if (!product.getName().startsWith("A")) {
                        product.setBuyer(this.userRepository.getReferenceById((long) buyerId));
                    }
                    product.setCategories(Set.of(
                            this.categoryRepository.getReferenceById((long) categoriesId),
                            this.categoryRepository.getReferenceById((long) categoriesId-1),
                            this.categoryRepository.getReferenceById((long) categoriesId-2)
                            ));
                    this.productRepository.save(product);
                });
    }

    @Override
    public void productsInRange(BigDecimal start, BigDecimal end) throws Exception {
        List<ProductInRangeDTO> productInRangeDTOS = this.productRepository.getAllProductsInRange(start, end);
        ProductsInRangeDTO productsInRangeDTO = new ProductsInRangeDTO(productInRangeDTOS);
        JAXBContext jaxbContext = JAXBContext.newInstance(ProductsInRangeDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(productsInRangeDTO, new File(FilesPaths.PRODUCTS_IN_RANGE_PATH));

    }
}
