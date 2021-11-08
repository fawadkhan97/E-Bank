package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Funds implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "funds amount is mandatory")
    private Double amount;
    @NotBlank(message = "funds description is mandatory is mandatory")
    private String description;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
}
