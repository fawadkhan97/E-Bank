package myapp.ebank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Users {
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "fullName")
	private String fullName;
	@Column(name = "userName")
	private String userName;
	@Column(name = "email")
	private String email;
	@Column(name = "CNIC")
	private String CNIC;
	@Column(name = "password")
	private String password;
	@Column(name = "userType")
	private String userType;
	@Column(name = "age")
	private int age;
	private String DOB;
	private String CreatedDate;
	private String UpdatedDate;
	private boolean status;
	

}
