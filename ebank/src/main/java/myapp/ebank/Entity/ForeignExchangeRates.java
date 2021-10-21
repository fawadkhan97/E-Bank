package myapp.ebank.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GeneratorType;

@Entity
public class ForeignExchangeRates {
	@Id
	@GeneratedValue
	private long id;
	private String currency;
	private Double buying;
	private Double selling;
}
