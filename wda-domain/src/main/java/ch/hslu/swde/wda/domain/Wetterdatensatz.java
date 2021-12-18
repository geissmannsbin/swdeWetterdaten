package ch.hslu.swde.wda.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;


@Entity
public class Wetterdatensatz implements Serializable, Comparable<Wetterdatensatz> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5448468903540627538L;
	@Id
	@GeneratedValue
	private int id;
	private float temperatur;
	private float feuchtigkeit;
	private float luftdruck;
	private Date datum;
	@ManyToOne
	private Ortschaft ortschaft;
	
	public Wetterdatensatz() {
		
	}
	
	public Wetterdatensatz(float temperatur, float feuchtigkeit, Date datum, Ortschaft ortschaft, float luftdruck) {
		this.temperatur = temperatur;
		this.feuchtigkeit = feuchtigkeit;
		this.luftdruck = luftdruck;
		this.datum = datum;
		this.ortschaft = ortschaft;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public float getTemperatur() {
		return this.temperatur;
	}
	
	public void setTemperatur(float temperatur) {
		this.temperatur = temperatur;
	}
	
	public void setLuftdruck(float luftdruck) {
		this.luftdruck = luftdruck;
	}
	
	public float getLuftdruck() {
		return this.luftdruck;
	}
	
	public float getFeuchtigkeit() {
		return this.feuchtigkeit;
	}
	
	public void setFeuchtigkeit(float feuchtigkeit) {
		this.feuchtigkeit = feuchtigkeit;
	}
	
	public Ortschaft getOrtschaft() {
		return this.ortschaft;
	}
	
	public void setOrtschaft(Ortschaft ortschaft) {
		this.ortschaft = ortschaft;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Wetterdatensatz other = (Wetterdatensatz) obj;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (Float.floatToIntBits(feuchtigkeit) != Float.floatToIntBits(other.feuchtigkeit))
			return false;
		if (id != other.id)
			return false;
		if (ortschaft == null) {
			if (other.ortschaft != null)
				return false;
		} else if (!ortschaft.equals(other.ortschaft))
			return false;
		if (Float.floatToIntBits(temperatur) != Float.floatToIntBits(other.temperatur))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + Float.floatToIntBits(feuchtigkeit);
		result = prime * result + id;
		result = prime * result + ((ortschaft == null) ? 0 : ortschaft.hashCode());
		result = prime * result + Float.floatToIntBits(temperatur);
		return result;
	}

	@Override
	public int compareTo(Wetterdatensatz o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		return "Wetterdatensatz [id=" + id + ", temperatur=" + temperatur + ", feuchtigkeit=" + feuchtigkeit
				+ ", datum=" + datum + ", ortschaft=" + ortschaft + ", luftdruck=" + luftdruck + "]";
	}
}
