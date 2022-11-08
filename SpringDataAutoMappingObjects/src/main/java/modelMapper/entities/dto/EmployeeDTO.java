package modelMapper.entities.dto;

import java.math.BigDecimal;

public class EmployeeDTO {

    private String firstName;

    private BigDecimal salary;

    private String addressCity;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String addressCity() {
        return addressCity;
    }

    public void setCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public EmployeeDTO() {
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "firstName='" + firstName + '\'' +
                ", salary=" + salary +
                ", addressCity='" + addressCity + '\'' +
                '}';
    }
}
