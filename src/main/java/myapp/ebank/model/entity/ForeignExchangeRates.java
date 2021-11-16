package myapp.ebank.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Entity
@Data
@Table(name = "foreignexchangerates",
        indexes = {
                @Index(name = "date_index", columnList = "createdDate"),
                @Index(name = "isActive_index", columnList = "isActive")})
public class ForeignExchangeRates implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "currency is mandatory")
    @Column(name = "currency")
    private String currency;
    @NotBlank(message = "symbol is mandatory")
    @Column(name = "symbol")
    private String symbol;
    @NotNull(message = "buying rate is mandatory")
    @Column(name = "buying")
     private Double buying;
    @NotNull(message = "selling rate is mandatory")
    @Column(name = "selling")
     private Double selling;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "isActive")
    private boolean isActive;

}
