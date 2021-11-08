package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class KiborRates implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "date is mandatory")
    @Column(name = "date", unique = true, nullable = false)
    private Date date;
    @NotBlank(message = "bid rate is mandatory")
    @Column(name = "bid")
    private Double bid;
    @NotBlank(message = "offer rate is mandatory")
    @Column(name = "offer")
    private Double offer;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;

}
