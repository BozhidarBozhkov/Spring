package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double salary;

    @Column(name = "hoursaweek", nullable = false)
    private Double hoursAWeek;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ManyToOne
    private Company company;

    @Override
    public String toString() {
        return String.format("Job title %s" + System.lineSeparator()
        + "-Salary: %.2f$" + System.lineSeparator()
        + "--Hours a week: %.2fh." + System.lineSeparator(), title, salary, hoursAWeek);
    }
}
