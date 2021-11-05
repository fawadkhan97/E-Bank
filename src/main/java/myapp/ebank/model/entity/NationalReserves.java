package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class NationalReserves implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "ForeignReserves")
    private String foreignReserves;
    @Column(name = "GoldReserves")
    private String goldReserves;
    @Column(name = "date")
    private String date;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
}
