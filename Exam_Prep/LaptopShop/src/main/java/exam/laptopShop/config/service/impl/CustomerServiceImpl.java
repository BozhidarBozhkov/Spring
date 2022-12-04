package exam.laptopShop.config.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.laptopShop.config.model.dto.ImportCustomerDto;
import exam.laptopShop.config.model.entities.Customer;
import exam.laptopShop.config.model.entities.Town;
import exam.laptopShop.config.repository.CustomerRepository;
import exam.laptopShop.config.repository.TownRepository;
import exam.laptopShop.config.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
        //27/06/2020
        this.modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.class, LocalDate.class);
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/json/customers.json");
        return Files.readString(path);
    }

    @Override
    public String importCustomers() throws IOException {
        String json = this.readCustomersFileContent();

        ImportCustomerDto[] customerDtos = this.gson.fromJson(json, ImportCustomerDto[].class);

        return Arrays.stream(customerDtos).map(this::importCustomer).collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCustomer(ImportCustomerDto dto) {

        Set<ConstraintViolation<ImportCustomerDto>> errors = this.validator.validate(dto);
        if (!errors.isEmpty()) {
            return "Invalid Customer";
        }

        Optional<Customer> optCustomer = this.customerRepository.findByEmail(dto.getEmail());
        if (optCustomer.isPresent()){
            return "Invalid Customer";
        }

        Customer customer = this.modelMapper.map(dto, Customer.class);
        Optional<Town> townByName = this.townRepository.findByName(customer.getTown().getName());

        customer.setTown(townByName.get());

        this.customerRepository.saveAndFlush(customer);

        return String.format("Successfully imported Customer %s %s - %s", customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }
}
