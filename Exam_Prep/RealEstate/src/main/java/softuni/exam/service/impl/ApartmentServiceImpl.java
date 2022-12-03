package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportApartmentsDto;
import softuni.exam.models.dto.ImportApartmentsWrapperDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.util.Constants.INVALID_APARTMENT;
import static softuni.exam.util.Constants.SUCCESSFULLY_IMPORTED_APARTMENT;
import static softuni.exam.util.Paths.APARTMENTS_XML_PATH;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository) throws JAXBException {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;

        JAXBContext context = JAXBContext.newInstance(ImportApartmentsWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        Path path = Path.of(APARTMENTS_XML_PATH);

        return Files.readString(path);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {

        ImportApartmentsWrapperDto apartDtos = (ImportApartmentsWrapperDto) this.unmarshaller.unmarshal(new FileReader(APARTMENTS_XML_PATH.toString()));

        return apartDtos.getApartments().stream().map(this::importApartment).collect(Collectors.joining("\n"));

    }

    private String importApartment(ImportApartmentsDto dto) {
        Set<ConstraintViolation<ImportApartmentsDto>> errors = this.validator.validate(dto);

        if (!errors.isEmpty()) {
            return INVALID_APARTMENT;
        }

        Optional<Apartment> optApartment = this.apartmentRepository.findByTown_TownNameAndArea(dto.getTownName(), dto.getArea());

        if (optApartment.isPresent()) {
            return INVALID_APARTMENT;
        }

        Apartment apartment = this.modelMapper.map(dto, Apartment.class);

        Optional<Town> town = this.townRepository.findByTownName(dto.getTownName());

        apartment.setTown(town.get());

        this.apartmentRepository.save(apartment);

        return String.format(SUCCESSFULLY_IMPORTED_APARTMENT, apartment.getApartmentType(), apartment.getArea());
    }
}
