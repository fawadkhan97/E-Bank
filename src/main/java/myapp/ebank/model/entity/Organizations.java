package myapp.ebank.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Data
@Table(name = "organizations",
        indexes = {
                @Index(name = "date_index", columnList = "createdDate"),
                @Index(name = "isActive_index", columnList = "isActive")})

public class Organizations implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @NotBlank(message = "organization Name is mandatory")
    @Column(unique = true,nullable = false)
    private String name;
    @NotBlank(message = "organization type is mandatory")
    private String type;
    @Column(name = "isActive")
    private boolean isActive;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "updatedDate")
    private Date updatedDate;
}
