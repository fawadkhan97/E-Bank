package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;


@Entity
@Data
public class FinancialMarketRates {
	
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
	

}
