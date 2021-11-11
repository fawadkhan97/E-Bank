package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Funds implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = "funds amount cannot be blank")
    @Column(nullable = false)
     private Double amount;
    @NotBlank(message = "funds description is mandatory is mandatory")
    private String description;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "aprrovedStatus")
    private Boolean approvedStatus;
    @Column(name = "isActive")
    private boolean isActive;

}
