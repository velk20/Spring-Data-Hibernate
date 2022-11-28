package exam.service.impl;

import exam.model.dtos.ImportShopsRoot;
import exam.model.dtos.ShopInformationDTO;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.util.ErrorMessages.INVALID_SHOP;
import static exam.util.FilesPaths.SHOPS_PATH;

@Service
public class ShopServiceImpl implements ShopService {
    private final ModelMapper mapper;
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public ShopServiceImpl(ModelMapper mapper, ShopRepository shopRepository, TownRepository townRepository, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.mapper = mapper;
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(SHOPS_PATH);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        ImportShopsRoot importShopsRoot = xmlParser.fromFile(SHOPS_PATH.toAbsolutePath().toString(), ImportShopsRoot.class);
        return importShopsRoot.getShops()
                .stream()
                .map(this::importShop)
                .collect(Collectors.joining("\n"));
    }

    private String importShop(ShopInformationDTO shopInformationDTO) {
        boolean isDtoValid = validationUtil.isValid(shopInformationDTO);
        if (!isDtoValid) return INVALID_SHOP;

        Optional<Shop> shopByName =
                this.shopRepository.findByName(shopInformationDTO.getName());
        if (shopByName.isPresent()) {
            return INVALID_SHOP;
        }

        Optional<Town> townByName = townRepository.findByName(shopInformationDTO.getTown().getName());
        if (townByName.isEmpty()) {
            return INVALID_SHOP;
        }

        Shop shop = mapper.map(shopInformationDTO, Shop.class);
        shop.setTown(townByName.get());

        this.shopRepository.save(shop);
        return String.format("Successfully imported Shop %s - %.2f", shopInformationDTO.getName(),
                shopInformationDTO.getIncome());

    }
}
