package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PersonImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    private final Path path = Path.of("src/main/resources/files/json/people.json");

    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, CountryRepository countryRepository) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        String json = this.readPeopleFromFile();
        PersonImportDto[] personImportDtos = this.gson.fromJson(json, PersonImportDto[].class);

        return Arrays.stream(personImportDtos).map(this::importPerson)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPerson(PersonImportDto dto) {
        Set<ConstraintViolation<PersonImportDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()){
            return "Invalid person";
        }

        Optional<Person> optionalPerson = this.personRepository.findByFirstNameOrEmailOrPhone(dto.getFirstName(), dto.getEmail(), dto.getPhone());
        if (optionalPerson.isPresent()){
            return "Invalid person";
        }

        Person person = this.modelMapper.map(dto, Person.class);
        Country country = this.countryRepository.getById(dto.getCountry());

        person.setCountry(country);
        this.personRepository.saveAndFlush(person);

        return String.format("Successfully imported person %s %s", person.getFirstName(), person.getLastName());
    }
}
