package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity{

    @Size(min = 2, max = 40)
    @Column(nullable = false)
    private String title;

    @Min(300)
    @Column(nullable = false)
    private Double salary;

    @Min(100)
    @Column(name = "hoursaweek", nullable = false)
    private Double hoursAWeek;

    @Size(min = 5)
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne
    private Company company;

}
