package myapp.ebank.Entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class DailyRates {

	@Id
@GeneratedValue
@Column(name = "id")	
private String id;
	private String interestRate;
	private String KIBOR;
	private String Date;
	
}
