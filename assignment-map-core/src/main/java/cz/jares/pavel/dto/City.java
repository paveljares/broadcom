package cz.jares.pavel.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author jaresp
 *
 */
public class City implements Serializable {
	
	private static final long serialVersionUID = -6249998537710616773L;

	@JsonProperty("gps")
	private Gps gps;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("population")
	private Long population;
	
	public City() {
	}

	public Gps getGps() {
		return gps;
	}

	public void setGps(Gps gps) {
		this.gps = gps;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getPopulation() {
		return population;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}
	
}
