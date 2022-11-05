package entities;

import javax.persistence.*;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "diagnose_medicaments", joinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"))
    private Diagnose diagnose;



    public Medicament() {
    }

    public Medicament(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }
}
