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
}
