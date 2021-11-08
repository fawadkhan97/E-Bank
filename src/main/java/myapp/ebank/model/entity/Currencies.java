package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Currencies implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "currency value is mandatory")
    @Column(name = "value", unique = true)
    private String value;
    @NotBlank(message = "currency dimensions is mandatory")
    @Column(name = "dimensions")
    private String dimensions;
    @NotBlank(message = "maincolor is mandatory")
    @Column(name = "maincolor")
    private String mainColor;
    @NotBlank(message = "note_description is mandatory")
    @Column(name = "note_description")
    private String noteDescription;
    @NotBlank(message = "currency issuedDate is mandatory")
    @Column(name = "issuedDate")
    private Date issuedDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "isActive")
    private Boolean isActive;

}
