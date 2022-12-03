package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportApartmentsWrapperDto;
import softuni.exam.models.dto.ImportOffersDto;
import softuni.exam.models.dto.ImportOffersWrapperDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.models.entity.ApartmentType.three_rooms;
import static softuni.exam.util.Constants.INVALID_OFFER;
import static softuni.exam.util.Constants.SUCCESSFULLY_IMPORTED_OFFER;
import static softuni.exam.util.Paths.OFFERS_XML_PATH;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;


    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository) throws JAXBException {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;

        JAXBContext context = JAXBContext.newInstance(ImportOffersWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();

        this.modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.class, LocalDate.class);
    }


    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        Path path = Path.of(OFFERS_XML_PATH);

        return Files.readString(path);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        FileReader fileReader = new FileReader(OFFERS_XML_PATH);

        ImportOffersWrapperDto importOffers = (ImportOffersWrapperDto) this.unmarshaller.unmarshal(fileReader);

        return importOffers.getOffers().stream().map(this::importOffer).collect(Collectors.joining(System.lineSeparator()));
    }

    private String importOffer(ImportOffersDto dto) {

        Set<ConstraintViolation<ImportOffersDto>> errors = this.validator.validate(dto);

        if (!errors.isEmpty()) {
            return INVALID_OFFER;
        }

        Optional<Agent> optAgent = this.agentRepository.findByFirstName(dto.getAgent().getName());
        if (optAgent.isEmpty()) {
            return INVALID_OFFER;
        }

        Optional<Apartment> optApartment = this.apartmentRepository.findById(dto.getApartment().getId());
        Offer offer = this.modelMapper.map(dto, Offer.class);


        offer.setAgent(optAgent.get());
        offer.setApartment(optApartment.get());

        this.offerRepository.save(offer);


        return String.format(SUCCESSFULLY_IMPORTED_OFFER, offer.getPrice());
    }


    @Override
    public String exportOffers() {

        return this.offerRepository.findByApartment_ApartmentTypeOrderByApartment_AreaDescPriceAsc(three_rooms)
                .orElseThrow(NoSuchElementException::new)
                .stream().map(Offer::toString).collect(Collectors.joining(System.lineSeparator()));
    }
}
