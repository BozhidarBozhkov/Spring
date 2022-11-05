package entities;

import javax.persistence.*;

@Entity
public class Diagnose extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String comments;


    public Diagnose() {
    }

    public Diagnose(String name, String comments) {
        this.name = name;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
