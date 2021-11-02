package myapp.ebank.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
@Data
public class InterestRates implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "interestRate")
    private Double interestRate;
    @Column(name = "interBankRate")
    private Double interBankRate;
    @Column(name = "sbpOvernightRepoFloorRate")
    private Double sbpOvernightRepoFloorRate;
    @Column(name = "m2mRevaluationRate")
    private Double m2mRevaluationRate;

    @Column(name = "date", unique = true, nullable = false)
    private String date;

}
