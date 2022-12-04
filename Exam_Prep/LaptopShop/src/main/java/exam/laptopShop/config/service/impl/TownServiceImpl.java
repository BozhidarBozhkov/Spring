package exam.laptopShop.config.service.impl;

import exam.laptopShop.config.model.dto.TownImportDtos;
import exam.laptopShop.config.model.dto.TownsImportWrapperDto;
import exam.laptopShop.config.model.entities.Town;
import exam.laptopShop.config.repository.TownRepository;
import exam.laptopShop.config.service.TownService;
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
public class TownServiceImpl implements TownService {

    private final Path path = Path.of("src/main/resources/files/xml/towns.xml");
    private final TownRepository townRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) throws JAXBException {
        this.townRepository = townRepository;

        JAXBContext context = JAXBContext.newInstance(TownsImportWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/xml/towns.xml");

        return Files.readString(path);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {

        TownsImportWrapperDto importTownsDto = (TownsImportWrapperDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        return importTownsDto.getTownImportDtos().stream()
                .map(this::ImportTown)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String ImportTown(TownImportDtos dto) {

        Set<ConstraintViolation<TownImportDtos>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()){
            return "Invalid town";
        }

        Optional<Town> optTown = this.townRepository.findByName(dto.getName());

        if (optTown.isPresent()){
            return "Invalid town";
        }

        Town town = this.modelMapper.map(dto, Town.class);
        this.townRepository.save(town);

        return String.format("Successfully imported Town %s",town.getName());
    }
}
