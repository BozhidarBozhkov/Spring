package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportTownDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static softuni.exam.util.Constants.INVALID_TOWN;
import static softuni.exam.util.Constants.SUCCESSFULLY_IMPORTED_TOWN;
import static softuni.exam.util.Paths.TOWN_JSON_PATH;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final Gson gson;

    private final Validator validator;

    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of(TOWN_JSON_PATH);

        return Files.readString(path);
    }

    @Override
    public String importTowns() throws IOException {

        String json = this.readTownsFileContent();

        ImportTownDto[] importTownDtos = this.gson.fromJson(json, ImportTownDto[].class);

        List<String> result = new ArrayList<>();

        for (ImportTownDto importTownDto : importTownDtos) {
            Set<ConstraintViolation<ImportTownDto>> errors = this.validator.validate(importTownDto);

            if (errors.isEmpty()){

                Optional<Town> optTown = this.townRepository.findByTownName(importTownDto.getTownName());

                if (optTown.isEmpty()) {

                    Town town = this.modelMapper.map(importTownDto, Town.class);

                    this.townRepository.saveAndFlush(town);

                    String msg = String.format(SUCCESSFULLY_IMPORTED_TOWN, town.getTownName(), town.getPopulation());
                    result.add(msg);
                } else {
                    return INVALID_TOWN;
                }
            } else {
                result.add(INVALID_TOWN);
            }
        }
        return String.join("\n", result);
    }
}
