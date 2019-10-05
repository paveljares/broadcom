package cz.jares.pavel.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jaresp
 *
 */
public class Gps implements Serializable {

	private static final long serialVersionUID = 536888707917671027L;

	@JsonProperty("latitude")
	private BigDecimal latitude;
	
	@JsonProperty("longitude")
	private BigDecimal longitude;

	public Gps() {
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	
}
