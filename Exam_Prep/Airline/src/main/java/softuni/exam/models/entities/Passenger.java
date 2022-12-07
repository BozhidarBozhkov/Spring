package softuni.exam.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "passengers")
public class Passenger extends BaseEntity{

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne(optional = false)
    private Town town;

    @OneToMany(mappedBy = "passenger", targetEntity = Ticket.class, fetch = FetchType.EAGER)
    private List<Ticket> tickets;

    public Passenger() {
        this.tickets = new ArrayList<>();
    }

    public Passenger(String firstName, String lastName, Integer age, String phoneNumber, String email, Town town) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.town = town;
    }


    @Override
    public String toString() {
        return String.format("Passenger %s  %s\n"
        + "\tEmail - %s\n"
        + "\tPhone - %s\n"
        + "\tNumber of tickets - %d",
          this.firstName, this.lastName,
              this.email,
                this.phoneNumber,
                this.tickets.size());
    }
}
