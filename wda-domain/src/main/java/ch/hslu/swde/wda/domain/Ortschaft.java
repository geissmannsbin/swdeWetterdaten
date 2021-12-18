package ch.hslu.swde.wda.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
public class Ortschaft implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2884632669736330470L;
	
	private int plz;
	@Id
	@GeneratedValue
	private int id;
	private String name;
	
	public Ortschaft() {
		
	}
	
	public Ortschaft (int plz, String name) {
		this.plz = plz;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPlz() {
		return this.plz;
	}
	
	public void setPlz(int plz) {
		this.plz = plz;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Ortschaft [plz=" + plz + ", id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + plz;
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
		Ortschaft other = (Ortschaft) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (plz != other.plz)
			return false;
		return true;
	} 
}
