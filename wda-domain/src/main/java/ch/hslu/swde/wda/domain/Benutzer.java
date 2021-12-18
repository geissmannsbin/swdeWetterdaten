package ch.hslu.swde.wda.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
public class Benutzer implements Serializable, Comparable<Benutzer>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7433018818498165744L;
	private String role;
	@Id
	@GeneratedValue
	private int id;
	private String userName;
	private String email;
	private String password;
	
	public Benutzer() {
		
	}
	
	public Benutzer (String role, String userName, String email, String password) {
		this.role = role;
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	@Override
	public int compareTo(Benutzer o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Benutzer other = (Benutzer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [role=" + role + ", id=" + id + ", userName=" + userName + ", email=" + email + "]";
	}	
}
