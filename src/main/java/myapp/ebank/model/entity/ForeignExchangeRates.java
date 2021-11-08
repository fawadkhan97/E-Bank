package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Entity
@Data
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
    @NotBlank(message = "buying rate is mandatory")
    @Column(name = "buying")
    private Double buying;
    @NotBlank(message = "selling rate is mandatory")
    @Column(name = "selling")
    private Double selling;
    @Column(name = "date")
    private Date date;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
}
