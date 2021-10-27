package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class IssuedCurrency {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "value")
	private String value;
	@Column(name = "dimensions")
	private String dimensions;
	@Column(name = "maincolor")
	private String mainColor;
}
