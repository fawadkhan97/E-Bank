package myapp.ebank.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "permissions",
		indexes = {
				@Index(name = "date_index", columnList = "createdDate"),
				@Index(name = "isActive_index", columnList = "isActive")})

public class Permissions {
	@Id
	@GeneratedValue
	private long id;
	@NotBlank(message = "role_name is mandatory")
	@Column(unique = true, nullable = false)
	private String name;
	@Column(name = "isActive")
	private boolean isActive;
	@Column(name = "createdDate")
	private Date createdDate;
	@Column(name = "updatedDate")
	private Date updatedDate;
}
