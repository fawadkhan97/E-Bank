package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class NationalReserves {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "ForeignReserves")
	private String foreignReserves;
	@Column(name = "GoldReserves")
	private String goldReserves;
	
	
	

}
