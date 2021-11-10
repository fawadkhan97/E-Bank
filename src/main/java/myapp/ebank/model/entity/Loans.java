package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Loans implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = "amount cannot be blank ")
    @Column(name = "loanAmount",nullable = false)
    private Double loanAmount;
    @Column(name = "description",nullable = false)
    private String description;
    @Column(name = "paidstatus")
    private Boolean paidStatus;
    @Column(name = "dueDate")
    private Date dueDate;
    @Column(name = "insterestRate")
    private Double interestRate;
    @Column(name = "totalAmountToBePaid")
    private Double totalAmountToBePaid;
    @Column(name = "amountPaid")
    private Double amountPaid;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "paidDate")
    private Date paidDate;
    @Column(name = "isActive")
    private boolean isActive;

}
