package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;

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
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;


    @Autowired
    public PartServiceImpl(PartRepository partRepository, Gson gson) {
        this.partRepository = partRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/json/parts.json");
        return Files.readString(path);
    }

    @Override
    public String importParts() throws IOException {
        String json = this.readPartsFileContent();

        PartImportDto[] partImports = this.gson.fromJson(json, PartImportDto[].class);

        return Arrays.stream(partImports).map(this::importPart)
                .collect(Collectors.joining(System.lineSeparator()));

    }

    private String importPart(PartImportDto dto) {

        Set<ConstraintViolation<PartImportDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()) {
            return "Invalid part";
        }

        Optional<Part> optionalPart = this.partRepository.findByPartName(dto.getPartName());
        if (optionalPart.isPresent()) {
            return "Invalid part";
        }

        Part part = this.modelMapper.map(dto, Part.class);

        this.partRepository.save(part);

        return String.format("Successfully imported part %s - %.2f", part.getPartName(), part.getPrice());
    }
}
