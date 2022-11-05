package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "medicaments")
    private List<Diagnose> diagnoses;

    public Medicament() {
    }

    public Medicament(String name) {
        this.name = name;
        this.diagnoses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
