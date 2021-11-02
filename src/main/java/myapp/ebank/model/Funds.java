package myapp.ebank.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Funds implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private Double amount;
    private String description;
}
