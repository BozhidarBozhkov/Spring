package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PassengerImportDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;

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
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/json/passengers.json");
        return Files.readString(path);
    }

    @Override
    public String importPassengers() throws IOException {

        String json = this.readPassengersFileContent();

        PassengerImportDto[] importPassengers = this.gson.fromJson(json, PassengerImportDto[].class);

        return Arrays.stream(importPassengers).map(this::importPassenger).collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPassenger(PassengerImportDto dto) {

        Set<ConstraintViolation<PassengerImportDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()) {
            return "Invalid passenger";
        }

        Optional<Town> optionalTown = this.townRepository.findByName(dto.getTown());
        Optional<Passenger> optionalPassenger = this.passengerRepository.findByEmail(dto.getEmail());
        if (optionalTown.isPresent() && optionalPassenger.isPresent()) {
            return "Invalid passenger";
        }
        Passenger passenger = this.modelMapper.map(dto, Passenger.class);
        passenger.setTown(optionalTown.get());
        this.passengerRepository.save(passenger);

        return String.format("Successfully imported Passenger %s - %s", passenger.getLastName(), passenger.getEmail());
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {

        return this.passengerRepository.findAllByOrderByTicketsDescEmailAsc().stream().map(Passenger::toString).collect(Collectors.joining(System.lineSeparator()));

    }
}
