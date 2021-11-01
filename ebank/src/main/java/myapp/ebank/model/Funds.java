package myapp.ebank.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Funds {

    @Id
    @GeneratedValue
    private long id;
    private Double amount;
    private String description;


}
