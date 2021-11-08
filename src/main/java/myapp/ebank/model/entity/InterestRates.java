package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data
public class InterestRates implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "interestRate is mandatory")
    @Column(name = "interestRate")
    private Double interestRate;
    @NotBlank(message = "interBankRate is mandatory")
    @Column(name = "interBankRate")
    private Double interBankRate;
    @NotBlank(message = "sbpOvernightRepoFloorRate is mandatory")
    @Column(name = "sbpOvernightRepoFloorRate")
    private Double sbpOvernightRepoFloorRate;
    @NotBlank(message = "m2mRevaluationRate is mandatory")
    @Column(name = "m2mRevaluationRate")
    private Double m2mRevaluationRate;
    @NotBlank(message = "interest rates date is mandatory")
    @Column(name = "date", unique = true, nullable = false)
    private String date;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
}
