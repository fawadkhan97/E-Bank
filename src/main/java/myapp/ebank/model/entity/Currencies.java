package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Currencies implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = "currency value is mandatory")
    @Column(name = "value", unique = true)
    private int value;
    @NotBlank(message = "currency dimensions is mandatory")
    @Column(name = "dimensions")
    private String dimensions;
    @NotBlank(message = "maincolor is mandatory")
    @Column(name = "maincolor")
    private String mainColor;
    @NotBlank(message = "note_description is mandatory")
    @Column(name = "note_description")
    private String noteDescription;
    @NotNull(message = "currency createdDate is mandatory")
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "isActive")
    private Boolean isActive;

}
