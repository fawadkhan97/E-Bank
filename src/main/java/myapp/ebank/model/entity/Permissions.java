package myapp.ebank.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Permissions {
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true, nullable = false)
	private String name;
	private Date CreatedDate;
	private Date UpdatedDate;
	private boolean isActive;
}
