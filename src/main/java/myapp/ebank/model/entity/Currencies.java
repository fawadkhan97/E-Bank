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
public class Currencies implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "value", unique = true)
    private String value;
    @Column(name = "dimensions")
    private String dimensions;
    @Column(name = "maincolor")
    private String mainColor;
    @Column(name = "note_description")
    private String noteDescription;
    @Column(name = "issuedDate")
    private Date issuedDate;
    @Column(name = "updatedDate")
    private java.util.Date updatedDate;
    @Column(name = "isActive")
    private Boolean isActive;

}
