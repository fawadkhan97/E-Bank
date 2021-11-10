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

@Entity
@Data
public class NationalReserves implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "ForeignReserves is mandatory")
    @Column(name = "ForeignReserves")
    private Double foreignReserves;
    @NotBlank(message = "GoldReserves is mandatory")
    @Column(name = "GoldReserves")
    private Double goldReserves;
    @NotNull(message = "National Reserves rates created date is mandatory")
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Column(name = "isActive")
    private boolean isActive;

}
