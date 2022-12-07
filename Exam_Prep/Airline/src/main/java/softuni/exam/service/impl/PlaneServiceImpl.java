package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PlaneDto;
import softuni.exam.models.dto.PlaneWrapperDto;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;

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
public class PlaneServiceImpl implements PlaneService {

    private final Path path = Path.of("src/main/resources/files/xml/planes.xml");
    private final PlaneRepository planeRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository) throws JAXBException {
        this.planeRepository = planeRepository;

        JAXBContext context = JAXBContext.newInstance(PlaneWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/xml/planes.xml");
        return Files.readString(path);
    }

    @Override
    public String importPlanes() throws FileNotFoundException, JAXBException {

        PlaneWrapperDto planeDtos = (PlaneWrapperDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        return planeDtos.getPlanes().stream().map(this::importPlane).collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPlane(PlaneDto planeDto) {

        Set<ConstraintViolation<PlaneDto>> errors = this.validator.validate(planeDto);
        if (!errors.isEmpty()) {
            return "Invalid plane";
        }

        Optional<Plane> optionalPlane = this.planeRepository.findByRegisterNumber(planeDto.getRegisterNumber());
        if (optionalPlane.isPresent()) {
            return "Invalid plane";
        }

        Plane plane = this.modelMapper.map(planeDto, Plane.class);
        this.planeRepository.save(plane);

        return String.format("Successfully imported Plane %s", plane.getRegisterNumber());
    }
}
