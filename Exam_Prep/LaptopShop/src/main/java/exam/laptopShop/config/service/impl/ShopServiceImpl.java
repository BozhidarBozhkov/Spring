package exam.laptopShop.config.service.impl;

import exam.laptopShop.config.model.dto.ShopImportDtos;
import exam.laptopShop.config.model.dto.ShopImportWrapperDto;
import exam.laptopShop.config.model.entities.Shop;
import exam.laptopShop.config.model.entities.Town;
import exam.laptopShop.config.repository.ShopRepository;
import exam.laptopShop.config.repository.TownRepository;
import exam.laptopShop.config.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {


    private final Path path = Path.of("src/main/resources/files/xml/shops.xml");
    private final ShopRepository shopRepository;
    private final TownRepository townRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository) throws JAXBException {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;

        JAXBContext context = JAXBContext.newInstance(ShopImportWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/xml/shops.xml");

        return Files.readString(path);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        ShopImportWrapperDto importShopsDto = (ShopImportWrapperDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        return importShopsDto.getShopImportDtos().stream().map(this::importShop).collect(Collectors.joining(System.lineSeparator()));

    }

    private String importShop(ShopImportDtos dto) {

        Set<ConstraintViolation<ShopImportDtos>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()) {
            return "Invalid shop";
        }

        Optional<Shop> optShop = this.shopRepository.findShopByName(dto.getName());
        if (optShop.isPresent()) {
            return "Invalid shop";
        }

        Shop shop = this.modelMapper.map(dto, Shop.class);
        Optional<Town> optionalTown = this.townRepository.findByName(dto.getTown().getName());
        if (optionalTown.isPresent()) {
            shop.setTown(optionalTown.get());
            this.shopRepository.save(shop);
        }

        return String.format("Successfully imported Shop %s - %.2f", shop.getName(), shop.getIncome());
    }

}
