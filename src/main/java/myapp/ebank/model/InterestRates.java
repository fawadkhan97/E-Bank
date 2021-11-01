package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;


@Entity
@Data
public class InterestRates {
	
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
	
	@Column(name = "date",unique = true,nullable = false)
	private String date;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	public Double getInterBankRate() {
		return interBankRate;
	}
	public void setInterBankRate(Double interBankRate) {
		this.interBankRate = interBankRate;
	}
	public Double getSbpOvernightRepoFloorRate() {
		return sbpOvernightRepoFloorRate;
	}
	public void setSbpOvernightRepoFloorRate(Double sbpOvernightRepoFloorRate) {
		this.sbpOvernightRepoFloorRate = sbpOvernightRepoFloorRate;
	}
	public Double getM2mRevaluationRate() {
		return m2mRevaluationRate;
	}
	public void setM2mRevaluationRate(Double m2mRevaluationRate) {
		this.m2mRevaluationRate = m2mRevaluationRate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	

}
