package myapp.ebank.model.entity;

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
    private Double bid;
    @Column(name = "offer")
    private Double offer;


}
