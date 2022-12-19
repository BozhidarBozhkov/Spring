package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CompanyDto;
import softuni.exam.models.dto.CompanyImportWrapperDto;
import softuni.exam.models.entity.Company;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;

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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final Path path = Path.of("src/main/resources/files/xml/companies.xml");

    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;

    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, CountryRepository countryRepository) throws JAXBException {
        this.companyRepository = companyRepository;
        this.countryRepository = countryRepository;

        JAXBContext context = JAXBContext.newInstance(CompanyImportWrapperDto.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();

        this.modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                String.class, LocalDate.class);
    }

    @Override
    public boolean areImported() {
        return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        CompanyImportWrapperDto companyDtos = (CompanyImportWrapperDto) this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        return companyDtos.getCompanies().stream()
                .map(this::importCompany)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCompany(CompanyDto dto) {
        Set<ConstraintViolation<CompanyDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()){
            return "Invalid company";
        }

        Optional<Company> optionalCompany = this.companyRepository.findByName(dto.getName());
        if (optionalCompany.isPresent()){
            return "Invalid company";
        }

        Company company = this.modelMapper.map(dto, Company.class);
        Optional<Country> optionalCountry = this.countryRepository.findById(dto.getCountry());

        if (optionalCountry.isPresent()){
            company.setCountry(optionalCountry.get());
            this.companyRepository.saveAndFlush(company);

        }

        return String.format("Successfully imported company %s - %d", company.getName(), company.getCountry().getId());
    }
}
