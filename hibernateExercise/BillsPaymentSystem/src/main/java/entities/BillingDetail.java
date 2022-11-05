package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    private String number;


    @ManyToOne
    private User owner;


    public BillingDetail(String number, User owner) {
        this.number = number;
        this.owner = owner;
    }

    public BillingDetail() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
