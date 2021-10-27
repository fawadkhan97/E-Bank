package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ForeignExchangeRates {
	
	
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "currency")
	private String currency;
	@Column(name = "symbol")
	private String symbol;
	@Column(name = "buying")
	private Double buying;
	@Column(name = "selling")
	private Double selling;
	@Column
	private String Date;
}
