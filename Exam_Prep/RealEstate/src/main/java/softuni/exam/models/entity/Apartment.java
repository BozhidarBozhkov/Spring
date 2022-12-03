package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity{
    @Column(name = "apartment_type", nullable = false)
    private ApartmentType apartmentType;

    @Column(nullable = false)
    private Double area;

    @ManyToOne
    private Town town;


    public Apartment() {}

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }


}
