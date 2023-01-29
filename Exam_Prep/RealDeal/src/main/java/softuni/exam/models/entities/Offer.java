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
@Table(name = "offers")
public class Offer extends BaseEntity{

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column(name = "has_gold_status", nullable = false)
    private Short hasGoldStatus;

    @Column(name = "added_on", nullable = false)
    private LocalDateTime addedOn;

    @ManyToOne(optional = false)
    private Picture picture;

    @OneToOne
    private Seller seller;

}
