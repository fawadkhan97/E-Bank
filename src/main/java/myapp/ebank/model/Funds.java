package myapp.ebank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Funds {
    @Id
    @GeneratedValue
    private long id;
    private Double amount;
    private String description;
}
