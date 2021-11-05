package myapp.ebank.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Loans implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "loanAmount")
    private Double loanAmount;
    @Column(name = "description")
    private String description;
    @Column(name = "paidstatus")
    private Boolean paidStatus;
    @Column(name = "date")
    private Date date;
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

}
