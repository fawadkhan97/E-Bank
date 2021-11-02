package myapp.ebank.model;

import lombok.Data;

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
    private Double loanAmount;
    private String description;
    private Boolean paidStatus;
    private Date date;
    private Date dueDate;
    private Double interestRate;
    private Double totalAmountToBePaid;
    private Double amountPaid;
}
