package model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;


@Setter
@Getter
@Entity
public abstract class PaymentDetails {
    @Id
    private Long id;
    private String name;

}
