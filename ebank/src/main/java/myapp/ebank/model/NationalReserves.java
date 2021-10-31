package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class NationalReserves {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "ForeignReserves")
    private String foreignReserves;
    @Column(name = "GoldReserves")
    private String goldReserves;
    @Column(name = "date")
    private String date;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getForeignReserves() {
        return foreignReserves;
    }

    public void setForeignReserves(String foreignReserves) {
        this.foreignReserves = foreignReserves;
    }

    public String getGoldReserves() {
        return goldReserves;
    }

    public void setGoldReserves(String goldReserves) {
        this.goldReserves = goldReserves;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
