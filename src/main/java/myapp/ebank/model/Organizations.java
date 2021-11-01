package myapp.ebank.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Organizations {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String type;
    private String createdDate;
    private String updatedDate;
    private Boolean isActive;
}
