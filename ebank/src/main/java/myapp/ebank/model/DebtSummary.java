package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class DebtSummary{
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "totalDebtandLiabilities")
	private Double  totalDebtandLiabilities;
	@Column(name = "GrossPublciDebt")
	private Double grossPublicDebt;
	@Column(name = "TotalGovernmentDebt")
	private Double  totalGovernmentDebt;
	@Column(name = "TotalExternalDebtAndLiabilities")
	private Double  totalExternalDebtAndLiabilities;
	@Column(name = "commodityOperationAndPSEDebt")
	private Double  commodityOperationAndPSEDebt;
	@Column(name = "FiscalYear")
	private Double  fiscalYear;
	
	
	
	
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Double getTotalDebtandLiabilities() {
		return totalDebtandLiabilities;
	}
	public void setTotalDebtandLiabilities(Double totalDebtandLiabilities) {
		this.totalDebtandLiabilities = totalDebtandLiabilities;
	}
	public Double getGrossPublicDebt() {
		return grossPublicDebt;
	}
	public void setGrossPublicDebt(Double grossPublicDebt) {
		this.grossPublicDebt = grossPublicDebt;
	}
	public Double getTotalGovernmentDebt() {
		return totalGovernmentDebt;
	}
	public void setTotalGovernmentDebt(Double totalGovernmentDebt) {
		this.totalGovernmentDebt = totalGovernmentDebt;
	}
	public Double getTotalExternalDebtAndLiabilities() {
		return totalExternalDebtAndLiabilities;
	}
	public void setTotalExternalDebtAndLiabilities(Double totalExternalDebtAndLiabilities) {
		this.totalExternalDebtAndLiabilities = totalExternalDebtAndLiabilities;
	}
	public Double getCommodityOperationAndPSEDebt() {
		return commodityOperationAndPSEDebt;
	}
	public void setCommodityOperationAndPSEDebt(Double commodityOperationAndPSEDebt) {
		this.commodityOperationAndPSEDebt = commodityOperationAndPSEDebt;
	}
	public Double getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(Double fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	
	
	
	
	
	
	
	
}
