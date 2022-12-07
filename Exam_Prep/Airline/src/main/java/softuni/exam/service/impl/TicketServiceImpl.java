package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TicketImportDto;
import softuni.exam.models.dto.TicketWrapperDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final Path path = Path.of("src/main/resources/files/xml/tickets.xml");

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final PlaneRepository planeRepository;
    private final TownRepository townRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, PassengerRepository passengerRepository, PlaneRepository planeRepository, TownRepository townRepository) throws JAXBException {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
        this.townRepository = townRepository;


        JAXBContext context = JAXBContext.newInstance(TicketWrapperDto.class);
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
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/xml/tickets.xml");
        return Files.readString(path);
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        TicketWrapperDto ticketDtos = (TicketWrapperDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        return ticketDtos.getTickets().stream().map(this::importTicket).collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTicket(TicketImportDto dto) {

        Set<ConstraintViolation<TicketImportDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()){
            return "Invalid ticket";
        }

        Optional<Ticket> optionalTicket = this.ticketRepository.findBySerialNumber(dto.getSerialNumber());
        Optional<Passenger> optionalPassenger = this.passengerRepository.findByEmail(dto.getPassenger().getEmail());
        Optional<Plane> optionalPlane = this.planeRepository.findByRegisterNumber(dto.getPlane().getRegisterNumber());
        Optional<Town> fromTown = this.townRepository.findByName(dto.getFromTown().getName());
        Optional<Town> toTown = this.townRepository.findByName(dto.getToTown().getName());

        boolean isValid = optionalTicket.isPresent() || optionalPassenger.isEmpty() || optionalPlane.isEmpty() || fromTown.isEmpty() || toTown.isEmpty();

        if (isValid){
            return "Invalid ticket";
        }

        Ticket ticket = this.modelMapper.map(dto, Ticket.class);
        ticket.setPassenger(optionalPassenger.get());
        ticket.setPlane(optionalPlane.get());
        ticket.setFromTown(fromTown.get());
        ticket.setToTown(toTown.get());

        this.ticketRepository.save(ticket);

        return String.format("Successfully imported Ticket %s - %s", ticket.getFromTown().getName(), ticket.getToTown().getName());
    }
}
