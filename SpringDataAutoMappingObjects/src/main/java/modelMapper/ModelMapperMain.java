package modelMapper;

import modelMapper.entities.Address;
import modelMapper.entities.dto.EmployeeDTO;
import modelMapper.entities.Employee;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//@Component
public class ModelMapperMain implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        //  System.out.println("Test");

        ModelMapper mapper = new ModelMapper();

//        PropertyMap<Employee, EmployeeDTO> propertyMap = new PropertyMap<>() {
//            @Override
//            protected void configure() {
//                map().setCity(source.getAddress().getCity());
//            }
//        };
//
//        mapper.addMappings(propertyMap);
//        mapper.validate();

        TypeMap<Employee, EmployeeDTO> typeMap = mapper.createTypeMap(Employee.class, EmployeeDTO.class);
        typeMap.addMappings(mapping -> mapping.map(source -> source.getAddress().getCity(),
                EmployeeDTO::setCity));

        typeMap.validate();

        Address address = new Address("Bulgaria", "Sofia");
        Employee employee = new Employee("Ivan", BigDecimal.TEN, address);

        EmployeeDTO employeeDTO = typeMap.map(employee);

        System.out.println(employeeDTO);
    }
}
