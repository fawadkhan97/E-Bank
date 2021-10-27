package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class KiborRates {
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "date")
	private String date;
	@Column(name = "bid")
	private String bid;
	@Column(name = "offer")
	private String offer;
}
