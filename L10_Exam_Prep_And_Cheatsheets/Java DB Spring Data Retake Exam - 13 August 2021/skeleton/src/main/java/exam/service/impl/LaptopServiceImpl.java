package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.ExportBestLaptopsDTO;
import exam.model.dtos.ImportLaptopDTO;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.util.ErrorMessages.INVALID_LAPTOP;
import static exam.util.FilesPaths.LAPTOPS_PATH;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final Gson gson;
    private final ModelMapper mapper;
    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;
    private final ValidationUtil validationUtil;

    @Autowired
    public LaptopServiceImpl(Gson gson, ModelMapper mapper, LaptopRepository laptopRepository, ShopRepository shopRepository, ValidationUtil validationUtil) {
        this.gson = gson;
        this.mapper = mapper;
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(LAPTOPS_PATH);
    }

    @Override
    public String importLaptops() throws IOException {
        ImportLaptopDTO[] importLaptopDTOS = gson+.fromJson(readLaptopsFileContent(), ImportLaptopDTO[].class);
        return Arrays.stream(importLaptopDTOS)
                .map(this::importLaptop)
                .collect(Collectors.joining("\n"));
    }

    private String importLaptop(ImportLaptopDTO importLaptopDTO) {
        if(!validationUtil.isValid(importLaptopDTO)) return INVALID_LAPTOP;
        if(laptopRepository.findByMacAddress(importLaptopDTO.getMacAddress()).isPresent()) return INVALID_LAPTOP;

        Shop shop = this.shopRepository.findByName(importLaptopDTO.getShop().getName()).get();

        Laptop laptop = mapper.map(importLaptopDTO, Laptop.class);
        double roundedCpuSpeed = Math.round(importLaptopDTO.getCpuSpeed() * 100.0) / 100.0;
        laptop.setCpuSpeed(roundedCpuSpeed);

        laptop.setShop(shop);
        this.laptopRepository.save(laptop);

        return String.format("Successfully imported Laptop %s - %.2f - %d - %d", laptop.getMacAddress(), laptop.getCpuSpeed()
                , laptop.getRam(), laptop.getStorage());
    }

    @Override
    public String exportBestLaptops() {
        List<ExportBestLaptopsDTO> bestLaptopsDTOS = this.laptopRepository.exportBestLaptop();

        return bestLaptopsDTOS.stream().map(ExportBestLaptopsDTO::toString).collect(Collectors.joining("\n"));
    }
}
