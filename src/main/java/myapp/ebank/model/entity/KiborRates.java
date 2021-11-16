package myapp.ebank.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "kiborrates",
        indexes = {
                @Index(name = "date_index", columnList = "created_date"),
                @Index(name = "isActive_index", columnList = "isActive")})

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
