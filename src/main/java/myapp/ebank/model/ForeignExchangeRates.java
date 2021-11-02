package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getBuying() {
        return buying;
    }

    public void setBuying(Double buying) {
        this.buying = buying;
    }

    public Double getSelling() {
        return selling;
    }

    public void setSelling(Double selling) {
        this.selling = selling;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
