package cz.jares.pavel.dto.geoname;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jaresp
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class City {

	@JsonProperty("lng")
	private BigDecimal longitude;
	
	@JsonProperty("lat")
	private BigDecimal latitude;
	
	@JsonProperty("countryName")
	private String country;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("population")
	private Long population;

	public City() {
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}
	
}
