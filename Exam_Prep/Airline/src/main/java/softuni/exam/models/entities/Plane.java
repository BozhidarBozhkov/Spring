package softuni.exam.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planes")
public class Plane extends BaseEntity{

    @Column(name = "register_number", nullable = false, unique = true)
    private String registerNumber;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String airline;
}
