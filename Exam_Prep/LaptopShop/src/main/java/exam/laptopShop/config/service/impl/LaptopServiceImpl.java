package exam.laptopShop.config.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.laptopShop.config.model.dto.LaptopImportDto;
import exam.laptopShop.config.model.entities.Laptop;
import exam.laptopShop.config.model.entities.Shop;
import exam.laptopShop.config.repository.LaptopRepository;
import exam.laptopShop.config.repository.ShopRepository;
import exam.laptopShop.config.service.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final ShopRepository shopRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/json/laptops.json");

        return Files.readString(path);
    }

    @Override
    public String importLaptops() throws IOException {

        String json = this.readLaptopsFileContent();

        LaptopImportDto[] laptops = this.gson.fromJson(json, (Type) LaptopImportDto[].class);

        return Arrays.stream(laptops).map(this::importLaptop).collect(Collectors.joining("\n"));
    }

    private String importLaptop(LaptopImportDto dto) {

        Set<ConstraintViolation<LaptopImportDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()){
            return "Invalid laptop";

        }

        Optional<Laptop> optLaptop = this.laptopRepository.findByMacAddress(dto.getMacAddress());
        if (optLaptop.isPresent()){
            return "Invalid laptop";
        }

        Optional<Shop> optShop = this.shopRepository.findByName(dto.getShop().getName());

        Laptop laptop = this.modelMapper.map(dto, Laptop.class);
        laptop.setShop(optShop.get());

        this.laptopRepository.save(laptop);

        return String.format("Successfully imported Laptop %s", laptop.getMacAddress());
    }

    @Override
    public String exportBestLaptops() {

        List<Laptop> laptops = this.laptopRepository.findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc();

       return laptops.stream().map(Laptop::toString).collect(Collectors.joining(System.lineSeparator()));

    }
}
