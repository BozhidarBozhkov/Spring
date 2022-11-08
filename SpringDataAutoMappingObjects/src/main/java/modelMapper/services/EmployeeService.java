package modelMapper.services;

import modelMapper.entities.Employee;
import modelMapper.entities.dto.CreateEmployeeDTO;
import modelMapper.entities.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    Employee create(CreateEmployeeDTO employeeDTO);

    List<EmployeeDTO> findAll();
}
