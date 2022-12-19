package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
public class Company extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String website;

    //"yyyy-MM-dd" format
    @Column(name = "date_established", nullable = false)
    private LocalDate dateEstablished;

    @OneToMany
    private List<Job> jobs;

    @OneToOne
    private Country country;

}
