package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class KiborRates implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "date", unique = true, nullable = false)
    private Date date;
    @Column(name = "bid")
    private String bid;
    @Column(name = "offer")
    private String offer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }


}
