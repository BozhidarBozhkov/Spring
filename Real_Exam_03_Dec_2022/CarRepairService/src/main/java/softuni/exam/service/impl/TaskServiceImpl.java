package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TaskDto;
import softuni.exam.models.dto.TaskImportWrapperDto;
import softuni.exam.models.entity.*;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final Path path = Path.of("src/main/resources/files/xml/tasks.xml");
    private final TaskRepository taskRepository;
    private final MechanicRepository mechanicRepository;
    private final CarRepository carRepository;
    private final PartRepository partRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, MechanicRepository mechanicRepository, CarRepository carRepository, PartRepository partRepository) throws JAXBException {
        this.taskRepository = taskRepository;
        this.mechanicRepository = mechanicRepository;
        this.carRepository = carRepository;
        this.partRepository = partRepository;


        JAXBContext context = JAXBContext.newInstance(TaskImportWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();

        this.modelMapper.addConverter(ctx -> LocalDateTime.from(LocalDateTime.parse(ctx.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))),
                String.class, LocalDateTime.class);

    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/xml/tasks.xml");
        return Files.readString(path);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        TaskImportWrapperDto taskDtos = (TaskImportWrapperDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        return taskDtos.getTasks().stream()
                .map(this::importTask)
                .collect(Collectors.joining(System.lineSeparator()));
    }


    @Override
    public String getCoupeCarTasksOrderByPrice() {

        return this.taskRepository.findAllByCar_CarTypeOrderByPriceDesc(CarType.coupe).stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTask(TaskDto dto) {

        Set<ConstraintViolation<TaskDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()){
            return "Invalid task";
        }


        Optional<Car> optionalCar = this.carRepository.findById(dto.getCar().getId());
        Optional<Part> optionalPart = this.partRepository.findById(dto.getPart().getId());
        Optional<Mechanic> optionalMechanic = this.mechanicRepository.findByFirstName(dto.getMechanic().getFirstName());


        Optional<Task> byMechanicFirstName = this.taskRepository.findByMechanicFirstName(dto.getMechanic().getFirstName());
        boolean isValid = byMechanicFirstName.isPresent() ||  optionalCar.isEmpty() || optionalPart.isEmpty() || optionalMechanic.isEmpty();
        if (isValid){
            return "Invalid task";
        }

        Task task = this.modelMapper.map(dto, Task.class);

        task.setCar(optionalCar.get());
        task.setMechanic(optionalMechanic.get());
        task.setPart(optionalPart.get());

        this.taskRepository.save(task);

        return String.format("Successfully imported task %.2f", task.getPrice());
    }
}
