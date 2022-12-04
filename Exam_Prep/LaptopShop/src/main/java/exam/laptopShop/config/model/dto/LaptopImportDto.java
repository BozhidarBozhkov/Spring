package exam.laptopShop.config.model.dto;

import exam.laptopShop.config.model.entities.WarrantyType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;

public class LaptopImportDto {

    @Size(min = 8)
    private String macAddress;

    @Positive
    private Double cpuSpeed;

    @Min(8)
    @Max(128)
    private Integer ram;

    @Min(128)
    @Max(1024)
    private Integer storage;

    @Size(min = 10)
    private String description;

    @Positive
    private BigDecimal price;

    @NotNull
    private WarrantyType warrantyType;

    @NotNull
    private NameDto shop;

    public LaptopImportDto() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public NameDto getShop() {
        return shop;
    }
}
