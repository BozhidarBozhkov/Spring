package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MechanicServiceImpl implements MechanicService {
    private final MechanicRepository mechanicRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }


    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        Path path = Path.of("src/main/resources/files/json/mechanics.json");
        return Files.readString(path);
    }

    @Override
    public String importMechanics() throws IOException {

        String json = this.readMechanicsFromFile();
        MechanicImportDto[] mechanicImportDtos = this.gson.fromJson(json, MechanicImportDto[].class);

        return Arrays.stream(mechanicImportDtos).map(this::importMechanic)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importMechanic(MechanicImportDto dto) {

        Set<ConstraintViolation<MechanicImportDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()) {
            return "Invalid mechanic";
        }

        Optional<Mechanic> optionalMechanic = this.mechanicRepository.findByEmail(dto.getEmail());
        if (optionalMechanic.isPresent()){
            return "Invalid mechanic";
        }

        Mechanic mechanic = this.modelMapper.map(dto, Mechanic.class);
        this.mechanicRepository.save(mechanic);

        return String.format("Successfully imported mechanic %s %s", mechanic.getFirstName(), mechanic.getLastName());
    }
}
