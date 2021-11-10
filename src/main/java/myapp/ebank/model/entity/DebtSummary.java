package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class DebtSummary implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = "{DebtSummary.totalDebtAndLiabilities.invalid}")
    @Column(name = "totalDebtandLiabilities")
    private Double totalDebtAndLiabilities;
    @NotNull(message = "{DebtSummary.grossPublicDebt.invalid}")
    @Column(name = "grossPublciDebt")
    private Double grossPublicDebt;
    @NotNull(message = "{DebtSummary.totalGovernmentDebt.invalid}")
    @Column(name = "totalGovernmentDebt")
    private Double totalGovernmentDebt;
    @NotNull(message = "{DebtSummary.totalExternalDebtAndLiabilities.invalid}")
    @Column(name = "TotalExternalDebtAndLiabilities")
    private Double totalExternalDebtAndLiabilities;
    @NotNull(message = "{DebtSummary.commodityOperationAndPSEDebt.invalid}")
    @Column(name = "commodityOperationAndPSEDebt")
    private Double commodityOperationAndPSEDebt;
    @NotBlank(message = "{DebtSummary.fiscalYear.invalid}")
    @Column(name = "fiscalYear")
    private String fiscalYear;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;

}
