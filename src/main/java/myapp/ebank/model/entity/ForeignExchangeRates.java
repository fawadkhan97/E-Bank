package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;


@Entity
@Data
public class ForeignExchangeRates implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "currency")
    private String currency;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "buying")
    private Double buying;
    @Column(name = "selling")
    private Double selling;
    @Column(name = "date")
    private String date;
    
}
