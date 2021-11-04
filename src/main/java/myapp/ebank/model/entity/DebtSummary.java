package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class DebtSummary  implements Serializable {
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "totalDebtandLiabilities")
	private Double totalDebtandLiabilities;
	@Column(name = "GrossPublciDebt")
	private Double grossPublicDebt;
	@Column(name = "TotalGovernmentDebt")
	private Double totalGovernmentDebt;
	@Column(name = "TotalExternalDebtAndLiabilities")
	private Double totalExternalDebtAndLiabilities;
	@Column(name = "commodityOperationAndPSEDebt")
	private Double commodityOperationAndPSEDebt;
	@Column(name = "FiscalYear")
	private Double fiscalYear;
}
