package myapp.ebank.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Permissions implements Serializable {
	@Id
	@GeneratedValue
	private long id;
	private String permission;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	

}
