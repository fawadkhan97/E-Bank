package myapp.ebank.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class User {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String deptNameString;
	

}
