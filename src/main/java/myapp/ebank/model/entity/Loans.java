package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "loan",
        indexes = {
                @Index(name = "date_index", columnList = "createdDate"),
                @Index(name = "isActive_index", columnList = "isActive")})

public class Loans implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = "amount cannot be blank ")
    @Column(name = "loanAmount",nullable = false)
    private Double loanAmount;
    @NotBlank(message = "description cannot be blank ")
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
    @Min(value=0, message = "amount paid  should be equal to total Amount To Be Paid")
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
