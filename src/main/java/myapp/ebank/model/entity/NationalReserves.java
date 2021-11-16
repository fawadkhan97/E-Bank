package myapp.ebank.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "nationalreservesrates",
        indexes = {
                @Index(name = "date_index", columnList = "createdDate"),
                @Index(name = "isActive_index", columnList = "isActive")})

public class NationalReserves implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @NotNull(message = "ForeignReserves is mandatory")
    @Column(name = "ForeignReserves")
    private Double foreignReserves;
    @NotNull(message = "GoldReserves is mandatory")
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
