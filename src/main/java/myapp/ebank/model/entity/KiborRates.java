package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class KiborRates implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = "bid rate is mandatory")
    @Column(name = "bid")
    private Double bid;
    @NotNull(message = "offer rate is mandatory")
    @Column(name = "offer")
    private Double offer;
    @NotNull(message = "interest rates createdDate is mandatory")
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "isActive")
    private boolean isActive;


}
