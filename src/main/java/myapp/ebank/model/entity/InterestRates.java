package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data
public class InterestRates implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = "interestRate is mandatory")
    @Column(name = "interestRate")
    private Double interestRate;
    @NotNull(message = "interBankRate is mandatory")
    @Column(name = "interBankRate")
    private Double interBankRate;
    @NotNull(message = "sbpOvernightRepoFloorRate is mandatory")
    @Column(name = "sbpOvernightRepoFloorRate")
    private Double sbpOvernightRepoFloorRate;
    @NotNull(message = "m2mRevaluationRate is mandatory")
    @Column(name = "m2mRevaluationRate")
    private Double m2mRevaluationRate;
    @NotNull(message = "created date is mandatory")
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
}
