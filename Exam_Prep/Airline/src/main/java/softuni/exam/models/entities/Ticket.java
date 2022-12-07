package softuni.exam.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity{

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime takeoff;

    @ManyToOne(optional = false)
    private Plane plane;

    @ManyToOne(optional = false)
    private Passenger passenger;

    @ManyToOne(optional = false)
    private Town fromTown;

    @ManyToOne(optional = false)
    private Town toTown;

}
