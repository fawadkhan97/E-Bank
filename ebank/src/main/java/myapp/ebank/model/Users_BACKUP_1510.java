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
	@Column(name = "userName", unique = true)
	private String userName;
	@Column(name = "PhoneNumber", unique = true)
	private String phoneNumber;
	@Column(name = "email", unique = true)
	private String email;
	@Column(name = "CNIC", unique = true)
	private String CNIC;
	@Column(name = "password")
	private String password;
	@Column(name = "userType")
	private String userType;
	@Column(name = "age")
	private int age;
<<<<<<< HEAD
	private String dob;
	private String createdDate;
	private String updatedDate;
	private boolean status;
	
=======
	@Column(name = "dob")
	private String DOB;
	@Column(name = "CreatedDate")
	private String CreatedDate;
	@Column(name = "UpdatedDate")
	private String UpdatedDate;
	@Column(name = "isActive")
	private boolean isActive;
	@Column(name = "emailToken")
	private int emailToken;
	@Column(name = "smsToken")
	private int smsToken;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCNIC() {
		return CNIC;
	}

	public void setCNIC(String cNIC) {
		CNIC = cNIC;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public String getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(int emailToken) {
		this.emailToken = emailToken;
	}

	public int getSmsToken() {
		return smsToken;
	}

	public void setSmsToken(int smsToken) {
		this.smsToken = smsToken;
	}
>>>>>>> development

}
