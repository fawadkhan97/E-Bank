package myapp.ebank.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Data
public class Organizations implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String type;
    private Date createdDate;
    private Date updatedDate;
    private Boolean isActive;
}
